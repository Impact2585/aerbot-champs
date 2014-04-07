package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import com.bellaire.aerbot.custom.RobotDrive3;
import com.bellaire.aerbot.input.InputMethod;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WheelSystem extends PIDSubsystem implements RobotSystem {

    public static final double Kp = .1;
    public static final double Ki = 0;
    public static final double Kd = 0.0;
	
    private RobotDrive3 wheels;
    
    //private SonarSystem sonar;
    
    private Relay gearbox;
    private int gear = 0; // off
    private boolean gearPress = false, dirToggle = false;
    private int dir = 1;
    
    private double currentLeftY = 0, currentRightX = 0;
    private double currentRampY = 0, currentRampX = 0;
    
    private GyroSystem gyro;
	private boolean straightDriving;
	private double heading;
	private double correctRotate;

    public WheelSystem() {
        super(Kp,Ki,Kd);
    }

    public void init(Environment e) {
        wheels = new RobotDrive3(1, 2);
        
        gearbox = new Relay(2);
        this.gearsOff();

        wheels.setSafetyEnabled(false);
        //this.motion = e.getMotionTracker();
        
        gyro = e.getGyroSystem();
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
    
    public void arcadeDrive(double moveValue, double rotateValue){
    	wheels.arcadeDrive(moveValue, rotateValue);
    }
    
    public void straightDrive(double moveValue) {
        if (!straightDriving) {
          heading = gyro.getHeading();
        }
        straightDriving = true;
        if (Math.abs(heading - gyro.getHeading()) > 2 && !getPIDController().isEnable()) {
          setSetpoint(heading);
          enable();
        } else if (Math.abs(heading - gyro.getHeading()) <= 2 && getPIDController().isEnable()) {
          disable();
          correctRotate = 0;
        }
        wheels.arcadeDrive(moveValue, correctRotate);
      }

    public void move(InputMethod input) {
        currentLeftY = -input.getLeftY();
        currentRightX = input.getRightX();
        
        currentRampY += (currentLeftY - currentRampY) * (70d/300d);
        currentRampX += (currentRightX - currentRampX) * (70d/300d);
        
        /*if(currentLeftY == 0) {
            currentRampY = 0;
        }
        if(currentRightX == 0) {
            currentRampX = 0;
        }*/
        
        wheels.arcadeDrive(currentRampY * dir, input.getRightX());
        
        /*if(sonar.getDistance() < 36) {
            wheels.arcadeDrive(-currentRampY, -currentRampX);
        }*/
        
        //SmartDashboard.putNumber("Robot Heading", motion.getHeading());
        //SmartDashboard.putNumber("Robot Speed", motion.getSpeed());
        
        boolean shift = input.shift();
        if (!shift) {
            gearPress = false;
        }

        if (gearPress == false) {
            if (shift) {
                gearPress = true;
                if (gear == 0) {
                    this.gearsReverse();
                } else if (gear == 1) {
                    this.gearsOff();
                }
            }
        }

        if(!dirToggle) {
            if(input.directionToggle()) {
                dirToggle = true;
                dir *= -1;
            }
        }
        dirToggle = input.directionToggle();
    }
    
    protected double returnPIDInput() {
        return gyro.getAngle();
      }

      protected void usePIDOutput(double d) {
        SmartDashboard.putNumber("Straight drive PID: ", d);
        SmartDashboard.putNumber("current heading: ", gyro.getHeading());
        correctRotate = d;
      }

      protected void initDefaultCommand() {

      }

}
