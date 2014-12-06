package org._2585robophiles.aerbot.input;

public interface InputMethod {
    
    // drive 
    
    /**
     * @return x value of base driver left stick
     */
    public double getLeftX();
    
    /**
     * @return x value of base driver right stick
     */
    public double getRightX();
    
    /**
     * @return y value of base driver left stick
     */
    public double getLeftY();
    
    /**
     * @return y value of base driver right stick
     */
    public double getRightY();
    
    /**
     * @return if base driver has pressed the shift gear button
     */
    public boolean shift();
    
    // intake / shooter
    
    /**
     * @return if catch button is pressed
     */
    public boolean catchBall();
    
    /**
     * @return true if shoot button is pressed
     */
    public boolean shoot();
    
    /**
     * @return true if shooter pneumatic button is pressed
     */
    public boolean shooterLift();
    
    /**
     * @return number representing the intake input
     */
    public int intake();
    
    /**
     * @return if auto intake button is pressed
     */
    public boolean intakeToggle();
    
    /**
     * @return if intakeLift button is pressed
     */
    public boolean intakeLift();
    
    /**
     * @return if switch direction button is pressed
     */
    public boolean directionToggle();
    
    /**
     * @return if enable/disable straight driving button is pressed
     */
    public boolean straightDrive();
    
}
