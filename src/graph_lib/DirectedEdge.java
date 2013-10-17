package graph_lib;

//Package-Privat
class DirectedEdge extends Edge {
	
	private final int ID;
	private static int count = 0; //Hilfszaehler
	
	public DirectedEdge(Vertex v1, Vertex v2) {
		super(v1, v2);		
//		v1.getOutgoingEdge().add(this);
//		v2.getIngoingEdge().add(this);
		
		v1.getOutgoingEdge().add(this.getID());
		v2.getIngoingEdge().add(this.getID());
		
		ID = count += 2; //Auto increment
	}
	
	//********************************************** GETTER METHODEN **********************************************
	@Override
	public int getID() {
		return this.ID;
	}

	//********************************************** IMPLEMENTIERUNGS METHODEN **********************************************	
	@Override
	public int getAttr(String attr) {
		if(attr == attrID) {
			return this.ID;
		}
		return Integer.MAX_VALUE;
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

}
