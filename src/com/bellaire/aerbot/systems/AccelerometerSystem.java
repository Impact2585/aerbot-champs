/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import edu.wpi.first.wpilibj.ADXL345_I2C;

public class AccelerometerSystem implements RobotSystem {

    private ADXL345_I2C accel;
    
    public void init(Environment e) {
       accel = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k2G);
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

    public void destroy() {
        
    }
    
}
