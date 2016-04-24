package view.sajjad.mostdesired;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;

public class Intro extends JFrame {

	private Color myOrange = new Color(240, 127, 7);
	private Color myCyan = new Color(60, 109, 130);
	private Color darkGray = new Color(55, 55, 55);

	private JProgressBar pBar;
	private Timer timer;

	public Intro() throws HeadlessException {

		setSize(400, 300);
		setLayout(new BorderLayout());
		setUndecorated(true);
		setLocationRelativeTo(null);

		setupTimer();
		setupProgressBar();
		
		IntroPanel p = new IntroPanel();
		add(p);
		

		setVisible(true);
	}

	private void setupProgressBar() {

		pBar = new JProgressBar(0, 100);
		pBar.setBorderPainted(false);
		pBar.setStringPainted(true);
		pBar.setString("");
		pBar.setBackground(myCyan);
		pBar.setForeground(myOrange);
		pBar.setPreferredSize(new Dimension(getContentPane().getWidth() - 50, 6));
		UIManager.put("pBar.selectionBackground", myOrange);
		UIManager.put("pBar.selectionBackground", Color.white);
		pBar.setValue(0);
		add(pBar, BorderLayout.SOUTH);
	}

	private void setupTimer() {
		timer = new Timer(500, listener);
		timer.start();
	}

	ActionListener listener = new ActionListener() {
		int counter = 10;

		public void actionPerformed(ActionEvent ae) {
			counter = counter + 10;
			pBar.setValue(counter);
			if (counter > 100) {
				new MainFrame();
				timer.stop();
				dispose();
			}
		}
	};
}

class IntroPanel extends JPanel {
	
	private Color myOrange = new Color(240, 127, 7);
	private Color myCyan = new Color(60, 109, 130);
	private Color darkGray = new Color(55, 55, 55);

	@Override
	public void paint(Graphics g) {
		
		g.setFont(new Font("Garamond", Font.BOLD , 40));
		g.setColor(myOrange);
		
		g.drawString("M", 90, 100);
		
		g.setFont(new Font("Garamond", Font.BOLD , 20));
		g.setColor(myCyan);
		
		g.drawString("ost", 125, 100);
		
		g.setFont(new Font("Garamond", Font.BOLD , 60));
		g.setColor(myOrange);
		
		g.drawString("D", 180, 120);

		g.setFont(new Font("Garamond", Font.BOLD , 30));
		g.setColor(myCyan);
		
		g.drawString("e", 220, 120);
		g.setColor(darkGray);
		g.setFont(new Font("Garamond", Font.BOLD , 50));
		
		g.drawString("s", 233, 125);
		
		g.setFont(new Font("Garamond", Font.BOLD , 30));
		
		g.drawString("i", 259, 120);
		
		g.drawString("r", 265, 120);
		g.setColor(myCyan);
		g.drawString("e", 277, 120);
		g.setColor(darkGray);
		g.setFont(new Font("Garamond", Font.BOLD , 50));
		
		g.drawString("d", 290, 120);
		
		
	}
	
	
	
}