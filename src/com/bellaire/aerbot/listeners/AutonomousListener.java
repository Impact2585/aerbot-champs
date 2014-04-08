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
        
        if(now - time < 2500 && time != -1) {
        	try{
        		env.getWheelSystem().straightDrive(0.5);
        	}catch(NullPointerException ex){
        		env.getWheelSystem().arcadeDrive(0.5, 0.007);
        	}
            //env.getShooterSystem().open();
        } else if(now - time < 5500 && time != -1) {
        	try{
        		env.getWheelSystem().straightDrive(0.1);
        	}catch(NullPointerException ex){
        		env.getWheelSystem().arcadeDrive(0.1, 0.007);;
        	}
            env.getIntakeSystem().setMotor(-1);//outake
        }else {
        	//stop motors
        	env.getIntakeSystem().setMotor(0);
        	env.getWheelSystem().drive(0, 0);
        	//env.getShooterSystem().close();
        }
    }
    
}
