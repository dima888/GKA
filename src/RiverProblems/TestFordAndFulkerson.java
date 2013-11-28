package RiverProblems;

import static org.junit.Assert.*;
import graph_lib.AIGraph;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFordAndFulkerson {
	
	AIGraph graph;
	FordAndFulkerson fordAndFulkerson;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		graph = new AIGraph();
		fordAndFulkerson = new FordAndFulkerson();		
	}

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
//	
	/**
	 * In diesem Test wird der Graph von GRBuch Seite 99 
	 * zusammen gebaut
	 */
	@Test
	public void optimalRiverTest() {
		int expected = 4;
		
		//Erstellen der Knoten
		int q = graph.addVertex("Source");
		int v2 = graph.addVertex("v2");
		int v3 = graph.addVertex("v3");
		int v4 = graph.addVertex("v4");
		int v5 = graph.addVertex("v5");		
		int s = graph.addVertex("Target");
		
		//Kanten hinzu fuegen
		int qTov2 = graph.addEdgeD(q, v2);
		int qTov4 = graph.addEdgeD(q, v4);
		int v2Tov3 = graph.addEdgeD(v2, v3);
		int v3Tov4 = graph.addEdgeD(v3, v4);
		int v3Tos = graph.addEdgeD(v3, s);
		
		int v4Tov2 = graph.addEdgeD(v4, v2);
		int v4Tov5 = graph.addEdgeD(v4, v5);
		int v5Tov3 = graph.addEdgeD(v5, v3);
		int v5Tos = graph.addEdgeD(v5, s);
		
		this.fordAndFulkerson.startAlgorithmus(graph, q, s);
		assertEquals(expected, fordAndFulkerson.getOptiomalRiver());
	}

}
