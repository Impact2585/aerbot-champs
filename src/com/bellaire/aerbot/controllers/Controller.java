package com.bellaire.aerbot.controllers;

import com.bellaire.aerbot.Environment;
import com.bellaire.aerbot.Executer;

public abstract class Controller {

    private Environment env;
    private Executer exec;
    
    /**
	 * @param env Environment to set
	 * @param exec Executer to set
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

	/**
	 * @return the env
	 */
	protected Environment getEnv() {
		return env;
	}

	/**
	 * @param env the env to set
	 */
	protected void setEnv(Environment env) {
		this.env = env;
	}
    
}
