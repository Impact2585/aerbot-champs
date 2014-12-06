package org._2585robophiles.aerbot.systems;

import org._2585robophiles.aerbot.Environment;
import org._2585robophiles.aerbot.custom.DoubleSolenoid;
import org._2585robophiles.aerbot.custom.MultiMotor;
import org._2585robophiles.aerbot.input.InputMethod;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

public class ShooterSystem implements RobotSystem, Runnable{

    private InputMethod inputMethod;
    private SpeedController shooter;
    private DoubleSolenoid doubleSolenoid;
    private boolean shooting = false;
    private long shootStart = 0, lastPress = 0, current;
    private boolean shotPress;
    private boolean manualShooting;
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.systems.RobotSystem#init(org._2585robophiles.aerbot.Environment)
     */
    public void init(Environment env) {
    	inputMethod = env.getInput();
    	
    	shooter = new MultiMotor(new SpeedController[]{new Victor(4),new Victor(5)});
        shooter.set(0);
        doubleSolenoid = new DoubleSolenoid(8,7);
    }
    
    /**
     * Shooter down
     */
    public void open() {
        if(doubleSolenoid.isDefaultState()){
        	doubleSolenoid.toggle();
        }
    }
    
    /**
     * Shooter up at default position
     */
    public void close() {
    	 if(!(doubleSolenoid.isDefaultState())){
         	doubleSolenoid.toggle();
         }
    }
    
    /**
     * sets speed of shooter motor
     * @param speed value should be from -1 to 1
     */
    public void setMotor(double speed){
    	shooter.set(speed);
    }
    
    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.systems.RobotSystem#destroy()
     */
    public void destroy() {
        if(shooter instanceof SensorBase){
        	SensorBase motor = (SensorBase) shooter;
        	motor.free();
        }
        doubleSolenoid.free();
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
		return shooter;
	}

	/**
	 * @param shooter the motor to set
	 */
	protected void setSpeedController(SpeedController shooter) {
		this.shooter = shooter;
	}

}
