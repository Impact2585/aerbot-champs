package com.bellaire.aerbot.listeners;

import com.bellaire.aerbot.Environment;

public class IntakeListener implements Listener {
    
    private Environment env;
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#init(com.bellaire.aerbot.Environment)
     */
    public void init(Environment env) {
        this.env = env;
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#isComplete()
     */
    public boolean isComplete() {
        return false;
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#shouldExecute()
     */
    public boolean shouldExecute() {
        return true;
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#execute()
     */
    public void execute() {
        env.getIntakeSystem().intake(env.getInput());
    }
    
}
