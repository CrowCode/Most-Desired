package view.sajjad.mostdesired;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import model.sajjad.mostdesired.sVertex;
import virus.azim.mostdesired.VirusSpread;

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

	private static final long serialVersionUID = 1L;
	
	private Color myOrange = new Color(240, 127, 7);
	private Color myCyan = new Color(60, 109, 130);
	private Color darkGray = new Color(55, 55, 55);

	
	public static ViewGraphPanelA graphPanelLeft;
	public static ViewGraphPanelB graphPanelRight;
	private JPanel textShowBottomPanel;
	private JPanel graphShowTopPanel;
	public static JTextArea graphPanelInfoLeft;
	public static JTextArea graphPanelInfoRight;
	
	
	private Dimension ViewGraphPanelDim;

	public static ArrayList<sVertex> sVertices;
	public static ArrayList<Integer> infectedSeedsList;

	public static ArrayList<Integer> infectedNodesA;
	public static ArrayList<Integer> infectedNodesB;

	
	
	public GraphFrame(ArrayList<sVertex> sVertices) {

		GraphFrame.sVertices = sVertices;
		initializeBasics();
		initializeTopPanel();
		initializeBottomPanel();

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, graphShowTopPanel, textShowBottomPanel);
		splitPane.setResizeWeight(.8d);
		add(splitPane, BorderLayout.CENTER);

		/*
		 * Set the bottom panel text area's info before run virus spread.
		 */
		setInfoBottomPanel();

		setVisible(true);
	}

	private void initializeBasics() {

		Dimension frameSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		ViewGraphPanelDim = new Dimension(1000, 1000);

		setTitle("Show Graph In Comparison Mode");
		setSize(frameSize.width, frameSize.height - 80);
		setLayout(new BorderLayout());

	}

	private void initializeTopPanel() {

		graphShowTopPanel = new JPanel();
		graphShowTopPanel.setPreferredSize(new Dimension(1300, 500));
		graphShowTopPanel.setBackground(Color.WHITE);
		graphShowTopPanel.setLayout(new BorderLayout());

		/**
		 * Create left graphViewPanel
		 * 
		 */
		graphPanelLeft = new ViewGraphPanelA(ViewGraphPanelDim, sVertices);
		JScrollPane graphScrollPanelLeft = new JScrollPane(graphPanelLeft);
		graphScrollPanelLeft.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		graphScrollPanelLeft.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

		/**
		 * Create right graphViewPanel
		 * 
		 */
		graphPanelRight = new ViewGraphPanelB(ViewGraphPanelDim, sVertices);
		JScrollPane graphScrollPanelRight = new JScrollPane(graphPanelRight);
		graphScrollPanelRight.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		graphScrollPanelRight.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

		graphPanelRight.addMouseListener(new PopClickListener());
		graphPanelLeft.addMouseListener(new PopClickListener());

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, graphScrollPanelLeft,
				graphScrollPanelRight);

		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(.5d);

		graphShowTopPanel.add(splitPane, BorderLayout.CENTER);
	}

	private void initializeBottomPanel() {

		textShowBottomPanel = new JPanel();
		textShowBottomPanel.setPreferredSize(new Dimension(1300, 150));
		textShowBottomPanel.setBackground(Color.WHITE);
		textShowBottomPanel.setLayout(new BorderLayout());

		/**
		 * Setup the left text area
		 */
		graphPanelInfoLeft = new JTextArea(10, 60);
		JScrollPane graphScrollPaneInfoLeft = new JScrollPane(graphPanelInfoLeft);
		graphScrollPaneInfoLeft.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		graphScrollPaneInfoLeft.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		graphPanelInfoLeft.setSize(new Dimension(100, 100));
		graphPanelInfoLeft.setBackground(darkGray);
		graphPanelInfoLeft.setForeground(myOrange);

		/**
		 * Setup the right text area
		 */
		graphPanelInfoRight = new JTextArea(10, 60);
		JScrollPane graphScrollPaneInfoRight = new JScrollPane(graphPanelInfoRight);
		graphScrollPaneInfoRight.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		graphScrollPaneInfoRight.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		graphPanelInfoRight.setSize(new Dimension(100, 100));
		graphPanelInfoRight.setBackground(darkGray);
		graphPanelInfoRight.setForeground(myOrange);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, graphScrollPaneInfoLeft,
				graphScrollPaneInfoRight);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(.5d);

		textShowBottomPanel.add(splitPane, BorderLayout.CENTER);
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
		 * This method will initialize seed array only
		 */
		infectedSeedListCreate(0);

		infectedNodesA = graphPanelLeft.getInfectedNodes();
		infectedNodesB = graphPanelRight.getInfectedNodes();

		GraphFrame.graphPanelLeft.repaint();
		GraphFrame.graphPanelRight.repaint();

		/*
		 * Set information about the total number of nodes in each experiment text area.
		 */
		GraphFrame.graphPanelInfoLeft.append(" [  ]  EXPERIMENT:\t[A]\n");
		GraphFrame.graphPanelInfoLeft.append(" [  ]  Vaccinateds:\tMOST INFLUENTIAL NODES\n");
		GraphFrame.graphPanelInfoLeft.append("_________________________________________________________________________________________________________________\n");
		GraphFrame.graphPanelInfoLeft.append(" [>]  Total:\t\t");
		GraphFrame.graphPanelInfoLeft.append(totalNoOfNodesA + "\n");
		
		GraphFrame.graphPanelInfoRight.append(" [  ]  EXPERIMENT:\t[B]\n");
		GraphFrame.graphPanelInfoRight.append(" [  ]  Vaccinateds:\tMAX DEGREE NODES\n");
		GraphFrame.graphPanelInfoRight.append("_________________________________________________________________________________________________________________\n");
		GraphFrame.graphPanelInfoRight.append(" [>]  Total:\t\t");
		GraphFrame.graphPanelInfoRight.append(totalNoOfNodesB + "\n");
		/*
		 * Set information about vaccinated nodes for each experiment text area.
		 */
		GraphFrame.graphPanelInfoLeft.append(" [>]  Vaccinated:\t");
		GraphFrame.graphPanelInfoLeft.append(noOfvaccinatedNodesA + "\t" + vaccinatedNodesA.toString() + "\n");
		GraphFrame.graphPanelInfoRight.append(" [>] Vaccinated:\t");
		GraphFrame.graphPanelInfoRight.append(noOfvaccinatedNodesB + "\t" + vaccinatedNodesB.toString() + "\n");

	}

	/*
	 * The method to create random integer as seed array for infecting nodes. If
	 * the randomly selected node is vaccinated here we will try to find skip
	 * and find not vaccinated.
	 */
	
	public static void infectedSeedListCreate(int noOfSeeds) {

		infectedSeedsList = new ArrayList<Integer>();

		if (noOfSeeds == 0)
			return;
		Random rn = new Random();
		for (int i = 0; i < noOfSeeds; i++) {

			int infectedIndex = rn.nextInt((GraphFrame.sVertices.size()));

			if (!sVertices.get(infectedIndex).isVaccinatedA() && !sVertices.get(infectedIndex).isVaccinatedB() && !sVertices.get(infectedIndex).isInfectedA() ) {
				
				infectedSeedsList.add(infectedIndex);
				GraphFrame.sVertices.get(infectedIndex).setIsInfcetedA(true);
				GraphFrame.sVertices.get(infectedIndex).setIsInfcetedB(true);
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
	private int spreadScale = 2;
	JMenuItem spreadVirus;

	public PopUpDemo() {
		spreadVirus = new JMenuItem("Spread The Virus!");

		spreadVirus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int sizeOfSeedArray = 10; // Default size of seeds array

				/**
				 * Try block to be sure user will enter a number
				 */
				try {
					sizeOfSeedArray = Integer.parseInt((String) JOptionPane.showInputDialog(getParent(),
							"Enter AN INTEGER As Number of Seed Nods to Infect:", "Question",
							JOptionPane.INFORMATION_MESSAGE, null, null, "10"));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(getParent(), "The entered is not an integer.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}

				int totalNoOfNodes = GraphFrame.sVertices.size();

				/*
				 * The condition to check user is not entering a number greater
				 * than total number of nodes
				 */
				if (sizeOfSeedArray > totalNoOfNodes) {
					JOptionPane.showMessageDialog(getParent(),
							"The choosen number is greater than total number of nodes.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					sizeOfSeedArray = 10; // If user enter a big number we will
											// use default
				}

				/*
				 * Generate seed array with user request's size or default size
				 */
				GraphFrame.infectedSeedListCreate(sizeOfSeedArray);
				
				GraphFrame.graphPanelLeft.setVaccinatedAndInfectedNodes();
				GraphFrame.graphPanelRight.setVaccinatedAndInfectedNodes();
				
				//=============================================
				for (sVertex tm: GraphFrame.sVertices) {
					if (tm.isInfectedA()) {
						System.out.println("OOOOOOOOOOOOO"+tm.getId());
					}
				}
				//=============================================	
				
				

				System.out.println(" CHOOSEN NO IS: => " + sizeOfSeedArray);

				System.out.println("Spreading the virus ...");

				VirusSpread.spread("A", GraphFrame.infectedSeedsList, MainFrame.graphOut,
						GraphFrame.sVertices, spreadScale);
				VirusSpread.spread("B", GraphFrame.infectedSeedsList, MainFrame.graphOut,
						GraphFrame.sVertices, spreadScale);
				
				GraphFrame.graphPanelLeft.setVaccinatedAndInfectedNodes();
				GraphFrame.graphPanelRight.setVaccinatedAndInfectedNodes();
				
				ArrayList<Integer> resultOfSpreadA = GraphFrame.graphPanelLeft.getInfectedNodes();
				ArrayList<Integer> resultOfSpreadB = GraphFrame.graphPanelRight.getInfectedNodes();

				int noOfInfectedSeeds = GraphFrame.infectedSeedsList.size();
				
				//=============================================
				for (sVertex tm: GraphFrame.sVertices) {
					if (tm.isInfectedA()) {
						System.out.println("WWWWWWWWWWWWWW"+tm.getId());
					}
				}
				//=============================================	

				System.out.println("GraphFrame>>>>>InfectedSeeds: " + GraphFrame.infectedSeedsList.toString());
				System.out.println("GraphFrame>>>>>Solution: " + MainFrame.solution.toString());

				/**
				 * Set information about infected nodes before virus spread
				 * experiment.
				 */
				GraphFrame.graphPanelInfoLeft
						.append(" [>]  Initially-Infected:\t");
				GraphFrame.graphPanelInfoLeft
						.append(noOfInfectedSeeds + "\t" + GraphFrame.infectedSeedsList.toString() + "\n");
				GraphFrame.graphPanelInfoRight
						.append(" [>]  Initially-Infected:\t");
				GraphFrame.graphPanelInfoRight
						.append(noOfInfectedSeeds + "\t" + GraphFrame.infectedSeedsList.toString() + "\n");

				/**
				 * Set information to text area's of each experiment about virus
				 * spread.
				 */
				GraphFrame.graphPanelInfoLeft
						.append(" [>]  Infected:\t\t");
				GraphFrame.graphPanelInfoLeft
						.append(resultOfSpreadA.size() + "\t" + resultOfSpreadA.toString() + "\n");
				GraphFrame.graphPanelInfoRight
						.append(" [>]  Infected:\t\t");
				GraphFrame.graphPanelInfoRight
						.append(resultOfSpreadB.size() + "\t" + resultOfSpreadB.toString() + "\n");

				/**
				 * Find info about percentage of spread in each experiment
				 */
				double pResultA = (resultOfSpreadA.size() * 100) / totalNoOfNodes;
				double pResultB = (resultOfSpreadB.size() * 100) / totalNoOfNodes;

				GraphFrame.graphPanelInfoLeft
						.append(" [>]  Degree of Infection:\t( "
								+ pResultA + " % )\n");
				GraphFrame.graphPanelInfoRight
						.append(" [>]  Degree of Infection:\t( "
								+ pResultB + " % )\n");

				GraphFrame.graphPanelLeft.repaint();
				GraphFrame.graphPanelRight.repaint();

			}
		});
		add(spreadVirus);
		
		
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
