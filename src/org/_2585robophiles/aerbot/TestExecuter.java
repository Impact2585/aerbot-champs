package org._2585robophiles.aerbot;

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
	 * @see org._2585robophiles.aerbot.Initializable#init(org._2585robophiles.aerbot.Environment)
	 */
	public void init(Environment environment) {
		env = environment;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.aerbot.Executer#execute()
	 */
	public void execute() {
		env.getIntakeSystem().setMotor(1);
                SmartDashboard.putString("Test", "Hello, world!");
	}

}
