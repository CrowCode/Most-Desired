package view.sajjad.mostdesired;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
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
