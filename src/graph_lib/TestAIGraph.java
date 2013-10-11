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
	}
	
	@Test
	public void testDeleteVertexPos() {
		graph.addVertex(1);
		graph.addVertex(2);
		
		graph.deleteVertex(1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testDeleteVertexNeg() {		
		graph.deleteVertex(1);
	}
	
	@Test
	public void testAddEdgeUPos() {
		AIGraph graph1 = new AIGraph();
		
		graph1.addVertex(1);
		graph1.addVertex(2);
		
		graph1.addEdgeU(0, 1); // TODO FEHLER BEHEBN
	}
	
	@Test
	public void testAddEdgeD() {
		
	}
	
}
