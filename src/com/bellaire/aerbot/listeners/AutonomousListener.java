package com.bellaire.aerbot.listeners;

import com.bellaire.aerbot.Environment;

public class AutonomousListener implements Listener {
    
	public static final int WAIT_LENGTH = 0;
	
    private Environment env;
    private long time = -1;
    private long delay;
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#init(com.bellaire.aerbot.Environment)
     */
    public void init(Environment env) {
        this.env = env;
        time = -1;
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#isComplete()
     */
    public boolean isComplete() {
        return env.isOperatorControl();
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#shouldExecute()
     */
    public boolean shouldExecute() {
        return env.isAutonomous();
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#execute()
     */
    public void execute() {
        long now = System.currentTimeMillis();
        
        if(delay == 0)
        	delay = now;
        
        if(now - delay >= WAIT_LENGTH && time == -1)
        	time = System.currentTimeMillis();
        
        env.getWheelSystem().gearsReverse();// shift into high gear
        
        if(now - time < 5500 && time != -1) {
            // move forward for 5.5 seconds
        	try{
        		env.getWheelSystem().straightDrive(-1);
        	}catch(NullPointerException ex){
        		env.getWheelSystem().arcadeDrive(-1, 0);
        	}
        } else if(now - time < 6000 && time != -1) {
            env.getWheelSystem().arcadeDrive(.4, 0);// back up a bit
           env.getShooterSystem().setMotor(1);
        } else if(now - time < 6500 && time != -1) {
           env.getShooterSystem().open();
            
        }else {
        	//stop motors
        	env.getIntakeSystem().setMotor(0);
        	env.getWheelSystem().drive(0, 0);
			env.getShooterSystem().setMotor(0);
			env.getShooterSystem().close();
        }
    }
    
}
