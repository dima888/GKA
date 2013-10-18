package graph_lib;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Package-Private
class Vertex {
		
//	private List<Edge> incidents = new ArrayList<>(); Old
	private List<Integer> incidents = new ArrayList<>(); //+++++++++++++New Version++++++++++++++
	
//	private List<Edge> outgoingEdge = new ArrayList<>(); //Ausgehende Kanten
	private List<Integer> outgoingEdge = new ArrayList<>(); //Ausgehende Kanten //+++++++++++++New Version++++++++++++++
	
//	private List<Edge> ingoingEdge = new ArrayList<>(); //Eingehende Kanten
	private List<Integer> ingoingEdge = new ArrayList<>(); //Eingehende Kanten //+++++++++++++New Version++++++++++++++
	
	private List<String> attrList = new ArrayList<>();
	private static int count = 0; //Hilfszaehler
	
	//*********ATTRIBUTE********	
	private String name; 
	private final int ID;  
	private int whatever = 25;
	private int vertexValue;
	
	//*********ATTRIBUTE-BEZEICHNUNGEN********	
	String attrName = "name";
	String attrID = "ID";
	String attrWhatever = "whatever";
	String attrVertexValue = "vertexValue";
	
	public Vertex(String vertexName) {		
		this.name = vertexName;
		ID = count++;
		
		attrList.addAll(Arrays.asList(attrName, attrID, attrWhatever, attrVertexValue)); 	//ATTRIBUTENLISTE	
	}
	
	//********************************************** GETTER METHODEN **********************************************
	public int getVertexValue() {
		return vertexValue;
	}
	
	public void addIngoingEdge(int edge) {
		ingoingEdge.add(edge);
	}
	
	public List<Integer> getOutgoingEdge() {
		return this.outgoingEdge;
	}
	
	public List<Integer> getIngoingEdge() {
		return this.ingoingEdge;
	}
	
	public List<Integer> getIncidents() {
		return this.incidents;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<String> getAttrList() {
		return this.attrList;
	}
	
	public int getWhatever() {
		return this.whatever;
	}
	
	//********************************************** SETTER METHODEN **********************************************
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	
	//********************************************** IMPLEMENTIERUNGS METHODEN **********************************************	
	public boolean setStrV(String attr, String val) {
		if (attr == attrName) {
			this.name = val;
			return true;
		}
		return false;
	}
	
	public boolean setValV(String attr, int val) {
		if (attr == attrID) {
			//this.ID = val;
			return true;
		}
		if (attr == attrWhatever) {
			this.whatever = val;
			return true;
		}
		if (attr == attrVertexValue) {
			this.vertexValue = val;
			return true;
		}
		return false;
	}
	
	public int getAttr(String attr) {
		if (attr == this.attrID) {
			return this.ID;
		}
		if(attr == attrVertexValue) {
			return this.vertexValue;
		}
		if(attr == attrWhatever) {
			return this.whatever;
		}
		return Integer.MAX_VALUE;
	}	
	 
	public String getAttrStr(String attr) {
		String result = "";
		if (attr == this.attrName) {
			result = this.name;
		}
		return result;
	}	
	
	public void addOutgoingEdge(int edge) {
		outgoingEdge.add(edge);
	}
	
	public void addIncident(int edgeID) {
		incidents.add(edgeID);
	}
	
	 @Override
	public String toString() {
		return "" + name;
	}
}
