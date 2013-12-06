package RiverProblems;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.html.parser.Parser;

import graph_lib.AIGraph;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import graph_lib_extensions.*;

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
	}
	
	
	/*
	 * Der Pfad, wo Ihre Textdatei mit den Graphen liegt
	 * Example: Pfrad -> c:\\graphen\\graph8
	 */
//	@Test 
//	public void graph8Test() {
//		String path = "c:\\users\\foxhound\\desktop\\graph8.txt";
//		
//	 	graph_lib_extensions.Parser graphParser = new graph_lib_extensions.Parser(Paths.get(path));
//	 	
//	 	AIGraph graph8 = graphParser.createGraph();
//	 	//graph8.toString();
//	 	
//	 	FordAndFulkerson fordAndFulkerson = new FordAndFulkerson(graph8);
//	 	fordAndFulkerson.startAlgorithmus();
//	 	System.out.println("Optimaler Fluss = " + fordAndFulkerson.getOptiomalFlow());
//	 	System.out.println("**************************ENDE*****************************");	 
//	 	System.out.println("Zugriffe auf den Graph = " + fordAndFulkerson.getAccess());
//	}
//	
//	@Test
//	public void graph9Test() {
//		String path = "c:\\users\\foxhound\\desktop\\graph9.txt";
//		
//	 	graph_lib_extensions.Parser graphParser = new graph_lib_extensions.Parser(Paths.get(path));
//	 	
//	 	AIGraph graph9 = graphParser.createGraph();
//	 	//graph8.toString();
//	 	
//	 	FordAndFulkerson fordAndFulkerson = new FordAndFulkerson(graph9);
//	 	fordAndFulkerson.startAlgorithmus();
//	 	System.out.println("Optimaler Fluss = " + fordAndFulkerson.getOptiomalFlow());
//	 	System.out.println("Zugriffe auf den Graph = " + fordAndFulkerson.getAccess());
//	}
////	
	@Test
	public void graphGRBuchSeite99() {
		String path = "c:\\users\\foxhound\\desktop\\GRBuch99.txt";
		
	 	graph_lib_extensions.Parser graphParser = new graph_lib_extensions.Parser(Paths.get(path));
	 	
	 	AIGraph GRBuch99 = graphParser.createGraph();
	 	//graph8.toString();
	 	
	 	FordAndFulkerson fordAndFulkerson = new FordAndFulkerson(GRBuch99);
	 	fordAndFulkerson.startAlgorithmus();
	 	System.out.println("Optimaler Fluss = " + fordAndFulkerson.getOptiomalFlow());
	}
