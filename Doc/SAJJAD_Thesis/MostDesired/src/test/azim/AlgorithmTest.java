package test.azim;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;
import algorithm.azim.mostdesired.Algorithm;


public class AlgorithmTest {

	Double[] sssResult = new Double[1];
	ArrayList<Integer> mostWantedTest;//actual result

	@Test
	public void test_1() throws IOException {
		
		ArrayList<Integer> mostWanted = new ArrayList<Integer>();//expected result
		mostWanted.add(4);
		mostWanted.add(1);
		mostWanted.add(2);
		mostWantedTest = Algorithm.runAlgorithm("toy_e1.txt", 3, 10, sssResult, null);
		for(int i = 0; i < mostWantedTest.size(); i++)
			System.out.println("=======" + mostWantedTest.get(i));
		assertEquals(mostWanted, mostWantedTest);
	}
	
	@Test
	public void test_2() throws IOException {
		
		ArrayList<Integer> mostWanted = new ArrayList<Integer>();//expected result
		mostWanted.add(4);
		mostWanted.add(1);
		mostWanted.add(2);
		mostWantedTest = Algorithm.runAlgorithm("toy_e1.txt", 3, 10, sssResult, null);
		assertEquals((Double)3.5550000000000006, sssResult[0]);
	}
	
	

}
