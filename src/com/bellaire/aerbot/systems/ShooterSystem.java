package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import com.bellaire.aerbot.input.InputMethod;
import edu.wpi.first.wpilibj.Jaguar;

public class ShooterSystem implements RobotSystem {

    private Jaguar jaguar;
    
    public void init(Environment e) {
        jaguar = new Jaguar(10);
    }

    public void destroy() {
        
    }
    
    public void shoot(InputMethod input) {
        if(input.shoot()) {
            jaguar.set(-0.025);
        } else if(input.antishoot()) {
            jaguar.set(0.025);
        } else {
            jaguar.set(0);
        }
    }
    
}
