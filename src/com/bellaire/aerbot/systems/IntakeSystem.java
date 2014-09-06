package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import com.bellaire.aerbot.input.InputMethod;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SpeedController;

public class IntakeSystem implements RobotSystem, Runnable {

    private ShooterSystem shooter;
    private InputMethod inputMethod;
    
    private SpeedController intake;
    private Relay intakeLift;
    
    private boolean isIntakeToggled = false, catchToggle = false, catching;
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.systems.RobotSystem#init(com.bellaire.aerbot.Environment)
     */
    public void init(Environment environment) {
        shooter = environment.getShooterSystem();
        intake = new Jaguar(7);
        intakeLift = new Relay(5);
        
        intakeLift.set(Relay.Value.kReverse);
        
        inputMethod = environment.getInput();
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.systems.RobotSystem#destroy()
     */
    public void destroy() {
    	if(intake instanceof PWM){
    		PWM motor = (PWM) intake;
    		motor.free();
    	}
        intakeLift.free();
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
	 * controls intake mechanism according to input
	 * 
	 * @param input input from driver
	 */
    public void intake(InputMethod input) {
        // Auto intake (w/ toggling)
        //  Toggle on will auto intake.
        //  Toggle off will return to default state.
        if(input.intakeToggle()) {
            this.open();
            intake.set(1);
            isIntakeToggled = true;
        } else if (isIntakeToggled && !input.intakeToggle()) { // set to default state
        	if(!catching)
        		this.close();
            intake.set(0);
            isIntakeToggled = false;
        }

        // Manual intake motors
        if(!isIntakeToggled) {
            if (input.intake() == -1) {
                intake.set(-1);
            } else if (input.intake() == 1) {
                intake.set(1);
            } else {
                intake.set(0);
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
     * intake forward
     */
    public void open() {
        intakeLift.set(Relay.Value.kForward);
    }
    
    /**
     * intake pneumatic goes back
     */
    public void close() {
        intakeLift.set(Relay.Value.kReverse);
    }
    
    public Relay.Value intakeLiftState(){
    	return intakeLift.get();
    }
    
    /**
     * set speed of intake motor
     * @param speed value should be between from -1 to 1
     */
    public void setMotor(double speed){
    	intake.set(speed);
    }
}
