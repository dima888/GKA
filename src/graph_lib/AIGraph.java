package graph_lib;

import java.util.ArrayList;
import java.util.List;

public class AIGraph {
	
	private List<Vertex> verticesList = new ArrayList<>();
	private List<Edge> edgesList = new ArrayList<>();
	
	public AIGraph() {
		//NULL Graph
	}
	
	public Vertex addVertex(int newItem) {
		Vertex vertex = new Vertex(newItem);
		verticesList.add(vertex);
		return vertex;
	}
	
	public void deleteVertex(Vertex v_id) {
		
	}
	
	private class Vertex {
		
		private int vertexValue;
		private List<Edge> inzidenten = new ArrayList<>();		
		
		public Vertex(int vertexValue) {
			this.vertexValue = vertexValue;
		}
		
		public void addEdge(Edge edge) {
			inzidenten.add(edge);
		}
		
		public int getGrad() {
			return inzidenten.size();
		}
		
	}
	
	private class Edge {
		
		private Vertex[] verticesFromEdge = new Vertex[2];
		
		public Edge(Vertex v1, Vertex v2) {
			verticesFromEdge[0] = v1;
			verticesFromEdge[1] = v2;
			
			v1.addEdge(this);
			v2.addEdge(this);
		}
		
	}
}
