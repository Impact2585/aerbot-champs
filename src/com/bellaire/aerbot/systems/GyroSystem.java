package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import edu.wpi.first.wpilibj.Gyro;

public class GyroSystem implements RobotSystem {

  private Gyro gyro;

  public void init(Environment e) {
    gyro = new Gyro(2);
  }

  public double getHeading() {
    double output = gyro.getAngle();

    while (output < 0) {
      output += 360;
    }

    return output % 360;
  }
  
  public double getAngle(){
	  return gyro.getAngle();
  }

  public void reset() {
    gyro.reset();
  }

  public void destroy() {
    gyro.free();
  }

}