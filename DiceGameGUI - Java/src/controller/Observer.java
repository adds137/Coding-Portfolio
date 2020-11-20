package controller;

public interface Observer {
	//update die
	public abstract void update(int dienum);
	
	//update result
	public abstract void updateResult(String playername);
}
