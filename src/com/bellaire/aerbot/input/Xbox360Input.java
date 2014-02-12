package com.bellaire.aerbot.input;

import edu.wpi.first.wpilibj.Joystick;

public class Xbox360Input implements InputMethod {

    private Joystick controller;
    
    public Xbox360Input() {
        controller = new Joystick(1);
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
    
    public boolean boost() {
        return controller.getRawButton(2);
    }
    
    public boolean shoot() {
        return controller.getRawButton(6);
    }
    
    public boolean antishoot() {
        return controller.getRawButton(5);
    }
    
}
