package client;

import javax.swing.SwingUtilities;
import validate.Validator;
import view.UIBuilder;

public class SimpleTestClientGUI {
	public static void main(String[] args) {
	// creates the UI on a separate thread 
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Validator.validate(true);
				new UIBuilder();
			}
		});
	}
}
