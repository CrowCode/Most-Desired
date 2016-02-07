package newview.sajjad.mostdesired;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import InputData.azim.mostdesired.DataReader;
import model.sajjad.mostdesired.sVertex;
import supplementaryClasses.azim.mostdesired.NodeAndWeight;
import view.sajjad.mostdesired.ViewGraphPanel;

public class GraphFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewGraphPanel graphPanelLeft;
	private ViewGraphPanel graphPanelRight;
	private Dimension ViewGraphDim;
	
	
	private ArrayList<LinkedList<NodeAndWeight>> graphIn;
//	private ArrayList<LinkedList<NodeAndWeight>> graphOut;
	
	private ArrayList<sVertex> sVertices;
	
	private ArrayList<Integer> k;
	
	public GraphFrame(DataReader rd) {

		this.graphIn = rd.getNodesList_In();
//		this.graphOut = rd.getNodesList_Out();
		
		setTitle("Show Graph In Comparison Mode");
        setSize(1300, 700);
        
        /**
         *  Prepare parameter dimension and sVertes array list in order to build ViewGraphPanels.
         *  sVertices can be built from dataReader, rd
         *  d is just preferred dimension of GraphPanel 
         */
        
        ViewGraphDim = new Dimension(2000, 2000);
        initSVertices();
        
        /**
         *  Create left graphViewPanel
         * 
         */
        graphPanelLeft = new ViewGraphPanel(ViewGraphDim, sVertices);
        
        JScrollPane graphScrollPanelLeft = new JScrollPane(graphPanelLeft);
        graphScrollPanelLeft.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        graphScrollPanelLeft.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
		add(graphScrollPanelLeft, BorderLayout.CENTER);
        
        /**
         *  Create right graphViewPanel
         * 
         */
        graphPanelRight = new ViewGraphPanel(ViewGraphDim, sVertices); 
        
        JScrollPane graphScrollPanelRight = new JScrollPane(graphPanelRight);
        graphScrollPanelRight.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        graphScrollPanelRight.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
		add(graphScrollPanelRight, BorderLayout.CENTER);

         
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                true, graphScrollPanelLeft, graphScrollPanelRight);
         
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(.5d);
        getContentPane().add(splitPane);
        
        setVisible(true);
		
	}
	
	private void initSVertices() {
		
		sVertices = new ArrayList<>();
		
		for (int i = 0; i < graphIn.size(); i++) {
			
			LinkedList<NodeAndWeight> vertexTmp = graphIn.get(i);
			int d = (vertexTmp.size() * 200) / graphIn.size();
			
			sVertex sv = new sVertex(i, new Random().nextInt(1000) + 100, new Random().nextInt((1000)) + 100, (d), false);
			
			if (k != null && k.contains(i)) {
				sv.setInK(true);
			}
			
			Iterator<NodeAndWeight> iterator = vertexTmp.iterator();
			
			while (iterator.hasNext()) {
				
				sv.addNeighbor((int) iterator.next().getAdjacentVertex());
			}
			sVertices.add(sv);
	}
	}

}
