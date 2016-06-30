package inputdata.azim.mostdesired;

import javax.swing.SwingWorker;

public class FileInfoReaderTask extends SwingWorker<Void, Void>  {
	
	private FilePropertyGetter filePropertyGetter;
	
	public FileInfoReaderTask(FilePropertyGetter filePropertyGetter) {
		
		
		this.filePropertyGetter = filePropertyGetter;
	}

	@Override
	protected Void doInBackground() throws Exception {
		
		filePropertyGetter.setProperty();
		return null;
	}

}
