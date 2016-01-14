package org.impact2585.aerbot.systems;

import org.impact2585.aerbot.Environment;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Timer;

public class GyroSystem implements RobotSystem {

  private Gyro gyro;
  private Timer timer;
  private double lastAngle;
  private double error;

  /* (non-Javadoc)
   * @see org.impact2585.aerbot.systems.RobotSystem#init(org.impact2585.aerbot.Environment)
   */
  public void init(Environment e) {
    gyro = new Gyro(2);
    timer = new Timer();
    timer.start();
  }

  /**
   * @return heading of robot in degrees value will be from 0 to 360
   */
  public synchronized double getHeading() {
    double output = getAngle();

    //make sure output is not negative
    while (output < 0) {
      output += 360;
    }

    return output % 360;
  }
  
  /**
   * Filters drift less then 1 degree per second
   * @return angle of robot with gyro.getAngle()\
   */
  public synchronized double getAngle(){
    double output = gyro.getAngle();
	  
    //prevent drift by ignoring change less than 1 degree in a second
    if(timer.get() > 1){
      if(Math.abs(output - lastAngle) < 1)
        error += output - lastAngle;
      timer.reset();
      lastAngle = output;
    }
    output -= error;

    return output;
  }

  /**
   * resets gyro
   */
  public void reset() {
    gyro.reset();
  }

  /* (non-Javadoc)
   * @see org.impact2585.aerbot.systems.RobotSystem#destroy()
   */
  public void destroy() {
    gyro.free();
  }

}