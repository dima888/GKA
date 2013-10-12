package graph_lib;

//Package-Privat
class UndirectedEdge implements Edge {
	
	private Vertex[] verticesFromEdge = new Vertex[2];
	
	public UndirectedEdge(Vertex v1, Vertex v2) {
		verticesFromEdge[0] = v1;
		verticesFromEdge[1] = v2;
		
		v1.addIncident(this);
		v2.addIncident(this);
	}

	@Override
	public Vertex[] getVertices() {
		return verticesFromEdge;
	}
	
	@Override
	public String toString() {
		String result = "";
		result = "Vertex v1: (" + verticesFromEdge[0] + "), Vertex v2: (" + verticesFromEdge[1] + ")";
		return result;
	}
}
