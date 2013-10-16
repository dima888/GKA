package graph_lib;

class UndirectedEdge extends Edge{
	
	
	public UndirectedEdge(Vertex v1, Vertex v2) {
		super(v1, v2);		
	}

	@Override
	public String toString() {
		String result = "";
		result = "Vertex v1: (" + this.getVertices()[0] + "), Vertex v2: (" +this.getVertices()[1] + ")";
		return result;
	}


}
