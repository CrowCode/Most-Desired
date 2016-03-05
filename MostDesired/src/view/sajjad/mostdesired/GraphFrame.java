package view.sajjad.mostdesired;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import Virusspread.azim.mostdesired.VirusSpread;
import model.sajjad.mostdesired.sVertex;

public class GraphFrame extends JFrame {

	/**
	 * This is split pane frame to present double graph panel in same visual
	 * time to observe result of</br>
	 * virus spread experiment in two different type of k-vaccinated nodes.</br>
	 * <ul>
	 * <li>A:(what is shown in left side with ViewGraphPanelA) k nodes are
	 * vaccinated from most influential</br>
	 * </li>
	 * <li>B:(what is shown in right side with viewGraphPanelB) k nodes are
	 * vaccinated from max degree</br>
	 * </li>
	 * </ul>
	 */

	private Color myOrange = new Color(240, 127, 7);
	private Color myCyan = new Color(60, 109, 130);
	private Color darkGray = new Color(55, 55, 55);

	private static final long serialVersionUID = 1L;
	public static ViewGraphPanelA graphPanelLeft;
	public static ViewGraphPanelB graphPanelRight;
	private JPanel textShowPanel;
	public static JTextArea graphPanelInfoLeft;
	public static JTextArea graphPanelInfoRight;
	private Dimension ViewGraphPanelDim;

	public static ArrayList<sVertex> sVertices;
	public static ArrayList<Integer> infectedSeedsList;

	public static ArrayList<Integer> infectedNodesA;
	public static ArrayList<Integer> infectedNodesB;

	public GraphFrame(ArrayList<sVertex> sVertices) {

		setTitle("Show Graph In Comparison Mode");
		
		Dimension frameSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setSize(frameSize.width, frameSize.height-20);
		setLayout(new BorderLayout());

		GraphFrame.sVertices = sVertices;

		initializeBottomPanel();

		/**
		 * Prepare parameter dimension and sVertes array list in order to build
		 * ViewGraphPanels. sVertices can be built from dataReader, rd d is just
		 * preferred dimension of GraphPanel
		 */

		ViewGraphPanelDim = new Dimension(1000, 1000);

		/**
		 * Create left graphViewPanel
		 * 
		 */

		graphPanelLeft = new ViewGraphPanelA(ViewGraphPanelDim, sVertices);

		JScrollPane graphScrollPanelLeft = new JScrollPane(graphPanelLeft);
		graphScrollPanelLeft.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		graphScrollPanelLeft.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
		add(graphScrollPanelLeft, BorderLayout.CENTER);

		/**
		 * Create right graphViewPanel
		 * 
		 */

		graphPanelRight = new ViewGraphPanelB(ViewGraphPanelDim, sVertices);

		JScrollPane graphScrollPanelRight = new JScrollPane(graphPanelRight);
		graphScrollPanelRight.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		graphScrollPanelRight.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
		add(graphScrollPanelRight, BorderLayout.CENTER);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, graphScrollPanelLeft,
				graphScrollPanelRight);

		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(.5d);

		getContentPane().add(splitPane, BorderLayout.CENTER);
		getContentPane().add(textShowPanel, BorderLayout.SOUTH);

		/*
		 * Set the bottom panel text area's info before run virus spread.
		 */
		setInfoBottomPanel();

