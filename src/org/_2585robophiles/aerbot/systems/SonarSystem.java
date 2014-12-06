package org._2585robophiles.aerbot.systems;

import org._2585robophiles.aerbot.Environment;

import edu.wpi.first.wpilibj.AnalogChannel;

// multiply voltage by 512 * 5 to get it in inches
public class SonarSystem implements RobotSystem {

    private AnalogChannel sonar;
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.systems.RobotSystem#init(org._2585robophiles.aerbot.Environment)
     */
    public void init(Environment environment) {
       sonar = new AnalogChannel(1);
    }

    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.systems.RobotSystem#destroy()
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
