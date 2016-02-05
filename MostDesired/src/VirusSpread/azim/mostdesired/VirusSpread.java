package VirusSpread.azim.mostdesired;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import model.sajjad.mostdesired.sVertex;
import supplementaryClasses.azim.mostdesired.NodeAndWeight;


/**
 * 
 * @author azim<br>
 * 
 * This is an abstract class which spread a virus to a graph, through the initially infected nodes which are called <b>seeds</b>.
 * To run the spread of virus, these pieces of information are needed:
 * <ul>
 * 	<li>seedsId:</li> an ArrayList of Integers containing the IDs of the initially infected nodes.
 * 	<li>nodesList_Out:</li> is a known representation of the graph. (Look at the class <i>DataReader</i> for details.)
 * 	<li>verticesProp:</li> is an ArrayList of objects of type 'sVertex' which keeps track of the properties of each vertex. It
 * is important to note that the i-th vertex in 'verticesProp' corresponds to the i-th node in 'nodesList_Out', however they
 * provide different pieces of information for that node/vertex.
 * 	<li>scale:</li> is a double value which scales the chance of the spread of a virus. If scale = 1, the spread is natural and depends
 * on the weight of each edge and random number generated at any time. Any number greater than 1 would multiply the chance by that number.
 * </ul>
 *
 */
public abstract class VirusSpread {
	
	
	/**
	 * This method has influence on 'isInfected' parameter of each vertex and at the end of the computation
	 * we can see the spread of the virus on the updated version of 'verticesProp'.<br>
	 * @param AorB if <b>A</b> then it is assumed that the k most-influential nodes are vaccinated,<br>
	 * if <b>B</b> then it is assumed that the k max-degree nodes are vaccinated.
	 * @param seedsId is a collection of IDs of the nodes which are initially infected as the seeds of the virus.
	 * @param nodesList_Out is a special representation of the graph. (Look at the class <i>DataReader</i> for details.)
	 * @param verticesProp contains properties of all vertices of the graph (i.e: position, color, isInfected, isVaccinated, ...).
	 * @param scale is a double which increases the chance of virus spread. If scale = 1, the spread is natural. Any number greater
	 * than 1, would multiply the chance by that number.
	 * 
	 */
	public static void spread( 	String AorB,
								ArrayList<Integer> seedsId,
								ArrayList<LinkedList<NodeAndWeight>> nodesList_Out,
								ArrayList<sVertex> verticesProp,
								double scale){						
		
		if(!AorB.equalsIgnoreCase("A") && !AorB.equalsIgnoreCase("B")){
			throw new IllegalArgumentException("AZIM: 'AorB' is neither 'A' nor 'B'!");
		}
		
		sVertex s;
		LinkedList<sVertex> newInfectedNodes;
		LinkedList<sVertex> infectedQueue = new LinkedList<sVertex>();
		
		//Push initial infected vertices into 'infectedQueue'
		for(int i = 0; i < seedsId.size(); i++){
			infectedQueue.add(verticesProp.get(seedsId.get(i)));
		}
		
		//A: The assumption is that the most influential nodes are vaccinated
		if(AorB.equalsIgnoreCase("A")){
			while(!infectedQueue.isEmpty()){
				//take the first vertex out of the 'infectedQueue'
				s = infectedQueue.removeFirst();									//or last ???
				
				newInfectedNodes = findNewInfectedNodesA(s, nodesList_Out, verticesProp, scale);
				if(!newInfectedNodes.isEmpty()){
					//add new infected nodes to 'infectedQueue'
					if((infectedQueue.addAll(newInfectedNodes)) == false)
						System.out.println("Error in VirusSpread>>spread()>>if(B)>>While()!");
				}
			}
		}
		//B: The assumption is that the max-degree nodes are vaccinated
		else{
			while(!infectedQueue.isEmpty()){
				//take the first vertex out of the 'infectedQueue'
				s = infectedQueue.removeFirst();									//or last ???
				
				newInfectedNodes = findNewInfectedNodesB(s, nodesList_Out, verticesProp, scale);
				if(!newInfectedNodes.isEmpty()){
					//add new infected nodes to 'infectedQueue'
					if((infectedQueue.addAll(newInfectedNodes)) == false)
						System.out.println("Error in VirusSpread>>spread()>>if(B)>>While()!");
				}
			}
		}
		
	}
	
