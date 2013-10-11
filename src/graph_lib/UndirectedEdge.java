package graph_lib;

//Package-Privat
class UndirectedEdge implements Edge {
	
	private Vertex[] verticesFromEdge = new Vertex[2];
	private final int ID;
	
	private static int objCounter = 0;
	
	public UndirectedEdge(Vertex v1, Vertex v2) {
		verticesFromEdge[0] = v1;
		verticesFromEdge[1] = v2;
		
		ID = objCounter;
		objCounter++;
	}

	@Override
	public Vertex[] getVertices() {
		return verticesFromEdge;
	}
	
	public int getID() {
		return ID;
	}

}
