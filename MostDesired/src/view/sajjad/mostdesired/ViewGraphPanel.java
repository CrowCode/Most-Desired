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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JViewport;

import model.sajjad.mostDesired.sVertex;

public class ViewGraphPanel extends JPanel implements VertexClickListener {

	/**
	 * This class is customized JPanel in order to visualize the graph.
	 */
	private static final long serialVersionUID = 1L;

	private boolean HOVERED = false;
	private boolean CLICKED = false;

	private int VERTEX_CLICKED;
	private int VERTEX_HOVERED;

	private BufferedImage bimg;
	private Graphics2D g2;
	private ArrayList<Vertex> vertices = new ArrayList<>();
	private ArrayList<sVertex> sVertices = new ArrayList<>();

	// private float scale = 1;

	private Point mouseStartPoint;

	public ViewGraphPanel(Dimension d, ArrayList<sVertex> sVertices) {

		this.sVertices = sVertices;

		mouseStartPoint = new Point();
		// setPreferredSize(new Dimension(1200, 1200));
		setPreferredSize(d);
		prepareVertices(sVertices);

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
			}
		});
		addMouseListener(new PopClickListener());
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);

		bimg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) bimg.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.BLACK);
		g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));

		for (Vertex v : vertices) {

			g2.setColor(Color.GRAY);
			g2.fill(v);
			if (HOVERED && v.getId() == VERTEX_HOVERED) {
				g2.setStroke(new BasicStroke(5));
				g2.setColor(Color.RED);
			} else {
				g2.setStroke(new BasicStroke(2));
				g2.setColor(Color.ORANGE);
			}

			g2.draw(v);

		}
		if (CLICKED) {
			Vertex v = vertices.get(VERTEX_CLICKED);
			sVertex sv = sVertices.get(VERTEX_CLICKED);
			popup(g2, (int) v.getX(), (int) v.getY());
			g2.setStroke(new BasicStroke(5));
			g2.setColor(Color.GREEN);
			for (Integer svid : sv.getNeibors()) {
				g2.draw(vertices.get(svid));
			}
		}
		// AffineTransform at = new AffineTransform();
		// at.scale(scale, scale);

		g2.dispose();
		g.drawImage(bimg, 0, 0, null);

	}

	private void popup(Graphics2D g, int x, int y) {

		/*
		 * BufferedImage image; try { image = ImageIO.read(new File("img.svg"));
		 * g.drawImage(image, x - 65, y - 97, 100, 100, null);
		 * g.setColor(Color.WHITE); g.setFont(new Font("Dialog", Font.PLAIN,
		 * 20)); g.drawString(VERTEX_CLICKED + "", x - 25, y - 48); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */

		g.setColor(Color.YELLOW);
		g.fill(new Rectangle(x - 50, y - 30, 50, 30));
		g.setColor(Color.WHITE);
		g.draw(new Rectangle(x - 50, y - 30, 50, 30));
		g.setColor(Color.BLACK);
		g.setFont(new Font("Dialog", Font.PLAIN, 20));
		g.drawString(VERTEX_CLICKED + "", x - 35, y - 8);

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
}

class PopUpDemo extends JPopupMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuItem anItem;

	public PopUpDemo() {
		anItem = new JMenuItem("Run the ALGORITHM ...");
		anItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("I am CLICKED ...");

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
