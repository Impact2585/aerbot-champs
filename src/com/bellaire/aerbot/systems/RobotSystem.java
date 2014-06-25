package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;

public interface RobotSystem {

	/**
	 * initialize the system
     * @param environment
     */
    public void init(Environment environment);
    
    /**
     * destroy object
     */
    public void destroy();
}
