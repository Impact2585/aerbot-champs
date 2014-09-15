package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;

public class AccelerometerSystem implements RobotSystem {

  private ADXL345_I2C accel;
  private double speed;
  private I2C i2c;
  private Timer timer;

  /* (non-Javadoc)
   * @see com.bellaire.aerbot.systems.RobotSystem#init(com.bellaire.aerbot.Environment)
   */
  public void init(Environment environment) {
    accel = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k4G);
    i2c = new I2C(DigitalModule.getInstance(1), 1);
    i2c.setCompatabilityMode(true);
    timer = new Timer();
    timer.start();
  }

  /**
   * @return x acceleration of the robot in g's
   */
  public double getAccelerationX() {
    return accel.getAcceleration(ADXL345_I2C.Axes.kX);
  }

  /**
   * @return y acceleration of the robot in g's
   */
  public double getAccelerationY() {
    return accel.getAcceleration(ADXL345_I2C.Axes.kY);
  }

  /**
   * @return z acceleration of the robot in g's
   */
  public double getAccelerationZ() {
    return accel.getAcceleration(ADXL345_I2C.Axes.kZ);
  }
  
    
  protected Timer getTimer(){
    return this.timer;
  }
  
  protected Timer setTimer(Timer timer){
    return this.timer = timer;
  }

  /**
   * The speed is calculated by finding the integral of the x acceleration
   * @return speed of robot in m/s
   */
  public synchronized double getSpeed() {
    if(timer.get() > .75)
      timer.reset();
    speed += getAccelerationX() * 9.8 * timer.get();
    if(Math.abs(getAccelerationX()) < 0.15 && Math.abs(speed) < 0.5)
	  speed = 0;//reset value if relatively no acceleration and no speed
    timer.reset();
    return speed;
  }

  /* (non-Javadoc)
   * @see com.bellaire.aerbot.systems.RobotSystem#destroy()
   */
  public void destroy() {
    accel.free();
    i2c.free();
  }

}
