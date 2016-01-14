package org.impact2585.aerbot.systems;

import org.impact2585.aerbot.Environment;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class RoboRealmSystem implements RobotSystem, ITableListener {
    
    private NetworkTable nt;
    
    /**
     * Nothing is done in this constructor
     */
    public RoboRealmSystem() {
        
    }

    /* (non-Javadoc)
     * @see org.impact2585.aerbot.systems.RobotSystem#init(org.impact2585.aerbot.Environment)
     */
    public void init(Environment environment) {
        nt = NetworkTable.getTable("VisionTable");
        nt.addTableListener(this);
    }

    /* (non-Javadoc)
     * @see org.impact2585.aerbot.systems.RobotSystem#destroy()
     */
    public void destroy() {
        
    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.tables.ITableListener#valueChanged(edu.wpi.first.wpilibj.tables.ITable, java.lang.String, java.lang.Object, boolean)
     */
    public void valueChanged(ITable itable, String key, Object obj, boolean isNew) {
        SmartDashboard.putString(key, obj.toString());
    }
    
}
