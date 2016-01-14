package org.impact2585.aerbot.custom;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class RobotDrive3 extends RobotDrive {
	
	public static final double ROTATION_CORRECTION = 0.3;

    /**
     * Constructor for RobotDrive3 with 4 motors specified as SpeedController objects.
     * Speed controller input version of RobotDrive (see previous comments).
     * @param rearLeftMotor The back left SpeedController object used to drive the robot.
     * @param frontLeftMotor The front left SpeedController object used to drive the robot
     * @param rearRightMotor The back right SpeedController object used to drive the robot.
     * @param frontRightMotor The front right SpeedController object used to drive the robot.
     */
    public RobotDrive3(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
            SpeedController frontRightMotor, SpeedController rearRightMotor) throws NullPointerException {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	}

    /**
     * Constructor for RobotDrive3 with 2 motors specified as SpeedController objects.
     * The SpeedController version of the constructor enables programs to use the RobotDrive classes with
     * subclasses of the SpeedController objects, for example, versions with ramping or reshaping of
     * the curve to suit motor bias or dead-band elimination.
     * @param leftMotor The left SpeedController object used to drive the robot.
     * @param rightMotor the right SpeedController object used to drive the robot.
     */
    public RobotDrive3(SpeedController leftMotor, SpeedController rightMotor) throws NullPointerException {
		super(leftMotor, rightMotor);
	}

	/**
     * @param leftMotorChannel port number of left motor
     * @param rightMotorChannel port number of right motor
     */
    public RobotDrive3(int leftMotorChannel, int rightMotorChannel) {
        super(leftMotorChannel, rightMotorChannel);
    }
    
	/**
	 * @param frontLeftMotor port number of the front left motor
	 * @param rearLeftMotor port number of the rear left motor
	 * @param frontRightMotor port number of the front right motor
	 * @param rearRightMotor port number of the rear right motor
	 */
	public RobotDrive3(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor)  {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor) ;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.RobotDrive#arcadeDrive(double, double)
	 */
	public void arcadeDrive(double moveValue, double rotateValue) {
        this.arcadeDrive(moveValue, rotateValue, false);
    }
    
    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.RobotDrive#arcadeDrive(double, double, boolean)
     */
    public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
    	
        if (squaredInputs) {
            // square the inputs (while preserving the sign) to increase fine control while permitting full power
            if (moveValue >= 0.0) {
                moveValue = (moveValue * moveValue);
            } else {
                moveValue = -(moveValue * moveValue);
            }
        }
        
        rotateValue *= rotateValue * rotateValue;//cubed rotation
        rotateValue -= ROTATION_CORRECTION * moveValue;// ghetto straight driving
        if(Math.abs(rotateValue) > 1)
        	rotateValue = rotateValue < 1 ? -1 : 1;// make sure it stays in range of -1 to 1
        
    	super.arcadeDrive(moveValue, rotateValue, false);
    }
    
    
    /**
     * Move the robot with mecanum wheels. Not currently used.
     * @param yMovement y movement value from -1 to 1
     * @param xMovement x movement value from -1 to 1
     * @param rotation rotation value from -1 to 1
     */
    public void mecanumDrive(double yMovement, double xMovement, double rotation) {
    	double frontLeftMotorSpeed, rearLeftMotorSpeed, frontRightMotorSpeed, rearRightMotorSpeed;

    	rotation = rotation * rotation * rotation;//cubed rotation

    	//sideways movement to the left for positive xMovement and vise versa
    	frontLeftMotorSpeed = rearRightMotorSpeed = -xMovement;
    	rearLeftMotorSpeed = frontRightMotorSpeed = xMovement;

    	//forward and backward
    	frontLeftMotorSpeed += yMovement;
    	frontRightMotorSpeed += yMovement;
    	rearLeftMotorSpeed += yMovement;
    	rearRightMotorSpeed += yMovement;

    	//positive rotation results in a turn to the left
    	frontRightMotorSpeed += rotation;
    	rearRightMotorSpeed += rotation;
    	frontLeftMotorSpeed -= rotation;
    	rearLeftMotorSpeed -= rotation;

    	//make sure values aren't above 1 or below -1
    	frontLeftMotorSpeed = limit(frontLeftMotorSpeed);
    	frontRightMotorSpeed = limit(frontRightMotorSpeed);
    	rearLeftMotorSpeed = limit(rearLeftMotorSpeed);
    	rearRightMotorSpeed = limit(rearRightMotorSpeed);

    	m_frontLeftMotor.set(frontLeftMotorSpeed);
    	m_frontRightMotor.set(frontRightMotorSpeed);
    	m_rearLeftMotor.set(rearLeftMotorSpeed);
    	m_rearRightMotor.set(rearRightMotorSpeed);
    }
    
}
