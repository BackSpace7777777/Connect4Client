package src;

import java.awt.Color;

public class LocalDisk extends Disk{

    public LocalDisk(Color co, int xloc, int ty) {
        super(co, xloc, ty);
    }

    @Override
    void diskFall() {
        if(y<ty)y++;
        
    }
    
}
