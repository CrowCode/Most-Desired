package algorithm.azim.mostdesired;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 * 
 * @author sajjad This is a swing worker class in order to run the procedure of
 *         MOST DESIRED Algorithm in swing background thread.
 */

public class AlgorithmTask extends SwingWorker<Integer, String> {

	private String filename;
	private int k;
	private int error;
	private Double[] sssResult;
	private AlgorithmFinishMonitor finish;
	private ArrayList<Integer> solution;
	private JTextArea consoleTextArea;
	


	public AlgorithmTask(AlgorithmFinishMonitor finish, String filename, int k, int error, JTextArea consoleTextArea) {
		this.filename = filename;
		this.k = k;
		this.error = error;
		this.sssResult = new Double[1];
		this.consoleTextArea = consoleTextArea;
		this.finish = finish;
	}

	@Override
	protected Integer doInBackground() throws Exception {
		
		setProgress(0);
		

		solution = Algorithm.runAlgorithm(filename, k, error, sssResult, new AlgorithmProgressMonitor() {

			@Override
			public void progressUpdated(int progress) {
				setProgress(progress);
			}

			@Override
			public void logUpdate(String log) {
				publish(log);
			}
			
			
		});
		
		setProgress(100);
		publish("[>] THE " + k + " MOST INFLUENTIAL NODES ARE:");
		publish("\t" + solution.toString());

		return null;
	}


	@Override
	protected void process(List<String> chunks) {
		// TODO Auto-generated method stub
		//super.process(chunks);
		
		for (String st: chunks) {
			st = st+"\n";
			consoleTextArea.append(st);
		}
		
	}

	@Override
	public void done() {
		
		
		finish.finish(solution);
		
		
	}

}
