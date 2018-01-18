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
    
    protected int disksize=30;
    protected int disksbelow;
    
    //array positions saved
    //target coordinate
    //two more classes that extend disk
    //local and network disk
    
    //disk requires the player-chosen colour, and an x-loc (column where it will be inserted)
    public Disk(Color co, int xloc)
    {
        //y will be 0 by default since it will start at the top and fall down
        y = 0;
        x = xloc;
        c = co;

    }

    //disk will fall to into place when inserted in a column
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
