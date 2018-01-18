package src;

import java.awt.Color;

public class NetworkDisk extends Disk{
    public NetworkDisk(Color co, int xloc, int targety)
    {
        super(co, xloc, targety);
    }
    @Override
    void diskFall() {
        if(y<ty)y++;
    }
    
}
