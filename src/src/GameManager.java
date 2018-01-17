package src;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameManager {
    private boolean turn;
    private Disk[][] slots=new Disk[7][6];
    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;
    private Thread inT,outT;
    public GameManager()
    {
        
    }
    public void draw(Graphics g)
    {
        
    }
    public void connect(boolean localHost,String ip)
    {
        if(localHost)
        {
            try {
                socket=new Socket("localhost",9876);
            } catch (IOException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else 
        {
            try
            {
                socket=new Socket(ip,9876);
            } catch (IOException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(socket!=null)
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
                    
                }
            });
        }
    }
}
