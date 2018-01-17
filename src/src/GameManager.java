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

public class GameManager {
    private boolean turn,isConnected;
    private Disk[][] slots=new Disk[7][6];
    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;
    private Thread inT,outT;
    private int mouseX,mouseY;
    private boolean mouseDown;
    public GameManager()
    {
        isConnected=false;
        slots[0][0]=new Disk(Color.BLUE,5);
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
                        try {//Disk:3
                            splitCommand=(in.readLine()).split(":");
                        } catch (IOException ex) {
                            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(splitCommand[0].equals("Disk"))
                        {
                            int xAxis=Integer.parseInt(splitCommand[1]);
                            for(int i=0;i<6;i++)
                            {
                                if(slots[xAxis][i]==null)
                                {
                                    slots[xAxis][i]=new Disk(Color.BLUE,5);//Change to the network disk
                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }
    }
}
