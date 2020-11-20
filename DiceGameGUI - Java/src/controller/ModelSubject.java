package controller;

import java.util.ArrayList;
import java.util.List;

//using the observer pattern for the rolling and updating of the dice
public class ModelSubject {
	private List<Observer> observers = new ArrayList<Observer>();
	private int dievalue;
	private String playername;
	private int playerpoint;
	private int playerbet;
	
	//getter for dievalue
	public int getdievalue() {
		return dievalue;
	}
	
	//getter for playername
	public String getPlayername() {
		return playername;
	}
	
	//getter for playerpoint
	public int getPlayerpoint() {
		return playerpoint;
	}

	//getter for playerbet
	public int getPlayerbet() {
		return playerbet;
	}
	
	//set the die dievalue number and notfiy the people that need to know
	public void setDienum(int dienum, int dievalue) {
		this.dievalue = dievalue;
		notifyObservers(dienum);
	}
	
	//adding a observer to the arraylist
	public void addObserver(Observer observer) {
		observers.add(observer);
	}
	
	//notify all observers in the arraylist and updates
	public void notifyObservers(int dienum) {
		for(Observer ob: observers)
			ob.update(dienum);
	}
	
	//set the player results 
	public void setResult(String playername, int playerpoint,int playerbet) {
		this.playername = playername;
		this.playerbet = playerbet;
		this.playerpoint = playerpoint;
		notifyObserversResult();
	}
	
	//notify all observers in the arraylist and updates
	public void notifyObserversResult() {
		for(Observer ob: observers) {
			ob.updateResult(playername);
		}
	}
}
