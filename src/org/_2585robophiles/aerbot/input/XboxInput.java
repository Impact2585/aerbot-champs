package org._2585robophiles.aerbot.input;

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
	
	public static final int LEFT_X_AXIS = 1;
	public static final int LEFT_Y_AXIS = 2;
	public static final int RIGHT_X_AXIS = 4;
	public static final int RIGHT_Y_AXIS = 5;

	public static final int A_BUTTON = 1;
	public static final int B_BUTTON = 2;
	public static final int X_BUTTON = 3;
	public static final int Y_BUTTON = 4;
	public static final int LEFT_BUMPER = 5;
	public static final int RIGHT_BUMPER = 6;
	public static final int BACK_BUTTON = 7;
	public static final int START_BUTTON = 8;
	public static final int LEFT_JOYSTICK_BUTTON = 9;
	public static final int RIGHT_JOYSTICK_BUTTON = 10;
	
    private Joystick controller;
    
    /**
     * initializes Joystick on port 1
     */
    public XboxInput() {
        controller = new Joystick(1);
    }
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#intakeLift()
     */
    public boolean intakeLift() {
        return controller.getRawButton(BACK_BUTTON);
    }
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#getLeftX()
     */
    public double getLeftX() {
        return controller.getRawAxis(LEFT_X_AXIS);
    }

    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#getRightX()
     */
    public double getRightX() {
        return controller.getRawAxis(RIGHT_X_AXIS);
    }
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#getLeftY()
     */
    public double getLeftY() {
        return controller.getRawAxis(LEFT_Y_AXIS);
    }

    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#getRightY()
     */
    public double getRightY() {
        return controller.getRawAxis(RIGHT_Y_AXIS);
    }
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#shift()
     */
    public boolean shift() {
        return controller.getRawButton(LEFT_JOYSTICK_BUTTON);
    }
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#catchBall()
     */
    public boolean catchBall() {
        return controller.getRawButton(START_BUTTON);//catch ball is start button
    }
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#shoot()
     */
    public boolean shoot() {
        return controller.getRawButton(A_BUTTON);
    }
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#shooterLift()
     */
    public boolean shooterLift() {
        return controller.getRawButton(B_BUTTON);
    }
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#intakeToggle()
     */
    public boolean intakeToggle() {
        return controller.getRawButton(X_BUTTON);
    }
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#directionToggle()
     */
    public boolean directionToggle() {
        return controller.getRawButton(Y_BUTTON);
    }
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.input.InputMethod#intake()
     */
    public int intake() {
        if(controller.getRawButton(LEFT_BUMPER)) {
            return 1;
        } else if(controller.getRawButton(RIGHT_BUMPER)) {
            return 2;
        } else {
            return 0;
        }
    }

	/* (non-Javadoc)
	 * @see org._2585robophiles.aerbot.input.InputMethod#straightDrive()
	 */
	public boolean straightDrive() {
		return controller.getRawButton(RIGHT_JOYSTICK_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.aerbot.input.InputMethod#joysticks()
	 */
	public Joystick[] joysticks() {
		return new Joystick[]{controller};
	}
}
