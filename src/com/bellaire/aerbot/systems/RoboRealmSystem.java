package com.bellaire.aerbot.systems;

import com.bellaire.aerbot.Environment;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class RoboRealmSystem implements RobotSystem, ITableListener {
    
    private NetworkTable nt;
    
    public RoboRealmSystem() {
        
    }

    public void init(Environment e) {
        nt = NetworkTable.getTable("VisionTable");
        nt.addTableListener(this);
    }

    public void destroy() {
        
    }

    public void valueChanged(ITable itable, String key, Object obj, boolean isNew) {
        SmartDashboard.putString(key, obj.toString());
    }
    
}
