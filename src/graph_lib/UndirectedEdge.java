package graph_lib;

//****************************TODO: WICHTIG: Die IMPLEMENTIERUNGS METHODEN muessen erweitert werden, wenn ein neues Attribut dazu kommt****************************

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Package-Privat
class UndirectedEdge implements Edge {
	
	private Vertex[] verticesFromEdge = new Vertex[2];
	private static int count = 0; //Hilfszaehler
	private List<String> attrList = new ArrayList<>();
	
	private Map<String, Object> attrMap = new HashMap();
	
	// *********ATTRIBUTE********
	private String name;
	private final int secondaryId;
	private int whatever = 100;

	// *********ATTRIBUTE-BEZEICHNUNGEN********
	String attrName = "name";
	String attrSecondaryId = "secondaryId";
	String attrWhatever = "whatever";
	
	public UndirectedEdge(Vertex v1, Vertex v2) {
		verticesFromEdge[0] = v1;
		verticesFromEdge[1] = v2;
		
		v1.addIncident(this);
		v2.addIncident(this);
		
		//****MAP*****
		attrMap.put(attrName, name);
//		attrMap.put(attrSecondaryId, secondaryId);
		attrMap.put(attrWhatever, whatever);
		
		secondaryId = count++; //Auto increment
		attrList.addAll(Arrays.asList(attrName, attrSecondaryId, attrWhatever)); 	//ATTRIBUTENLISTE	
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
	
	@Override
	public int getWhatever() {
		return this.whatever;
	}
	
	//********************************************** SETTER METHODEN **********************************************
	@Override
	public void setName(String newName) {
		this.name = newName;		
	}
	
	//********************************************** IMPLEMENTIERUNGS METHODEN **********************************************
	@Override
	public boolean setStrE(String attr, String val) {
		if(attr == attrName) {
			this.name = val;
			return true;
		}
		return false;
	}

	@Override
	public boolean setValE(String attr, int val) {
		if(attr == attrSecondaryId) {
			//this.secondaryId = val;
			return true;
		}
		if(attr == attrWhatever) {
			this.whatever = val;
			return true;
		}
		return false;
	}
	//*********************************************************************************************************
	
	@Override
	public String toString() {
		String result = "";
		result = "Vertex v1: (" + verticesFromEdge[0] + "), Vertex v2: (" + verticesFromEdge[1] + ")";
		return result;
	}

	@Override
	public int getAttr(String attr) {
		if(attr == attrSecondaryId) {
			return this.secondaryId;
		}
		return Integer.MAX_VALUE;
	}

	
	@Override
	public String getAttrStr(String attr) {
		String result = "";
		if(attr == attrName) {
			result = this.name;
		}
		return result;
	}

}