		graphPanelRight.addMouseListener(new PopClickListener());
		graphPanelLeft.addMouseListener(new PopClickListener());
		setVisible(true);

	}

	private void initializeBottomPanel() {

		textShowPanel = new JPanel();
		textShowPanel.setPreferredSize(new Dimension(1300, 100));
		textShowPanel.setBackground(Color.WHITE);
		textShowPanel.setLayout(new FlowLayout());

		graphPanelInfoLeft = new JTextArea(6, 60);
		graphPanelInfoLeft.setSize(getContentPane().getPreferredSize());
		graphPanelInfoLeft.setMinimumSize(new Dimension(220, 110));
		graphPanelInfoLeft.setBackground(darkGray);
		graphPanelInfoLeft.setForeground(myOrange);

		textShowPanel.add(graphPanelInfoLeft);

		graphPanelInfoRight = new JTextArea(6, 60);
		graphPanelInfoRight.setSize(getContentPane().getPreferredSize());
		graphPanelInfoRight.setMinimumSize(new Dimension(220, 110));
		graphPanelInfoRight.setBackground(darkGray);
		graphPanelInfoRight.setForeground(myOrange);

		textShowPanel.add(graphPanelInfoRight);

	}

	private void setInfoBottomPanel() {

		/*
		 * Get total number of nodes in each experiment
		 */
		int totalNoOfNodesA = graphPanelLeft.getTotalNoOfNodes();
		int totalNoOfNodesB = graphPanelRight.getTotalNoOfNodes();

		/*
		 * Call the setVaccinatedAndInfectedNodes will set proper information of
		 * sVertices in corresponding fields in order to get in next step.
		 */
		graphPanelLeft.setVaccinatedAndInfectedNodes();
		graphPanelRight.setVaccinatedAndInfectedNodes();

		/*
		 * Get the vaccinated nodes from each experiment.
		 */
		ArrayList<Integer> vaccinatedNodesA = graphPanelLeft.getVaccinatedNodes();
		ArrayList<Integer> vaccinatedNodesB = graphPanelRight.getVaccinatedNodes();

		/*
		 * The size of vaccinatedNode arrays are the the number of vaccinated
		 * nodes.
		 */
		int noOfvaccinatedNodesA = vaccinatedNodesA.size();
		int noOfvaccinatedNodesB = vaccinatedNodesB.size();

		/*
		 * This method will generate array of seed nodes for initial infection
		 */
		infectedSeedListCreate();

		infectedNodesA = graphPanelLeft.getInfectedNodes();
		infectedNodesB = graphPanelRight.getInfectedNodes();

		GraphFrame.graphPanelLeft.repaint();
		GraphFrame.graphPanelRight.repaint();

		int noOfInfectedSeeds = infectedSeedsList.size();

		/*
		 * Set info about total number of nodes in each experiment text area.
		 */
		GraphFrame.graphPanelInfoLeft.append(" [>]  Tolal Number of Nodes In Most Influential Experiment:\t");
		GraphFrame.graphPanelInfoLeft.append("[ " + totalNoOfNodesA + " ]\n");
		GraphFrame.graphPanelInfoRight.append(" [>]  Total Number of Nodes In Max Degree Experiment:\t\t");
		GraphFrame.graphPanelInfoRight.append("[ " + totalNoOfNodesB + " ]\n");
		/*
		 * Set information about vaccinated nodes for each experiment text area.
		 */
		GraphFrame.graphPanelInfoLeft.append(" [>]  Number of Vaccinated Nodes In Most Influential Experiment:\t");
		GraphFrame.graphPanelInfoLeft.append("[ " + noOfvaccinatedNodesA + " ]\t" + vaccinatedNodesA.toString() + "\n");
		GraphFrame.graphPanelInfoRight.append(" [>] Number of Vaccinated Nodes In Max Degree Experiment:\t");
		GraphFrame.graphPanelInfoRight.append("[ " + noOfvaccinatedNodesB + " ]\t" + vaccinatedNodesB + "\n");

		/*
		 * Set information about infected nodes before virus spread experiment.
		 */
		GraphFrame.graphPanelInfoLeft
				.append(" [>]  Number of Seeds In Most Influential Experiment Before Virus Spread:\t");
		GraphFrame.graphPanelInfoLeft
				.append("[ " + noOfInfectedSeeds + " ]\n\t\t" + infectedSeedsList.toString() + "\n");
		GraphFrame.graphPanelInfoRight.append(" [>] Number of Seeds In Max Degree Experiment Before Virus Spread:\t");
		GraphFrame.graphPanelInfoRight
				.append("[ " + noOfInfectedSeeds + " ]\n\t\t" + infectedSeedsList.toString() + "\n");

	}

	/*
	 * The method to create random integer as seed array for infecting nodes. If
	 * the randomly selected node is vaccinated here we will try to find skip
	 * and find not vaccinated.
	 */
	public void infectedSeedListCreate() {

		infectedSeedsList = new ArrayList<Integer>();
		Random rn = new Random();
		for (int i = 0; i < 10; i++) {

			int infectedIndex = rn.nextInt((GraphFrame.sVertices.size()));

			if (!sVertices.get(infectedIndex).isVaccinatedA() && !sVertices.get(infectedIndex).isVaccinatedB()) {
				infectedSeedsList.add(infectedIndex);
			} else {
				i--;
			}
		}

	}
}

/**
 * 
 * @author sajjad The class to add and handle right click pop up on panels to
 *         run algorithm.
 */
class PopUpDemo extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	JMenuItem anItem;

	public PopUpDemo() {
		anItem = new JMenuItem("Spread The Virus!");
		anItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("Spreading the virus ...");

				int resultOfSpreadA = VirusSpread.spread("A", GraphFrame.infectedSeedsList, MainFrame.graphOut,
						GraphFrame.sVertices, 1);
				int resultOfSpreadB = VirusSpread.spread("B", GraphFrame.infectedSeedsList, MainFrame.graphOut,
						GraphFrame.sVertices, 1);

				System.out.println("GraphFrame>>>>>InfectedSeeds: " + GraphFrame.infectedSeedsList.toString());
				System.out.println("GraphFrame>>>>>Solution: " + MainFrame.solution.toString());
				

				GraphFrame.graphPanelInfoLeft
						.append(" [>]  Number of Infected Nodes After Vaccinate Most Influential Nodes:\t");
				GraphFrame.graphPanelInfoLeft
						.append("[ " + resultOfSpreadA + " ]\n\t\t" + GraphFrame.infectedNodesA.toString());
				GraphFrame.graphPanelInfoRight
						.append(" [>]  Number of Infected Nodes After Vaccinate Max Degree Nodes:\t");
				GraphFrame.graphPanelInfoRight
						.append("[ " + resultOfSpreadB + " ]\n\t\t" + GraphFrame.infectedNodesB.toString());

				GraphFrame.graphPanelLeft.repaint();
				GraphFrame.graphPanelRight.repaint();

			}
		});
		add(anItem);
	}
}

class PopClickListener extends MouseAdapter {
	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger()) {
			doPop(e);

		}

	}

	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()) {
			doPop(e);

		}

	}

	private void doPop(MouseEvent e) {
		PopUpDemo menu = new PopUpDemo();
		menu.show(e.getComponent(), e.getX(), e.getY());
	}
}
