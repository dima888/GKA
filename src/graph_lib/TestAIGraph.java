package graph_lib;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

// TODO TESTS VERBESSERN

public class TestAIGraph {
	
	AIGraph graph = new AIGraph();
	
	@Before
	public void init() {
		
	}
	
	@Test
	public void testAddVertex() {
		graph.addVertex(1);
		graph.addVertex(2);
		
		//System.out.println(graph);
	}
	
	@Test
	public void testDeleteVertexPos() {
		Vertex v1 = graph.addVertex(1);
		Vertex v2 = graph.addVertex(2);
		
		graph.deleteVertex(v2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDeleteVertexNeg() {		
		graph.deleteVertex(null);
	}
	
	@Test
	public void testAddEdgeUPos() {
		Vertex vertex1 = graph.addVertex(1);
		Vertex vertex2 = graph.addVertex(2);
	
		graph.addEdgeU(vertex1, vertex2);
		
		System.out.println(graph);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddEdgeUNeg() {
		Vertex vertex1 = graph.addVertex(1);
		Vertex vertex2 = graph.addVertex(2);
	
		graph.addEdgeU(vertex1, vertex2);
		graph.addEdgeU(vertex1, vertex2);
	}
	
	@Test
	public void testAddEdgeDPos() {
		Vertex vertex1 = graph.addVertex(1);
		Vertex vertex2 = graph.addVertex(2);
	
		graph.addEdgeD(vertex1, vertex2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddEdgeDNeg() {
		Vertex vertex1 = graph.addVertex(1);
		Vertex vertex2 = graph.addVertex(2);
	
		graph.addEdgeD(vertex1, vertex2);
		graph.addEdgeD(vertex1, vertex2);
	}
	
	@Test
	public void testDeleteEdgePos() {
		Vertex vertex1 = graph.addVertex(1);
		Vertex vertex2 = graph.addVertex(2);
	
		graph.addEdgeU(vertex1, vertex2);
		
		graph.deleteEdge(vertex1, vertex2);
		
		Vertex vertex3 = graph.addVertex(1);
		Vertex vertex4 = graph.addVertex(2);
	
		graph.addEdgeD(vertex3, vertex4);
		
		graph.deleteEdge(vertex3, vertex4);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDeleteEdgeNeg() {
		Vertex vertex1 = graph.addVertex(1);
		Vertex vertex2 = graph.addVertex(2);
	
		graph.addEdgeU(vertex1, vertex2);
		
		graph.deleteEdge(vertex1, vertex2);
		graph.deleteEdge(vertex1, vertex2);
	}
	
	@Test
	public void testGetSourcePos() {
		Vertex vertex1 = graph.addVertex(1);
		Vertex vertex2 = graph.addVertex(2);
	
		Edge edge = graph.addEdgeD(vertex1, vertex2);
		
		Vertex result = graph.getSource(edge);
		
		assertEquals(vertex1, result);
	}
	
	@Test //(expected = IllegalArgumentException.class)
	public void testGetSourceNeg() {
		Vertex vertex1 = graph.addVertex(1);
		Vertex vertex2 = graph.addVertex(2);
		Vertex vertex3 = graph.addVertex(3);
	
		Edge edge1 = graph.addEdgeD(vertex1, vertex2);
		Edge edge2 = graph.addEdgeD(vertex3, vertex1);
		
		Vertex result = graph.getSource(edge1);
		
		if(result == null) {
			//throw new IllegalArgumentException("Ergebnis == null");
			System.out.println("HI");
		}
		
		//assertEquals(vertex1, result);
	}
	
	@Test
	public void testGetTargetPos() {
		Vertex vertex1 = graph.addVertex(1);
		Vertex vertex2 = graph.addVertex(2);
	
		Edge edge = graph.addEdgeD(vertex1, vertex2);
		
		Vertex result = graph.getTarget(edge);
		
		assertEquals(vertex2, result);
	}
	
}
