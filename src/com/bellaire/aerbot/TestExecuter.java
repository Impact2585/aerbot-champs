package com.bellaire.aerbot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Run a test. This won't be used during competition.
 */
public class TestExecuter implements Executer {
	
	private Environment env;

	/**
	 * Doesn't initialize anything
	 */
	public TestExecuter(){

	}

	/**
	 * Calls init
	 * @param env environment to initialize with
	 */
	public TestExecuter(Environment env) {
		init(env);
	}
	
	/* (non-Javadoc)
	 * @see com.bellaire.aerbot.Initializable#init(com.bellaire.aerbot.Environment)
	 */
	public void init(Environment environment) {
		env = environment;
	}

	/* (non-Javadoc)
	 * @see com.bellaire.aerbot.Executer#execute()
	 */
	public void execute() {
		env.getIntakeSystem().setMotor(1);
                SmartDashboard.putString("Test", "Hello, world!");
	}

}
