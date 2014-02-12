package com.bellaire.aerbot.input;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickInput implements InputMethod {
    
    private Joystick left, right;
    
    public JoystickInput() {
        left = new Joystick(1);
        right = new Joystick(2);
    }

    public double getLeftY() {
        return left.getY();
    }

    public double getRightY() {
        return right.getY();
    }
    
    public double getLeftX() {
        return left.getY();
    }

    public double getRightX() {
        return right.getY();
    }
    
    public boolean boost() {
        return false;
    }
    
    public boolean shoot() {
        return false;
    }
    
    public boolean antishoot() {
        return false;
    }
    
}
