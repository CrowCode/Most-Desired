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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

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

import algorithm.azim.mostdesired.Algorithm;
import algorithm.azim.mostdesired.AlgorithmFinishMonitor;
import algorithm.azim.mostdesired.AlgorithmTask;
import inputdata.azim.mostdesired.DataReader;
import model.sajjad.mostdesired.sVertex;
import supplementaryclasses.azim.mostdesired.NodeAndWeight;
import virus.azim.mostdesired.Vaccinate;
import virus.azim.mostdesired.VirusSpread;

/***
 * 
 * @author sajjad
 * 
 *         This the man view frame of the application. User can choose different
 *         path through of this window into the application.</br>
 * 
 *         <ul>
 *         <li>The left side of window:</br>
 *         In this part is user controls of application. Top to bottom, there is
 *         Brows button to choose different input file from hard disk. Text
 *         filed to show the file path. The information area to show input file
 *         information, size, no. of line, no. of nodes, ... . The button to run
 *         the algorithm in order to find most influ_ ential nodes. The button
 *         to run virus spread experiment in graphic mode. The button to run
 *         virus spread experiment in text mode.</li>
 *         <li>The right side of window:</br>
 *         In this area the log of application activity will be presented to
 *         user.</li>
 *         </ul>
 * 
 */

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private int spreadScale = 2;
	// private JPanel mainPanel;
	private JPanel contentPane;
	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	private JButton browsBtn;
	private JButton runAlgorithmBtn;
	private JButton goInGraphicModeBtn;
	private JButton goInTxtModeBtn;
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
	// private JFrame VisualGraph;

	private DataReader rd;

	private ArrayList<sVertex> sVertices;
	private ArrayList<Integer> maxDegrees;
	public static ArrayList<Integer> solution;
	private ArrayList<Integer> infectedSeedsList;
	private ArrayList<Integer> vaccinatedNodes;
	private ArrayList<Integer> infectedNodes;

	public static ArrayList<LinkedList<NodeAndWeight>> graphIn;
	public static ArrayList<LinkedList<NodeAndWeight>> graphOut;

	public MainFrame() {

		initializeBasics();
		init();
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
		setSize(800, 430);
		setTitle("MOST DESIRED");
		setResizable(false);
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
		runAlgorithmBtn = new JButton("FIND MOST-DESIRED");
		runAlgorithmBtn.setFont(new Font("Arial", Font.BOLD, 10));
		runAlgorithmBtn.setBackground(myOrange);
		runAlgorithmBtn.setForeground(Color.white);
		runAlgorithmBtn.setBorderPainted(false);
		runAlgorithmBtn.setFocusPainted(false);
		runAlgorithmBtn.setPreferredSize(new Dimension(200, 20));
		runAlgorithmBtn.addActionListener(runAlgorithmAct);
		addCompToLeftPanel(runAlgorithmBtn, 0, 7, 6, 1);

		/** Row 8 **/
		goInGraphicModeBtn = new JButton("GRAPHIC MODE");
		goInGraphicModeBtn.setFont(new Font("Arial", Font.BOLD, 10));
		goInGraphicModeBtn.setBackground(myCyan);
		goInGraphicModeBtn.setForeground(Color.white);
		goInGraphicModeBtn.setBorderPainted(false);
		goInGraphicModeBtn.setFocusPainted(false);
		goInGraphicModeBtn.setPreferredSize(new Dimension(200, 20));
		goInGraphicModeBtn.addActionListener(showGraphAct);
		addCompToLeftPanel(goInGraphicModeBtn, 0, 8, 6, 1);

		/** Row 9 **/
		goInTxtModeBtn = new JButton("TEXT MODE");
		goInTxtModeBtn.setFont(new Font("Arial", Font.BOLD, 10));
		goInTxtModeBtn.setBackground(myCyan);
		goInTxtModeBtn.setForeground(Color.white);
		goInTxtModeBtn.setBorderPainted(false);
		goInTxtModeBtn.setFocusPainted(false);
		goInTxtModeBtn.setPreferredSize(new Dimension(200, 20));
		goInTxtModeBtn.addActionListener(showGraphAct);
		addCompToLeftPanel(goInTxtModeBtn, 0, 9, 6, 1);

		/** Row 10 **/
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
		addCompToLeftPanel(kSpinner, 2, 10, 4, 1);

		/** Row 11 **/
		fErrorLabel = new JLabel("ERROR:");
		fErrorLabel.setPreferredSize(new Dimension(80, 20));
		fErrorLabel.setForeground(Color.black);
		addCompToLeftPanel(fErrorLabel, 0, 11, 2, 1);

		SpinnerModel errorSpinnerModel = new SpinnerNumberModel(10, 0, 100, 1);
		errorSpinner = new JSpinner(errorSpinnerModel);
		errorSpinner.setPreferredSize(new Dimension(120, 20));
		errorSpinner.getEditor().getComponent(0).setBackground(myCyan);
		errorSpinner.getEditor().getComponent(0).setForeground(Color.white);
		errorSpinner.getComponent(0).setBackground(myOrange);
		errorSpinner.getComponent(1).setBackground(myOrange);
		errorSpinner.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		addCompToLeftPanel(errorSpinner, 2, 11, 4, 1);

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
		pBar.setPreferredSize(new Dimension(getContentPane().getWidth() - 50, 20));
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
		consoleTextArea.setText("\n\tOUTPUT:\n\n");

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
				consoleTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				consoleTextArea.setText("\n\tOUTPUT:\n");
				int k = Integer.parseInt(kSpinner.getValue().toString());
				int error = Integer.parseInt(errorSpinner.getValue().toString());
				if (!fileNameLabel.getText().equals("") && !fileNameLabel.getText().equals("[...]")) {
					AlgorithmTask task = new AlgorithmTask(af, fileInputField.getText(), k, error, consoleTextArea);

					task.addPropertyChangeListener(PropertyChangeListener);
					task.execute();
					createSvertexArray();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Please choose a file first!", "Dialog",
							JOptionPane.ERROR_MESSAGE);
					setCursor(Cursor.getDefaultCursor());
					consoleTextArea.setCursor(Cursor.getDefaultCursor());

				}

			}

		}
	};
	ActionListener showGraphAct = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			/**
			 * If the source of action is 'goInGraphicModeBtn'. It means if
			 * GRAPHIC MODE button clicked.
			 */

			if (e.getSource() == goInGraphicModeBtn) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {

						if (!fileNameLabel.getText().equals("") && !fileNameLabel.getText().equals("[...]")) {

							if (sVertices == null) {
								JOptionPane.showMessageDialog(new JFrame(), "Please run the algorithm at first",
										"ERROR", JOptionPane.ERROR_MESSAGE);
							} else {
								vaccinateSvertexArray();
								/* VisualGraph = */new GraphFrame(sVertices);
							}
						} else {
							JOptionPane.showMessageDialog(new JFrame(), "Please choose a file first!", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});

				/**
				 * If the source of action is 'goInTxtModeBtn'. It means if TEXT
				 * MODE button clicked.
				 */
			} else if (e.getSource() == goInTxtModeBtn) {

				if (!fileNameLabel.getText().equals("") && !fileNameLabel.getText().equals("[...]")) {
					if (sVertices == null) {
						JOptionPane.showMessageDialog(new JFrame(), "Please run the algorithm at first", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					} else {
						vaccinateSvertexArray();
						runExperinment();
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Please choose a file first!", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}

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

	/**
	 * The custom WndowListener to make sure after closing the frame and threads
	 * are disposed.
	 */
	WindowListener exitListener = new WindowAdapter() {

		public synchronized void windowClosing(WindowEvent evt) {
			setVisible(false); // default behavior of JFrame
			dispose();

		}

	};

	/**
	 * This is function in order to get input file size for showing in GUI
	 * 
	 * @param file
	 *            input file from user
	 */
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

	/**
	 * This function set file information into GUI labels
	 * 
	 * @param file
	 *            input file from user
	 */
	private void setFileInfo(File file) {

		try {
			rd = new DataReader(file.getPath());
			fileLineLabel.setText(rd.getNumberOfLines() + "");
			fileIdLabel.setText(rd.getMaxIndex() + "");
			fileNodesLabel.setText(rd.getnNodes() + "");
			graphIn = rd.getNodesList_In();
			graphOut = rd.getNodesList_Out();

		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}

	/**
	 * The method to create sVertices from rd of type dataReader.
	 */
	private void createSvertexArray() {

		sVertices = new ArrayList<>();
		int k = Integer.parseInt(kSpinner.getValue().toString());

		for (int i = 0; i < graphIn.size(); i++) {

			LinkedList<NodeAndWeight> tempList = graphOut.get(i);
			int d = ((tempList.size() * 200) / graphOut.size()) + 5;

			sVertex sv = new sVertex(i, new Random().nextInt(1000) + 100, new Random().nextInt((1000)) + 100, (d),
					false);

			Iterator<NodeAndWeight> iterator = tempList.iterator();

			while (iterator.hasNext()) {

				sv.addNeighbor((int) iterator.next().getAdjacentVertex());
			}
			sVertices.add(sv);
		}

		maxDegrees = rd.findKMaxDegree(k);
		Double sss = Algorithm.steadyStateSpread(maxDegrees, rd.getNodesList_In());
		DecimalFormat df = new DecimalFormat("####0.00");
		consoleTextArea.append("[>] THE " + k + " MAXIMUM DEGREE NODES ARE:\n\t" + maxDegrees.toString() + "\n");
		consoleTextArea.append("[B]: SSS:\n\t" + df.format(sss)+"\n");
	}

	/**
	 * The method to set vaccinate field (true, false) of sVertex according to
	 * Max degree or Most influential.
	 */
	private void vaccinateSvertexArray() {
		for (sVertex s : sVertices) {

			if (maxDegrees != null)
				if (maxDegrees.contains(s.getId())) {
					s.setInMax(true);
				}
			if (solution != null)
				if (solution.contains(s.getId())) {
					s.setInK(true);
				}
		}
		System.out.println(">>>>" + solution.toString());
		System.out.println(">>>>" + maxDegrees.toString());

		Vaccinate.vaccinateMaxDegrees(sVertices);
		Vaccinate.vaccinateMostInfluentials(sVertices);

	}

	/*
	 * The method to create random integer as seed array for infecting nodes. If
	 * the randomly selected node is vaccinated here we will try to find skip
	 * and find not vaccinated.
	 */
	private void infectedSeedListCreate(ArrayList<sVertex> sVertices) {

		infectedSeedsList = new ArrayList<Integer>();
		int sizeOfSeedArray = 10;
		int totalNoOfNodes = sVertices.size();

		try {
			sizeOfSeedArray = Integer.parseInt(
					(String) JOptionPane.showInputDialog(this, "Enter AN INTEGER As Number of Seed Nods to Infect:",
							"Question", JOptionPane.INFORMATION_MESSAGE, null, null, "10"));
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "The entered is not an integer.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		if (sizeOfSeedArray > totalNoOfNodes) {
			JOptionPane.showMessageDialog(getParent(), "The choosen number is greater than total number of nodes.",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			sizeOfSeedArray = 0; // If user enter a big number we will
									// use default
		}

		Random rn = new Random();
		for (int i = 0; i < sizeOfSeedArray; i++) {

			int infectedIndex = rn.nextInt(totalNoOfNodes);

			if (!sVertices.get(infectedIndex).isVaccinatedA() && !sVertices.get(infectedIndex).isVaccinatedB()) {
				infectedSeedsList.add(infectedIndex);
			} else {
				i--;
			}
		}

	}

	/**
	 * The method to run virus spread experiment on current sample.
	 */
	private void runExperinment() {

		vaccinateSvertexArray();
		infectedSeedListCreate(sVertices);

		int totalNoOfNodes = sVertices.size();

		int noOfInfectedA = VirusSpread.spread("A", infectedSeedsList, graphOut, sVertices, spreadScale);
		int noOfInfectedB = VirusSpread.spread("B", infectedSeedsList, graphOut, sVertices, spreadScale);
		
		double resultPercentA = (noOfInfectedA * 100) / totalNoOfNodes;
		double resultPercentB = (noOfInfectedB * 100) / totalNoOfNodes;

		setVaccinatedAndInfectedNodes();

		consoleTextArea.append("[A:B]");
		consoleTextArea.append("   Total Number of Nodes:\t" + totalNoOfNodes + "\n");

		consoleTextArea.append("[A:B]");
		consoleTextArea.append("   Number of Initially-Infected Nodes:\t" + infectedSeedsList.size()
				+ "\n\t" + infectedSeedsList.toString() + "\n");

		consoleTextArea.append("[A]");
		consoleTextArea
				.append("   Number of Infected Nodes After Virus Spread:\t" + noOfInfectedA + "\n");
		consoleTextArea.append("[B]");
		consoleTextArea
				.append("   Number of Infected Nodes After Virus Spread:\t" + noOfInfectedB + "\n");
		
		consoleTextArea.append("[A]");
		consoleTextArea
				.append("   Percentage of Infected Nodes After Virus Spread:\t" + resultPercentA + " %\n");
		consoleTextArea.append("[B]");
		consoleTextArea
				.append("   Percentage of Infected Nodes After Virus Spread:\t" + resultPercentB + " %\n");

	}

	FilePropertyGetter fp = new FilePropertyGetter() {

		@Override
		public void setProperty() {

			/*
			 * Get the input file properties and set the labels
			 */
			setFileInfo(file);

			setCursor(Cursor.getDefaultCursor());
			consoleTextArea.setCursor(Cursor.getDefaultCursor());
		}
	};

	public void setVaccinatedAndInfectedNodes() {

		vaccinatedNodes = new ArrayList<>();
		infectedNodes = new ArrayList<>();

		for (sVertex sV : sVertices) {
			if (sV.isVaccinatedB()) {
				vaccinatedNodes.add(sV.getId());
			}
			if (sV.isInfectedB()) {
				infectedNodes.add(sV.getId());
			}
		}
	}

	/**
	 * Since we are using SwingWorker in order to run algorithm in background.
	 * We need to some changes in GUI get cursor back from waiting state get
	 * answer of algorithm to problem and show to user ... .
	 * 
	 * The AlgorithmFfinish interface will do abstraction of all we need to do
	 * in GUI when algorithm finish.
	 */

	AlgorithmFinishMonitor af = new AlgorithmFinishMonitor() {

		@Override
		public void finish(ArrayList<Integer> s) {

			solution = s;
			Double sss = Algorithm.steadyStateSpread(solution, rd.getNodesList_In());
			DecimalFormat df = new DecimalFormat("####0.00");
			consoleTextArea.append("[A]: SSS:\n\t" + df.format(sss)+ "\n");
			setCursor(Cursor.getDefaultCursor());
			consoleTextArea.setCursor(Cursor.getDefaultCursor());

		}
	};
}
