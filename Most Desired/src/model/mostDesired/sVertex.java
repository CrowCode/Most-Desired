package model.mostDesired;

import java.util.ArrayList;

public class sVertex {
	
	private int id;
	private int x;
	private int y;
	private int d;
	private ArrayList<Integer> neibors;
	
	
	public sVertex(int id, int x, int y, int d) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.d = d;
		neibors = new ArrayList<>();
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getD() {
		return d;
	}


	public void setD(int d) {
		this.d = d;
	}
	
	public void addNeighbor(int neighbor) {
		if (neighbor!=-1)
			neibors.add(neighbor);
	}


	public ArrayList<Integer> getNeibors() {
		return neibors;
	}
	
	
	
	
	

}
