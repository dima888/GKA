package graph_lib;

//Package-Privat
class DirectedEdge extends Edge {
	
	//*********ATTRIBUTE********
	private final int ID;
	private static int count = 0; //Hilfszaehler
	private int capacity = 0; //Kapazität
	private int actualRiver = 0; //Tatsächlicher Fluss
	
	//*********ATTRIBUTE-BEZEICHNUNGEN********
	private String attrcapacity = "capacity"; 
	private String attrActualRiver = "actualRiver"; 
	
	//*************************** KONSTRUKTOTREN ****************************
	public DirectedEdge(Vertex v1, Vertex v2, String name) {
		super(v1, v2, name);
		super.attrList.add(attrcapacity);
		super.attrList.add(attrActualRiver);
		
		ID = count; //Auto increment
		
		v1.getOutgoingEdge().add(ID);
		v2.getIngoingEdge().add(ID);
		
		v1.addIncident(ID);
		v2.addIncident(ID);
		
		count += 2;
	}
	
	public DirectedEdge(Vertex v1, Vertex v2) {
		super(v1, v2);
		super.attrList.add(attrcapacity);
		super.attrList.add(attrActualRiver);
		
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
		if (attr == attrcapacity) {
			return this.capacity;
		}
		if (attr == attrActualRiver) {
			return this.actualRiver;
		}
		return Integer.MAX_VALUE;
	}
	
	@Override
	public boolean setValE(String attr, int val) {
		if(super.setValE(attr, val)) {
			return true;
		}
		if(attr == attrcapacity) {
			this.capacity = val;
			return true; 
		}
		if(attr == attrActualRiver) {
			this.actualRiver = val;
			return true; 
		}
		return false;
	}
	
	@Override
	public String toString() {				
		String result = "";
		result = "(Source: "  + this.getVertices()[0] + ", Target: " + this.getVertices()[1] + ")";		
		return result;
	}
	
//	/**
//	 * Prüft für eine gerichtete Kante, ob sie eine Quelle hat
//	 * @return boolean true, wenn ihr Eingangsgrad größer 0 ist, sonst false
//	 */
//	public boolean hatQuelle() {
//		Vertex source = this.getVertices()[0];
//		if(source.getIngoingEdge().size() > 0) {
//			return false;
//		}		
//		return true;
//	}
	
//	/**
//	 * Prüft für eine gerichtete Kante, ob sie eine Senke hat
//	 * @return boolean true, wenn die aufrufende Kante eine Senke hat, sonst false
//	 */
//	public boolean hatSenke() {
//		Vertex target = this.getVertices()[1];
//		if(target.getOutgoingEdge().size() > 1) {
//			return false;
//		}
//		return true;
//	}

}
