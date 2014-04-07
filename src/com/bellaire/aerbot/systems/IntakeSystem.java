package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import com.bellaire.aerbot.input.InputMethod;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Relay;

public class IntakeSystem implements RobotSystem {

    private Environment env;
    
    private Jaguar intake;
    private Relay intakeLift;
    
    private boolean isIntakeToggled = false, catchToggle = false;
    
    public void init(Environment e) {
        this.env = e;
        intake = new Jaguar(7);
        intakeLift = new Relay(5);
        
        intakeLift.set(Relay.Value.kReverse);
    }

    public void destroy() {
        
    }
    
    // Intake
    // ------
    // Should be the only possible states:
    //  1) Intake lift up. Intake motors stopped. (default state)
    //  2) Intake lift down. Intake motors intaking.
    //  3) Intake lift up. Intake motors outtaking.
    public void intake(InputMethod input) {
        // Auto intake (w/ toggling)
        //  Toggle on will auto intake.
        //  Toggle off will return to default state.
        if(env.getInput().intakeToggle()) {
            this.open();
            intake.set(1);
            isIntakeToggled = true;
        } else if (isIntakeToggled && !env.getInput().intakeToggle()) { // set to default state
            this.close();
            intake.set(0);
            isIntakeToggled = false;
        }

        // Manual intake motors
        if(!isIntakeToggled) {
            if (env.getInput().intake() == -1) {
                intake.set(-1);
            } else if (env.getInput().intake() == 1) {
                intake.set(1);
            } else {
                intake.set(0);
            }
        }

        // Catch
        // Interacts with shooter pneumatic
        // (Can be moved into its own subsystem)
        if(catchToggle == false) {
            if(env.getInput().catchBall()) {
                catchToggle = true;
                if(intakeLift.get() == Relay.Value.kForward) {
                    intakeLift.set(Relay.Value.kReverse);
                    env.getShooterSystem().close();
                    env.getShooterSystem().setMotor(0);
                } else {
                    intakeLift.set(Relay.Value.kForward);
                    env.getShooterSystem().open();
                    env.getShooterSystem().setMotor(-.25);
                }
            }
        }
        if(!env.getInput().catchBall()) {
            catchToggle = false;
        }
    }

    public void open() {
        intakeLift.set(Relay.Value.kForward);
    }
    
    public void close() {
        intakeLift.set(Relay.Value.kReverse);
    }
    
    public void setMotor(double speed){
    	intake.set(speed);
    }
}
