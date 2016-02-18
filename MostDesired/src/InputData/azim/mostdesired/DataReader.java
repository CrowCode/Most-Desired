package InputData.azim.mostdesired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import sortingClasses.azim.mostdesired.SyncHeapsort;
import supplementaryClasses.azim.mostdesired.NodeAndWeight;

/**
 * 
 * @author azim<br>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <br>
 * 		<b>Definitions</b>
 * <br>
 * <i>Incoming edge:</i> is an edge which is coming from a neighbor to a node.<br>
 * <i>Outgoing edge:</i> is an edge which is going out from a node to any of its neighbors.<br>
 * <i>Outgoing neighbor:</i> is a neighbor of a node with an outgoing edge from this node to it.<br>
 * <i>Incoming neighbor:</i> is a neighbor of a node with an incoming edge from it to this node.<br>
 * 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * <br>
 * 		<b>INPUT FILE:</b>
 * <br>
 * It is a text file which describes a graph in the following fashion:<br>
 * 		1. Every line, representing an edge, contains 3 numbers separated by single space.<br>
 * 		2. The first number represents the id of the starting node.<br>
 * 		3. The second number represents the id of the ending node.<br>
 * 		4. The third number represents the weight of this edge.<br>
 * 		5. The indexing starts from zero.<br>
 * 		6. Some nodes may be absent because they are isolated nodes.<br>
 * <br>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  
 * <br>
 * 		<b>DATA STRUCTURE:</b>
 * 	<br>
 * To Store the input file which represents the graph of data we employ the following<br>
 * data structure: <br>
 * 			[ [NW, NW, ..., NW], [NW, NW, ..., NW] ,... ,[NW, NW, ..., NW] ]<br>
 * where:<br>
 * 	-the outer brackets represent an ArrayList named 'nodesList'<br>
 * 	-each pair of inner brackets represents a LinkedList named 'neighborsList'<br>
 * <br>
 * 
 * 
 *	<b>nodesList_Out:</b><br> 
 *		- It contains lists of objects of type 'NodeAndWeight'.<br>
 *		- Its i-th element is a set of outgoing neighbors of i.<br>
 *		- Its size is equal to the number of vertices (= maxIndex + 1).<br>
 *		- We do not let any of these lists to be empty: They always contain a special instance of class 'NodeAndWeight'
 * no matter if they have any outgoing neighbor or not.<br>
 *<br>
 *	<b>nodesList_In:</b><br> 
 *		- It is the same as 'nodesList_Out' but regarding incoming neighbors instead of outgoing ones.<br>
 *<br>
 *	<b>neighborsList_Out[i]:</b><br>
 *		- It contains objects of type 'NodeAndWeight'.<br>
 *		- Its j-th element represents the j-th neighbor (outgoing) of i-th vertex and the weight of that (outgoing) edge.<br>
 *		- Its size is equal to the number of outgoing neighbors of the corresponding node.<br>
 *<br>
 *	<b>neighborsList_In[i]:</b><br>
 *		- It is the same as 'neighborsList_Out' but regarding incoming neighbors instead of outgoing neighbors.</br>
 *<br>
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

public class DataReader {

	
	private  int maxIndex;
	private  int numberOfLines;
	private  NodeAndWeight nAndW_Out;
	private  NodeAndWeight nAndW_In;
	private  LinkedList<NodeAndWeight> neighborsList_Out;
	private  LinkedList<NodeAndWeight> neighborsList_In;
	private  ArrayList<LinkedList<NodeAndWeight>> nodesList_Out;
	private  ArrayList<LinkedList<NodeAndWeight>> nodesList_In;
	
	public DataReader(String fileName) throws IOException{
		this.readData(fileName);
	}
	
	
	
	public void readData(String fileName) throws IOException{
		
		Double[] temp = new Double[3];
		FileInputStream inputStream = null;
		Scanner sc = null;
		
		//1.Count the number of lines
		System.out.println("Counting number of lines...");
		countLines(fileName);
		System.out.println("Number of lines = " + numberOfLines);
		//2.Find the greatest id of vertices
		System.out.println("Finding the max id...");
		findMaxIndex(fileName);
		System.out.println("Max ID = " + maxIndex);
		
		
		nodesList_Out = new ArrayList<LinkedList<NodeAndWeight>>(maxIndex + 1);
		nodesList_In  = new ArrayList<LinkedList<NodeAndWeight>>(maxIndex + 1);
		
		//fill 'nodesList_Out' and 'nodesList_In' with dummy data so that later we can add 
		//meaningful data to 'nodesList' at any position that we want and not only at the end.
		//We do not remove this one element from any 'neighborsList' at any time later. Actually
		//whenever the size of such list is 1, means that it has no incoming/outgoing neighbors.
		for(int i = 0; i < maxIndex + 1; i++){
			neighborsList_Out = new LinkedList<NodeAndWeight>();
			neighborsList_Out.add(new NodeAndWeight(-1.0, -1.0));
			nodesList_Out.add(neighborsList_Out);
			
			neighborsList_In  = new LinkedList<NodeAndWeight>();
			neighborsList_In.add(new NodeAndWeight(-1.0, -1.0));
			nodesList_In.add(neighborsList_In);
		}
		
//		System.out.println("Initialization is done. Ready to copy data to memory!");

		
		try {
		    inputStream = new FileInputStream(fileName);
		    sc = new Scanner(inputStream, "UTF-8");

		    while (sc.hasNextDouble()) {

		    	//read 3 consecutive numbers (they comprise the whole line)
		        temp[0] = sc.nextDouble();	//starting node
		        temp[1] = sc.nextDouble();	//ending node
		        temp[2] = sc.nextDouble();	//weight
		    	
		        nAndW_Out = new NodeAndWeight(temp[1], temp[2]);
		        nAndW_In  = new NodeAndWeight(temp[0], temp[2]);
		        
		        //add 'nAdnW' to a list which is located in nodesList[temp[0]]
//		        if(nodesList.get(temp[0].intValue()).getLast().getAdjacentVertex() == -1.0)
//		        	nodesList.get(temp[0].intValue()).set(0, nAndW);
		        
		        nodesList_Out.get(temp[0].intValue()).add(nAndW_Out);
		        nodesList_In .get(temp[1].intValue()).add(nAndW_In);
		    }
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
		
		System.out.println("Graph Storing: DONE");
		System.out.println("Graph Size: " + nodesList_Out.size() + " nodes");

		
//		System.out.println("================NODESLIST=====OUT=======");
//		for(int i = 0; i < nodesList_Out.size(); i++){
//			System.out.println("node " + i + " ->");
//			neighborsList_Out = nodesList_Out.get(i);
//			System.out.print("\t");
//			for(int j = 0; j < neighborsList_Out.size(); j++){
//				System.out.print(":" + neighborsList_Out.get(j).getAdjacentVertex());
//			}
//			System.out.println();
//		}
//		System.out.println("================NODESLIST=====IN========");
//		for(int i = 0; i < nodesList_In.size(); i++){
//			System.out.println("node " + i + " <-");
//			neighborsList_In = nodesList_In.get(i);
//			System.out.print("\t");
//			for(int j = 0; j < neighborsList_In.size(); j++){
//				System.out.print(":" + neighborsList_In.get(j).getAdjacentVertex());
//			}
//			System.out.println();
//		}
	}
	
	
	/** not used so far
	 * 
	 * @param fileName is the name of the file whose number of lines is requested. Full file name is required.
	 * @return number of lines that the given file contains.
	 * @throws IOException
	 */
	private void countLines(String fileName) throws IOException{
		
		LineNumberReader  lnr = new LineNumberReader(new FileReader(new File(fileName)));
		lnr.skip(Long.MAX_VALUE);
		numberOfLines = lnr.getLineNumber() + 1; //Add 1 because line index starts at 0
		lnr.close();
	}
	
	/**
	 * Finds the id of the last index and sets it to the field 'maxIndex'.<br>
	 * <b>WARNING:</b> It is assumed that ids of indices start from zero and increment by 1. 
	 * @param fileName
	 * @throws IOException
	 */
	private void findMaxIndex(String fileName) throws IOException{
		
		FileInputStream inputStream = null;
		Scanner sc = null;
		Double[] temp = new Double[2];
		Double maxInd = 0.0;
		Double d = 0.0; 
		
		try {
		    inputStream = new FileInputStream(fileName);
		    sc = new Scanner(inputStream, "UTF-8");
		    
		    while (sc.hasNextDouble()) {
		    	
		        temp[0] = sc.nextDouble();
		        temp[1] = sc.nextDouble();
		        sc.nextDouble();
	        	
		        if((d = Math.max(temp[0], temp[1])) > maxInd)
		        	maxInd = d;
		    }
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
		maxIndex = maxInd.intValue();
	}
	
		
	public int getMaxIndex() {
		return maxIndex;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

//	public NodeAndWeight getnAndW() {
//		return nAndW_Out;
//	}

//	public LinkedList<NodeAndWeight> getNeighborsList() {
//		return neighborsList_Out;
//	}

	public ArrayList<LinkedList<NodeAndWeight>> getNodesList_Out() {
		return nodesList_Out;
	}
	
	public ArrayList<LinkedList<NodeAndWeight>> getNodesList_In() {
		return nodesList_In;
	}
	
	public int getnNodes() {
		return nodesList_In.size();
	}
	
	public ArrayList<Integer> findKMaxDegree(int k) {
		
		Double [] degrees = new Double [nodesList_Out.size()];
		int [] ids = new int [nodesList_Out.size()];
		ArrayList<Integer> kMaxIds = new ArrayList<>();
		
		int i = 0;
		for (LinkedList<NodeAndWeight> node: nodesList_Out){
			
			degrees[i] = (double)node.size() - 1;
			ids [i] = i;
			i++;
			
		}
		SyncHeapsort.sort(degrees, ids);
		
		for (i=0; i<k; i++) {
			
			kMaxIds.add(ids[ids.length-i-1]);
		}
		
		
		return kMaxIds;
		
	}
	
	
}
