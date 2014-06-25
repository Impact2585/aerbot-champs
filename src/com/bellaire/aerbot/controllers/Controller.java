package com.bellaire.aerbot.controllers;

import com.bellaire.aerbot.Environment;
import com.bellaire.aerbot.Executer;

public abstract class Controller {

    protected Environment env;
    protected Executer exec;
    
    /**
	 * @param env
	 * @param exec
	 */
	public Controller(Environment env, Executer exec) {
		this.env = env;
		this.exec = exec;
	}

	/**
	 * calls exec.update()
	 */
	public void update() {
        exec.update();
    }
    
}
