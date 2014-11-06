package com.bellaire.aerbot;

import edu.wpi.first.wpilibj.Talon;

/**
 * Run a test. This won't be used during competition.
 */
public class TestExecuter implements Executer {
	
	private long startTime = -1;
	private Talon motor1;
	private Talon motor2;

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
		startTime = -1;
		motor1 = new Talon(1);
		motor2 = new Talon(2);
	}

	/* (non-Javadoc)
	 * @see com.bellaire.aerbot.Executer#execute()
	 */
	public void execute() {
		if(startTime == -1)
			startTime = System.currentTimeMillis();
		motor1.set(1);
		motor2.set(1);
	}

}
