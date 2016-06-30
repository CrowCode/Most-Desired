package main.sajjad.mostdesired;

import javax.swing.SwingUtilities;

import view.sajjad.mostdesired.Intro;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				new Intro();
				// new MainFrame();

			}
		});

	}

}
