package org.impact2585.aerbot;

import org.impact2585.aerbot.input.InputMethod;
import org.impact2585.aerbot.input.XboxInput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Run a test. This won't be used during competition.
 */
public class TestExecuter implements Executer {
	
	private Environment env;
	private InputMethod input;

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
	 * @see org.impact2585.aerbot.Initializable#init(org.impact2585.aerbot.Environment)
	 */
	public void init(Environment environment) {
		env = environment;
		input = environment.getInput();
	}

	/* (non-Javadoc)
	 * @see org.impact2585.aerbot.Executer#execute()
	 */
	public void execute() {
		env.getIntakeSystem().setMotor(1);
		if(input.joysticks().length > 0){

			//get all the axis values
			SmartDashboard.putNumber("Left X axis", input.joysticks()[0].getRawAxis(XboxInput.LEFT_X_AXIS));
			SmartDashboard.putNumber("Right X axis", input.joysticks()[0].getRawAxis(XboxInput.RIGHT_X_AXIS));
			SmartDashboard.putNumber("Left Y axis", input.joysticks()[0].getRawAxis(XboxInput.LEFT_Y_AXIS));
			SmartDashboard.putNumber("Right X axis", input.joysticks()[0].getRawAxis(XboxInput.RIGHT_X_AXIS));

			//get all the button values
			SmartDashboard.putBoolean("A button", input.joysticks()[0].getRawButton(XboxInput.A_BUTTON));
			SmartDashboard.putBoolean("B button", input.joysticks()[0].getRawButton(XboxInput.B_BUTTON));
			SmartDashboard.putBoolean("X button", input.joysticks()[0].getRawButton(XboxInput.X_BUTTON));
			SmartDashboard.putBoolean("Y button", input.joysticks()[0].getRawButton(XboxInput.Y_BUTTON));
			SmartDashboard.putBoolean("Left bumper", input.joysticks()[0].getRawButton(XboxInput.LEFT_BUMPER));
			SmartDashboard.putBoolean("Right bumper", input.joysticks()[0].getRawButton(XboxInput.RIGHT_BUMPER));
			SmartDashboard.putBoolean("Back button ", input.joysticks()[0].getRawButton(XboxInput.BACK_BUTTON));
			SmartDashboard.putBoolean("Start button ", input.joysticks()[0].getRawButton(XboxInput.START_BUTTON));
			SmartDashboard.putBoolean("Left joystick button ", input.joysticks()[0].getRawButton(XboxInput.LEFT_JOYSTICK_BUTTON));
			SmartDashboard.putBoolean("Right joystick button ", input.joysticks()[0].getRawButton(XboxInput.RIGHT_JOYSTICK_BUTTON));
		}
		SmartDashboard.putString("Test", "Hello, world!");
	}
}


