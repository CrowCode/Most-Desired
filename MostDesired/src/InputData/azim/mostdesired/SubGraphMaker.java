package InputData.azim.mostdesired;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import supplementaryClasses.azim.mostdesired.NodeAndWeight;

/**
 * 
 * @author azim
 *
 */
public class SubGraphMaker {

	private  int maxIndex;
	private  int numberOfLines;
	private  NodeAndWeight nAndW_Out;
	private  NodeAndWeight nAndW_In;
	private  LinkedList<NodeAndWeight> neighborsList_Out;
	private  LinkedList<NodeAndWeight> neighborsList_In;
	private  ArrayList<LinkedList<NodeAndWeight>> nodesList_Out;
	private  ArrayList<LinkedList<NodeAndWeight>> nodesList_In;
	
	public SubGraphMaker(String fileName) throws IOException{
		this.makeSubgraph(fileName);
	}
	
	
	
	public void makeSubgraph(String fileName) throws IOException{
		
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

		for(int i = 0; i < maxIndex + 1; i++){
			neighborsList_Out = new LinkedList<NodeAndWeight>();
			neighborsList_Out.add(new NodeAndWeight(-1.0, -1.0));
			nodesList_Out.add(neighborsList_Out);
			
			neighborsList_In  = new LinkedList<NodeAndWeight>();
			neighborsList_In.add(new NodeAndWeight(-1.0, -1.0));
			nodesList_In.add(neighborsList_In);
		}
		
		System.out.println("Initialization is done. Ready to copy!");

		
		try {
		    inputStream = new FileInputStream(fileName);
		    sc = new Scanner(inputStream, "UTF-8");

		    while (sc.hasNextDouble()) {

		    	//read 3 consecutive numbers (they comprise the whole line)
		        temp[0] = sc.nextDouble();	//starting node
		        temp[1] = sc.nextDouble();	//ending node
		        temp[2] = sc.nextDouble();	//weight
		    	
		        
		        if(temp[0] < 50 && temp[1] < 50){
		        	nAndW_Out = new NodeAndWeight(temp[1], temp[2]);
		        	nAndW_In  = new NodeAndWeight(temp[0], temp[2]);
		        
		        	nodesList_Out.get(temp[0].intValue()).add(nAndW_Out);
		        	nodesList_In .get(temp[1].intValue()).add(nAndW_In);
		        }
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
		
		System.out.println("DONE!");
		System.out.println("nodesList(I/O) --> size = " + nodesList_Out.size());

		System.out.println("================NODESLIST=====OUT=======");
		for(int i = 0; i < 20; i++){
			neighborsList_Out = nodesList_Out.get(i);
			for(int j = 0; j < neighborsList_Out.size(); j++){
				if(neighborsList_Out.get(j).getAdjacentVertex() != -1.0){
					System.out.print(i + " " + neighborsList_Out.get(j).getAdjacentVertex() + 
							 " " + neighborsList_Out.get(j).getWeight() + "\n");
				}
			}
			
		}
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

}
