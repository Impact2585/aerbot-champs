package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import com.bellaire.aerbot.input.InputMethod;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class ShooterSystem implements RobotSystem, Runnable{

	private InputMethod inputMethod;
    private SpeedController shooter;
    private Relay lift;
    
    // v2 code
    private boolean shooting = false;
    private long shootStart = 0, lastPress = 0, current;
    private boolean shotPress;
    private boolean manualShooting;
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.systems.RobotSystem#init(com.bellaire.aerbot.Environment)
     */
    public void init(Environment env) {
    	inputMethod = env.getInput();
    	
        shooter = new Victor(4);
        shooter.set(0);
        lift = new Relay(8);
        
        lift.set(Relay.Value.kOff);
    }
    
    /**
     * Shooter down
     */
    public void open() {
        lift.set(Relay.Value.kForward);
    }
    
    /**
     * Shooter up at default position
     */
    public void close() {
        lift.set(Relay.Value.kOff);
    }
    
    /**
     * sets speed of shooter motor
     * @param speed value should be from -1 to 1
     */
    public void setMotor(double speed){
    	shooter.set(speed);
    }
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.systems.RobotSystem#destroy()
     */
    public void destroy() {
        if(shooter instanceof PWM){
        	PWM motor = (PWM) shooter;
        	motor.free();
        }
        lift.free();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run(){
    	shoot(inputMethod);
    }
    
    /**
     * controls shooter with given input
     * @param input input from driver
     */
    public void shoot(InputMethod input) {
        current = System.currentTimeMillis();
        
        if(input.shoot() && current - lastPress > 500) { // fix da toggle
            lastPress = current;
            if(shooting == false) {
                close();
                shooter.set(1);

                shooting = true;
                shootStart = current;
            } else {
                if(current - shootStart < 2000) {
                    shooter.set(0);
                    
                    shooting = false;
                    shootStart = 0;
                }
            }
        }
        
        if(shooting) {
            if(current - shootStart >= 4000 && current - shootStart < 4500) {
                open();
            } else if(current - shootStart >= 4500) {
                close();
                shooter.set(0);
                
                shooting = false;
                shootStart = 0;
            }
        }else if(!shotPress && input.shooterLift()){
        	if(manualShooting){
        		close();
        		shooter.set(0);
        	}else{
        		open();
        		shooter.set(1);
        	}
        	manualShooting = !manualShooting;
        }
        shotPress = input.shooterLift();
        
        
    }

	/**
	 * @return the shootStart
	 */
	protected long getShootStart() {
		return shootStart;
	}

	/**
	 * @param shootStart the shootStart to set
	 */
	protected void setShootStart(long shootStart) {
		this.shootStart = shootStart;
	}
    
    /**
	 * @return the lastPress
	 */
	protected long getLastPress() {
		return lastPress;
	}

	/**
	 * @param lastPress the lastPress to set
	 */
	protected void setLastPress(long lastPress) {
		this.lastPress = lastPress;
	}

	/**
	 * @return the current
	 */
	protected long getCurrent() {
		return current;
	}

	/**
	 * @param current the current to set
	 */
	protected void setCurrent(long current) {
		this.current = current;
	}

	/**
	 * @return the shooter
	 */
	protected SpeedController getShooter() {
		return shooter;
	}

	/**
	 * @param shooter the shooter to set
	 */
	protected void setShooter(SpeedController shooter) {
		this.shooter = shooter;
	}

}
