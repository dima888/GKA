package graph_lib;

//Package-Privat
class DirectedEdge extends Edge {
	
	public DirectedEdge(Vertex v1, Vertex v2) {
		super(v1, v2);		
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {				
		String result = "";
		result = "Source: ("  + this.getVertices()[0] + ") Target: (" +this.getVertices()[1] + ")";		
		return result;
	}
	
	/**
	 * TODO: Doku bitte
	 * @return
	 */
	public boolean hatQuelle() {
		Vertex source = this.getVertices()[0];
		if(source.getIngoingEdge().size() > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * TODO: Doku bitte
	 * @return
	 */
	public boolean hatSenke() {
		Vertex target = this.getVertices()[1];
		if(target.getOutgoingEdge().size() > 1) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		
	}

//	@Override
//	public int getWhatever() {
//		// TODO Auto-generated method stub
//		return 0;
//	}


}
