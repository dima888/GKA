package graph_lib;

//Package-Privat
class DirectedEdge extends Edge {
	
	private final int ID;
	private static int count = 0; //Hilfszaehler
	
	//*************************** KONSTRUKTOTREN ****************************
	public DirectedEdge(Vertex v1, Vertex v2, String name) {
		super(v1, v2, name);		
		ID = count; //Auto increment
		
		v1.getOutgoingEdge().add(ID);
		v2.getIngoingEdge().add(ID);
		
		v1.addIncident(ID);
		v2.addIncident(ID);
		
		count += 2;
	}
	
	public DirectedEdge(Vertex v1, Vertex v2) {
		super(v1, v2);		
		
		ID = count; //Auto increment
		
		v1.getOutgoingEdge().add(ID);
		v2.getIngoingEdge().add(ID);
		
		v1.addIncident(ID);
		v2.addIncident(ID);
		
		count += 2;
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
		if(attr == attrWhatever) {
			return super.getWhatever();
		}
		if(attr == attrValue) {
			return super.getValue();
		}
		return Integer.MAX_VALUE;
	}
	
	@Override
	public String toString() {				
		String result = "";
		result = "(Source: "  + this.getVertices()[0] + ", Target: " + this.getVertices()[1] + ")";		
		return result;
	}
	
	/**
	 * Prüft für eine gerichtete Kante, ob sie eine Quelle hat
	 * @return boolean true, wenn ihr Eingangsgrad größer 0 ist, sonst false
	 */
	public boolean hatQuelle() {
		Vertex source = this.getVertices()[0];
		if(source.getIngoingEdge().size() > 0) {
			return false;
		}		
		return true;
	}
	
	/**
	 * Prüft für eine gerichtete Kante, ob sie eine Senke hat
	 * @return boolean true, wenn die aufrufende Kante eine Senke hat, sonst false
	 */
	public boolean hatSenke() {
		Vertex target = this.getVertices()[1];
		if(target.getOutgoingEdge().size() > 1) {
			return false;
		}
		return true;
	}

}
