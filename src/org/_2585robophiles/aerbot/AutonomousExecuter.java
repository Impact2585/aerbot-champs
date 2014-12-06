package org._2585robophiles.aerbot;

public class AutonomousExecuter implements Executer {

	public static final int WAIT_LENGTH = 0;

	private Environment env;
	private long time = -1;
	private long delay;

	/**
	 * Doesn't initialize anything
	 */
	public AutonomousExecuter(){

	}

	/**
	 * Calls init
	 * @param env environment to initialize with
	 */
	public AutonomousExecuter(Environment env) {
		init(env);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.aerbot.Initializable#init(org._2585robophiles.aerbot.Environment)
	 */
	public void init(Environment env) {
		this.env = env;
		time = -1;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.aerbot.Executer#execute()
	 */
	public void execute() {
		long now = System.currentTimeMillis();

		if(delay == 0)
			delay = now;

		if(now - delay >= WAIT_LENGTH && time == -1)
			time = System.currentTimeMillis();

		if(now - time < 2500 && time != -1) {
			// move forward (intake forward) for 2.5 seconds
			env.getWheelSystem().arcadeDrive(1, 0.3);
			if(now - time > 250 && time != -1)
				env.getWheelSystem().gearsReverse();// shift into high gear
		} else if(now - time < 5500 && time != -1) {
			env.getWheelSystem().arcadeDrive(0.1, 0);// keep going forward a bit
			env.getIntakeSystem().setMotor(-1);// outtake

		}else {
			//stop motors
			env.getIntakeSystem().setMotor(0);
			env.getWheelSystem().drive(0, 0);
			env.getWheelSystem().gearsOff();// back to low gear
		}
	}
}
