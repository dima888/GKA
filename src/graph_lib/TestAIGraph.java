package graph_lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAIGraph {
	
	AIGraph graph;
	AIGraph graph2;
	
	@Before
	public void init() {
		graph = new AIGraph();
		graph2 = new AIGraph();
	}
	
	@Test
	public void testAddVertex() {
		graph.addVertex("1");
		graph.addVertex("2");
		
		graph2.addVertex("1");
		graph2.addVertex("2");
		
		assertTrue(graph.equals(graph2));
	}
	
	@Test
	public void testDeleteVertexPos() {
		int v1 = graph.addVertex("1");
		int v2 = graph.addVertex("2");
		
		graph.deleteVertex(v2);
		
		graph2.addVertex("1");
		
		assertTrue(graph.equals(graph2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDeleteVertexNeg() {		
		graph.deleteVertex(1);
	}
	
	@Test
	public void testAddEdgeUPos() {
		int vertex1 = graph.addVertex("1");
		int vertex2 = graph.addVertex("2");
	
		graph.addEdgeU(vertex1, vertex2);
		
		int vertex3 = graph2.addVertex("1");
		int vertex4 = graph2.addVertex("2");
	
		graph2.addEdgeU(vertex3, vertex4);
		
		assertTrue(graph.equals(graph2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddEdgeUNeg() {
		int vertex1 = graph.addVertex("1");
		int vertex2 = graph.addVertex("2");
	
		graph.addEdgeU(vertex1, vertex2);
		graph.addEdgeU(vertex1, vertex2);
	}
	
	@Test
	public void testAddEdgeDPos() {
		int vertex1 = graph.addVertex("1");
		int vertex2 = graph.addVertex("2");
	
		graph.addEdgeD(vertex1, vertex2);
		
		int vertex3 = graph2.addVertex("3");
		int vertex4 = graph2.addVertex("4");
	
		graph2.addEdgeD(vertex3, vertex4);
		
		assertTrue(graph.equals(graph2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddEdgeDNeg() {
		int vertex1 = graph.addVertex("1");
		int vertex2 = graph.addVertex("2");
	
		graph.addEdgeD(vertex1, vertex2);
		graph.addEdgeD(vertex1, vertex2);
	}
	
	@Test
	public void testDeleteEdgeDPos() {
		int vertex1 = graph.addVertex("1");
		int vertex2 = graph.addVertex("2");
	
		graph.addEdgeD(vertex1, vertex2);
		
		graph.deleteEdge(vertex1, vertex2);
		
		int vertex3 = graph2.addVertex("1");
		int vertex4 = graph2.addVertex("2");
	
		graph2.addEdgeD(vertex3, vertex4);
		
		graph2.deleteEdge(vertex3, vertex4);
		
		assertTrue(graph.equals(graph2));
	}
	
	@Test
	public void testDeleteEdgeUPos() {
		int vertex1 = graph.addVertex("1");
		int vertex2 = graph.addVertex("2");
	
		graph.addEdgeU(vertex1, vertex2);
		
		graph.deleteEdge(vertex1, vertex2);
		
		int vertex3 = graph2.addVertex("1");
		int vertex4 = graph2.addVertex("2");
	
		graph2.addEdgeU(vertex3, vertex4);
		
		graph2.deleteEdge(vertex3, vertex4);
		
		assertTrue(graph.equals(graph2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDeleteEdgeNeg() {
		int vertex1 = graph.addVertex("1");
		int vertex2 = graph.addVertex("2");
	
		graph.addEdgeU(vertex1, vertex2);
		
		graph.deleteEdge(vertex1, vertex2);
		graph.deleteEdge(vertex1, vertex2);
	}
	
	@Test
	public void testGetSourcePos1() {
		int vertex1 = graph.addVertex("1");
		int vertex2 = graph.addVertex("2");
		
		int edge = graph.addEdgeD(vertex1, vertex2);
		int result = graph.getSource(edge);
		
		assertEquals(vertex1, result);
	}
	
	@Test (expected = IllegalArgumentException.class) //TODO: BESPRECHEN WAS WIR ZURÜCK ERWARTEN BEI FEHLERFALL VON GET_SOURCE
	public void testGetSourcePos2() {
		int vertex1 = graph.addVertex("1");
		int vertex2 = graph.addVertex("2");
	
		int edge1 = graph.addEdgeD(vertex1, vertex2);
		int edge2 = graph.addEdgeD(vertex2, vertex1);
		
		assertEquals(-1, graph.getSource(edge1));
	}
	
	@Test
	public void testGetTargetPos() {
		int vertex1 = graph.addVertex("1");
		int vertex2 = graph.addVertex("2");
	
		int edge = graph.addEdgeD(vertex1, vertex2);
		
		int result = graph.getTarget(edge);
		
		assertEquals(vertex2, result);
	}
	
	@Test
	public void testGetAdjacents() {
		int vertex1 = graph.addVertex("1");
		int vertex2 = graph.addVertex("2");
		int vertex3 = graph.addVertex("3");
		int vertex4 = graph.addVertex("4");
		
		graph.addEdgeU(vertex1, vertex2);
		graph.addEdgeU(vertex1, vertex3);
		graph.addEdgeU(vertex1, vertex4);
		
		//EXPECTED
		List<Integer> expResult = new ArrayList<>(Arrays.asList(vertex2, vertex3, vertex4));
		
		assertEquals(expResult, graph.getAdjacent(vertex1));
	}
	
	@Test
	public void testGetValE() { //Drei Tests im ein
		int vertex1ID = graph.addVertex("Timon");
		int vertex2ID = graph.addVertex("Pumba");				
		int edgeID = graph.addEdgeD(vertex1ID, vertex2ID);
					
		assertEquals(graph.getValE(edgeID, "al"), Integer.MAX_VALUE);
		assertEquals(graph.getValE(edgeID, "ID"), edgeID);				
		assertEquals(graph.getValE(edgeID, "whatever"), 100);				
	}
	
	@Test
	public void testGetValV() {	//Drei Tests im ein
		int vertexID = graph.addVertex("v100");
		
		assertEquals(graph.getValV(vertexID, "ID"), vertexID);
		assertEquals(graph.getValV(vertexID, "whatever"), 25);
		assertEquals(graph.getValV(vertexID, "Katze"), Integer.MAX_VALUE);
	}
	
	@Test
	public void testGetStrE() { //Zwei Tests im ein
		int vertex1ID = graph.addVertex("Alf");
		int vertex2ID = graph.addVertex("Ralf");				
		int edgeID = graph.addEdgeD(vertex1ID, vertex2ID);
		String emptyString = "";		
				
		assertEquals(graph.getStrE(edgeID, "name"), null); //Because, name wurde nicht gesetzt
		assertEquals(graph.getStrE(edgeID, "Hund"), emptyString);
	}
	
	@Test
	public void testGetStrV() { //Zwei Tests im ein
		int vertexID = graph.addVertex("vertex:D");		
		String emptyString = "";
		
		assertEquals(graph.getStrV(vertexID, "name"), "vertex:D");
		assertEquals(graph.getStrV(vertexID, "Banane"), emptyString);
	}
	
	/*TODO: Unnoetig hier zu testen, bei jeder erweiterung, muss der Test auch erweitert werden
			Das gleiche gilt auch fuer testGetAttrE
			Machen wir jetzt trozdem einmal zu illustration	
			*/
	@Test
	public void testGetAttrVANDE() {
		int vertexID = graph.addVertex("Mozart");
		List<String> l = new ArrayList<>(Arrays.asList("name", "ID", "whatever", "value"));
		assertEquals(l, graph.getAttrV(vertexID));
	}
	
	@Test 
	public void testSetValE() { //Drei Tests im ein
		int vertex1ID = graph.addVertex("Schröder");
		int vertex2ID = graph.addVertex("Merkel");				
		int edgeID = graph.addEdgeD(vertex1ID, vertex2ID);
		
		assertTrue(graph.setValE(edgeID, "whatever", 1500)); //Hier Setzten wir erfolgreich
		assertEquals(graph.getValE(edgeID, "whatever"), 1500); //Hier pruefen wir erfolgreich
		assertFalse(graph.setValE(edgeID, "Gnom", 1500)); //Hier pruefen wir negativ erfolgreich
	}
	
	@Test 
	public void testSetValV() { //Drei Tests im ein
		int vertexID = graph.addVertex("dostojewski");
		
		assertTrue(graph.setValV(vertexID, "whatever", 700)); //Hier Setzten wir erfolgreich
		assertEquals(graph.getValV(vertexID, "whatever"), 700);	//Hier pruefen wir erfolgreich			
		assertFalse(graph.setValV(vertexID, "Gnom", 7000)); //Hier pruefen wir negativ erfolgreich
	}
	
	@Test
	public void testSetStrE() { //Drei Tests im ein
		int vertex1ID = graph.addVertex("Schröder");
		int vertex2ID = graph.addVertex("Merkel");				
		int edgeID = graph.addEdgeD(vertex1ID, vertex2ID);
		
		assertTrue(graph.setStrE(edgeID, "name", "e100")); //Hie Setzten wir erfolgreich
		assertEquals(graph.getStrE(edgeID, "name"), "e100"); //Hier pruefen wir erfolgreich		
		assertFalse(graph.setStrE(edgeID, "Pumukel", "e100")); //Hier pruefen wir negativ erfolgreich
	}
	
	
	@Test
	public void testSetStrV() { //Drei Tests im ein
		int vertexID = graph.addVertex("Putin");
		
		assertTrue(graph.setStrV(vertexID, "name", "Hura")); //Hie Setzten wir erfolgreich
		assertEquals(graph.getStrV(vertexID, "name"), "Hura"); //Hier pruefen wir erfolgreich		
		assertFalse(graph.setStrV(vertexID, "name123", "Hura")); //Hier pruefen wir negativ erfolgreich
		
	}
	
}
