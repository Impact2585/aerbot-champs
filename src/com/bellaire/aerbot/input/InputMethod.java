package com.bellaire.aerbot.input;

public interface InputMethod {
    
    public double getLeftX();
    
    public double getRightX();
    
    public double getLeftY();
    
    public double getRightY();
    
    public boolean boost();
    
    public boolean shoot();
    
    public boolean antishoot();
    
}
