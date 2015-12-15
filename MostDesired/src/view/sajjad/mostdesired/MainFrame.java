package view.sajjad.mostdesired;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalProgressBarUI;

import task.sajjad.mostdesired.AlgorithmTask;

/**
 * 
 * @author sajjad
 *
 */

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Declaration of gui component
	 */
	private JPanel mainPanel;
	private JTextField filenameTxtfield;
	private JButton choosefileBtn;
	private JButton visualizeNetworkBtn;
	private JButton runAlgorithmBtn;
	private JProgressBar progressBar;
	private JLabel noOfDesiredLable;
	private JSpinner noOfDesiredSpinner;
	private JLabel errorOfAlgorithmLabel;
	private JSpinner erroOfAlgorithmSpinner;
	private JTextArea appLogTxtArea;
	private final JFileChooser fc;

	private GridBagConstraints constraints = new GridBagConstraints();

	public MainFrame() {

		fc = new JFileChooser();
		setSize(500, 500);
		setTitle("MOST DESIRED");
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		init();
		add(mainPanel, BorderLayout.CENTER);
		setVisible(true);

	}

	private void addComponent(Component c, int x, int y) {
		constraints.gridx = y;
		constraints.gridy = x;
		mainPanel.add(c, constraints);
	}

	void init() {

		/**
		 * initializing components
		 */
		mainPanel = new JPanel();
		filenameTxtfield = new JTextField();
		choosefileBtn = new JButton("Browse");
		visualizeNetworkBtn = new JButton("Visualize Network");
		runAlgorithmBtn = new JButton("Run Algorithm");
		progressBar = new JProgressBar(0, 100);
		noOfDesiredLable = new JLabel("The Number K:");
		errorOfAlgorithmLabel = new JLabel("Error:");
		appLogTxtArea = new JTextArea();

		mainPanel.setBackground(Color.BLACK);
		mainPanel.setLayout(new GridBagLayout());

		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5, 5, 5, 5);

		/**
		 * Layout the filenameTxtfiled
		 */
		filenameTxtfield.setBackground(Color.GRAY);
		filenameTxtfield.setForeground(Color.ORANGE);
		filenameTxtfield.setPreferredSize(new Dimension(300, 50));
		filenameTxtfield.setFont(new Font("serif", Font.BOLD, 14));
		filenameTxtfield.setHorizontalAlignment(SwingConstants.CENTER);

		constraints.gridwidth = 2;

		addComponent(filenameTxtfield, 0, 0);

		/**
		 * Layout the choosefilenameBtn
		 */
		choosefileBtn.setBackground(Color.GRAY);
		choosefileBtn.setForeground(Color.ORANGE);
		choosefileBtn.setFont(new Font("serif", Font.BOLD, 14));
		choosefileBtn.addActionListener(choosefileAction);

		constraints.gridwidth = 1;

		addComponent(choosefileBtn, 0, 2);

		/**
		 * Layout the visualizeNetworkBtn
		 */
		visualizeNetworkBtn.setBackground(Color.GRAY);
		visualizeNetworkBtn.setForeground(Color.ORANGE);
		visualizeNetworkBtn.setFont(new Font("serif", Font.BOLD, 14));
		visualizeNetworkBtn.addActionListener(visualizeNetworkAction);

		constraints.gridwidth = 3;

		addComponent(visualizeNetworkBtn, 1, 0);

		/**
		 * Layout k desired Label and Spinner
		 */

		noOfDesiredLable.setBackground(Color.GRAY);
		noOfDesiredLable.setForeground(Color.ORANGE);

		constraints.gridwidth = 1;

		addComponent(noOfDesiredLable, 2, 0);

		SpinnerModel spinnerModel1 = new SpinnerNumberModel(10, 1, 100, 1);
		noOfDesiredSpinner = new JSpinner(spinnerModel1);
		JComponent editor1 = noOfDesiredSpinner.getEditor();
		Component com1 = editor1.getComponent(0);
		JSpinner.DefaultEditor spinnerEditor1 = (JSpinner.DefaultEditor) editor1;
		spinnerEditor1.getTextField().setHorizontalAlignment(JTextField.CENTER);
		spinnerEditor1.getTextField().setFont(new Font("serif", Font.BOLD, 14));
		com1.setBackground(Color.GRAY);
		com1.setForeground(Color.ORANGE);

		constraints.gridwidth = 1;
		constraints.weighty = 0.1;

		addComponent(noOfDesiredSpinner, 2, 1);

		/**
		 * Layout the errorOfAlgorithm Label and Spinner
		 */

		errorOfAlgorithmLabel.setBackground(Color.GRAY);
		errorOfAlgorithmLabel.setForeground(Color.ORANGE);

		constraints.gridwidth = 1;

		addComponent(errorOfAlgorithmLabel, 3, 0);

		SpinnerModel spinnerModel2 = new SpinnerNumberModel(0, 0, 100, 1);
		erroOfAlgorithmSpinner = new JSpinner(spinnerModel2);
		JComponent editor2 = erroOfAlgorithmSpinner.getEditor();
		Component com2 = editor2.getComponent(0);
		JSpinner.DefaultEditor spinnerEditor2 = (JSpinner.DefaultEditor) editor2;
		spinnerEditor2.getTextField().setHorizontalAlignment(JTextField.CENTER);
		spinnerEditor2.getTextField().setFont(new Font("serif", Font.BOLD, 14));
		com2.setBackground(Color.GRAY);
		com2.setForeground(Color.ORANGE);

		constraints.gridwidth = 1;

		addComponent(erroOfAlgorithmSpinner, 3, 1);

		/**
		 * Layout the runAlgorithmBtn
		 */

		runAlgorithmBtn.setBackground(Color.GRAY);
		runAlgorithmBtn.setForeground(Color.ORANGE);
		runAlgorithmBtn.setFont(new Font("serif", Font.BOLD, 14));
		runAlgorithmBtn.addActionListener(runAlgorithmAction);

		constraints.gridheight = 2;

		addComponent(runAlgorithmBtn, 2, 2);

		/**
		 * Layout the progressBar
		 */
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setPreferredSize(new Dimension(200, 50));
		progressBar.setBackground(Color.GRAY);
		progressBar.setForeground(Color.ORANGE);

		MetalProgressBarUI ui = new MetalProgressBarUI() {

			/**
			 * The "selectionForeground" is the color of the text when it is
			 * painted over a filled area of the progress bar.
			 */
			@Override
			protected Color getSelectionForeground() {

				return Color.WHITE;
			}

			/**
			 * The "selectionBackground" is the color of the text when it is
			 * painted over an unfilled area of the progress bar.
			 */
			@Override
			protected Color getSelectionBackground() {

				return Color.ORANGE;
			}
		};

		progressBar.setUI(ui);

		constraints.gridwidth = 3;
		constraints.gridheight = 1;

		addComponent(progressBar, 4, 0);

		/**
		 * Layout the appLogTxtArea
		 */

		appLogTxtArea.setBackground(Color.GRAY);
		appLogTxtArea.setForeground(Color.ORANGE);
		appLogTxtArea.setFont(new Font("serif", Font.BOLD, 12));
		appLogTxtArea.setEditable(false);

		constraints.weighty = 1;

		JScrollPane sp = new JScrollPane(appLogTxtArea);

		addComponent(sp, 5, 0);

	}

	ActionListener choosefileAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == choosefileBtn) {
				int returnVal = fc.showOpenDialog(MainFrame.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();

					filenameTxtfield.setText(file.getPath());
				} else {
					filenameTxtfield.setText("No File has been chosen!");
				}
			}
		}
	};
	ActionListener visualizeNetworkAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {

					if (!filenameTxtfield.getText().equals("")) {
						new ViewGraphFrame(filenameTxtfield.getText(), new CloseListener() {
							@Override
							public void doClose(JFrame frame) {

								frame.dispose();
							}
						});
					}

				}
			});
		}
	};
	ActionListener runAlgorithmAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int k = Integer.parseInt(noOfDesiredSpinner.getValue().toString());
			int error = Integer.parseInt(erroOfAlgorithmSpinner.getValue().toString());
			AlgorithmTask task = new AlgorithmTask(filenameTxtfield.getText(), k, error);
			task.setOIComponent(appLogTxtArea);
			task.addPropertyChangeListener(PropertyChangeListener);
			task.execute();
		}
	};
	PropertyChangeListener PropertyChangeListener = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if ("progress" == evt.getPropertyName()) {
				int progress = (Integer) evt.getNewValue();
				progressBar.setValue(progress);
				// taskOutput.append(String.format(
				// "Completed %d%% of task.\n", task.getProgress()));
			}

		}
	};

}
