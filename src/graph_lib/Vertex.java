package graph_lib;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Package-Private
class Vertex {
		
	private List<Integer> incidents = new ArrayList<>(); //+++++++++++++New Version++++++++++++++
	private List<Integer> outgoingEdge = new ArrayList<>(); //Ausgehende Kanten //+++++++++++++New Version++++++++++++++
	private List<Integer> ingoingEdge = new ArrayList<>(); //Eingehende Kanten //+++++++++++++New Version++++++++++++++
	
	private List<String> attrList = new ArrayList<>();
	private static int count = 0; //Hilfszaehler
	
	//*********ATTRIBUTE********	
	private String name; 
	private final int ID;  
	private int whatever = 25;
	private int value;
	
	/*
	 * Zweiter Tuppel fuer die Markierung (id, delta)
	 * id -> id des Vorgaengers
	 */
	private int predecessorID; //merked != 0 ist markiert
	private int delta; //
	
	private String inspected = "";
	
	
	//*********ATTRIBUTE-BEZEICHNUNGEN********	
	String attrName = "name";
	String attrID = "ID";
	String attrWhatever = "whatever";
	String attrValue = "value";
	String attrPredecessorID = "predecessorID";
	String attrDelta = "delta";
	String attrInspected = "inspected";
	
	public Vertex(String vertexName) {		
		this.name = vertexName;
		ID = count++;
		
		//ATTRIBUTENLISTE
		attrList.addAll(Arrays.asList(attrName, attrID, attrWhatever, attrValue, attrPredecessorID, attrDelta, attrInspected)); 		
	}
	
	//********************************************** GETTER METHODEN **********************************************
	public int getvalue() {
		return value;
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

	public int getDelta() {
		return delta;
	}

	public int getPredecessorID() {
		return predecessorID;
	}
	
	public String getInspected() {
		return inspected;
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
		if (attr == attrInspected) {
			this.inspected = val;
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
		if (attr == attrValue) {
			this.value = val;
			return true;
		}
		if (attr == attrDelta) {
			this.delta = val;
			return true;
		}
		if (attr == attrPredecessorID) {
			this.predecessorID = val;
			return true;
		}
		return false;
	}
	
	public int getAttr(String attr) {
		if (attr == this.attrID) {
			return this.ID;
		}
		if (attr == attrValue) {
			return this.value;
		}
		if (attr == attrWhatever) {
			return this.whatever;
		}
		if (attr == attrDelta) {
			return this.delta;
		}
		if (attr == attrPredecessorID) {
			return predecessorID;
		}
		return Integer.MAX_VALUE;
	}	
	 
	public String getAttrStr(String attr) {
		String result = "";
		if (attr == this.attrName) {
			result = this.name;
		}
		if (attr == attrInspected) {
			result = this.inspected;
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
