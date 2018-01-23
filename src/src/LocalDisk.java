package src;

import java.awt.Color;

public class LocalDisk extends Disk{//Child of disk and handles local side

    public LocalDisk(Color co, int xloc, int targety) {
        super(co, xloc, targety);
    }

    @Override
    void diskFall() {
        if(y<ty)y++;
    }
    
    
    
}
