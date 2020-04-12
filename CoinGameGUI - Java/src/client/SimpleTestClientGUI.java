package client;

import javax.swing.SwingUtilities;
import validate.Validator;
import view.UIBuilder;

public class SimpleTestClientGUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Validator.validate(true);
				new UIBuilder();
			}
		});
	}

}
