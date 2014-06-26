package com.bellaire.aerbot.custom;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.communication.UsageReporting;

public class RobotDrive3 extends RobotDrive {

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

	public void arcadeDrive(double moveValue, double rotateValue) {
        this.arcadeDrive(moveValue, rotateValue, false);
    }
    
    public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
        if(!kArcadeStandard_Reported){
            UsageReporting.report(UsageReporting.kResourceType_RobotDrive, getNumMotors(), UsageReporting.kRobotDrive_ArcadeStandard);
            kArcadeStandard_Reported = true;
        }

        double leftMotorSpeed;
        double rightMotorSpeed;

        moveValue = limit(moveValue);
        rotateValue = limit(rotateValue);

        if (squaredInputs) {
            // square the inputs (while preserving the sign) to increase fine control while permitting full power
            if (moveValue >= 0.0) {
                moveValue = (moveValue * moveValue);
            } else {
                moveValue = -(moveValue * moveValue);
            }
        }
        
        // cube the rotate values
        rotateValue = (rotateValue * rotateValue * rotateValue);

        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }

        setLeftRightMotorOutputs(leftMotorSpeed, rightMotorSpeed);
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
