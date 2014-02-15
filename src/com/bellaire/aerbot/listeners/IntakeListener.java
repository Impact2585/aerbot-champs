package com.bellaire.aerbot.listeners;

import com.bellaire.aerbot.Environment;

public class IntakeListener implements Listener {
    
    private Environment env;
    
    public void init(Environment env) {
        this.env = env;
    }

    public boolean isComplete() {
        return false;
    }

    public boolean shouldExecute() {
        return true;
    }

    public void execute() {
        env.getShooterSystem().shoot(env.getInput());
    }
    
}
