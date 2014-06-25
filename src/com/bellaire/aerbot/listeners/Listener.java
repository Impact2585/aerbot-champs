package com.bellaire.aerbot.listeners;

import com.bellaire.aerbot.Environment;

public interface Listener {
    
    /**
     * initialize listener
     * @param env environment of the systems
     */
    public void init(Environment env);
    
    /**
     * @return if the listener is done
     */
    public boolean isComplete();
    
    /**
     * @return if the listener should be executed
     */
    public boolean shouldExecute();
    
    /**
     * execute listener
     */
    public void execute();
    
}
