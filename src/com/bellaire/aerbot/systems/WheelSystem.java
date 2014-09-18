package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import com.bellaire.aerbot.custom.RobotDrive3;
import com.bellaire.aerbot.input.InputMethod;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WheelSystem implements RobotSystem, Runnable {

	public static final double SHIFT_DELAY = 0.5;
	public static double Kp = -.08;
	public static double Ki = 0;
	public static double Kd = 0.0;
	public static final double SHIFTING_SPEED = 1.8;
	public static final double RAMPING = 0.5;
	
	private StraightDrivePID straightDrivePID;

	private RobotDrive3 wheels;

	private InputMethod inputMethod;
	
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

	/**
	 * Nothing is done in this constructor
	 */
	public WheelSystem() {

	}

	/* (non-Javadoc)
	 * @see com.bellaire.aerbot.systems.RobotSystem#init(com.bellaire.aerbot.Environment)
	 */
	public void init(Environment environment) {
		wheels = new RobotDrive3(1, 2);

		gearbox = new Relay(2);
		this.gearsOff();

		wheels.setSafetyEnabled(false);
		// this.motion = e.getMotionTracker();

		gyro = environment.getGyroSystem();
		accelerometer = environment.getAccelerometerSystem();
		inputMethod = environment.getInput();

		timer = new Timer();
		timer.start();
		
		straightDrivePID = new StraightDrivePID();
	}
	
	/**
	 * PIDSubsystem subclass for straight driving with gyro
	 */
	public class StraightDrivePID extends PIDSubsystem{
		
		/**
		 * passes Kp, Ki and Kd to super
		 */
		public StraightDrivePID(){
			super(Kp, Ki, Kd);
		}
		
		/* (non-Javadoc)
		 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#returnPIDInput()
		 */
		protected double returnPIDInput() {
			return gyro.getAngle();
		}

		/* (non-Javadoc)
		 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#usePIDOutput(double)
		 */
		protected void usePIDOutput(double value) {
			//SmartDashboard.putNumber("Straight drive PID: ", value);
			correctRotate = value;
		}

		/* (non-Javadoc)
		 * @see edu.wpi.first.wpilibj.command.Subsystem#initDefaultCommand()
		 */
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
	 * @param setpoint the target value
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

	/**
	 * Shifts to low gear
	 */
	public void gearsOff() {
		gear = 0;
		gearbox.set(Relay.Value.kOff);
	}

	/**
	 * Shifts to high gear
	 */
	public void gearsReverse() {
		gear = 1;
		gearbox.set(Relay.Value.kReverse);
	}

	/* (non-Javadoc)
	 * @see com.bellaire.aerbot.systems.RobotSystem#destroy()
	 */
	public void destroy() {
		gearbox.free();
		wheels.free();
	}
	
	/**
	 * @param moveValue current movement value
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

	/**
	 * @param outputMaginitude magnitude value
	 * @param curve curve value
	 */
	public void drive(double outputMaginitude, double curve) {
		wheels.drive(outputMaginitude, curve);
	}

	/**
	 * @param moveValue movement value from -1 to 1
	 * @param rotateValue rotation value from -1 to 1
	 */
	public void arcadeDrive(double moveValue, double rotateValue) {
		wheels.arcadeDrive(moveValue, rotateValue);
	}

	/**
	 * Uses PID and gyro to drive straight
	 * @param moveValue movement value
	 * @throws NullPointerException if gyro is null
	 */
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
		arcadeDrive(moveValue, correctRotate);
	}

	/**
	 * Shift gears automatically if necessary
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		move(inputMethod);
	}
	
	/**
	 * move according to input
	 * @param input input from driver
	 */
	public void move(InputMethod input) {
		currentLeftY = -input.getLeftY();

		currentRampY += (currentLeftY - currentRampY) * RAMPING;

		if (!disableStraightDrive && Math.abs(input.getLeftY()) > .15
				&& Math.abs(input.getRightX()) < .15)
			straightDrive(currentRampY * dir);
		else{
			arcadeDrive(currentRampY * dir, input.getRightX());
			straightDriving = false;
		}

		try{
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
		}catch (UnsatisfiedLinkError ex){

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
