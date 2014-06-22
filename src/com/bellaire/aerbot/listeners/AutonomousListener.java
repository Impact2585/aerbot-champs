package com.bellaire.aerbot.listeners;

import com.bellaire.aerbot.Environment;

public class AutonomousListener implements Listener {
    
	public static final int WAIT_LENGTH = 0;
	
    private Environment env;
    private long time = -1;
    private long delay;
    
    public void init(Environment env) {
        this.env = env;
        time = -1;
    }

    public boolean isComplete() {
        return env.isOperatorControl();
    }

    public boolean shouldExecute() {
        return env.isAutonomous();
    }

    public void execute() {
        long now = System.currentTimeMillis();
        
        if(delay == 0)
        	delay = now;
        
        if(now - delay >= WAIT_LENGTH && time == -1)
        	time = System.currentTimeMillis();
        
        env.getWheelSystem().gearsReverse();
        
        if(now - time < 5500 && time != -1) {
        	try{
        		env.getWheelSystem().straightDrive(-1);
        	}catch(NullPointerException ex){
        		env.getWheelSystem().arcadeDrive(-1, 0);
        	}
        } else if(now - time < 6000 && time != -1) {
            env.getWheelSystem().arcadeDrive(.65, 0);
           // env.getShooterSystem().setMotor(1);
        } else if(now - time < 70 && time != -1) {
            //env.getShooterSystem().open();
            
        }else {
        	//stop motors
        	env.getIntakeSystem().setMotor(0);
        	env.getWheelSystem().drive(0, 0);
                env.getShooterSystem().setMotor(0);
                env.getShooterSystem().close();
        }
    }
    
}
