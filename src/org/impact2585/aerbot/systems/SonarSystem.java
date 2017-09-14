package org.impact2585.aerbot.systems;

import org.impact2585.aerbot.Environment;
import org.impact2585.aerbot.RobotMap;

import edu.wpi.first.wpilibj.AnalogChannel;

// multiply voltage by 512 * 5 to get it in inches
public class SonarSystem implements RobotSystem {

    private AnalogChannel sonar;
    
    /* (non-Javadoc)
     * @see org.impact2585.aerbot.systems.RobotSystem#init(org.impact2585.aerbot.Environment)
     */
    public void init(Environment environment) {
       sonar = new AnalogChannel(RobotMap.SONAR_CHANNEL);
    }

    /* (non-Javadoc)
     * @see org.impact2585.aerbot.systems.RobotSystem#destroy()
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
