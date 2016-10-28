package org.impact2585.aerbot.systems;

import org.impact2585.aerbot.Environment;
import org.impact2585.aerbot.RobotMap;
import org.impact2585.aerbot.custom.MultiMotor;
import org.impact2585.aerbot.input.InputMethod;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Jaguar;

public class ShooterSystem implements RobotSystem, Runnable{

    private InputMethod inputMethod;
    private SpeedController speedController;
    private Solenoid solenoid;
    private boolean shooting = false;
    private long shootStart = 0, lastPress = 0, current;
    private boolean shotPress;
    private boolean manualShooting;
    
    /* (non-Javadoc)
     * @see org.impact2585.aerbot.systems.RobotSystem#init(org.impact2585.aerbot.Environment)
     */
    public void init(Environment env) {
    	inputMethod = env.getInput();
    	
    	speedController = new MultiMotor(new SpeedController[]{new Jaguar(RobotMap.SHOOTER_MOTOR_1),new Jaguar(RobotMap.SHOOTER_MOTOR_2)});
        speedController.set(0);
        solenoid = new Solenoid(RobotMap.SHOOTER_SOLENOID);
    }
    
    /**
     * Shooter down
     */
    public void open() {
        solenoid.set(true);
    }
    
    /**
     * Shooter up at default position
     */
    public void close() {
    	solenoid.set(false);
    }
    
    /**
     * sets speed of speedController motor
     * @param speed value should be from -1 to 1
     */
    public void setMotor(double speed){
    	speedController.set(speed);
    }
    
    /* (non-Javadoc)
     * @see org.impact2585.aerbot.systems.RobotSystem#destroy()
     */
    public void destroy() {
        if(speedController instanceof SensorBase){
        	SensorBase motor = (SensorBase) speedController;
        	motor.free();
        }
        solenoid.free();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run(){
    	shoot(inputMethod);
    }
    
    /**
     * controls speedController with given input
     * @param input input from driver
     */
    public void shoot(InputMethod input) {
        current = System.currentTimeMillis();
        
        if(input.shoot() && current - lastPress > 500) { // fix da toggle
            lastPress = current;
            if(shooting == false) {
                close();
                setMotor(1);

                shooting = true;
                shootStart = current;
            } else {
                if(current - shootStart < 2000) {
                    setMotor(0);
                    
                    shooting = false;
                    shootStart = 0;
                }
            }
        }
        
        if(shooting) {
            if(current - shootStart >= 4000 && current - shootStart < 5000) {
                open();
            } else if(current - shootStart >= 5000) {
                close();
                setMotor(0);
                
                shooting = false;
                shootStart = 0;
            }
        }else if(!shotPress && input.shooterLift()){
        	if(manualShooting){
        		close();
        		setMotor(0);
        	}else{
        		open();
        		setMotor(1);
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
	 * @return the motor
	 */
	protected SpeedController getSpeedController() {
		return speedController;
	}

	/**
	 * @param speedController the motor to set
	 */
	protected void setSpeedController(SpeedController shooter) {
		this.speedController = shooter;
	}

}
