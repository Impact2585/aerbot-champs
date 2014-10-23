package com.bellaire.aerbot.input;

import edu.wpi.first.wpilibj.Joystick;

/*

1: A - outtake
2: B - shift
3: X - intake
4: Y - shooter pneumatic
5: Left Bumper - shoot
6: Right Bumper - shift
7: Back - straight drive
8: Start
9: Left Joystick
10: Right Joystick

*/

public class PartnerXboxInput implements InputMethod {

    private Joystick driver, shooter;
    
    /**
     * initalizes drive base controller on port 1 and shooter controller on port 2
     */
    public PartnerXboxInput() {
        driver = new Joystick(1);
        shooter = new Joystick(2);
    }
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#getLeftX()
     */
    public double getLeftX() {
        return driver.getRawAxis(XboxInput.LEFT_X_AXIS);
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#getRightX()
     */
    public double getRightX() {
        return driver.getRawAxis(XboxInput.RIGHT_X_AXIS);
    }
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#getLeftY()
     */
    public double getLeftY() {
        return driver.getRawAxis(XboxInput.LEFT_Y_AXIS);
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#getRightY()
     */
    public double getRightY() {
        return driver.getRawAxis(XboxInput.RIGHT_Y_AXIS);
    }
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#shift()
     */
    public boolean shift() {
        return driver.getRawButton(XboxInput.B_BUTTON) || driver.getRawButton(XboxInput.RIGHT_BUMPER);
    }
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#catchBall()
     */
    public boolean catchBall() {
        return driver.getRawButton(XboxInput.A_BUTTON);
    }
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#shoot()
     */
    public boolean shoot() {
        return shooter.getRawButton(XboxInput.LEFT_BUMPER);
    }
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#intakeLift()
     */
    public boolean intakeLift() {
        return shooter.getRawButton(XboxInput.B_BUTTON);
    }
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#shooterLift()
     */
    public boolean shooterLift() {
        return shooter.getRawButton(XboxInput.Y_BUTTON);
    }
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#directionToggle()
     */
    public boolean directionToggle() {
        return driver.getRawButton(XboxInput.Y_BUTTON) || driver.getRawButton(XboxInput.RIGHT_BUMPER);
    }
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#intakeToggle()
     */
    public boolean intakeToggle() {
        return shooter.getRawButton(XboxInput.RIGHT_BUMPER);
    }
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.input.InputMethod#intake()
     */
    public int intake() {
        if(shooter.getRawButton(XboxInput.A_BUTTON)) { // outtake
            return -1;
        } else if(shooter.getRawButton(XboxInput.X_BUTTON)) {
            return 1;
        } else {
            return 0;
        }
    }

	/* (non-Javadoc)
	 * @see com.bellaire.aerbot.input.InputMethod#straightDrive()
	 */
	public boolean straightDrive() {
		return driver.getRawButton(XboxInput.BACK_BUTTON);// driver's back button
	}
    
}
