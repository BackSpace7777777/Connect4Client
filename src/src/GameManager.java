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
    private Disk[][] slots=new Disk[7][6];//Array with disks for local manipulation
    private Socket socket;//Used to connect to the server
    private BufferedReader in;//Gets the input from server
    private DataOutputStream out;//Sends data to the server
    private Thread inT;//This thread handles incoming data from the server
    private int mouseX,mouseY,gameNumber;//Keeps track of the mouse x and y and what game number the server has assigned
    private boolean mouseDown;//Keeps track of the left mouse button
    private ImageIcon board;//Picture of the board
    private final String connectIP="localhost";//Ip to connect to
    public GameManager()
            //Loads data then connects to the server
    {
        isConnected=false;
        inGame=false;
        board=new ImageIcon(this.getClass().getResource("pictures/Connect4Board.png"));
        //slots[0][0]=new Disk(Color.BLUE,5);
        connect(connectIP);
    }
    public void draw(Graphics g)
    {
        if(inGame)
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
            //System.out.println(mouseX+","+mouseY);//20 to 90, 90 to 110
//            if(mouseX>20&&mouseX<90&&mouseDown&&turn)
//            {
//                turn=false;
//                playServer(0);
//            }
            for(int i=0;i<7;i++)//Gets the mouse input and asks the server to play a peice at a location relitive to the mouse
            {
                if(mouseX>(20+90*i)&&mouseX<(90+90*i)&&mouseDown&&turn)
                {
                    System.out.println(i);
                    turn=false;
                    playServer(i);
                    break;
                }
            }
        }
    }
    public void mouseInformation(boolean md,int x,int y)//Gets mouse information from class Main
    {
        mouseDown=md;
        mouseX=x;
        mouseY=y;
    }
    public void connect(String ip)//Connect to server code
    {
        try {
            socket=new Socket(ip,9876);//Connecting to a server with the ip
            isConnected=true;//setting connected to true for use in the threads
            Main.turnTitle("Waiting for game");
        } catch (IOException ex) {
            Main.messagePopup("Could not connect: " + ex);
        }
        if(isConnected)//Only if the connection was successfull
        {
            try {//Gets the in and out from the server
                in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out=new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            inT=new Thread(new Runnable() {//Sets up incoming thread
                @Override
                public void run() {
                    String[] splitCommand=new String[2];
                    while(isConnected)
                    {
                        try {
                            splitCommand=(in.readLine()).split(":");//Splits information into 2 strings, position 0 being the command and 1 being the data
                            System.out.println(splitCommand[0]+":"+splitCommand[1]);
                        } catch (IOException ex) {
                            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //The following if statements handle the commands the server gives to the client
                        if(splitCommand[0].equals("Game Number"))
                        {
                            gameNumber=Integer.parseInt(splitCommand[1]);
                        }
                        else if(splitCommand[0].equals("Game Start"))
                        {
                            inGame=true;
                            turn=Boolean.parseBoolean(splitCommand[1]);
                            if(turn)
                            {
                                Main.messagePopup("New Game: it is your turn");
                                Main.turnTitle("Your turn");
                            }
                            else 
                            {
                                Main.messagePopup("New Game: it is the other players turn");
                                Main.turnTitle("Other player's turn");
                            }
                        }
                        else if(splitCommand[0].equals("Turn"))
                        {
                            turn=Boolean.parseBoolean(splitCommand[1]);
                            if(turn)Main.turnTitle("Your turn");
                            else Main.turnTitle("Other player's turn");
                        }
                        else if(splitCommand[0].equals("Game Finished"))
                        {
                            inGame=false;
                            endGame(Boolean.parseBoolean(splitCommand[1]));
                            Main.turnTitle("Waiting for game");
                        }
                        else if(splitCommand[0].equals("Piece"))
                        {
                            String[] splitData=splitCommand[1].split(",");//Due to the network commands you have to split the incoming data again for the 2 pieces of data
                            playPiece(Integer.parseInt(splitData[0]),Integer.parseInt(splitData[1]));
                        }
                        else if(splitCommand[0].equals("Dropped"))
                        {
                            disconnect();
                        }
                    }
                }
            });
            inT.start();
        }
        
    }
    private void disconnect()//Method to disconnect from the server and reconnect
    {
        this.disconnect(1);
        connect(connectIP);
    }
    private void endGame(boolean in)//Resets everything and gives a prompt as to who won or lost
    {
        if(in)
        {
            System.out.println("You win");
            Main.messagePopup("You win");
        }
        else 
        {
            System.out.println("You lose");
            Main.messagePopup("You lose");
        }
        for(int x=0;x<7;x++)
            for(int y=0;y<6;y++)
                slots[x][y]=null;
    }
    private void playServer(int x)//Sends the clients play to the server
    {
        try {
            out.writeBytes("Move:"+x+","+gameNumber+"\r");
        } catch (IOException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void disconnect(int reason)//Disconnect with a reason as to why there was a disconnect
    {
        try {
            out.writeBytes("Disconnect:"+reason+"\r");
        } catch (IOException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
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
                if(player==1)//Local player
                {
                    int c=(109-19)*x+19;
                    int target=(250-170)*i+70;
                    target*=-1;
                    target+=480;
                    slots[x][i]=new LocalDisk(Color.RED,c,target);
                    System.out.println("Local Player Moved");
                    return;
                }
                else if(player==-1)//Player over the network
                {
                    int c=(109-19)*x+19;
                    int target=(250-170)*i+70;
                    target*=-1;
                    target+=480;
                    slots[x][i]=new NetworkDisk(Color.BLUE,c,target);
                    System.out.println("Opposition player moved");
                    return;
                }
            }
        }
    }
}