	/**
	 * This method finds and returns a possible subset of neighbors of an infected node which become infected.
	 * It also applies the new changes in regard with newly infected vertices in 'verticesProp'.<br> 
	 * @param sourceVertex is a single node which is infected
	 * @param nodesList_Out is a special representation of the graph. (Look at the class <i>DataReader</i> for details.)
	 * @param verticesProp contains properties of all vertices of the graph (i.e: position, color, isInfected, isVaccinated, ...).
	 * @param scale is a double which increases the chance of virus spread. If scale = 1, the spread is natural. Any number greater
	 * than 1, would multiply the chance by that number.
	 * @return a subset of neighbors of 'sourceVertex' which are infected by it.
	 * <br>
	 * <b>WARNING:</b> In most of the cases a call of this method would return an <i>empty</i> list.
	 */
	private static LinkedList<sVertex> findNewInfectedNodesA(sVertex sourceVertex,
															ArrayList<LinkedList<NodeAndWeight>> nodesList_Out,
															ArrayList<sVertex> verticesProp,
															double scale){
		
		/**
		 * The node containing the virus:
		 * 		ID: 'sourceId'	|	sVertex: 'sourcesVertex'	|
		 * ----------------------------------------------------------------------------
		 * A neighbor of the source node:
		 * 		ID: 'i'			|	sVertex: 'nSVertex'			|	NodeAndWeight: 'n'
		 *  
		 */
		int i = 0;
		double w = 0.0;
		double rnW = 0.0;
		
		
		sVertex nSVertex;
		LinkedList<sVertex> newInfectedNodes = new LinkedList<sVertex>();
		LinkedList<NodeAndWeight> neighbors;
		int sourceId;												//id of the node which contains the virus
		
		sourceId = sourceVertex.getId();
		neighbors = nodesList_Out.get(sourceId);				//nodes in touch with the source node
		Random rn = new Random();
		//iterate over all neighbors of 'sourceVertex'
		for(NodeAndWeight n : neighbors){
			
			i = (int) n.getAdjacentVertex();						//id of a neighbor of  'sourceVertex'
			if (i == -1)
				continue;
			nSVertex = verticesProp.get(i);
			//if this neighbor ('nSVertex') is vaccinated or infected we just move on to the next neighbor
			if(nSVertex.isVaccinatedA() || nSVertex.isInfectedA()){
				//do nothing
			}
			else{
				w = n.getWeight();									//weight of the edge: (source) ----> (n)
				rnW = Math.floor(rn.nextDouble() * 100) / 100;		//generate a random double in [0,1] with 2 decimal places
				if(rnW <= scale * w){
					//then n gets infected
					nSVertex.setIsInfcetedA(true);					//Does this affect 'verticesProp' as well ???
					newInfectedNodes.add(nSVertex);
				}
			}
		}
		
		
		return newInfectedNodes;
	}
	
	
	
	/**
	 * This method finds and returns a possible subset of neighbors of an infected node which become infected.
	 * It also applies the new changes in regard with newly infected vertices in 'verticesProp'.<br> 
	 * @param sourceVertex is a single node which is infected
	 * @param nodesList_Out is a special representation of the graph. (Look at the class <i>DataReader</i> for details.)
	 * @param verticesProp contains properties of all vertices of the graph (i.e: position, color, isInfected, isVaccinated, ...).
	 * @param scale is a double which increases the chance of virus spread. If scale = 1, the spread is natural. Any number greater
	 * than 1, would multiply the chance by that number.
	 * @return a subset of neighbors of 'sourceVertex' which are infected by it.
	 * <br>
	 * <b>WARNING:</b> In most of the cases a call of this method would return an <i>empty</i> list.
	 */
	private static LinkedList<sVertex> findNewInfectedNodesB(sVertex sourceVertex,
															ArrayList<LinkedList<NodeAndWeight>> nodesList_Out,
															ArrayList<sVertex> verticesProp,
															double scale){
		
		/**
		 * The node containing the virus:
		 * 		ID: 'sourceId'	|	sVertex: 'sourcesVertex'	|
		 * ----------------------------------------------------------------------------
		 * A neighbor of the source node:
		 * 		ID: 'i'			|	sVertex: 'nSVertex'			|	NodeAndWeight: 'n'
		 *  
		 */
		int i = 0;
		double w = 0.0;
		double rnW = 0.0;
		
		
		sVertex nSVertex;
		LinkedList<sVertex> newInfectedNodes = new LinkedList<sVertex>();
		LinkedList<NodeAndWeight> neighbors;
		int sourceId;												//id of the node which contains the virus
		
		sourceId = sourceVertex.getId();
		neighbors = nodesList_Out.get(sourceId);				//nodes in touch with the source node
		Random rn = new Random();
		//iterate over all neighbors of 'sourceVertex'
		for(NodeAndWeight n : neighbors){
			
			i = (int) n.getAdjacentVertex();						//id of a neighbor of  'sourceVertex'
			nSVertex = verticesProp.get(i);
			//if this neighbor ('nSVertex') is vaccinated or infected we just move on to the next neighbor
			if(nSVertex.isVaccinatedB() || nSVertex.isInfectedB()){
				//do nothing
			}
			else{
				w = n.getWeight();									//weight of the edge: (source) ----> (n)
				rnW = Math.floor(rn.nextDouble() * 100) / 100;		//generate a random double in [0,1] with 2 decimal places
				if(rnW <= scale * w){
					//then n gets infected
					nSVertex.setIsInfcetedB(true);					//Does this affect 'verticesProp' as well ???
					newInfectedNodes.add(nSVertex);
				}
			}
		}
		
		
		return newInfectedNodes;
	}
	

}

