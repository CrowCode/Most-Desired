package view.sajjad.mostdesired;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import InputData.azim.mostdesired.DataReader;
import task.sajjad.mostdesired.AlgorithmFinish;
import task.sajjad.mostdesired.AlgorithmTask;

/***
 * 
 * @author sajjad
 * 
 *         This is the main frame of the application where user has access to
 *         choose different input file, control the error of algorithm, change
 *         the number of influential nodes and ...
 *         =====================================================================
 *         ==============================Features :=============================
 *         =====================================================================
 *         <ul>
 *         <li>The right side of the window there exists a text area which is
 *         configured to be our Console. Despite it will behave like an visible
 *         log window to user and programmer which will show all errors and
 *         exceptions could possible be thrown during the compilation of
 *         application.</li>
 *         <li>All the error handled by the programmer to avoid unwelcome
 *         accident. They will be shown either in console of the application or
 *         by error dialog in application.</li>
 *         </ul>
 */

public class MainFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	// private JPanel mainPanel;
	private JPanel contentPane;
	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	private JButton browsBtn;
	private JButton runAlgorithmBtn;
	private JButton showGraphBtn;
	private JLabel inputFileLabel;
	private JLabel fNameLabel;
	private JLabel fileNameLabel;
	private JLabel fSizeLabel;
	private JLabel fileSizeLabel;
	private JLabel fLineLabel;
	private JLabel fileLineLabel;
	private JLabel fIdLabel;
	private JLabel fileIdLabel;
	private JLabel fNodesLabel;
	private JLabel fileNodesLabel;
	private JLabel fKLabel;
	private JLabel fErrorLabel;
	private JTextField fileInputField;
	private JTextArea consoleTextArea;
	private JSpinner kSpinner;
	private JSpinner errorSpinner;
	private JProgressBar pBar;
	private BorderLayout bl;
	private GridBagConstraints GBC_left;
	private GridBagConstraints GBC_bottom;
	// private GridBagConstraints GBC_center;
	//
	// private GridBagConstraints constraints = new GridBagConstraints();

	private Color myOrange = new Color(240, 127, 7);
	private Color myCyan = new Color(60, 109, 130);
	private Color darkGray = new Color(55, 55, 55);

	private JFileChooser fc;
	private File file;
	private JFrame VisualGraph;
	
	//private ArrayList<Integer> solution = new ArrayList<>();
	
	/*
	 * Necessary fields in order to convert a text area to console
	 */
	private Thread reader;
	private Thread reader2;
	private boolean quit;
	private final PipedInputStream pin = new PipedInputStream();
	private final PipedInputStream pin2 = new PipedInputStream();

	public MainFrame() {
		initializeBasics();
		init();
		consInit();
	}

	void init() {

		fc = new JFileChooser();

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		// gl = new GridLayout(1, 2);
		// gl.setHgap(5);
		// contentPane.setLayout(gl);
		contentPane.setBackground(Color.BLACK);

		bl = new BorderLayout(5, 5);
		contentPane.setLayout(bl);

		leftPanel = new JPanel();
		leftPanel.setBackground(Color.white);
		initializeLeftPanel(leftPanel);

		centerPanel = new JPanel();
		centerPanel.setBackground(Color.white);
		initializeCenterPanel(centerPanel);

		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.white);
		initializeBottomPanel(bottomPanel);

		contentPane.add(leftPanel, BorderLayout.WEST);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);
		setContentPane(contentPane);

	}

	public void initializeBasics() {
		setSize(800, 400);
		setTitle("MOST DESIRED");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initializeLeftPanel(JPanel lPanel) {

		lPanel.setLayout(new GridBagLayout());
		GBC_left = new GridBagConstraints();
		GBC_left.fill = GridBagConstraints.BOTH;
		GBC_left.insets = new Insets(2, 2, 2, 2);// padding

		/** Row 0 **/
		inputFileLabel = new JLabel("[PROPERTIES]", (int) CENTER_ALIGNMENT);
		// inputFileLabel.setAlignmentX(CENTER_ALIGNMENT);
		inputFileLabel.setPreferredSize(new Dimension(200, 20));
		inputFileLabel.setForeground(Color.black);
		addCompToLeftPanel(inputFileLabel, 0, 0, 6, 1);

		/** Row 1 **/
		browsBtn = new JButton("BROWS");
		browsBtn.setFont(new Font("Arial", Font.BOLD, 10));
		browsBtn.setBackground(myOrange);
		browsBtn.setForeground(Color.white);
		browsBtn.setBorderPainted(false);
		browsBtn.setFocusPainted(false);
		browsBtn.setPreferredSize(new Dimension(80, 20));
		browsBtn.addActionListener(browsAct);
		addCompToLeftPanel(browsBtn, 0, 1, 2, 1);

		fileInputField = new JTextField();
		fileInputField.setText("file.txt");
		fileInputField.setPreferredSize(new Dimension(120, 20));
		fileInputField.setBackground(Color.gray);
		fileInputField.setForeground(Color.white);
		fileInputField.setFont(new Font("Courier", Font.BOLD, 12));
		fileInputField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		addCompToLeftPanel(fileInputField, 2, 1, 4, 1);

		/** Row 2 **/
		fNameLabel = new JLabel("FILE NAME:");
		fNameLabel.setPreferredSize(new Dimension(80, 20));
		fNameLabel.setForeground(Color.black);
		addCompToLeftPanel(fNameLabel, 0, 2, 2, 1);

		fileNameLabel = new JLabel("[...]", (int) CENTER_ALIGNMENT);
		fileNameLabel.setPreferredSize(new Dimension(120, 20));
		fileNameLabel.setForeground(myCyan);
		addCompToLeftPanel(fileNameLabel, 2, 2, 4, 1);

		/** Row 3 **/
		fSizeLabel = new JLabel("FILE SIZE:");
		fSizeLabel.setPreferredSize(new Dimension(80, 20));
		fSizeLabel.setForeground(Color.black);
		addCompToLeftPanel(fSizeLabel, 0, 3, 2, 1);

		fileSizeLabel = new JLabel("[...]", (int) CENTER_ALIGNMENT);
		fileSizeLabel.setPreferredSize(new Dimension(120, 20));
		fileSizeLabel.setForeground(myCyan);
		addCompToLeftPanel(fileSizeLabel, 2, 3, 4, 1);

		/** Row 4 **/
		fLineLabel = new JLabel("N LINES:");
		fLineLabel.setPreferredSize(new Dimension(80, 20));
		fLineLabel.setForeground(Color.black);
		addCompToLeftPanel(fLineLabel, 0, 4, 2, 1);

		fileLineLabel = new JLabel("[...]", (int) CENTER_ALIGNMENT);
		fileLineLabel.setPreferredSize(new Dimension(120, 20));
		fileLineLabel.setForeground(myCyan);
		addCompToLeftPanel(fileLineLabel, 2, 4, 4, 1);

		/** Row 5 **/
		fIdLabel = new JLabel("MAX ID:");
		fIdLabel.setPreferredSize(new Dimension(80, 20));
		fIdLabel.setForeground(Color.black);
		addCompToLeftPanel(fIdLabel, 0, 5, 2, 1);

		fileIdLabel = new JLabel("[...]", (int) CENTER_ALIGNMENT);
		fileIdLabel.setPreferredSize(new Dimension(120, 20));
		fileIdLabel.setForeground(myCyan);
		addCompToLeftPanel(fileIdLabel, 2, 5, 4, 1);

		/** Row 6 **/
		fNodesLabel = new JLabel("N NODES:");
		fNodesLabel.setPreferredSize(new Dimension(80, 20));
		fNodesLabel.setForeground(Color.black);
		addCompToLeftPanel(fNodesLabel, 0, 6, 2, 1);

		fileNodesLabel = new JLabel("[...]", (int) CENTER_ALIGNMENT);
		fileNodesLabel.setPreferredSize(new Dimension(120, 20));
		fileNodesLabel.setForeground(myCyan);
		addCompToLeftPanel(fileNodesLabel, 2, 6, 4, 1);

		/** Row 7 **/
		runAlgorithmBtn = new JButton("FIND MOSTWANTED");
		runAlgorithmBtn.setFont(new Font("Arial", Font.BOLD, 10));
		runAlgorithmBtn.setBackground(myOrange);
		runAlgorithmBtn.setForeground(Color.white);
		runAlgorithmBtn.setBorderPainted(false);
		runAlgorithmBtn.setFocusPainted(false);
		runAlgorithmBtn.setPreferredSize(new Dimension(200, 20));
		runAlgorithmBtn.addActionListener(runAlgorithmAct);
		addCompToLeftPanel(runAlgorithmBtn, 0, 7, 6, 1);

		/** Row 8 **/
		showGraphBtn = new JButton("SHOW GRAPH");
		showGraphBtn.setFont(new Font("Arial", Font.BOLD, 10));
		showGraphBtn.setBackground(myCyan);
		showGraphBtn.setForeground(Color.white);
		showGraphBtn.setBorderPainted(false);
		showGraphBtn.setFocusPainted(false);
		showGraphBtn.setPreferredSize(new Dimension(200, 20));
		showGraphBtn.addActionListener(showGraphAct);
		addCompToLeftPanel(showGraphBtn, 0, 8, 6, 1);

		/** Row 9 **/
		fKLabel = new JLabel("K:");
		fKLabel.setPreferredSize(new Dimension(80, 20));
		fKLabel.setForeground(Color.black);
		addCompToLeftPanel(fKLabel, 0, 9, 2, 1);

		SpinnerModel kSpinnerModel = new SpinnerNumberModel(3, 0, 100, 1);
		kSpinner = new JSpinner(kSpinnerModel);
		kSpinner.setPreferredSize(new Dimension(120, 20));
		kSpinner.getEditor().getComponent(0).setBackground(myCyan);
		kSpinner.getEditor().getComponent(0).setForeground(Color.white);
		kSpinner.getComponent(0).setBackground(myOrange);
		kSpinner.getComponent(1).setBackground(myOrange);
		kSpinner.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		addCompToLeftPanel(kSpinner, 2, 9, 4, 1);

		/** Row 10 **/
		fErrorLabel = new JLabel("ERROR:");
		fErrorLabel.setPreferredSize(new Dimension(80, 20));
		fErrorLabel.setForeground(Color.black);
		addCompToLeftPanel(fErrorLabel, 0, 10, 2, 1);

		SpinnerModel errorSpinnerModel = new SpinnerNumberModel(10, 0, 100, 1);
		errorSpinner = new JSpinner(errorSpinnerModel);
		errorSpinner.setPreferredSize(new Dimension(120, 20));
		errorSpinner.getEditor().getComponent(0).setBackground(myCyan);
		errorSpinner.getEditor().getComponent(0).setForeground(Color.white);
		errorSpinner.getComponent(0).setBackground(myOrange);
		errorSpinner.getComponent(1).setBackground(myOrange);
		errorSpinner.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		addCompToLeftPanel(errorSpinner, 2, 10, 4, 1);

		addWindowListener(exitListener);

	}

	private void initializeBottomPanel(JPanel bPanel) {

		bPanel.setLayout(new GridBagLayout());
		GBC_bottom = new GridBagConstraints();
		GBC_bottom.fill = GridBagConstraints.BOTH;
		GBC_bottom.insets = new Insets(0, 0, 0, 0);// padding

		pBar = new JProgressBar(0, 100);
		pBar.setBorderPainted(false);
		pBar.setStringPainted(true);
		pBar.setString("PROGRESS");
		pBar.setBackground(myCyan);
		pBar.setForeground(myOrange);
		pBar.setPreferredSize(new Dimension(getContentPane().getWidth(), 20));
		UIManager.put("pBar.selectionBackground", myOrange);
		UIManager.put("pBar.selectionBackground", Color.white);
		addCompToBottomPanel(pBar, 0, 0, 1, 1);

	}

	private void initializeCenterPanel(JPanel cPanel) {

		cPanel.setLayout(new FlowLayout(0, 0, 0));

		consoleTextArea = new JTextArea(30, 55);
		consoleTextArea.setSize(getContentPane().getPreferredSize());
		consoleTextArea.setMinimumSize(new Dimension(100, 100));
		consoleTextArea.setBackground(darkGray);
		consoleTextArea.setForeground(myOrange);
		consoleTextArea.setText("\n\tOUTPUT:");

		DefaultCaret caret = (DefaultCaret) consoleTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane sPane = new JScrollPane(consoleTextArea);
		sPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sPane.setPreferredSize(new Dimension(573, 357));
		sPane.setViewportView(consoleTextArea);
		cPanel.add(sPane);
	}

	private void addCompToLeftPanel(JComponent comp, int x, int y, int width, int height) {

		GBC_left.gridx = x; // column
		GBC_left.gridy = y; // row
		GBC_left.gridwidth = width;
		GBC_left.gridheight = height;
		leftPanel.add(comp, GBC_left);
	}

	private void addCompToBottomPanel(JComponent comp, int x, int y, int width, int height) {

		GBC_bottom.gridx = x; // column
		GBC_bottom.gridy = y; // row
		GBC_bottom.gridwidth = width;
		GBC_bottom.gridheight = height;
		bottomPanel.add(comp, GBC_bottom);
	}

	/*
	 * private void addCompToCenterPanel(JComponent comp, int x, int y, int
	 * width, int height) {
	 * 
	 * GBC_center.gridx = x; // column GBC_center.gridy = y; // row
	 * GBC_center.gridwidth = width; GBC_center.gridheight = height;
	 * centerPanel.add(comp, GBC_center); }
	 */

	ActionListener browsAct = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == browsBtn) {

				int GET_FILE = fc.showOpenDialog(MainFrame.this);

				if (GET_FILE == JFileChooser.APPROVE_OPTION) {

					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					file = fc.getSelectedFile();
					fileNameLabel.setText(file.getName());
					fileInputField.setText(file.getPath());

					/*
					 * Get the input file size and set the labels
					 */
					getFileSize(file);

					(new FileInfoReaderTask(fp)).execute();

				}
			}

		}
	};
	ActionListener runAlgorithmAct = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == runAlgorithmBtn) {

				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				consoleTextArea.setText("\n\tOUTPUT:\n\t");
				int k = Integer.parseInt(kSpinner.getValue().toString());
				int error = Integer.parseInt(errorSpinner.getValue().toString());
				if (!fileNameLabel.getText().equals("") && !fileNameLabel.getText().equals("[...]")) {
					AlgorithmTask task = new AlgorithmTask(af, fileInputField.getText(), k, error);

					task.addPropertyChangeListener(PropertyChangeListener);
					task.execute();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Please choose a file first!", "Dialog",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	};
	ActionListener showGraphAct = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == showGraphBtn) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {

						if (!fileNameLabel.getText().equals("") && !fileNameLabel.getText().equals("[...]")) {
							try {
									VisualGraph = new ViewGraphFrame(fileNameLabel.getText(), new CloseListener() {
									@Override
									public void doClose(JFrame frame) {

										frame.dispose();
										dispose();
									}
								});
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(new JFrame(), "Please choose a file first!", "Dialog",
									JOptionPane.ERROR_MESSAGE);
						}

					}
				});
			}

		}
	};
	PropertyChangeListener PropertyChangeListener = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if ("progress" == evt.getPropertyName()) {
				int progress = (Integer) evt.getNewValue();
				pBar.setValue(progress);
				// taskOutput.append(String.format(
				// "Completed %d%% of task.\n", task.getProgress()));
			}

		}
	};

	@Override
	public synchronized void run() {

		try {
			while (Thread.currentThread() == reader) {
				try {
					this.wait(100);
				} catch (InterruptedException ie) {
				}
				if (pin.available() != 0) {
					String input = this.readLine(pin);
					consoleTextArea.append(input);
				}
				if (quit)
					return;
			}

			while (Thread.currentThread() == reader2) {
				try {
					this.wait(100);
				} catch (InterruptedException ie) {
				}
				if (pin2.available() != 0) {
					String input = this.readLine(pin2);
					consoleTextArea.append(input);
				}
				if (quit)
					return;
			}
		} catch (Exception e) {
			consoleTextArea.append("\nConsole reports an Internal error.");
			consoleTextArea.append("The error is: " + e);
		}

	}

	public synchronized String readLine(PipedInputStream in) throws IOException {
		String input = "";
		do {
			int available = in.available();
			if (available == 0)
				break;
			byte b[] = new byte[available];
			in.read(b);
			input = input + new String(b, 0, b.length);
		} while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit);
		return input;
	}

	/**
	 * The method for convert textArea to console initialization.
	 */
	private void consInit() {
		try {
			PipedOutputStream pout = new PipedOutputStream(this.pin);
			System.setOut(new PrintStream(pout, true));
		} catch (java.io.IOException io) {
			consoleTextArea.append("Couldn't redirect STDOUT to this console\n" + io.getMessage());
		} catch (SecurityException se) {
			consoleTextArea.append("Couldn't redirect STDOUT to this console\n" + se.getMessage());
		}

		try {
			PipedOutputStream pout2 = new PipedOutputStream(this.pin2);
			System.setErr(new PrintStream(pout2, true));
		} catch (java.io.IOException io) {
			consoleTextArea.append("Couldn't redirect STDERR to this console\n" + io.getMessage());
		} catch (SecurityException se) {
			consoleTextArea.append("Couldn't redirect STDERR to this console\n" + se.getMessage());
		}

		quit = false; // signals the Threads that they should exit

		// Starting two separate threads to read from the PipedInputStreams
		//
		reader = new Thread(this);
		reader.setDaemon(true);
		reader.start();

		reader2 = new Thread(this);
		reader2.setDaemon(true);
		reader2.start();
	}

	WindowListener exitListener = new WindowAdapter() {

		public synchronized void windowClosed(WindowEvent evt) {
			quit = true;
			this.notifyAll(); // stop all threads
			try {
				reader.join(1000);
				pin.close();
			} catch (Exception e) {
			}
			try {
				reader2.join(1000);
				pin2.close();
			} catch (Exception e) {
			}
			System.exit(0);
		}

		public synchronized void windowClosing(WindowEvent evt) {
			setVisible(false); // default behavior of JFrame
			dispose();
			
		}

	};

	private void getFileSize(File file) {

		Double bytes = (double) file.length();
		Double kilobytes = (bytes / 1024);
		Double megabytes = (kilobytes / 1024);

		DecimalFormat df = new DecimalFormat("0.00");

		if (df.format(kilobytes).equals("0.00"))
			fileSizeLabel.setText(df.format(bytes) + "  B");
		else if (df.format(megabytes).equals("0.00"))
			fileSizeLabel.setText(df.format(kilobytes) + "  KB");
		else
			fileSizeLabel.setText(df.format(megabytes) + "  MB");
	}

	private void setFileInfo(File file) {

		try {
			DataReader rd = new DataReader(file.getPath());
			fileLineLabel.setText(rd.getNumberOfLines() + "");
			fileIdLabel.setText(rd.getMaxIndex() + "");
			fileNodesLabel.setText(rd.getnNodes() + "");

		} catch (IOException e1) {

			e1.printStackTrace();
		}

	}

	FilePropertyGetter fp = new FilePropertyGetter() {

		@Override
		public void setProperty() {

			/*
			 * Get the input file properties and set the labels
			 */
			setFileInfo(file);
			setCursor(Cursor.getDefaultCursor());

		}
	};

	AlgorithmFinish af = new AlgorithmFinish() {

		
		@Override
		public void finish(ArrayList<Integer> solution) {

			setCursor(Cursor.getDefaultCursor());
			if (VisualGraph == null) {
				try {
					ArrayList<Integer> k = solution;
					
					VisualGraph = new ViewGraphFrame(fileNameLabel.getText(), new CloseListener() {
						@Override
						public void doClose(JFrame frame) {

							//frame.dispose();
							//dispose();
							VisualGraph = null;
						}
					}, k);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	};

}
