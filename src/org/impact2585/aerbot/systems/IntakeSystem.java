package org.impact2585.aerbot.systems;

import org.impact2585.aerbot.Environment;
import org.impact2585.aerbot.RobotMap;
import org.impact2585.aerbot.input.InputMethod;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;

public class IntakeSystem implements RobotSystem, Runnable {

    private ShooterSystem shooter;
    private InputMethod inputMethod;
    
    private SpeedController motorController;
    private Relay intakeLift;
    private Relay intakeLift2;
    
    private boolean isIntakeToggled = false, catchToggle = false, catching;
    
    /* (non-Javadoc)
     * @see org.impact2585.aerbot.systems.RobotSystem#init(org.impact2585.aerbot.Environment)
     */
    public void init(Environment environment) {
        shooter = environment.getShooterSystem();
        motorController = new Jaguar(RobotMap.INTAKE_MOTOR_CONTROLLER);
        intakeLift = new Relay(RobotMap.INTAKE_LIFT_1);
        intakeLift2 = new Relay(RobotMap.INTAKE_LIFT_2);
        close();
        inputMethod = environment.getInput();
    }

    /* (non-Javadoc)
     * @see org.impact2585.aerbot.systems.RobotSystem#destroy()
     */
    public void destroy() {
    	if(motorController instanceof SensorBase){
    		SensorBase motor = (SensorBase) motorController;
    		motor.free();
    	}
        intakeLift.free();
        intakeLift2.free();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run(){
    	intake(inputMethod);
    }
    
	/**
	 * Intake Should be the only possible states: 1) Intake lift up. Intake
	 * motors stopped. (default state) 2) Intake lift down. Intake motors
	 * intaking. 3) Intake lift up. Intake motors outtaking.
	 * 
	 * controls motorController mechanism according to input
	 * 
	 * @param input input from driver
	 */
    public void intake(InputMethod input) {
        // Auto motorController (w/ toggling)
        //  Toggle on will auto motorController.
        //  Toggle off will return to default state.
        if(input.intakeToggle()) {
            this.open();
            motorController.set(1);
            isIntakeToggled = true;
        } else if (isIntakeToggled && !input.intakeToggle()) { // set to default state
        	if(!catching)
        		this.close();
            motorController.set(0);
            isIntakeToggled = false;
        }

        // Manual motorController motors
        if(!isIntakeToggled) {
            if (input.intake() == -1) {
                motorController.set(-1);
            } else if (input.intake() == 1) {
                motorController.set(1);
            } else {
                motorController.set(0);
            }
        }

        // Catch
        // Interacts with shooter pneumatic
        // (Can be moved into its own subsystem)
        if(!catchToggle && input.catchBall()) {
        	catchToggle = true;
        	catching = !catching;
        	if(intakeLiftState() == Relay.Value.kForward) {
        		close();
        		shooter.close();
        		shooter.setMotor(0);
        	} else {
        		open();
        		shooter.open();
        		shooter.setMotor(-.25);
        	}
        }
        if(input.catchBall()) {
            catchToggle = false;
        }
    }

    /**
     * motorController forward
     */
    public void open() {
        intakeLift.set(Relay.Value.kReverse);
        intakeLift2.set(Relay.Value.kReverse);
    }
    
    /**
     * motorController pneumatic goes back
     */
    public void close() {
        intakeLift.set(Relay.Value.kOff);
        intakeLift2.set(Relay.Value.kOff);
    }
    
    public Relay.Value intakeLiftState(){
    	return intakeLift.get();
    }
    
    /**
     * set speed of motorController motor
     * @param speed value should be between from -1 to 1
     */
    public void setMotor(double speed){
    	motorController.set(speed);
    }

	/**
	 * @param motorController the motor to set
	 */
	protected void setSpeedController(SpeedController intake) {
		this.motorController = intake;
	}

	/**
	 * @param shooter the shooter to set
	 */
	protected void setShooter(ShooterSystem shooter) {
		this.shooter = shooter;
	}
}
