package com.bellaire.aerbot.input;

import edu.wpi.first.wpilibj.Joystick;

/*

1: A
2: B
3: X
4: Y
5: Left Bumper
6: Right Bumper
7: Back
8: Start
9: Left Joystick
10: Right Joystick

*/

public class XboxInput implements InputMethod {

    private Joystick controller;
    
    public XboxInput() {
        controller = new Joystick(1);
    }
    
    public boolean intakeLift() {
        return false;
    }
    
    public double getLeftX() {
        return controller.getRawAxis(1);
    }

    public double getRightX() {
        return controller.getRawAxis(4);
    }
    
    public double getLeftY() {
        return controller.getRawAxis(2);
    }

    public double getRightY() {
        return controller.getRawAxis(5);
    }
    
    public boolean shift() {
        return controller.getRawButton(2);
    }
    
    public boolean catchBall() {
        return controller.getRawButton(8);//catch ball is start button
    }
    
    public boolean shoot() {
        return controller.getRawButton(1);
    }
    
    public boolean shooterLift() {
        return controller.getRawButton(4);
    }
    
    public boolean intakeToggle() {
        return controller.getRawButton(3);
    }
    
    public boolean directionToggle() {
        return false;
    }
    
    public int intake() {
        if(controller.getRawButton(5)) {
            return 1;
        } else if(controller.getRawButton(6)) {
            return 2;
        } else {
            return 0;
        }
    }

	public boolean straightDrive() {
		return false;
	}
}
