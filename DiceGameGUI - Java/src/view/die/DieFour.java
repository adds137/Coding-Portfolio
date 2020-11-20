package view.die;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DieFour extends JPanel{
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		this.setSize(417, 417);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5));
		g2.drawRect(10,10,400,400);
		g2.fillOval(50, 270, 100, 100);
		g2.fillOval(270, 30, 100, 100);
		g2.fillOval(50, 30, 100, 100);
		g2.fillOval(270, 270, 100, 100);
	}
}

