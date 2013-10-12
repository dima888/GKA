package graph_lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Package-Privat
class DirectedEdge implements Edge {

	private Vertex[] verticesFromEdge = new Vertex[2];
	private List<String> attrList = new ArrayList<>();
	private static int count = 0; //Hilfszaehler
	
	// *********ATTRIBUTE********
	private String name;
	private final int secondaryId;

	// *********ATTRIBUTE-BEZEICHNUNGEN********
	String attrName = "name";
	String attrSecondaryId = "secondaryId";
		
	
	public DirectedEdge(Vertex v1, Vertex v2) {
		verticesFromEdge[0] = v1; //StartKnoten
		verticesFromEdge[1] = v2; //EndKnoten
		
		v1.addOutgoingEdge(this);
		v2.addIngoingEdge(this);
		
		v1.addIncident(this);
		v2.addIncident(this);
		
		secondaryId = count++; //Auto increment
		attrList.addAll(Arrays.asList(attrName, attrSecondaryId));	
	}
	
	//********************************************** GETTER METHODEN **********************************************
	@Override
	public Vertex[] getVertices() {
		return verticesFromEdge;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public int getSecondaryId() {
		return this.secondaryId;
	}
	
	@Override
	public List<String> getAttrList() {
		return this.attrList;
	}
	
	//********************************************** SETTER METHODEN **********************************************
	@Override
	public void setName(String newName) {
		this.name = newName;		
	}
	
	//********************************************** IMPLEMENTIERUNGS METHODEN **********************************************
	
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

	@Override
	public int getAttr(String attr) {
		if(attr == "secondaryId") {
			return this.secondaryId;
		}
		return Integer.MAX_VALUE;
	}


	@Override
	public String getAttrStr(String attr) {
		String result = "";
		if(attr == "name") {
			result = this.name;
		}
		return result;
	}
}
