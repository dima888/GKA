package graph_lib;

/*****************************TODO: WICHTIG: Die IMPLEMENTIERUNGS METHODEN muessen erweitert werden, wenn ein neues Attribut dazu kommt****************************
Das Override muss nicht angegeben werden, ist aber so schoener, fuer die unterliegenden Klassen von Edge

SPECIFIKATION: ------------------------------------------ 
Die Gerichteten Kanten IDs sind  {0, 2, 4, 6, 8, ...}
Die Ungerichteten Katen IDs sind {1, 3, 5, 7, 9, ...} 
---------------------------------------------------------
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Edge implements EdgeInterface{

	
	private Vertex[] verticesFromEdge = new Vertex[2];
	private List<String> attrList = new ArrayList<>();
	
	
	private Map<String, Object> attrMap = new HashMap<>();
	
	// *********ATTRIBUTE********
	private String name;
	private int whatever = 100;
	private String value = "";

	// *********ATTRIBUTE-BEZEICHNUNGEN********
	String attrName = "name";
	String attrID = "ID";
	String attrWhatever = "whatever";
	String attrValue = "10"; //Geändert wegen Distanzmatrix zum TESTEN !
	
	//*************************** KONSTRUKTOTREN ****************************
	public Edge(Vertex v1, Vertex v2, String name) {
		this.name = name;
		verticesFromEdge[0] = v1;
		verticesFromEdge[1] = v2;
		
		//****MAP*****
		attrMap.put(attrName, name);
		attrMap.put(attrWhatever, whatever);
		
		attrList.addAll(Arrays.asList(attrName, attrID, attrWhatever, attrValue)); 	//ATTRIBUTENLISTE	
	}
	
	public Edge(Vertex v1, Vertex v2) {
		verticesFromEdge[0] = v1;
		verticesFromEdge[1] = v2;
		
		//****MAP*****
		attrMap.put(attrName, name);
		attrMap.put(attrWhatever, whatever);
		
		attrList.addAll(Arrays.asList(attrName, attrID, attrWhatever)); 	//ATTRIBUTENLISTE	
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
		if(attr == attrValue) {
			this.value = val;
			return true; 
		}
		return false;
	}
	
	@Override
	public boolean setValE(String attr, int val) {
//		if(attr == attrID) {
//			this.ID = val;
//			return true;
//		}
		if(attr == attrWhatever) {
			this.whatever = val;
			return true;
		}
		return false;
	}

	@Override
	public String getAttrStr(String attr) {
		String result = "";
		if(attr == attrName) {
			result = this.name;
		}
		if(attr == attrValue) {
			result = this.value;
		}
		
		return result;
	}
	
	public String getAttrValue() {
		return attrValue;
	}
	
}
