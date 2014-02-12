package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import edu.wpi.first.wpilibj.Gyro;

public class GyroSystem implements RobotSystem {

    private Gyro gyro;
    
    public void init(Environment e) {
        gyro = new Gyro(1);
    }
    
    public double getHeading() {
        return gyro.getAngle();
    }
    
    public void reset() {
        gyro.reset();
    }

    public void destroy() {
        
    }
    
}
