package model.sajjad.mostdesired;

import java.util.ArrayList;

public class sVertex {
	
	private int id;
	private int x;
	private int y;
	private int d;
	private boolean isInK;
	private boolean isInMax;
	private ArrayList<Integer> neibors;
	private boolean isInfectedA;		//True if this node is infected in the experiment in 
										//regard with the most influential nodes
	private boolean isInfectedB;		//True if this node is infected in the experiment in 
										//regard with the max-degree nodes
	
	private boolean isVaccinatedA;		//True if this node is vaccinated (is a most influential node)
	private boolean isVaccinatedB;		//True if this node is vaccinated (is a max degree node)
	
	
	public sVertex(int id, int x, int y, int d, boolean isInK) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.d = d;
		this.neibors = new ArrayList<>();
		this.isInK = isInK;
		this.isInfectedA = false;
		this.isInfectedB = false;
		
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


	public boolean isInK() {
		return isInK;
	}


	public void setInK(boolean isInK) {
		this.isInK = isInK;
	}
	
	public void setIsInfcetedA(boolean isInfected){
		this.isInfectedA = isInfected;
	}
	
	public void setIsInfcetedB(boolean isInfected){
		this.isInfectedB = isInfected;
	}

	public void setVaccinatedA(boolean isVaccinatedA) {
		this.isVaccinatedA = isVaccinatedA;
	}

	public void setVaccinatedB(boolean isVaccinatedB) {
		this.isVaccinatedB = isVaccinatedB;
	}

	public boolean isVaccinatedA() {
		return isVaccinatedA;
	}
	
	public boolean isVaccinatedB() {
		return isVaccinatedB;
	}


	public boolean isInMax() {
		return isInMax;
	}

	public void setInMax(boolean isInMax) {
		this.isInMax = isInMax;
	}


	
	
	
	
	

}
