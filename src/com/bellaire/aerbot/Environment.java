package com.bellaire.aerbot;

import com.bellaire.aerbot.controllers.MotionTracker;
import com.bellaire.aerbot.input.InputMethod;
import com.bellaire.aerbot.input.Xbox360Input;
import com.bellaire.aerbot.systems.ShooterSystem;
import com.bellaire.aerbot.systems.SonarSystem;
import com.bellaire.aerbot.systems.WheelSystem;
import edu.wpi.first.wpilibj.RobotBase;

public class Environment {
    
    private RobotBase robot;
    
    private InputMethod input;
    
    private WheelSystem wheels;
    private SonarSystem sonar;
    private ShooterSystem shooter;
    
    //private GyroSystem gyro;
    //private AccelerometerSystem accel;
    
    private Thread motionThread;
    private MotionTracker motion;

    public Environment(RobotBase robot) {
        this.robot = robot;
        
        this.input = new Xbox360Input();
        
        this.sonar = new SonarSystem();
        this.sonar.init(this);
        
        this.wheels = new WheelSystem();
        this.wheels.init(this);
        
        this.shooter = new ShooterSystem();
        this.shooter.init(this);
    }
    
    public InputMethod getInput() {
        return input;
    }
    
    public WheelSystem getWheelSystem() {
        return wheels;
    }
    
    public SonarSystem getSonarSystem() {
        return sonar;
    }
    
    public ShooterSystem getShooterSystem() {
        return shooter;
    }
    
    /*public GyroSystem getGyroSystem() {
        return gyro;
    }*/
/*
    public AccelerometerSystem getAccelerometerSystem() {
        return accel;
    }
  */  
    public MotionTracker getMotionTracker() {
        return motion;
    }
    
    public boolean isAutonomous() {
        return robot.isAutonomous();
    }
    
    public boolean isOperatorControl() {
        return robot.isOperatorControl();
    }
    
}
