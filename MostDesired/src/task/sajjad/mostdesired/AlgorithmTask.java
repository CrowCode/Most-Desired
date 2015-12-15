package task.sajjad.mostdesired;

import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import algorithm.azim.mostdesired.Algorithm;

/**
 * 
 * @author sajjad This is a swing worker class in order to run the procedure of
 *         MOST DESIRED Algorithm in swing background thread.
 */

public class AlgorithmTask extends SwingWorker<Void, Void> {

	private String filename;
	private int k;
	private int error;
	private Double[] sssResult;
	private ArrayList<Integer> mostWanted;

	private JTextArea logTxtArea;

	public AlgorithmTask(String filename, int k, int error) {
		this.filename = filename;
		this.k = k;
		this.error = error;
		this.sssResult = new Double[1];
	}

	@Override
	protected Void doInBackground() throws Exception {

		mostWanted = Algorithm.runAlgorithm(filename, k, error, sssResult, new ProgressMonitor() {

			@Override
			public void progressUpdated(int progress) {
				setProgress(progress);
			}
		});

		return null;
	}

	public void setOIComponent(JTextArea logTxtArea) {
		this.logTxtArea = logTxtArea;
	}

	@Override
	public void done() {
		Toolkit.getDefaultToolkit().beep();
		logTxtArea.append("Done!\n");
		logTxtArea.append("[>] " + k + " MOST INFLUENTIAL NODES ARE:\n");
		for (int i = 0; i < mostWanted.size(); i++) {
			logTxtArea.append("[" + i + "] : " + mostWanted.get(i) + "\t");
		}
		logTxtArea.append("[>] sss for these nodes are:\n\t" + sssResult[0] + "\n");
	}

}
