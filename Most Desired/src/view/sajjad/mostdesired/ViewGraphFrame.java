package view.sajjad.mostdesired;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import InputData.azim.mostdesired.DataReader;
import model.mostWanted.sVertex;
import supplementaryClasses.azim.mostdesired.NodeAndWeight;

public class ViewGraphFrame extends JFrame {

	/**
	 * The JFrame which is main frame for graph representation. This class
	 * simply contains ViewGraphPanel which does all graph visualization.
	 */
	private static final long serialVersionUID = -7496390669979535394L;

	ArrayList<LinkedList<NodeAndWeight>> graph;
	CloseListener closeListener;

	public ViewGraphFrame(String filename, CloseListener closeListener) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1000, 700);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.closeListener = closeListener;
		addWindowListener(exitListener);
		setLocationRelativeTo(null);

		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		// setUndecorated(true);

		setLayout(new BorderLayout());

		try {
			DataReader rd = new DataReader(filename);
			graph = rd.getNodesList_In();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<sVertex> sVs = new ArrayList<>();

		for (int i = 0; i < graph.size(); i++) {
			LinkedList<NodeAndWeight> tmp = graph.get(i);
			int d = (tmp.size() * 500) / graph.size();
			sVertex sv = new sVertex(i, new Random().nextInt(1000) + 100, new Random().nextInt((1000)) + 100, (d));

			Iterator<NodeAndWeight> iterator = tmp.iterator();
			while (iterator.hasNext()) {
				sv.addNeighbor((int) iterator.next().getAdjacentVertex());
			}
			sVs.add(sv);
		}

		ViewGraphPanel panel = new ViewGraphPanel(screenSize, sVs);
		JScrollPane spanel = new JScrollPane(panel);

		spanel.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		spanel.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

		add(spanel, BorderLayout.CENTER);
		setVisible(true);
	}

	WindowListener exitListener = new WindowAdapter() {

		@Override
		public void windowClosing(WindowEvent e) {
			int confirm = JOptionPane.showOptionDialog(null, "Are You Sure to Close Application?", "Exit Confirmation",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (confirm == 0) {
				closeListener.doClose(ViewGraphFrame.this);

			}
		}
	};

}
