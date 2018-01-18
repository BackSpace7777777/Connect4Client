package src;

import java.awt.Color;

public class LocalDisk extends Disk{

    public LocalDisk(Color co, int xloc, int targety) {
        super(co, xloc, targety);
    }

    @Override
    void diskFall() {
        if(y<ty)y++;
    }
    
    
    
}
