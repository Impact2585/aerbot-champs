package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import com.bellaire.aerbot.custom.RobotDrive3;
import com.bellaire.aerbot.input.InputMethod;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WheelSystem extends PIDSubsystem implements RobotSystem {

	public static final double SHIFT_DELAY = 0.5;
	public static double Kp = -.08;
	public static double Ki = 0;
	public static double Kd = 0.0;
	public static final double SHIFTING_SPEED = 1.8;
	public static final double RAMPING = 0.5;

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
	private boolean automatic = false;

	private boolean disableStraightDrive = true;
	private boolean disableStraightDrivePressed;

	public WheelSystem() {
		super(Kp, Ki, Kd);
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
			getPIDController().reset();
		}
		straightDriving = true;
		// correct error in heading if error more than 2 degrees
		if (Math.abs(heading - gyro.getAngle()) > 2
				&& !getPIDController().isEnable()) {
			setSetpoint(heading);
			enable();
		} else if (Math.abs(heading - gyro.getAngle()) <= 2
				&& getPIDController().isEnable()) {
			disable();
			correctRotate = 0;
		}
		// TODO add in signnum(currentY) since Java ME lacks it
		arcadeDrive(moveValue, dir * correctRotate);
	}

	public void automaticGearShift() {
		//only autoshift once every half second
		if(timer.get() > SHIFT_DELAY){
			//shift based on speed from accelerometer and only when on the wrong gear
			if (Math.abs(accelerometer.getSpeed()) > SHIFTING_SPEED && gear == 0 && Math.abs(currentLeftY) > .75) {
				// also only shift into high gear if throttle is high
				gearsOff();
				timer.reset();
			} else if (Math.abs(accelerometer.getSpeed()) <= SHIFTING_SPEED && gear == 1) {
				gearsReverse();
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
			SmartDashboard.putNumber("Straight drive setpoint: ", getSetpoint());
			SmartDashboard.putData("Straight drive PID: ", getPIDController());//PID tuning in smart dashboard
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

	protected double returnPIDInput() {
		return gyro.getAngle();
	}

	protected void usePIDOutput(double value) {
		SmartDashboard.putNumber("Straight drive PID: ", value);
		correctRotate = value;
	}

	protected void initDefaultCommand() {

	}

}
