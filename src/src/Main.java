package src;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    private static JFrame frame;
    private static JPanel panel;
    private static GameManager gm;
    private static int x,y;
    private static boolean md;
    public static void main(String[] args) {
        md=false;
        x=0;
        y=0;
        frame=new JFrame("Connect 4");//Setting up the frame
        frame.setDefaultCloseOperation(3);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(640,640);
        gm=new GameManager();
        panel=new JPanel()
        {
            @Override
            public void paintComponent(Graphics g)//Drawing code here
            {
                super.paintComponent(g);
                g.clearRect(0, 0, panel.getWidth(), panel.getHeight());
                gm.draw(g);
                repaint();
            }
        };
        panel.addMouseMotionListener(new MouseMotionListener() {
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
        panel.setBounds(0,0,frame.getHeight(),frame.getWidth());
        frame.add(panel);
        frame.setVisible(true);
    }
}