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
//	private Color myCyan = new Color(60, 109, 130);
	private Color darkGray = new Color(55, 55, 55);
	
	private static final long serialVersionUID = 1L;
	public static ViewGraphPanelA graphPanelLeft;
	public static ViewGraphPanelB graphPanelRight;
	private JPanel textShowPanel;
	public static JTextArea graphPanelInfoLeft;
	public static JTextArea graphPanelInfoRight;
	private Dimension ViewGraphPanelDim;

	// private ArrayList<LinkedList<NodeAndWeight>> graphIn;

	public static ArrayList<sVertex> sVertices;

	public GraphFrame(ArrayList<sVertex> sVertices) {

		setTitle("Show Graph In Comparison Mode");
		setSize(1300, 700);
		setLayout(new BorderLayout());

		GraphFrame.sVertices = sVertices;

		initializeBottonPanel();

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

		graphPanelRight.addMouseListener(new PopClickListener());
		graphPanelLeft.addMouseListener(new PopClickListener());
		setVisible(true);

	}
	
	private void initializeBottonPanel() {
		
		textShowPanel = new JPanel();
		textShowPanel.setPreferredSize(new Dimension(1300, 100));
		textShowPanel.setBackground(Color.WHITE);
		textShowPanel.setLayout(new FlowLayout());
		
		graphPanelInfoLeft = new JTextArea(6, 58);
		graphPanelInfoLeft.setSize(getContentPane().getPreferredSize());
		graphPanelInfoLeft.setMinimumSize(new Dimension(100, 100));
		graphPanelInfoLeft.setBackground(darkGray);
		graphPanelInfoLeft.setForeground(myOrange);
		
		textShowPanel.add(graphPanelInfoLeft);
		
		graphPanelInfoRight = new JTextArea(6, 58);
		graphPanelInfoRight.setSize(getContentPane().getPreferredSize());
		graphPanelInfoRight.setMinimumSize(new Dimension(100, 100));
		graphPanelInfoRight.setBackground(darkGray);
		graphPanelInfoRight.setForeground(myOrange);
		
		textShowPanel.add(graphPanelInfoRight);
		
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

				double a = 0;
				double b = 0;

				System.out.println("Spreading the virus ...");

				/********************
				 * AZIM ***** For test
				 ***********************/
				/********
				 * Generate a random set of nodes to carry the virus
				 *****/
				Random rn = new Random();
				ArrayList<Integer> infectedSeedsList = new ArrayList<Integer>();
				for (int i = 0; i < 10; i++) {

					/*
					 * infectedSeedsList.add(rn.nextInt((GraphFrame.sVertices.
					 * size()) + 1));
					 */
					/** Gives index out bound '+ 1' removed **/

					infectedSeedsList.add(rn.nextInt((GraphFrame.sVertices.size())));
				}
				
				int resultOfSpreadA = VirusSpread.spread("A", infectedSeedsList, MainFrame.graphOut, GraphFrame.sVertices, 1);
				int resultOfSpreadB = VirusSpread.spread("B", infectedSeedsList, MainFrame.graphOut, GraphFrame.sVertices, 1);

				System.out.println("GraphFrame>>>>>InfectedSeeds: " + infectedSeedsList.toString());
				System.out.println("GraphFrame>>>>>Solution: " + MainFrame.solution.toString());
				/*****************************************************************/
				
				GraphFrame.graphPanelInfoLeft.append(" [>]  Number of Infected Nodes After Vaccinate Most Influential Nodes:\n\t");
				GraphFrame.graphPanelInfoLeft.append(resultOfSpreadA+"");
				GraphFrame.graphPanelInfoRight.append(" [>]  Number of Infected Nodes After Vaccinate Max Degree Nodes:\n\t");
				GraphFrame.graphPanelInfoRight.append(""+resultOfSpreadB);
				
				GraphFrame.graphPanelLeft.repaint();
				GraphFrame.graphPanelRight.repaint();

				/** AZIM: REMOVE THIS LATER **/
				sVertex sv;
				for (int i = 0; i < GraphFrame.sVertices.size(); i++) {
					sv = GraphFrame.sVertices.get(i);
					if (sv.isInfectedA()) {
						a++;
					}
					if (sv.isInfectedB()) {
						b++;
					}
				}
				System.out.println("Infected in A: " + a);
				System.out.println("Infected in B: " + b);
				System.out.println("Proportion of infected A: " + (a * 100) / GraphFrame.sVertices.size());
				System.out.println("Proportion of infected B: " + (b * 100) / GraphFrame.sVertices.size());
				/** ************************************ **/
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
