package test.azim;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import InputData.azim.mostdesired.DataReader;

public class DataReaderTest {

	@Test
	public void getNumberOfLinesTest_1() throws IOException {
		DataReader dr = new DataReader("midSizeGraph_20.txt");
		assertEquals(70, dr.getNumberOfLines());
	}
	
	@Test
	public void getNumberOfLinesTest_2() throws IOException {
		DataReader dr = new DataReader("5NodeGraph.txt");
		assertEquals(6, dr.getNumberOfLines());
	}

	@Test
	public void getNumberOfLinesTest_3() throws IOException {
		DataReader dr = new DataReader("DBLP_subgraph_10000_p.txt");
		assertEquals(17968, dr.getNumberOfLines());
	}
	
	@Test
	public void getMaxIndexTest_1() throws IOException {
		DataReader dr = new DataReader("5NodeGraph.txt");
		assertEquals(4, dr.getMaxIndex());
	}
	
	@Test
	public void getMaxIndexTest_2() throws IOException {
		DataReader dr = new DataReader("toy_e1.txt");
		assertEquals(8, dr.getMaxIndex());
	}
	
	@Test
	public void getMaxIndexTest_3() throws IOException {
		DataReader dr = new DataReader("DBLP_subgraph_10000_p.txt");
		assertEquals(9999, dr.getMaxIndex());
	}

}
