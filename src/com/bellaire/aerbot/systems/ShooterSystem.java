package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import com.bellaire.aerbot.input.InputMethod;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;

public class ShooterSystem implements RobotSystem {

    private Environment env;
    private Victor shooter;
    private Relay lift;
    
    // v2 code
    private boolean shooting = false;
    private long shootStart = 0, lastPress = 0;
    private boolean shotPress;
    private boolean manualShooting;
    
    public void init(Environment e) {
        this.env = e;
        shooter = new Victor(4);
        shooter.set(0);
        lift = new Relay(8);
        
        lift.set(Relay.Value.kOff);
    }
    
    public void open() {
        lift.set(Relay.Value.kForward);
    }
    
    public void close() {
        lift.set(Relay.Value.kOff);
    }
    
    public void setMotor(double speed){
    	shooter.set(speed);
    }
    
    public void destroy() {
        
    }
    
    public void shoot(InputMethod input) {
        long current = System.currentTimeMillis();
        
        if(env.getInput().shoot() && current - lastPress > 500) { // fix da toggle
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
    
}
