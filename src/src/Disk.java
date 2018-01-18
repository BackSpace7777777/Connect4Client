package src;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Disk {

    //custom colour for the player disks
    protected Color c;
    
    //coordinates of disk at the moment, as it falls
    protected int x, y;
    
    //final target coordinates of the disk (x won't change so there's no point in a seperate "tx")
    protected int ty;
    
    //disk requires the player-chosen colour, and an x-loc (column where it will be inserted),
    //as well as a target y: we need to know how far it will fall, whether or not there are pieces already in the column
    public Disk(Color co, int xloc, int targety)
    {
        //y will be 0 by default since it will start at the top and fall down
        y = 0;
        x = xloc;
        c = co;
        ty = targety;
        
    }

    //disk will fall to into place when inserted in a column
    abstract void diskFall();
    
    //draws the disk and the falling "animation"
    public void draw(Graphics g)
    {
        g.setColor(c);
        diskFall();
        g.fillOval(x, y, 71, 71);
    }
    
    public Color getColor(){
        return c;
    }
    
}
