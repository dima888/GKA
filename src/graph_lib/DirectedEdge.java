package graph_lib;

//Package-Privat
class DirectedEdge implements Edge {

	private Vertex[] verticesFromEdge = new Vertex[2];
	private final int ID;
	
	private static int objCounter = 0;
	
	public DirectedEdge(Vertex v1, Vertex v2) {
		verticesFromEdge[0] = v1; //StartKnoten
		verticesFromEdge[1] = v2; //EndKnoten
		
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
