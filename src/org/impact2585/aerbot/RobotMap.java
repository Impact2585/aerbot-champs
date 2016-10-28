package org.impact2585.aerbot;

/**
 *Interface containing the constants of the robot
 */
public interface RobotMap {
	
	//motor and relay ports for the drivetrain
	public static final int LEFT_DRIVETRAIN_MOTOR = 1;
	public static final int RIGHT_DRIVETRAIN_MOTOR = 10;
	public static final int GEARBOX = 2;
	
	//ports for the intake
	public static final int INTAKE_MOTOR_CONTROLLER = 2;
	public static final int INTAKE_LIFT_1 = 5;
	public static final int INTAKE_LIFT_2 = 6;
	
	//ports for the shooter
	public static final int SHOOTER_SOLENOID = 8;
	public static final int SHOOTER_MOTOR_1 = 4;
	public static final int SHOOTER_MOTOR_2 = 5;
	
	//channel for the sonar
	public static final int SONAR_CHANNEL = 1;
	
	//port for the accelerometer
	public static final int ACCELEROMETER_PORT = 1;
	
	//port for the gyro
	public static final int GYRO_PORT = 2;
}
