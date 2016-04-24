package view.sajjad.mostdesired;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JViewport;

import supplementaryclasses.azim.mostdesired.sVertex;

public class ViewGraphPanelB extends JPanel implements VertexClickListener {

	/**
	 * This class is customized JPanel in order to visualize the graph.
	 */
	private static final long serialVersionUID = 1L;

	private boolean HOVERED = false;
	private boolean CLICKED = false;
	// private boolean CLICKED_OUT = false;

	private int VERTEX_CLICKED;
	private int VERTEX_HOVERED;

	private BufferedImage bimg;
	private Graphics2D g2;
	private ArrayList<Vertex> vertices = new ArrayList<>();
	private ArrayList<sVertex> sVertices = new ArrayList<>();
	
	private ArrayList<Integer> vaccinatedNodes;
	private ArrayList<Integer> infectedNodes;

	private Color myOrange = new Color(240, 127, 7);
	private Color myCyan = new Color(60, 109, 130);
	private Color darkGray = new Color(55, 55, 55);

	// private int scale = 5;

	private Point mouseStartPoint;

	public ViewGraphPanelB(Dimension d, ArrayList<sVertex> sVertices) {

		this.sVertices = sVertices;

		mouseStartPoint = new Point();
		// setPreferredSize(new Dimension(1200, 1200));
		setPreferredSize(d);
		prepareVertices(sVertices);

		/**
		 * To have sensitive vertices to mouse action we need handler for
		 * hovering and clicking.
		 * 
		 */
		for (final Vertex v : vertices) {
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					VertexClickEvent vertexClickEvent = new VertexClickEvent(e, 0);
					v.Clicked(vertexClickEvent);

				}

			});

			addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					VertexClickEvent vertexClickEvent = new VertexClickEvent(e, 0);
					v.Hovered(vertexClickEvent);
				}

			});
		}

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				JPanel panel = (JPanel) e.getSource();
				JViewport viewPort = (JViewport) panel.getParent();
				Point vpp = viewPort.getViewPosition();
				vpp.translate(mouseStartPoint.x - e.getX(), mouseStartPoint.y - e.getY());
				panel.scrollRectToVisible(new Rectangle(vpp, viewPort.getSize()));

			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseStartPoint.setLocation(e.getX(), e.getY());
				CLICKED = false;
				HOVERED = false;
				repaint();

			}
		});
		// addMouseListener(new PopClickListener());
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);

		bimg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) bimg.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		drawFrame();

		for (Vertex v : vertices) {

			if (HOVERED && v.getId() == VERTEX_HOVERED) {
				drawHovered(v);
			} else if (v.getSv().isInfectedB()) {
				drawInfected(v);
			} else if (v.getSv().isVaccinatedB()) {
				drawVaccinated(v);
			} else {
				drawNormal(v);
			}

		}

		if (CLICKED) {
			Vertex v = vertices.get(VERTEX_CLICKED);
			sVertex sv = sVertices.get(VERTEX_CLICKED);
			drawClicked(v);
			popup(g2, (int) v.getX(), (int) v.getY(), (int) v.getCenterX(), (int) v.getCenterY());

			g2.setColor(Color.GREEN);
			for (Integer svid : sv.getNeibors()) {
				drawNeighbor(vertices.get(svid));
			}
		}

		g2.dispose();
		g.drawImage(bimg, 0, 0, null);

	}

	private void popup(Graphics2D g, int x, int y, int cx, int cy) {

		g.setColor(Color.WHITE);
		
		int r = cx - x;
		
		if (x -50 < 0) {
			
		}
		if (y - 30 < 0) {
			
		}
			
		g.fill(new Rectangle(x - 50, y - 30, 50, 30));
		g.setColor(myCyan);
		g2.setStroke(new BasicStroke(2));
		g.draw(new Rectangle(x - 50, y - 30, 50, 30));
		g.setColor(myOrange);
		g.setFont(new Font("Dialog", Font.PLAIN, 20));
		
		int length = (int)(Math.log10(VERTEX_CLICKED)+1);
		int xpos = 0;
		
		switch (length) {
			
		case -2147483648: 
		case 1:
			xpos = x - 31;
			break;
			
		case 2:
			xpos = x - 37;
			break;
			
		case 3:
			xpos = x - 44;
			break;
			
		default:
			xpos = x - 50;
			
		}
		g.drawString(VERTEX_CLICKED + "", xpos, y - 8);

	}

	private int prepareVertices(ArrayList<sVertex> sVertices) {

		for (sVertex sv : sVertices) {

			Vertex v = new Vertex(sv);
			v.setVertexClickListener(this);
			vertices.add(v);
		}

		return 0;
	}

	public ArrayList<sVertex> getsVertices() {
		return sVertices;
	}

	public void setsVertices(ArrayList<sVertex> sVertices) {
		this.sVertices = sVertices;
	}

	@Override
	public void vertexClicked(VertexClickEvent vertexClickEvent) {
		CLICKED = true;
		VERTEX_CLICKED = vertexClickEvent.getVertexId();
		repaint();
	}

	@Override
	public void vertexHovered(VertexClickEvent vertexClickEvent) {
		HOVERED = true;
		VERTEX_HOVERED = vertexClickEvent.getVertexId();
		repaint();
	}
	
	/* ============================================================================ */
	/* ============= Following methods draw different type of vertex ============== */
	/* ======================== Names are self explanatory ======================== */
	/* ============================================================================ */

	private void drawFrame() {

		g2.setColor(darkGray);
		g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));

	}

	private void drawNormal(Vertex v) {

		g2.setColor(Color.WHITE);
		g2.fill(v);
		g2.setStroke(new BasicStroke((float) ((v.getHeight() * 15) / 100)));
		g2.setColor(myOrange);
		g2.draw(v);

	}

	private void drawInfected(Vertex v) {

		g2.setColor(Color.WHITE);
		g2.fill(v);
		g2.setStroke(new BasicStroke((float) ((v.getHeight() * 20) / 100)));
		g2.setColor(Color.YELLOW);
		g2.draw(v);
	}

	private void drawVaccinated(Vertex v) {

		g2.setColor(Color.WHITE);
		g2.fill(v);
		g2.setStroke(new BasicStroke((float) ((v.getHeight() * 20) / 100)));
		g2.setColor(Color.RED);
		g2.draw(v);
	}

	private void drawHovered(Vertex v) {

		g2.setColor(Color.WHITE);
		g2.fill(v);
		g2.setStroke(new BasicStroke((float) ((v.getHeight() * 20) / 100)));
		g2.setColor(myCyan);
		g2.draw(v);
	}

	private void drawClicked(Vertex v) {

		g2.setColor(Color.WHITE);
		g2.fill(v);
		g2.setStroke(new BasicStroke((float) (v.getHeight() * 25) / 100));
		g2.setColor(Color.BLACK);
		g2.draw(v);

	}

	private void drawNeighbor(Vertex v) {

		g2.setColor(Color.WHITE);
		g2.fill(v);
		g2.setStroke(new BasicStroke((float) (v.getHeight() * 25) / 100));
		g2.setColor(Color.GREEN);
		g2.draw(v);
	}

	/* ============================================================================ */
	
	public int getTotalNoOfNodes() {
		
		return sVertices.size();

	}
	
	public void setVaccinatedAndInfectedNodes() {
		
		vaccinatedNodes = new ArrayList<>();
		infectedNodes = new ArrayList<>();
		
		for (sVertex sV: sVertices) {
			if (sV.isVaccinatedB()) {
				vaccinatedNodes.add(sV.getId());
			}
			if (sV.isInfectedB()) {
				infectedNodes.add(sV.getId());
			}
		}
	}

	public ArrayList<Integer> getVaccinatedNodes() {
		return vaccinatedNodes;
	}

	public ArrayList<Integer> getInfectedNodes() {
		return infectedNodes;
	}
	
	
}
