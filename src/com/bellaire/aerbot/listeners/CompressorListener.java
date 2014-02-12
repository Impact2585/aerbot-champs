package com.bellaire.aerbot.listeners;

import com.bellaire.aerbot.Environment;
import edu.wpi.first.wpilibj.Compressor;

public class CompressorListener implements Listener {

    private Compressor compressor;
    
    public void init(Environment env) {
        this.compressor = new Compressor(1, 1);
        this.compressor.start();
    }

    public boolean isComplete() {
       return false;
    }

    public boolean shouldExecute() {
        return true;
    }

    public void execute() {
        
    }
    
}
