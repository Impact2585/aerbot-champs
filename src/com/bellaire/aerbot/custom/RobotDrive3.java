package com.bellaire.aerbot.custom;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.communication.UsageReporting;

public class RobotDrive3 extends RobotDrive {

    public RobotDrive3(int leftMotorChannel, int rightMotorChannel) {
        super(leftMotorChannel, rightMotorChannel);
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
    
}
