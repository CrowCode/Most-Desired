package VirusSpread.azim.mostdesired;

import java.util.ArrayList;

import model.sajjad.mostdesired.sVertex;


/**
 * 
 * @author azim <br><br>
 * This is an abstract class which is designed to vaccinate the network in two different fashion:<br>
 * <ul>
 * 		<li>Vaccinate only those k nodes which are discovered as the <i>most influentials</>.</li>
 * 		<li>Vaccinate only those k nodes with the greatest degree with respect to their outgoing
 * 		edges and not incoming ones.</li>
 * </ul> 
 *
 */
public class Vaccinate {
	/**
	 * This method vaccinates the most influential nodes of the graph. It sets the boolean field 
	 * (<i>isVaccinatedA</i>) of the class <b>sVertex<b> to True.
	 * @param nodes is a list of vertices of type <b>sVertex</b>
	 */
	public static void vaccinateMostInfluentials(ArrayList<sVertex> nodes){
		
		for(sVertex node: nodes){
			if(node.isInK())
				node.setVaccinatedA(true);
		}
	}
	
	/**
	 * This method vaccinates k-max-degree nodes of the graph. It sets the boolean field (<i>isVaccinatedB</i>) 
	 * of the class <b>sVertex<b> to True.
	 * @param nodes is a list of vertices of type <b>sVertex</b>
	 */
	public static void vaccinateMaxDegrees(ArrayList<sVertex> nodes){
		
		for(sVertex node: nodes){
			if(node.isInMax())
				node.setVaccinatedB(true);
		}
		
	}

}
