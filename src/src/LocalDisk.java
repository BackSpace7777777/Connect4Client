package src;

import java.awt.Color;

public class LocalDisk extends Disk{

    public LocalDisk(Color co, int xloc) {
        super(co, xloc);
    }

    @Override
    void diskFall() {
        if(y<ty)y++;
        
    }
    
}
