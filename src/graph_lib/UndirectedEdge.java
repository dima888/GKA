package graph_lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Package-Privat
class UndirectedEdge implements Edge {
	
	private Vertex[] verticesFromEdge = new Vertex[2];
	private static int count = 0; //Hilfszaehler
	private List<String> attrList = new ArrayList<>();
	
	// *********ATTRIBUTE********
	private String name;
	private final int secondaryId;

	// *********ATTRIBUTE-BEZEICHNUNGEN********
	String attrName = "name";
	String attrSecondaryId = "secondaryId";
	
	public UndirectedEdge(Vertex v1, Vertex v2) {
		verticesFromEdge[0] = v1;
		verticesFromEdge[1] = v2;
		
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
		result = "Vertex v1: (" + verticesFromEdge[0] + "), Vertex v2: (" + verticesFromEdge[1] + ")";
		return result;
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
