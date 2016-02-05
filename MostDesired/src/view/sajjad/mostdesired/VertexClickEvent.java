package view.sajjad.mostdesired;

import java.awt.event.MouseEvent;

public class VertexClickEvent {
	
	private MouseEvent mouseEvent;
	private int vertexId;
	

	public VertexClickEvent(MouseEvent mouseEvent, int vertexId) {
		
		this.mouseEvent = mouseEvent;
		this.vertexId = vertexId;
	}
	
	public MouseEvent getMouseEvent() {
		return mouseEvent;
	}

	public int getVertexId() {
		return vertexId;
	}
	public void setVertexId(int vertexId) {
		this.vertexId = vertexId;
	}
	
	

}
