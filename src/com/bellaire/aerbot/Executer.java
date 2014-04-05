package com.bellaire.aerbot;

import com.bellaire.aerbot.listeners.AutonomousListener;
import com.bellaire.aerbot.listeners.CompressorListener;
import com.bellaire.aerbot.listeners.IntakeListener;
import com.bellaire.aerbot.listeners.MovementListener;
import com.bellaire.aerbot.listeners.Listener;
import com.bellaire.aerbot.listeners.ShooterListener;
import java.util.Vector;

public class Executer {
    
    private final Vector notRunning, running;
    
    public Executer(Environment e) {
        notRunning = new Vector();
        running = new Vector();

        MovementListener ml = new MovementListener();
        AutonomousListener al = new AutonomousListener();
        CompressorListener cl = new CompressorListener();
        ShooterListener sl = new ShooterListener();
        IntakeListener il = new IntakeListener();
        
        ml.init(e);
        al.init(e);
        cl.init(e);
        sl.init(e);
        il.init(e);
        
        notRunning.addElement(ml);
        notRunning.addElement(al);
        notRunning.addElement(cl);
        notRunning.addElement(sl);
        notRunning.addElement(il);
    }
    
    public void update() {
        for(int i = 0; i < notRunning.size(); i++) {
            Listener l = (Listener) notRunning.elementAt(i);
            if(l.shouldExecute() && this.canExecute(l)) {
                this.execute(l);
            }
        }
        
        for(int i = 0; i < running.size(); i++) {
            Listener l = (Listener) running.elementAt(i);
            if(l.isComplete()) {
                stop(l);
            } else {
                l.execute();
            }
        }
    }
    
    public void execute(Listener listener) {
        running.addElement(listener);
        notRunning.removeElement(listener);
    }
    
    public boolean canExecute(Listener listener) {
        return true;
    }
    
    public void stop(Listener listener) {
        notRunning.addElement(listener);
        running.removeElement(listener);
    }
    
    public void stopAll() {
        for(int i = 0; i < running.size(); i++) {
            Listener l = (Listener) running.elementAt(i);
            this.stop(l);
        }
    }
    
    public void onTeleop() {
        
    }
    
    public void onAutonomous() {
        
    }
    
}
