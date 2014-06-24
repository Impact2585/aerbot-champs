package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import com.bellaire.aerbot.custom.RobotDrive3;
import com.bellaire.aerbot.input.InputMethod;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WheelSystem implements RobotSystem {

	public static final double SHIFT_DELAY = 0.5;
	public static double Kp = -.08;
	public static double Ki = 0;
	public static double Kd = 0.0;
	public static final double SHIFTING_SPEED = 1.8;
	public static final double RAMPING = 0.5;
	
	private StraightDrivePID straightDrivePID;

	private RobotDrive3 wheels;

	private Relay gearbox;
	private int gear = 0; // off
	private boolean gearPress = false, dirToggle = false;
	private int dir = 1;

	private double currentLeftY = 0;
	private double currentRampY = 0;

	private GyroSystem gyro;
	private boolean straightDriving;
	private double heading;
	private double correctRotate;

	private AccelerometerSystem accelerometer;
	private Timer timer;
	private boolean automatic = true;

	private boolean disableStraightDrive = false;
	private boolean disableStraightDrivePressed;

	public WheelSystem() {

	}

	public void init(Environment e) {
		wheels = new RobotDrive3(1, 2);

		gearbox = new Relay(2);
		this.gearsOff();

		wheels.setSafetyEnabled(false);
		// this.motion = e.getMotionTracker();

		gyro = e.getGyroSystem();

		accelerometer = e.getAccelerometerSystem();

		timer = new Timer();
		timer.start();
		
		straightDrivePID = new StraightDrivePID();
	}
	
	public class StraightDrivePID extends PIDSubsystem{
		
		public StraightDrivePID(){
			super(Kp, Ki, Kd);
		}
		
		protected double returnPIDInput() {
			return gyro.getAngle();
		}

		protected void usePIDOutput(double value) {
			//SmartDashboard.putNumber("Straight drive PID: ", value);
			correctRotate = value;
		}

		protected void initDefaultCommand() {

		}
	}
	
	/**
	 * resets straightDrivePID's PIDController
	 */
	public void resetStraightDrivePID(){
		straightDrivePID.getPIDController().reset();
	}
	
	/**
	 * @return if straightDrivePID's PIDController is enabled
	 */
	public boolean straightDriveControllerEnabled(){
		return straightDrivePID.getPIDController().isEnable();
	}
	
	/**
	 * @param setpoint
	 */
	public void setStraightDrivePIDSetpoint(double setpoint){
		straightDrivePID.setSetpoint(setpoint);
	}
	
	/**
	 * calls enable()
	 */
	public void enableStraightDrivePID(){
		straightDrivePID.enable();
	}
	
	/**
	 * calls disable()
	 */
	public void disableStraightDrivePID(){
		straightDrivePID.disable();
	}

	/**
	 * @return the gyro
	 */
	protected GyroSystem getGyro() {
		return gyro;
	}

	/**
	 * @param gyro the gyro to set
	 */
	protected void setGyro(GyroSystem gyro) {
		this.gyro = gyro;
	}

	/**
	 * @return the accelerometer
	 */
	protected AccelerometerSystem getAccelerometer() {
		return accelerometer;
	}

	/**
	 * @param accelerometer the accelerometer to set
	 */
	protected void setAccelerometer(AccelerometerSystem accelerometer) {
		this.accelerometer = accelerometer;
	}

	/**
	 * @return the correctRotate
	 */
	protected double getCorrectRotate() {
		return correctRotate;
	}

	/**
	 * @param correctRotate the correctRotate to set
	 */
	protected void setCorrectRotate(double correctRotate) {
		this.correctRotate = correctRotate;
	}

	/**
	 * @return the gear
	 */
	public int getGear() {
		return gear;
	}

	/**
	 * @param gear the gear to set
	 */
	protected void setGear(int gear) {
		this.gear = gear;
	}

	/**
	 * @return the currentLeftY
	 */
	protected double getCurrentLeftY() {
		return currentLeftY;
	}

	/**
	 * @param currentLeftY the currentLeftY to set
	 */
	protected void setCurrentLeftY(double currentLeftY) {
		this.currentLeftY = currentLeftY;
	}

	/**
	 * @return the timer
	 */
	protected Timer getTimer() {
		return timer;
	}

	/**
	 * @param timer the timer to set
	 */
	protected void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void gearsOff() {
		gear = 0;
		gearbox.set(Relay.Value.kOff);
	}

	public void gearsReverse() {
		gear = 1;
		gearbox.set(Relay.Value.kReverse);
	}

	public void destroy() {

	}
	
	/**
	 * @param moveValue
	 * @return equivilent of signnum
	 */
	public int actualMovementDirection(double moveValue){
		if(moveValue == 0)
			return 0;
		else if(moveValue > 0)
			return 1;
		else
			return -1;
	}

	public void drive(double outputMaginitude, double curve) {
		wheels.drive(outputMaginitude, curve);
	}

	public void arcadeDrive(double moveValue, double rotateValue) {
		wheels.arcadeDrive(moveValue, rotateValue);
	}

	public void straightDrive(double moveValue) throws NullPointerException{
		// set correct heading
		if (!straightDriving) {
			heading = gyro.getAngle();
			resetStraightDrivePID();
		}
		straightDriving = true;
		// correct error in heading if error more than 2 degrees
		if (Math.abs(heading - gyro.getAngle()) > 2
				&& !straightDriveControllerEnabled()) {
			setStraightDrivePIDSetpoint(heading);
			enableStraightDrivePID();
		} else if (Math.abs(heading - gyro.getAngle()) <= 2
				&& straightDriveControllerEnabled()) {
			disableStraightDrivePID();
			correctRotate = 0;
		}
		arcadeDrive(moveValue, actualMovementDirection(moveValue) * correctRotate);
	}

	public void automaticGearShift() {
		//only autoshift once every half second
		if(timer.get() > SHIFT_DELAY){
			//shift based on speed from accelerometer and only when on the wrong gear
			if (Math.abs(accelerometer.getSpeed()) > SHIFTING_SPEED && gear == 0 && Math.abs(currentLeftY) > .75) {
				// also only shift into high gear if throttle is high
				gearsReverse();
				timer.reset();
			} else if (Math.abs(accelerometer.getSpeed()) <= SHIFTING_SPEED && gear == 1) {
				gearsOff();
				timer.reset();
			}
		}
	}

	public void move(InputMethod input) {
		currentLeftY = -input.getLeftY();

		currentRampY += (currentLeftY - currentRampY) * RAMPING;

		if (!disableStraightDrive && Math.abs(input.getLeftY()) > .15
				&& Math.abs(input.getRightX()) < .15)
			straightDrive(currentRampY * dir);
		else{
			wheels.arcadeDrive(currentRampY * dir, input.getRightX());
			straightDriving = false;
		}

		try {
			SmartDashboard.putBoolean("Low gear: ", gear == 0);
			SmartDashboard.putBoolean("Switched front: ", dir == -1);
			SmartDashboard.putBoolean("Automatic shifting: ", automatic);
			SmartDashboard.putBoolean("Straight driving: ", straightDriving);
			SmartDashboard.putNumber("Angle: ", gyro.getHeading());
			SmartDashboard.putNumber("Ramped Movement: ", currentRampY);
			SmartDashboard.putNumber("Rotation Input: " , input.getRightX());
			SmartDashboard.putBoolean("Straight driving disabled: ", disableStraightDrive);
		} catch (NullPointerException ex) {

		}
		try {
			SmartDashboard.putNumber("AccelerationX: ",
					accelerometer.getAccelerationX());
			SmartDashboard.putNumber("AccelerationY: ",
					accelerometer.getAccelerationY());
			SmartDashboard.putNumber("AccelerationZ: ",
					accelerometer.getAccelerationZ());
		} catch (NullPointerException ex) {

		}
		try {
			SmartDashboard.putNumber("Speed: ", accelerometer.getSpeed());
		} catch (NullPointerException ex) {

		}

		boolean shift = input.shift();
		if (!shift) {
			gearPress = false;
		}

		if (gearPress == false) {
			if (shift) {
				gearPress = true;
				automatic = false;
				if (gear == 0) {
					this.gearsReverse();
				} else if (gear == 1) {
					this.gearsOff();
				}
			}
		}

		//don't autoshift while turning
		if (automatic && Math.abs(input.getRightX()) < 0.15)
			automaticGearShift();

		if (!dirToggle) {
			if (input.directionToggle()) {
				dirToggle = true;
				dir *= -1;
			}
		}
		dirToggle = input.directionToggle();

		// toggle straight driving
		if (!disableStraightDrivePressed && input.straightDrive())
			disableStraightDrive = !disableStraightDrive;
		disableStraightDrivePressed = input.straightDrive();
	}

}
