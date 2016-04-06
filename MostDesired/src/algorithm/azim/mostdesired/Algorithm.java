package algorithm.azim.mostdesired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import inputdata.azim.mostdesired.DataReader;
import sortingclasses.azim.mostdesired.InsertionSort;
import supplementaryclasses.azim.mostdesired.NodeAndWeight;

/**
 * 
 * @author azim
 *<br><br><br>
 *	This class contains 2 important methods:<br>
 *<br>
 *
 *
 *</UL TYPE="square">
 *		<LI> int steadyStateProbability(List s, List_of_Lists p):<br>
 *
 *&nbsp		This computes the steady state probability for a set of nodes (given by s) chosen from
 *		all nodes. It also needs the transition probability matrix that in our case is the weight
 *		of edges (given by p). From each node's point of view, we are interested in edges coming in
 *		to them and not going out from them.<br>
 *&nbsp		The result is the number of nodes under influence of the nodes in s.
 *
 * 
 * 		<LI> rankedReplace(List_of_Lists p, int k):<br>
 * 
 * &nbsp	This method is responsible for finding a set of k nodes which together gives the greatest
 * 		steady state probability in comparison to any other set of k nodes. In other words, it finds
 * 		k most influential nodes.
 * 
 * </UL>
 */

public abstract class Algorithm {
	
	private static int maxUselessIterations;
	private static ArrayList<LinkedList<NodeAndWeight>> nodesList_Out;
	private static ArrayList<LinkedList<NodeAndWeight>> nodesList_In;
	
	
	
