package VirusSpread.azim.mostdesired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import InputData.azim.mostdesired.DataReader;
import supplementaryClasses.azim.mostdesired.NodeAndWeight;

public class VirusSpread {
	
	private static ArrayList<Integer> mostDesiredNodes;
	private static ArrayList<LinkedList<NodeAndWeight>> nodesList_Out;
	private static ArrayList<LinkedList<NodeAndWeight>> nodesList_In;
	
	public VirusSpread() throws IOException{
		
		
		DataReader dr = new DataReader("test.txt");
		nodesList_Out = dr.getNodesList_Out();
		nodesList_In = dr.getNodesList_In();
	}
	

}
