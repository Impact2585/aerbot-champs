package com.bellaire.aerbot.input;

public interface InputMethod {
    
    // drive 
    
    public double getLeftX();
    
    public double getRightX();
    
    public double getLeftY();
    
    public double getRightY();
    
    public boolean shift();
    
    // intake / shooter
    
    public boolean catchBall();
    
    public boolean shoot();
    
    public boolean shooterLift();
    
    public int intake();
    
    public boolean intakeToggle();
    
    public boolean intakeLift();
    
    public boolean directionToggle();
    
}
