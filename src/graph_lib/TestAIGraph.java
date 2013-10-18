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
	
	@Test // (expected = IllegalArgumentException.class) TODO: BESPRECHEN WAS WIR ZURÜCK ERWARTEN BEI FEHLERFALL VON GET_SOURCE
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
	
	//TODO: DIESE METHODEN TESTE
//	@Test
//	public void testGetValE() {
//		Vertex v1 = graph.addVertex("1");
//		Vertex v2 = graph.addVertex("2");	
//
//		Edge e1 = graph.addEdgeD(v1, v2);
//					
//		assertEquals(graph.getValE(e1, "al"), Integer.MAX_VALUE);
//		assertEquals(graph.getValE(e1, "secondaryId"), e1.getSecondaryId());				
//	}
//	
//	@Test
//	public void testGetValV() {
//		Vertex v1 = graph.addVertex(10);
//		assertEquals(graph.getValV(v1, "secondaryId"), v1.getSecondaryId());
//		assertEquals(graph.getValV(v1, "Katze"), Integer.MAX_VALUE);
//	}
//	
//	@Test
//	public void testGetStrE() {
//		Vertex v1 = graph.addVertex("1");
//		Vertex v2 = graph.addVertex("2");	
//
//		Edge e1 = graph.addEdgeD(v1, v2);
//		e1.setName("e1");
//		String emptyString = "";
//		
//		assertEquals(graph.getStrE(e1, "name"), e1.getName());
//		assertEquals(graph.getStrE(e1, "Hund"), emptyString);
//	}
//	
//	@Test
//	public void testGetStrV() {
//		Vertex v1 = graph.addVertex("1");
//		v1.setName("v1");
//		String emptyString = "";
//		assertEquals(graph.getStrV(v1, "name"), v1.getName());
//		assertEquals(graph.getStrV(v1, "Banane"), emptyString);
//	}
//	
//	//Unnoetig hier zu testen, bei jeder erweiterung, muss der Test auch erweitert werden
//	//Das gleiche gielt auch fuer testGetAttrE
//	@Test
//	public void testGetAttrVANDE() {
//		Vertex v1 = graph.addVertex("1");
//		Edge e1 = graph.addEdgeU(v1, v1);
//		//System.out.println("AttrListV = " + v1.getAttrList());
//		//System.out.println("AttrListE = " + e1.getAttrList());
//	}
//	
//	@Test 
//	public void testSetValE() {
//		Vertex v1 = graph.addVertex("1");
//		Vertex v2 = graph.addVertex("2");	
//		Edge e1 = graph.addEdgeD(v1, v2);
//		
//		assertTrue(graph.setValE(e1, "whatever", 1500));
//		assertEquals(e1.getWhatever(), 1500);				
//		assertFalse(graph.setValE(e1, "Gnom", 1500));
//	}
//	
//	@Test 
//	public void testSetValV() {
//		Vertex v1 = graph.addVertex("1");
//		
//		assertTrue(graph.setValV(v1, "whatever", 7000));
//		assertEquals(v1.getWhatever(), 7000);				
//		assertFalse(graph.setValV(v1, "Gnom", 7000));
//	}
//	
//	@Test
//	public void testSetStrE() {
//		Vertex v1 = graph.addVertex("1");
//		Vertex v2 = graph.addVertex("2");	
//
//		Edge e1 = graph.addEdgeD(v1, v2);
//		assertTrue(graph.setStrE(e1, "name", "e100"));
//		assertEquals(e1.getName(), "e100");
//		assertFalse(graph.setStrE(e1, "Pumukel", "e100"));
//	}
//	
//	@Test
//	public void testSetStrV() {
//		Vertex v1 = graph.addVertex(25);
//		assertTrue(graph.setStrV(v1, "name", "Hura"));
//		assertEquals(v1.getName(), "Hura");
//		assertFalse(graph.setStrV(v1, "name123", "Hura"));
//		
//	}
	
}
