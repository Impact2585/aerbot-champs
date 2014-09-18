package com.bellaire.aerbot;


public interface Executer extends Initializable{
	
	/**
	 * Execute actions which should happen every 20 or so milliseconds
	 */
	public void execute();
	
}
