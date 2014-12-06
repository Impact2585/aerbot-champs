package org._2585robophiles.aerbot;

import org._2585robophiles.aerbot.input.InputMethod;
import org._2585robophiles.aerbot.input.PartnerXboxInput;
import org._2585robophiles.aerbot.systems.AccelerometerSystem;
import org._2585robophiles.aerbot.systems.GyroSystem;
import org._2585robophiles.aerbot.systems.IntakeSystem;
import org._2585robophiles.aerbot.systems.ShooterSystem;
import org._2585robophiles.aerbot.systems.WheelSystem;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.RobotBase;

public class Environment {
    
    private RobotBase robot;
    
    private InputMethod input;
    
    private WheelSystem wheels;
    private ShooterSystem shooter;
    private IntakeSystem intake;
    
    private GyroSystem gyro;
    private AccelerometerSystem accel;

	private Compressor compressor;
    
    /**
     * initializes nothing
     */
    public Environment(){
    	
    }

    /**
     * Initializes systems
     * @param robot the RobotBase to set
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
        
        this.compressor = new Compressor(2, 1);
        this.compressor.start();
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
	 * @return the compressor
	 */
	public Compressor getCompressor() {
		return compressor;
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
    
    /**
     * Destroy all the things!
     */
    public void destroy(){
    	wheels.destroy();
    	shooter.destroy();
    	intake.destroy();
    	gyro.destroy();
    	accel.destroy();
    	compressor.free();
    }
    
}
