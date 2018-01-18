package src;

import java.awt.Color;

public class NetworkDisk extends Disk{
    public NetworkDisk(Color in,int xloc,int ty)
    {
        super(in, xloc,ty);
    }
    @Override
    void diskFall() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
