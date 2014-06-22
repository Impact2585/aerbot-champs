package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Timer;

public class GyroSystem implements RobotSystem {

  private Gyro gyro;
  private Timer timer;
  private double lastAngle;
  private double error;

  public void init(Environment e) {
    gyro = new Gyro(2);
    timer = new Timer();
    timer.start();
  }

  public synchronized double getHeading() {
    double output = getAngle();

    //make sure output is not negative
    while (output < 0) {
      output += 360;
    }

    return output % 360;
  }
  
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

  public void reset() {
    gyro.reset();
  }

  public void destroy() {
    gyro.free();
  }

}