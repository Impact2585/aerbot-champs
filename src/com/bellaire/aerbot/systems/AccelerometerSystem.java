/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

  public void init(Environment e) {
    accel = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k4G);
    i2c = new I2C(DigitalModule.getInstance(1), 1);
    i2c.setCompatabilityMode(true);
    timer = new Timer();
    timer.start();
  }

  public double getAccelerationX() {
    return accel.getAcceleration(ADXL345_I2C.Axes.kX);
  }

  public double getAccelerationY() {
    return accel.getAcceleration(ADXL345_I2C.Axes.kY);
  }

  public double getAccelerationZ() {
    return accel.getAcceleration(ADXL345_I2C.Axes.kZ);
  }

  public double getSpeed() {
	if(timer.get() > .75)
	  timer.reset();
    speed += getAccelerationX() * 9.8 * timer.get();
    if(Math.abs(getAccelerationX()) < 0.1 && Math.abs(speed) < 0.25)
      speed = 0;//reset accelerometer if relatively no acceleration and no speed
    timer.reset();
    return speed;
  }

  public void destroy() {
    accel.free();
    i2c.free();
  }

}
