    package src;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class GameManager {
    private boolean turn,isConnected,inGame;
    private Disk[][] slots=new Disk[7][6];
    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;
    private Thread inT,outT;
    private int mouseX,mouseY,gameNumber;
    private boolean mouseDown;
    private ImageIcon board;
    public GameManager()
    {
        isConnected=false;
        inGame=false;
        board=new ImageIcon("src/src/pictures/Connect4Board.png");
        //slots[0][0]=new Disk(Color.BLUE,5);
    }
    public void draw(Graphics g)
    {
        for(int x=0;x<7;x++)
        {
            for(int y=0;y<6;y++)
            {
                if(slots[x][y]!=null)
                {
                    slots[x][y].draw(g);//Drawing all of the slots only if they are not null
                }
            }
        }
        //g.fillOval(19,170,71,71);//next one over is 109 next one down is 250
        g.drawImage(board.getImage(), 5, 5, null);
    }
    public void mouseInformation(boolean md,int x,int y)
    {
        mouseDown=md;
        mouseX=x;
        mouseY=y;
    }
    public void connect(String ip)
    {
        try {
            socket=new Socket(ip,9876);//Connecting to a server with the ip
            isConnected=true;//setting connected to true for use in the threads
        } catch (IOException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(isConnected)//Only if the connection was successfull
        {
            try {
                in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out=new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            inT=new Thread(new Runnable() {
                @Override
                public void run() {
                    String[] splitCommand=new String[2];
                    while(isConnected)
                    {
                        try {
                            splitCommand=(in.readLine()).split(":");
                        } catch (IOException ex) {
                            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(splitCommand[0].equals("Game Number"))
                        {
                            gameNumber=Integer.parseInt(splitCommand[1]);
                        }
                        else if(splitCommand[0].equals("Game Start"))
                        {
                            inGame=true;
                            turn=Boolean.parseBoolean(splitCommand[1]);
                        }
                        else if(splitCommand[0].equals("Turn"))
                        {
                            turn=Boolean.parseBoolean(splitCommand[1]);
                        }
                        else if(splitCommand[0].equals("Game Finished"))
                        {
                            inGame=false;
                        }
                        else if(splitCommand[0].equals("Piece"))
                        {
                            String[] splitData=splitCommand[1].split(",");//Due to the network commands you have to split the incoming data again for the 2 pieces of data
                            playPiece(Integer.parseInt(splitData[0]),Integer.parseInt(splitData[1]));
                        }
                    }
                }
            });
            inT.start();
        }
        
    }
    private void playPiece(int x,int player)//-1 for other player 1 for local player x is the xcoord on the board
    {
        //Actual x coord: c=(109-19)*x+(109-19)
        //Actual y coord: c=(250-170)*y+(250-170)
        for(int i=0;i<6;i++)
        {
            if(slots[x][i]==null)
            {
                if(player==1)
                {
                    int c=(109-19)*x+19;
                    int target=(250-170)*i+70;
                    target*=-1;
                    target+=480;
                    slots[x][i]=new LocalDisk(Color.RED,c,target);
                    return;
                }
                else if(player==-1)
                {
                    //slots[x][i]=new NetworkDisk();
                    return;
                }
            }
        }
    }
}
