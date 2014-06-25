package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import edu.wpi.first.wpilibj.AnalogChannel;

// multiply voltage by 512 * 5 to get it in inches
public class SonarSystem implements RobotSystem {

    private AnalogChannel sonar;
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.systems.RobotSystem#init(com.bellaire.aerbot.Environment)
     */
    public void init(Environment environment) {
       sonar = new AnalogChannel(1);
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.systems.RobotSystem#destroy()
     */
    public void destroy() {
        
    }
    
    /**
     * @return distance from robot to an object
     */
    public double getDistance() {
        return sonar.getVoltage() * (512d / 5d);
    }
    
}
