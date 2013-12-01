package RiverProblems;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import graph_lib.AIGraph;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFordAndFulkerson {
	
	AIGraph graph;
	FordAndFulkerson fordAndFulkerson;

	int source;
	int target;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() {
		graph = new AIGraph();
		source = graph.addVertex("source");
		target = graph.addVertex("target");
		fordAndFulkerson = new FordAndFulkerson(graph, source , target);		
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
		int v3Tos = graph.addEdgeD(v3, target);
		
		int v4Tov2 = graph.addEdgeD(v4, v2);
		int v4Tov5 = graph.addEdgeD(v4, v5);
		int v5Tov3 = graph.addEdgeD(v5, v3);
		int v5Tos = graph.addEdgeD(v5, target);
		
		//this.fordAndFulkerson.startAlgorithmus(graph, source, s);
		//assertEsourceuals(expected, fordAndFulkerson.getOptiomalRiver());
	}
	
	@Test
	public void markedAllTest() {
		//Erstellen der Knoten
		//int source = graph.addVertex("Source");
		int v2 = graph.addVertex("v2");
		int v3 = graph.addVertex("v3");
		int v4 = graph.addVertex("v4");
		
		//Kanten hinzu fuegen
		int sourceTov2 = graph.addEdgeD(source, v2);
		int v4ToSource = graph.addEdgeD(v4, source);
		int v2Tov3 = graph.addEdgeD(v2, v3);
		
		
		
		//Bei den Kanten den zweier Tupel von Kapazitaet und tatsaechlichen Fluss setzten
		fordAndFulkerson.setCapacityActualRiverTuple(sourceTov2, 3, 0);
		fordAndFulkerson.setCapacityActualRiverTuple(v2Tov3, 25, 0);
		fordAndFulkerson.setCapacityActualRiverTuple(v4ToSource, 2, 0);
		
		fordAndFulkerson.markedAllVertex(source);
		
		List<Integer> vertexIDList = new ArrayList<>(Arrays.asList(source, v2, v4, v3));
		//Funktioniert
//		for (int vertexID : vertexIDList) {
//			System.out.println("VertexID = " + graph.getStrV(vertexID, "name") + "; ID = " + vertexID + " marked = " + fordAndFulkerson.isMarked(vertexID) + " Tuple = (" + fordAndFulkerson.getPredecessorID(vertexID) + " | " + fordAndFulkerson.getDelta(vertexID) + ")");
//		}
	}
	
	@Test
	public void markedAllTest2() {
		//Erstellen der Knoten
		//int source = graph.addVertex("Source");
		int v2 = graph.addVertex("v2");
		int v3 = graph.addVertex("v3");
		int v4 = graph.addVertex("v4");
		
		//Kanten hinzu fuegen
		int sourceTov2 = graph.addEdgeD(source, v2);
		int v4ToSource = graph.addEdgeD(v4, source);
		int v2Tov3 = graph.addEdgeD(v2, v3);
				
		//Bei den Kanten den zweier Tupel von Kapazitaet und tatsaechlichen Fluss setzten
		fordAndFulkerson.setCapacityActualRiverTuple(sourceTov2, 3, 2);
		fordAndFulkerson.setCapacityActualRiverTuple(v2Tov3, 25, 2);
		fordAndFulkerson.setCapacityActualRiverTuple(v4ToSource, 2, 4);
		
		fordAndFulkerson.markedAllVertex(source);
		
		List<Integer> vertexIDList = new ArrayList<>(Arrays.asList(source, v2, v4, v3));
		
		//Funktioniert
//		for (int vertexID : vertexIDList) {
//			System.out.println("VertexID = " + graph.getStrV(vertexID, "name") + "; ID = " + vertexID + " marked = " + fordAndFulkerson.isMarked(vertexID) + " Tuple = (" + fordAndFulkerson.getPredecessorID(vertexID) + " | " + fordAndFulkerson.getDelta(vertexID) + ")");
//		}
	}
	
	@Test
	public void markedAllTest3() {
		//Erstellen der Knoten
		//int source = graph.addVertex("Source");
		int v2 = graph.addVertex("v2");
		int v3 = graph.addVertex("v3");
		int v4 = graph.addVertex("v4");
		
		//Kanten hinzu fuegen
		int sourceTov2 = graph.addEdgeD(source, v2);
		int v4ToSource = graph.addEdgeD(v4, source);
		int v2Tov3 = graph.addEdgeD(v2, v3);
				
		//Bei den Kanten den zweier Tupel von Kapazitaet und tatsaechlichen Fluss setzten
		fordAndFulkerson.setCapacityActualRiverTuple(sourceTov2, 7, 0);
		fordAndFulkerson.setCapacityActualRiverTuple(v2Tov3, 12, 6);
		fordAndFulkerson.setCapacityActualRiverTuple(v4ToSource, 10, 4);
		
		fordAndFulkerson.markedAllVertex(source);
		fordAndFulkerson.inspectedRandomVertex(source);
		fordAndFulkerson.markedAllVertex(v2);
		
		List<Integer> vertexIDList = new ArrayList<>(Arrays.asList(source, v2, v4, v3));
		//Funktioniert
//		for (int vertexID : vertexIDList) {
//			System.out.println("VertexID = " + graph.getStrV(vertexID, "name") + "; ID = " + vertexID + " marked = " + fordAndFulkerson.isMarked(vertexID) + " Tuple = (" + fordAndFulkerson.getPredecessorID(vertexID) + " | " + fordAndFulkerson.getDelta(vertexID) + ")");
//		}
	}
	
	@Test
	public void isAllmarkedVertexNotInspectedTest() {
		int v2 = graph.addVertex("v2");
		int v3 = graph.addVertex("v3");
		
		fordAndFulkerson.setMarked(v2, 1, 1);
		fordAndFulkerson.setMarked(v3, 1, 1);
		
		/*
		 * Muss true zurueck liefern, weil die markierten Knoten nicht inspeziert sind
		 */
		assertEquals(true, fordAndFulkerson.isAllmarkedVertexNotInspected());
		
		/*
		 * Jetzt inspezieren wir nur einen Knoten, muss trozdem noch true zurueck liefern
		 */
		fordAndFulkerson.setInspected(v2);
		assertEquals(true, fordAndFulkerson.isAllmarkedVertexNotInspected());
		
		/*
		 * Jetzt inspezieren wir die letzte markierten Knoten, somit waeren alle 
		 * markierten Knoten inspeziert und wir erwarten ein false
		 */
		fordAndFulkerson.setInspected(v3);
		assertEquals(false, fordAndFulkerson.isAllmarkedVertexNotInspected());
	}
	
	/*
	 * Der Target wird anhand des namen "target" indifiziert
	 */
	@Test 
	public void isTargetTest() {
		int v2 = graph.addVertex("v2");
		int v3 = graph.addVertex("v3");
		int target = graph.addVertex("target");
				
		assertEquals(false, fordAndFulkerson.isTarget(v2));
		assertEquals(false, fordAndFulkerson.isTarget(v3));
		assertEquals(true, fordAndFulkerson.isTarget(target));
	}

	@Test
	public void inspectedRandomVertexTest() {
		//Erstellen der Knoten
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
		int v3Tos = graph.addEdgeD(v3, target);
		
		int v4Tov2 = graph.addEdgeD(v4, v2);
		int v4Tov5 = graph.addEdgeD(v4, v5);
		int v5Tov3 = graph.addEdgeD(v5, v3);
		int v5Tos = graph.addEdgeD(v5, target);
		
		
		
		//Funktioniert!
		int inspectedVertex = source;
		fordAndFulkerson.markedAllVertex(inspectedVertex);
		
//		system.out.println("vertexliste die mit den inspizierten knoten in direkter verbindung stehen: " + graph.getadjacent(inspectedvertex)) ;
//		//system.out.println("davon die vertexliste, deren knoten noch nicht inspiziert sind: " ); 
//		system.out.println("wurde inspiziert mit der id: " + fordandfulkerson.inspectedrandomvertex(inspectedvertex));
	}
}
