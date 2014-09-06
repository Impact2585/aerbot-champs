package com.bellaire.aerbot;


import edu.wpi.first.wpilibj.IterativeRobot;

public class Aerbot extends IterativeRobot {
    
    private Environment environment;
    private Executer executer;

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#robotInit()
     */
    public void robotInit() {
        this.environment = new Environment(this);
    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#autonomousInit()
     */
    public void autonomousInit() {
    	executer = new AutonomousExecuter(environment);
    }
    
    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#autonomousPeriodic()
     */
    public void autonomousPeriodic() {
    	executer.execute();
    }
    
    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#teleopInit()
     */
    public void teleopInit() {
    	executer = new TeleopExecuter(environment);
    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#teleopPeriodic()
     */
    public void teleopPeriodic() {
    	executer.execute();
    }
    
    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#testPeriodic()
     */
    public void testPeriodic() {
    
    }

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#disabledInit()
	 */
	public void disabledInit() {

	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#disabledPeriodic()
	 */
	public void disabledPeriodic() {

	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.RobotBase#free()
	 */
	public void free() {
		environment.destroy();
		super.free();
	}
    
}
