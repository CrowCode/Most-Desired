package newview.sajjad.mostdesired;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import VirusSpread.azim.mostdesired.VirusSpread;
import model.sajjad.mostdesired.sVertex;
import supplementaryClasses.azim.mostdesired.NodeAndWeight;
import view.sajjad.mostdesired.MainFrame;
import view.sajjad.mostdesired.ViewGraphPanelA;
import view.sajjad.mostdesired.ViewGraphPanelB;

public class GraphFrame extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static ViewGraphPanelA graphPanelLeft;
	public static ViewGraphPanelB graphPanelRight;
	private Dimension ViewGraphPanelDim;
	
	// private ArrayList<LinkedList<NodeAndWeight>> graphIn;
	
	 public static ArrayList<sVertex> sVertices;
	
	public GraphFrame(ArrayList<sVertex> sVertices) {

		setTitle("Show Graph In Comparison Mode");
		setSize(1300, 700);
		
		this.sVertices = sVertices;
		
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
		getContentPane().add(splitPane);
		
		graphPanelRight.addMouseListener(new PopClickListener());
		graphPanelLeft.addMouseListener(new PopClickListener());
		setVisible(true);

	}
	
	public void repainting() {
		
	}

}

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
				
				
				/******************** AZIM ***** For test ***********************/
				/******** Generate a random set of nodes to carry the virus *****/
				Random rn = new Random();
				ArrayList<Integer> infectedSeedsList = new ArrayList<Integer>();
				for(int i = 0; i < 10; i++){
					infectedSeedsList.add(rn.nextInt((GraphFrame.sVertices.size()) + 1));
				}
				VirusSpread.spread("A", infectedSeedsList, MainFrame.graphOut, GraphFrame.sVertices, 1);
				VirusSpread.spread("B", infectedSeedsList, MainFrame.graphOut, GraphFrame.sVertices, 1);
				
				
				System.out.println("GraphFrame>>>>>InfectedSeeds: " + infectedSeedsList.toString());
				System.out.println("GraphFrame>>>>>Solution: " + MainFrame.solution.toString());
				/*****************************************************************/
				
				
				
				
				
				
//				VirusSpread.spread("A", MainFrame.solution, MainFrame.graphOut, GraphFrame.sVertices, 1);
//				VirusSpread.spread("B", MainFrame.solution, MainFrame.graphOut, GraphFrame.sVertices, 1);
				
				GraphFrame.graphPanelLeft.repaint();
				GraphFrame.graphPanelRight.repaint();
				
				
				
				
				/**			AZIM: REMOVE THIS LATER		**/
				sVertex sv;
				for(int i = 0; i < GraphFrame.sVertices.size(); i++){
					sv = GraphFrame.sVertices.get(i);
					if(sv.isInfectedA()){
						a++;
					}
					if(sv.isInfectedB()){
						b++;
					}
				}
				System.out.println("Infected in A: " + a);
				System.out.println("Infected in B: " + b);
				System.out.println("Proportion of infected A: " + (a*100)/GraphFrame.sVertices.size());
				System.out.println("Proportion of infected B: " + (b*100)/GraphFrame.sVertices.size());
				/**	************************************ **/
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

