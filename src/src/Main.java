package src;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    private static JFrame frame;
    private static JPanel panel;
    private static GameManager gm;
    public static void main(String[] args) {
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
        panel.setBounds(0,0,frame.getHeight(),frame.getWidth());
        frame.add(panel);
        frame.setVisible(true);
    }
}