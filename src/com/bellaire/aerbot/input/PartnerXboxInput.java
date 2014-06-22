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

public class PartnerXboxInput implements InputMethod {

    private Joystick driver, shooter;
    
    public PartnerXboxInput() {
        driver = new Joystick(1);
        shooter = new Joystick(2);
    }
    
    public double getLeftX() {
        return driver.getRawAxis(1);
    }

    public double getRightX() {
        return driver.getRawAxis(4);
    }
    
    public double getLeftY() {
        return driver.getRawAxis(2);
    }

    public double getRightY() {
        return driver.getRawAxis(5);
    }
    
    public boolean shift() {
        return driver.getRawButton(2) || driver.getRawButton(6);
    }
    
    public boolean catchBall() {
        return driver.getRawButton(1);
    }
    
    public boolean shoot() {
        return shooter.getRawButton(5);
    }
    
    public boolean intakeLift() {
        return shooter.getRawButton(2);
    }
    
    public boolean shooterLift() {
        return shooter.getRawButton(4);
    }
    
    public boolean directionToggle() {
        return driver.getRawButton(4) || driver.getRawButton(6);
    }
    
    public boolean intakeToggle() {
        return shooter.getRawButton(6);
    }
    
    public int intake() {
        if(shooter.getRawButton(1)) { // outtake
            return -1;
        } else if(shooter.getRawButton(3)) {
            return 1;
        } else {
            return 0;
        }
    }

	public boolean straightDrive() {
		return driver.getRawButton(7);// driver's back button
	}
    
}