	/**
	 * 
	 * @param fileName is the input file with the required format (Look at the documentations).
	 * @param k is the number of influential nodes we are looking for.
	 * @param error is a number of ineffective iterations after which the loop stops.
	 * @throws IOException
	 */
	public static ArrayList<Integer> runAlgorithm(String fileName, int k, int error, Double[] sssResult, AlgorithmProgressMonitor progressMonitor) throws IOException{
		
		
		//progressMonitor.logUpdate("Start ...");
		maxUselessIterations = error;
		DataReader dr = new DataReader(fileName);
		
		//get the full graph as it was read form the input file
		nodesList_Out = dr.getNodesList_Out();
		nodesList_In  = dr.getNodesList_In(); 
		System.out.println("\n\n nodesList.size():" + nodesList_Out.size());
		
		
		ArrayList<Integer> s = new ArrayList<Integer>();
		
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		progressMonitor.progressUpdated(50);
		s = rankedReplace(nodesList_In, k, progressMonitor);
		sssResult[0] = steadyStateSpread(s, nodesList_In);
		//progressMonitor.logUpdate("[A]: SSS:\n\t" + sssResult[0]);
		
		System.out.println("[>] " + k + " MOST INFLUENTIAL NODES ARE:");
		for(int i = 0 ; i < s.size(); i++){
			System.out.println(i + ": " + s.get(i));
		}
		
		//progressMonitor.logUpdate("End ...");
		return s;
	}
	
	
	/**
	 * 
	 * @param s is a set that uniquely contains the id of some nodes which are
	 * somehow chosen among all nodes from 0 to 'graphSize'
	 * 
	 * @param tm
	 */
	public static Double steadyStateSpread(ArrayList<Integer> s, ArrayList<LinkedList<NodeAndWeight>> nodes_In){
		
		Double thisNeighborId;
		Double product = 1.0;								//result of the bounded product
		Double transProbTo_i = 0.0;							//transition probability
		Double initialError = 0.0;
		Double currentError;
		boolean firstIteration = true;
		boolean doContinue = true;
		LinkedList<NodeAndWeight> incomingNeighbors1;
		//Both 'q' and '_q' are a 1-to-1 map to 'nodes_In' or 'nodes_Out'
		Double[] q = new Double[nodes_In.size()]; 	//is the probability of assimilation of data by each node
		Double[] q_ = new Double[nodes_In.size()];	//is the same as 'q' but in the next iteration
		
		
		//iterate over all nodes and initialize their assimilation probability by either 1 or 0.
		// 1 means this node was mentioned in s
		// 0 otherwise
		for(int i = 0; i < q.length; i++){
			if(s.contains(i)){
				q[i] = 1.0;
			}
			else{
				q[i] = 0.0;
			}
		}
		
//		System.out.println("Initial assimilation for " + s.size() + " node(s).");
//		System.out.println(Arrays.toString(q));
		
		
		while(doContinue){
			
			
			//iterate over all nodes and update q_[i] for all i's.
			for(int i = 0; i < q.length; i++){
				
				if(s.contains(i)){
					q_[i] = 1.0;
				}
				else{
					incomingNeighbors1 = nodes_In.get(i);
					transProbTo_i = 0.0;
					product = 1.0;//reset product
					
					
					//BEGIN  computation of the formula
					
					//If this node has no neighbors from which an edge is coming
					if(incomingNeighbors1.size() == 1){
						q_[i] = 0.0;	//assimilation probability is zero
					}
					else{
						//this loop starts from 1 (not 0) because we put one dummy data as the beginning of such lists.
						for(int j = 1; j < incomingNeighbors1.size(); j++){
							
							thisNeighborId = incomingNeighbors1.get(j).getAdjacentVertex();
							transProbTo_i = incomingNeighbors1.get(j).getWeight();	//transition probability from 'thisNeighborId' to i
							product *= (1 - (transProbTo_i * q[thisNeighborId.intValue()]));
							}
						}
						q_[i] = 1 - product;
					}
					//END formula
				
			}//END of inner loop
			
			currentError = sumOfDifArrays(q_, q);
			
			//if the error rate is satisfying stop computation
			if(currentError <= 0.01 * initialError){	//without the equality sign, this loop may never stop (if 
														//in currentError = initialError = 0)
				doContinue = false;
				//we also can return here
			}
			else{
				//copy q_ to q
				q = Arrays.copyOf(q_, q_.length);
			}
			if(firstIteration){
				initialError = currentError;//only once we update initial error
				firstIteration = false;
			}
			
		}//END of outer loop
		
//		System.out.println("Final assimilation:");
//		System.out.println(Arrays.toString(q));
		
		//System.out.println(".");
		
		
		return sumOfArray(q, s);
	}
	
	
	public static ArrayList<Integer> rankedReplace(ArrayList<LinkedList<NodeAndWeight>> nodes_In, int k, AlgorithmProgressMonitor progressMonitor){
		
		/**
		 * We keep values in 'sss' and their indices in 'indices'.
		 * 		1.sss value for node i is sss[i].
		 * 		2.If indices[t] = k, then sss[k] is the corresponding sss value.
		 * 
		 * When we sort sssCloned and indices:
		 * 
		 * 		sssCloned [[			A				][	 B	]]
		 * 		indices   [[			A'				][	 B'	]]
		 * 
		 * 		A (in reversed order) ---> u_s_values
		 * 		A'(in reversed order) ---> u_s_indices
		 * 
		 * 		B (in the same order) ---> s_values
		 * 		B'(in the same order) ---> s_indices
		 */
		
		//int progress = 0;
		
		
		
		
		ArrayList<Integer> indices = new ArrayList<Integer>(nodes_In.size());//indices of elements in sssCloned
		ArrayList<Double> sss = new ArrayList<Double>(nodes_In.size());
		ArrayList<Double> sssCopy;
		ArrayList<Integer> s_indices = new ArrayList<Integer>(k);	//k authority nodes (based on their sss values)
		ArrayList<Double> s_values = new ArrayList<Double>(k);
		ArrayList<Integer> u_s_indices = new ArrayList<Integer>(nodes_In.size() - k);	//All nodes minus s
		ArrayList<Double> u_s_values = new ArrayList<Double>(nodes_In.size() - k);
		ArrayList<Integer> smallSet = new ArrayList<Integer>(1);	//this is a single element set
		
		System.out.println(">>>");
		
		
		//1.Find steady state probability for single element sets.
		for(int i = 0; i < nodes_In.size(); i++){
			smallSet.clear();
			smallSet.add(i);
			sss.add(steadyStateSpread(smallSet, nodes_In));
			indices.add(i);
//			System.out.println("Node("+i+")" + "--------" + sss.get(i));
//			System.out.println("\n__________________________________\n");
		}
		
		
		//2.Store the k nodes, which obtained the best sss results, in s in ASCENDING order
		
//		sssCloned =  (ArrayList<Double>) sss.clone();
		sssCopy = new ArrayList<Double>(sss);
		InsertionSort.sort(sssCopy, indices);
		
		//put last k nodes (which are the k authority nodes) in s
		for(int i = nodes_In.size() - k; i < nodes_In.size(); i++){
			s_indices.add(indices.get(i));
			s_values.add(sssCopy.get(i));
		}
		//3.Put the remaining nodes in u-s, but in reversed order (:DESCENDING)
		for(int i = nodes_In.size() - k - 1; i >= 0; i--){
			u_s_indices.add(indices.get(i));
			u_s_values.add(sssCopy.get(i));
		}
		
		//4.Check of any replacement of any node from U_S with a node in S, can increase sss of s.
		boolean anyChangesOnS = false;
		int lastAccepted_i = 0;
		int candidateFromU_S;
		int candidateFromS;
		Double lastValueForS = 0.0;
		for(int i = 0; ((i < u_s_indices.size()) && ((i - lastAccepted_i) < maxUselessIterations)); i++){
			
			
			//sort s if any of its nodes was replaces in previous iterations
			progressMonitor.progressUpdated(10);
			if(anyChangesOnS)
				InsertionSort.sort(s_values, s_indices);
			
			anyChangesOnS = false;
			
			candidateFromU_S = u_s_indices.get(i);
			lastValueForS = steadyStateSpread(s_indices, nodes_In);
			
			System.out.print("\n\n|||||Proposed s:|||||");
			
			for(int p = 0; p < s_indices.size(); p++)
				System.out.print(s_indices.get(p) + ",");
			System.out.println();
			
			
			
			for(int j = 0; j < s_indices.size(); j++){
				
				//progress += (100 / u_s_indices.size()*s_indices.size());
				
				
				candidateFromS = s_indices.get(j);
				s_indices.set(j, candidateFromU_S);//replacement
				if(steadyStateSpread(s_indices, nodes_In) > lastValueForS){
					//This candidate was accepted, so we agree on recent changes on s
					lastAccepted_i = i;
					anyChangesOnS = true;
					System.out.println("One change in S ");
					break;
				}
				else{//Undo changes
					s_indices.set(j, candidateFromS);
				}
				
			}//ENF of inner loop
			
		}//END of outer loop
		
		
		System.out.println("> > >");
		
		return s_indices;
	}
	
	/**
	 * This method computes the sum of differences of all pairs like (x,y) where x = a[i] and y = b[i]. 
	 * @param a	is an array of Doubles
	 * @param b is an array of Doubles
	 * @return the computed sum.
	 */
	private static Double sumOfDifArrays(Double a[], Double b[]){
		
		assert(a != null && b != null);
		assert(a.length == b.length);
		
		Double c = 0.0;
		
		for(int i = 0; i < a.length; i++)
			c += Math.abs(a[i] - b[i]);
		return c;
		
	}
	
	/**
	 * This method computes the sum of all elements in 'a' except those whose indices can be found in 's'.
	 * @param a is an array of Double to be summed up.
	 * @param s is a set containing some indices of 'a'
	 * @return the computed sum.
	 */
	private static Double sumOfArray(Double a[], ArrayList<Integer> s){
		
		Double sum = 0.0;
		for(int i = 0; i < a.length; i++){
			if(!s.contains(i))
				sum += a[i];
		}
		return sum;
	}
}
