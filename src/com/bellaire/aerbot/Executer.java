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
    
    /**
     * initializes listeners with given environment
     * @param environment
     */
    public Executer(Environment environment) {
        notRunning = new Vector();
        running = new Vector();

        MovementListener ml = new MovementListener();
        AutonomousListener al = new AutonomousListener();
        CompressorListener cl = new CompressorListener();
        ShooterListener sl = new ShooterListener();
        IntakeListener il = new IntakeListener();
        
        ml.init(environment);
        al.init(environment);
        cl.init(environment);
        sl.init(environment);
        il.init(environment);
        
        notRunning.addElement(ml);
        notRunning.addElement(al);
        notRunning.addElement(cl);
        notRunning.addElement(sl);
        notRunning.addElement(il);
    }
    
    /**
     * executes listeners which should run
     */
    public void update() {
        for(int i = 0; i < notRunning.size(); i++) {
            Listener listener = (Listener) notRunning.elementAt(i);
            if(listener.shouldExecute()) {
                this.execute(listener);
            }
        }
        
        for(int i = 0; i < running.size(); i++) {
            Listener listener = (Listener) running.elementAt(i);
            if(listener.isComplete()) {
                stop(listener);
            } else {
                listener.execute();
            }
        }
    }
    
    /**
     * adds listener to running and removes it from notRunning
     * @param listener
     */
    public void execute(Listener listener) {
        running.addElement(listener);
        notRunning.removeElement(listener);
    }
    
    /**
     * removes listener from running and adds it to notRunning
     * @param listener
     */
    public void stop(Listener listener) {
        notRunning.addElement(listener);
        running.removeElement(listener);
    }
    
    /**
     * stops all listeners
     */
    public void stopAll() {
        for(int i = 0; i < running.size(); i++) {
            Listener listener = (Listener) running.elementAt(i);
            this.stop(listener);
        }
    }
    
    /**
     * does nothing at the moment
     */
    public void onTeleop() {
        
    }
    
    /**
     * does nothing at the moment
     */
    public void onAutonomous() {
        
    }
    
}
