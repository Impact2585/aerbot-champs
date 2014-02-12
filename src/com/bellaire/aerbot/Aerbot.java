package com.bellaire.aerbot;


import com.bellaire.aerbot.controllers.AutonomousController;
import com.bellaire.aerbot.controllers.MotionTracker;
import com.bellaire.aerbot.controllers.OperatorController;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.camera.AxisCamera;

public class Aerbot extends IterativeRobot {
    
    private Environment environment;
    private Executer exec;
    
    private AutonomousController autonomous;
    private OperatorController operator;

    public void robotInit() {
        this.environment = new Environment(this);
        this.exec = new Executer(environment);
    }

    public void autonomousInit() {
        exec.onAutonomous();
        autonomous = new AutonomousController(environment, exec);
    }

    public void autonomousPeriodic() {
        //autonomous.update();
    }
    
    public void teleopInit() {
        exec.onTeleop();
        operator = new OperatorController(environment, exec);
    }

    public void teleopPeriodic() {
        operator.update();
    }
    
    public void testPeriodic() {
    
    }
    
}
