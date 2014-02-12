package com.bellaire.aerbot.listeners;

import com.bellaire.aerbot.Environment;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousListener implements Listener {
    
    private Environment env;
    
    public void init(Environment env) {
        this.env = env;
        
        //this.camera = env.getCameraSystem();
        
        //SmartDashboard.putNumber("Autonomous Turn Speed", 0.1);
    }

    public boolean isComplete() {
        return env.isOperatorControl();
    }

    public boolean shouldExecute() {
        return env.isAutonomous();
    }

    public void execute() {
        //SmartDashboard.putString("Found Target", "false");
        env.getWheelSystem().drive(0.1, 1);
    }
    
}
