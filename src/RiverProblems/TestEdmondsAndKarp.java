package RiverProblems;

import static org.junit.Assert.*;
import graph_lib.AIGraph;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestEdmondsAndKarp {
	
	AIGraph graph;
	EdmondsAndKarp edmondsAndKarp;

	int source;
	int target;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		graph = new AIGraph();
		source = graph.addVertex("source");
		target = graph.addVertex("target");
		edmondsAndKarp = new EdmondsAndKarp(graph, source , target);
	}

	
	/**
	 * In diesem Test wird der Graph von GRBuch Seite 99 
	 * zusammen gebaut
	 */
	@Test
	public void optimalRiverTest() {
		int expected = 4;

		//Erstellen der Knoten
		//int source = graph.addVertex("Source");
		int v2 = graph.addVertex("v2");
		int v3 = graph.addVertex("v3");
		int v4 = graph.addVertex("v4");
		int v5 = graph.addVertex("v5");		
		//int s = graph.addVertex("Target");
		
		
		
		//Kanten hinzu fuegen
		int sourceTov2 = graph.addEdgeD(source, v2);
		int sourceTov4 = graph.addEdgeD(source, v4);
		int v2Tov3 = graph.addEdgeD(v2, v3);
		int v3Tov4 = graph.addEdgeD(v3, v4);
		int v3Totarget = graph.addEdgeD(v3, target);
		
		int v4Tov2 = graph.addEdgeD(v4, v2);
		int v4Tov5 = graph.addEdgeD(v4, v5);
		int v5Tov3 = graph.addEdgeD(v5, v3);
		int v5Totarget = graph.addEdgeD(v5, target);
		
		//Tupe der Kanten Setzten
		edmondsAndKarp.setCapacityActualRiverTuple(sourceTov2, 3, 0);
		edmondsAndKarp.setCapacityActualRiverTuple(v2Tov3, 2, 0);
		edmondsAndKarp.setCapacityActualRiverTuple(v3Totarget, 2, 0);
		edmondsAndKarp.setCapacityActualRiverTuple(v3Tov4, 1, 0);
		
		edmondsAndKarp.setCapacityActualRiverTuple(sourceTov4, 2, 0);
		edmondsAndKarp.setCapacityActualRiverTuple(v4Tov2, 2, 0);
		edmondsAndKarp.setCapacityActualRiverTuple(v4Tov5, 2, 0);
		
		edmondsAndKarp.setCapacityActualRiverTuple(v5Tov3, 1, 0);
		edmondsAndKarp.setCapacityActualRiverTuple(v5Totarget, 4, 0);
		
		
		System.out.println(graph.toString());
		edmondsAndKarp.startAlgorithmus();
		
	}
}
