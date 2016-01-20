package VirusSpread.azim.mostdesired;

import java.util.ArrayList;

import model.sajjad.mostdesired.sVertex;

public abstract class Vaccinate {
	
	public void vaccinateMostInfluentials(ArrayList<sVertex> nodes){
		
		for(sVertex node: nodes){
			if(node.isInK())
				node.setVaccinatedA(true);
		}
	}
	
	public void vaccinateMaxDegrees(ArrayList<sVertex> nodes){
		
		for(sVertex node: nodes){
			if(node.isInMax())
				node.setVaccinatedB(true);
		}
		
	}

}
