package view;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.ModelSubject;
import controller.Observer;

@SuppressWarnings("serial")
public class Summarypanel extends JPanel implements Observer {
	private JLabel lblplayername;
	private JLabel lblpoint;
	private JLabel lblbet;
	private ModelSubject subject;

	public Summarypanel(ModelSubject subject) {
		lblplayername = new JLabel("Player Name: ");
		lblpoint = new JLabel("Current Points: ");
		lblbet = new JLabel("Bet: ");
		add(lblplayername);
		add(lblpoint);
		add(lblbet);
		this.subject = subject;
		subject.addObserver(this);
		
	}
	
	//setting the playername to its needed value
	public void setLblplayername(String name) {
		lblplayername.setText("Player Name: " + name);
	}
	
	//setting the point to its needed value
	public void setLblpoint(int point) {
		lblpoint.setText("Current Points: " + point);
	}
	
	//setting the bet amount to its needed value
	public void setLblbet(int amount) {
		lblbet.setText("Bet: " + amount);
	}

	//tell the user that the roll was succesfull
	@Override
	public void updateResult(String playername) {
		if(playername != "House")
			JOptionPane.showMessageDialog(null,"Roll Sucessful, please roll all other player before rolling house to get final results","Roll Success",JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(null,"House Roll Sucessful, all win/losses have been calculated","Roll Success",JOptionPane.INFORMATION_MESSAGE);
	}
	
	//not needed
	@Override
	public void update(int dienum) {
		// NULL

	}

}
