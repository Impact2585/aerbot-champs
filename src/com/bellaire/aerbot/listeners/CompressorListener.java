package com.bellaire.aerbot.listeners;

import com.bellaire.aerbot.Environment;

import edu.wpi.first.wpilibj.Compressor;

public class CompressorListener implements Listener {

    private Compressor compressor;
    
    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#init(com.bellaire.aerbot.Environment)
     */
    public void init(Environment env) {
        this.compressor = new Compressor(2, 1);
        this.compressor.start();
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#isComplete()
     */
    public boolean isComplete() {
       return false;
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#shouldExecute()
     */
    public boolean shouldExecute() {
        return true;
    }

    /* (non-Javadoc)
     * @see com.bellaire.aerbot.listeners.Listener#execute()
     */
    public void execute() {
        
    }
    
}
