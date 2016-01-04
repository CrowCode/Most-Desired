package task.sajjad.mostdesired;

import java.awt.Toolkit;

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
	private AlgorithmFinish finish;
	


	public AlgorithmTask(AlgorithmFinish finish, String filename, int k, int error) {
		this.filename = filename;
		this.k = k;
		this.error = error;
		this.sssResult = new Double[1];
		this.finish = finish;
	}

	@Override
	protected Void doInBackground() throws Exception {

		Algorithm.runAlgorithm(filename, k, error, sssResult, new ProgressMonitor() {

			@Override
			public void progressUpdated(int progress) {
				setProgress(progress);
			}
		});

		return null;
	}



	@Override
	public void done() {
		
		Toolkit.getDefaultToolkit().beep();
		finish.finish();
		
		
	}

}