//	
//	@Test
//	public void markedAllTest() {
//		//Erstellen der Knoten
//		//int source = graph.addVertex("Source");
//		int v2 = graph.addVertex("v2");
//		int v3 = graph.addVertex("v3"); 
//		int v4 = graph.addVertex("v4");
//		
//		//Kanten hinzu fuegen
//		int sourceTov2 = graph.addEdgeD(source, v2);
//		int v4ToSource = graph.addEdgeD(v4, source);
//		int v2Tov3 = graph.addEdgeD(v2, v3);
//		
//		
//		
//		//Bei den Kanten den zweier Tupel von Kapazitaet und tatsaechlichen Fluss setzten
//		fordAndFulkerson.setCapacityActualRiverTuple(sourceTov2, 3, 0);
//		fordAndFulkerson.setCapacityActualRiverTuple(v2Tov3, 25, 0);
//		fordAndFulkerson.setCapacityActualRiverTuple(v4ToSource, 2, 0);
//		
//		fordAndFulkerson.markedAllVertex(source);
//		
//		List<Integer> vertexIDList = new ArrayList<>(Arrays.asList(source, v2, v4, v3));
//		//Funktioniert
////		for (int vertexID : vertexIDList) {
////			System.out.println("VertexID = " + graph.getStrV(vertexID, "name") + "; ID = " + vertexID + " marked = " + fordAndFulkerson.isMarked(vertexID) + " Tuple = (" + fordAndFulkerson.getPredecessorID(vertexID) + " | " + fordAndFulkerson.getDelta(vertexID) + ")");
////		}
//	}
//	
//	@Test
//	public void markedAllTest2() {
//		//Erstellen der Knoten
//		//int source = graph.addVertex("Source");
//		int v2 = graph.addVertex("v2");
//		int v3 = graph.addVertex("v3");
//		int v4 = graph.addVertex("v4");
//		
//		//Kanten hinzu fuegen
//		int sourceTov2 = graph.addEdgeD(source, v2);
//		int v4ToSource = graph.addEdgeD(v4, source);
//		int v2Tov3 = graph.addEdgeD(v2, v3);
//				
//		//Bei den Kanten den zweier Tupel von Kapazitaet und tatsaechlichen Fluss setzten
//		fordAndFulkerson.setCapacityActualRiverTuple(sourceTov2, 3, 2);
//		fordAndFulkerson.setCapacityActualRiverTuple(v2Tov3, 25, 2);
//		fordAndFulkerson.setCapacityActualRiverTuple(v4ToSource, 2, 4);
//		
//		fordAndFulkerson.markedAllVertex(source);
//		
//		List<Integer> vertexIDList = new ArrayList<>(Arrays.asList(source, v2, v4, v3));
//		
//		//Funktioniert
////		for (int vertexID : vertexIDList) {
////			System.out.println("VertexID = " + graph.getStrV(vertexID, "name") + "; ID = " + vertexID + " marked = " + fordAndFulkerson.isMarked(vertexID) + " Tuple = (" + fordAndFulkerson.getPredecessorID(vertexID) + " | " + fordAndFulkerson.getDelta(vertexID) + ")");
////		}
//	}
//	
//	@Test
//	public void markedAllTest3() {
//		//Erstellen der Knoten
//		//int source = graph.addVertex("Source");
//		int v2 = graph.addVertex("v2");
//		int v3 = graph.addVertex("v3");
//		int v4 = graph.addVertex("v4");
//		
//		//Kanten hinzu fuegen
//		int sourceTov2 = graph.addEdgeD(source, v2);
//		int v4ToSource = graph.addEdgeD(v4, source);
//		int v2Tov3 = graph.addEdgeD(v2, v3);
//				
//		//Bei den Kanten den zweier Tupel von Kapazitaet und tatsaechlichen Fluss setzten
//		fordAndFulkerson.setCapacityActualRiverTuple(sourceTov2, 7, 0);
//		fordAndFulkerson.setCapacityActualRiverTuple(v2Tov3, 12, 6);
//		fordAndFulkerson.setCapacityActualRiverTuple(v4ToSource, 10, 4);
//		
//		fordAndFulkerson.markedAllVertex(source);
//		fordAndFulkerson.inspectedRandomVertex(source);
//		fordAndFulkerson.markedAllVertex(v2);
//		
//		List<Integer> vertexIDList = new ArrayList<>(Arrays.asList(source, v2, v4, v3));
//		//Funktioniert
////		for (int vertexID : vertexIDList) {
////			System.out.println("VertexID = " + graph.getStrV(vertexID, "name") + "; ID = " + vertexID + " marked = " + fordAndFulkerson.isMarked(vertexID) + " Tuple = (" + fordAndFulkerson.getPredecessorID(vertexID) + " | " + fordAndFulkerson.getDelta(vertexID) + ")");
////		}
//	}
//	
//	@Test
//	public void isAllmarkedVertexNotInspectedTest() {
//		int v2 = graph.addVertex("v2");
//		int v3 = graph.addVertex("v3");
//		
//		fordAndFulkerson.setMarked(v2, 1, 1);
//		fordAndFulkerson.setMarked(v3, 1, 1);
//		
//		/*
//		 * Muss true zurueck liefern, weil die markierten Knoten nicht inspeziert sind
//		 */
//		assertEquals(true, fordAndFulkerson.isAllmarkedVertexNotInspected());
//		
//		/*
//		 * Jetzt inspezieren wir nur einen Knoten, muss trozdem noch true zurueck liefern
//		 */
//		fordAndFulkerson.setInspected(v2);
//		assertEquals(true, fordAndFulkerson.isAllmarkedVertexNotInspected());
//		
//		/*
//		 * Jetzt inspezieren wir die letzte markierten Knoten, somit waeren alle 
//		 * markierten Knoten inspeziert und wir erwarten ein false
//		 */
//		fordAndFulkerson.setInspected(v3);
//		assertEquals(false, fordAndFulkerson.isAllmarkedVertexNotInspected());
//	}
//	
//	/*
//	 * Der Target wird anhand des namen "target" indifiziert
//	 */
//	@Test 
//	public void isTargetTest() {
//		int v2 = graph.addVertex("v2");
//		int v3 = graph.addVertex("v3");
//		int target = graph.addVertex("target");
//				
//		assertEquals(false, fordAndFulkerson.isTarget(v2));
//		assertEquals(false, fordAndFulkerson.isTarget(v3));
//		assertEquals(true, fordAndFulkerson.isTarget(target));
//	}
//
//	@Test
//	public void inspectedRandomVertexTest() {
//		//Erstellen der Knoten
//		int v2 = graph.addVertex("v2");
//		int v3 = graph.addVertex("v3");
//		int v4 = graph.addVertex("v4");
//		int v5 = graph.addVertex("v5");		
//		//int s = graph.addVertex("Target");
//		
//		//Kanten hinzu fuegen
//		int sourceTov2 = graph.addEdgeD(source, v2);
//		int sourceTov4 = graph.addEdgeD(source, v4);
//		int v2Tov3 = graph.addEdgeD(v2, v3);
//		int v3Tov4 = graph.addEdgeD(v3, v4);
//		int v3Tos = graph.addEdgeD(v3, target);
//		
//		int v4Tov2 = graph.addEdgeD(v4, v2);
//		int v4Tov5 = graph.addEdgeD(v4, v5);
//		int v5Tov3 = graph.addEdgeD(v5, v3);
//		int v5Tos = graph.addEdgeD(v5, target);
//		
//		
//		
//		//Funktioniert!
//		int inspectedVertex = source;
//		fordAndFulkerson.markedAllVertex(inspectedVertex);
//		
////		system.out.println("vertexliste die mit den inspizierten knoten in direkter verbindung stehen: " + graph.getadjacent(inspectedvertex)) ;
////		//system.out.println("davon die vertexliste, deren knoten noch nicht inspiziert sind: " ); 
////		system.out.println("wurde inspiziert mit der id: " + fordandfulkerson.inspectedrandomvertex(inspectedvertex));
//	}
//	
//	@Test
//	public void backToTheSourceTest() {
//		//Erstellen der Knoten
//		int v2 = graph.addVertex("v2");
//		int v3 = graph.addVertex("v3");
//		
//		//Erstellen der Kanten
//		int sourceTov2 = graph.addEdgeD(source, v2);
//		int v2Tov3 = graph.addEdgeD(v2, v3);
//		int v3Totarget = graph.addEdgeD(v3, target);
//		
//		//Tupe der Kanten Setzten
//		fordAndFulkerson.setCapacityActualRiverTuple(sourceTov2, 3, 0);
//		fordAndFulkerson.setCapacityActualRiverTuple(v2Tov3, 2, 0);
//		fordAndFulkerson.setCapacityActualRiverTuple(v3Totarget, 2, 0);
//		
//		//Die Knoten markieren :
//		fordAndFulkerson.markedAllVertex(source);
//		fordAndFulkerson.inspectedRandomVertex(source);
//		
//		fordAndFulkerson.markedAllVertex(v2);
//		fordAndFulkerson.inspectedRandomVertex(v2);
//		
//		fordAndFulkerson.markedAllVertex(v3);
//		fordAndFulkerson.inspectedRandomVertex(v3);
//		
//		fordAndFulkerson.markedAllVertex(target);
//		fordAndFulkerson.inspectedRandomVertex(target);
//		
//		//Funktioniert fuer den definierten Fall!
//		
//		//Einmal betrachten wie der Graph vor der Methode aussieht
////		System.out.println("Vorher: ");
////		System.out.println("Kapazitaet = " + fordAndFulkerson.getCapacity(sourceTov2) + " Tatsaechlicher Fluss = " + fordAndFulkerson.getActualRiver(sourceTov2));
////		System.out.println("Kapazitaet = " + fordAndFulkerson.getCapacity(v2Tov3) + " Tatsaechlicher Fluss = " + fordAndFulkerson.getActualRiver(v2Tov3));
////		System.out.println("Kapazitaet = " + fordAndFulkerson.getCapacity(v3Totarget) + " Tatsaechlicher Fluss = " + fordAndFulkerson.getActualRiver(v3Totarget));
////		System.out.println(fordAndFulkerson.isMarked(source));
////		System.out.println(fordAndFulkerson.isMarked(v2));
////		System.out.println(fordAndFulkerson.isMarked(v3));
////		System.out.println(fordAndFulkerson.isMarked(target));
////		
////		fordAndFulkerson.backToTheSource(target);
////		
////		//Betrachten, wie der Graph nach der Methode aussieht
////		System.out.println("Nachher: ");
////		System.out.println("Kapazitaet = " + fordAndFulkerson.getCapacity(sourceTov2) + " Tatsaechlicher Fluss = " + fordAndFulkerson.getActualRiver(sourceTov2));
////		System.out.println("Kapazitaet = " + fordAndFulkerson.getCapacity(v2Tov3) + " Tatsaechlicher Fluss = " + fordAndFulkerson.getActualRiver(v2Tov3));
////		System.out.println("Kapazitaet = " + fordAndFulkerson.getCapacity(v3Totarget) + " Tatsaechlicher Fluss = " + fordAndFulkerson.getActualRiver(v3Totarget));
////		System.out.println(fordAndFulkerson.isMarked(source));
////		System.out.println(fordAndFulkerson.isMarked(v2));
////		System.out.println(fordAndFulkerson.isMarked(v3));
////		System.out.println(fordAndFulkerson.isMarked(target));
//		
//		
//	}
}
