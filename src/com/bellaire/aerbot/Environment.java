package com.bellaire.aerbot;

import com.bellaire.aerbot.input.InputMethod;
import com.bellaire.aerbot.input.PartnerXboxInput;
import com.bellaire.aerbot.systems.AccelerometerSystem;
import com.bellaire.aerbot.systems.GyroSystem;
import com.bellaire.aerbot.systems.IntakeSystem;
import com.bellaire.aerbot.systems.ShooterSystem;
import com.bellaire.aerbot.systems.WheelSystem;

import edu.wpi.first.wpilibj.RobotBase;

public class Environment {
    
    private RobotBase robot;
    
    private InputMethod input;
    
    private WheelSystem wheels;
    private ShooterSystem shooter;
    private IntakeSystem intake;
    
    private GyroSystem gyro;
    private AccelerometerSystem accel;
    
    /**
     * initializes nothing
     */
    public Environment(){
    	
    }

    /**
     * Initializes systems
     * @param robot
     */
    public Environment(RobotBase robot) {
        this.robot = robot;
        
        this.input = new PartnerXboxInput();
        
        this.accel = new AccelerometerSystem();
        this.accel.init(this);
        
        this.gyro = new GyroSystem();
        this.gyro.init(this);
        
        this.wheels = new WheelSystem();
        this.wheels.init(this);
        
        this.shooter = new ShooterSystem();
        this.shooter.init(this);
        
        this.intake = new IntakeSystem();
        this.intake.init(this);
    }
    
    /**
     * @return input
     */
    public InputMethod getInput() {
        return input;
    }
    
    /**
     * @return wheels
     */
    public WheelSystem getWheelSystem() {
        return wheels;
    }
    
    /**
     * @return shooter
     */
    public ShooterSystem getShooterSystem() {
        return shooter;
    }
    
    /**
     * @return intake
     */
    public IntakeSystem getIntakeSystem() {
        return intake;
    }
    
    /**
     * @return gyro
     */
    public GyroSystem getGyroSystem() {
        return gyro;
    }

    /**
     * @return accelerometer
     */
    public AccelerometerSystem getAccelerometerSystem() {
        return accel;
    }
    
    /**
     * @return if the robot is in auton mode
     */
    public boolean isAutonomous() {
        return robot.isAutonomous();
    }
    
    /**
     * @return if robot is in teleop mode
     */
    public boolean isOperatorControl() {
        return robot.isOperatorControl();
    }
    
}
