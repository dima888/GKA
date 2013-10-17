package graph_lib;

class UndirectedEdge extends Edge{
	
	private final int ID;
	private static int count = 1; //Hilfszaehler
	
	public UndirectedEdge(Vertex v1, Vertex v2) {
		super(v1, v2);		
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
		result = "Vertex v1: (" + this.getVertices()[0] + "), Vertex v2: (" +this.getVertices()[1] + ")";
		return result;
	}


}
