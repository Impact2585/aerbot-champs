package com.bellaire.aerbot.controllers;

import com.bellaire.aerbot.Environment;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotionTracker implements  Runnable {

    private Environment env;
    
    private long previousTime = 0, currentTime = 0;
    private double currentSpeed = 0, currentHeading = 0;
    
    public MotionTracker(Environment env) {
        this.env = env;
    }
    
    public void reset() {
        currentSpeed = currentHeading = 0;
    }
    
    public double getSpeed() {
        return currentSpeed;
    }
    
    public double getHeading() {
        return currentHeading;
    }

    public void run() {
        previousTime = System.currentTimeMillis();
        while(true) {
            try {
                currentTime = System.currentTimeMillis();
                //currentSpeed = (currentTime - previousTime) * env.getAccelerometerSystem().getAccelerationX();
                //currentHeading = (currentTime - previousTime) * env.getGyroSystem().getHeading();
                
                previousTime = currentTime;
                
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
