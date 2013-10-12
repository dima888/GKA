package graph_lib;

//Package-Privat
class DirectedEdge implements Edge {

	private Vertex[] verticesFromEdge = new Vertex[2];
	
	public DirectedEdge(Vertex v1, Vertex v2) {
		verticesFromEdge[0] = v1; //StartKnoten
		verticesFromEdge[1] = v2; //EndKnoten
		
		v1.addOutgoingEdge(this);
		v2.addIngoingEdge(this);
		
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
		result = "Source: (" + verticesFromEdge[0] + ") Target: (" + verticesFromEdge[1] + ")";
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hatQuelle() {
		Vertex source = verticesFromEdge[0];
		if(source.getIngoingEdge().size() > 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hatSenke() {
		Vertex target = verticesFromEdge[1];
		if(target.getOutgoingEdge().size() > 1) {
			return false;
		}
		return true;
	}

}
