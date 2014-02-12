package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;

public interface RobotSystem {
    
    public void init(Environment e);
    
    public void destroy();
    
}
