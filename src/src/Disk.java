package src;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Disk {

    //custom colour for the player disks
    protected Color c;
    
    //coordinates of disk at the moment, as it falls
    protected int x, y;
    //final target coordinates of the disk
    protected int tx, ty;
    
    
    //array positions saved
    //target coordinate
    //two more classes that extend disk
    //local and network disk
    
    public Disk(Color co, int xloc)
    {
        y = 0;
        x = xloc;
        c = co;

    }

    abstract void diskFall();
    
    public void draw(Graphics g)
    {
        g.setColor(c);
        diskFall();
        g.fillOval(x, y, 30, 30);
    }
    
    public Color getColor(){
        return c;
    }
    
}
