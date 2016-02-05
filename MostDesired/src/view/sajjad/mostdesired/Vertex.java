package view.sajjad.mostdesired;

import java.awt.geom.Ellipse2D;

import model.sajjad.mostdesired.sVertex;

public class Vertex extends Ellipse2D.Double {

	/**
	 *  This class is the visual object of Vertex in graph.
	 *  with all click listeners an hover mouse features.
	 */
	private static final long serialVersionUID = 1L;

	private VertexClickListener vertexClickListener;
	private int id;
	private boolean isInK;
	private sVertex sv;
	
	
	public Vertex(sVertex svertex) {
		super(svertex.getX(), svertex.getY(), svertex.getD(), svertex.getD());
		this.id = svertex.getId();
		this.sv = svertex;
	}

	public void Clicked(VertexClickEvent mouseEvent) {
		if (mouseEvent != null) {
			if (contains(mouseEvent.getMouseEvent().getPoint())) {
				if (vertexClickListener != null) {
					mouseEvent.setVertexId(this.id);
					vertexClickListener.vertexClicked(mouseEvent);
				}
			}
		}
	}

	public void Hovered(VertexClickEvent mouseEvent) {
		if (mouseEvent != null) {
			if (contains(mouseEvent.getMouseEvent().getPoint())) {
				if (vertexClickListener != null) {
					mouseEvent.setVertexId(this.id);
					vertexClickListener.vertexHovered(mouseEvent);
				}
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setVertexClickListener(VertexClickListener vertexClickListener) {
		this.vertexClickListener = vertexClickListener;
	}
	
	

	public sVertex getSv() {
		return sv;
	}

	public void setSv(sVertex sv) {
		this.sv = sv;
	}

	
	public boolean isInK() {
		return isInK;
	}

	public void setInK(boolean isInK) {
		this.isInK = isInK;
	}
}
