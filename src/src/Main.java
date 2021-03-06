package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {
    private static JFrame frame;
    private static JPanel panel;
    private static GameManager gm;
    private static int x,y;//Mouse x and mouse y
    private static boolean md;//Mouse Down
    public static void main(String[] args) {
        md=false;
        x=0;
        y=0;
        frame=new JFrame("Connect 4");//Setting up the frame
        frame.setDefaultCloseOperation(3);//Close on press of the top right x button
        frame.setResizable(false);
        frame.setLayout(null);//Allows you to use set bounds in the coord system
        frame.setSize(650,519);
        gm=new GameManager();//Make a new Game Manager of the class GameManager.java
        panel=new JPanel()
        {
            @Override
            public void paintComponent(Graphics g)//Drawing code here
            {
                super.paintComponent(g);
                g.clearRect(0, 0, panel.getWidth(), panel.getHeight());
                gm.draw(g);
                g.setColor(Color.BLACK);
                g.fillRect(0,0,panel.getWidth(),5);
                g.fillRect(0,0,5,panel.getHeight());
                g.fillRect(640,0,5,panel.getHeight());
                g.fillRect(0,485,panel.getWidth(),10);
                repaint();
            }
        };
        panel.addMouseMotionListener(new MouseMotionListener() {//Gets mouse input
            @Override
            public void mouseDragged(MouseEvent me) {
                x=me.getX();
                y=me.getY();
                gm.mouseInformation(md, x, y);
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                x=me.getX();
                y=me.getY();
                gm.mouseInformation(md, x, y);
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                md=true;
                gm.mouseInformation(md, x, y);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                md=false;
                gm.mouseInformation(md, x, y);
            }
        });
        panel.setBounds(0,0,frame.getWidth()+10,frame.getHeight()+10);//Same as setting the size but as an object in the frame, which is we set it to a null layout
        frame.add(panel);
        frame.setVisible(true);
    }
    public static void messagePopup(String in)
    {
        JOptionPane.showMessageDialog(null,in);
    }
    public static void turnTitle(String in)
    {
        frame.setTitle("Connect 4:"+in);//Setting the title
    }
}