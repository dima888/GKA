package graph_lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Package-Private
class Vertex {
	
	private int vertexValue;
	private List<Edge> incidents = new ArrayList<>();
	private List<Edge> outgoingEdge = new ArrayList<>(); //Ausgehende Kanten
	private List<Edge> ingoingEdge = new ArrayList<>(); //Eingehende Kanten
	private List<String> attrList = new ArrayList<>();
	private int count = 0; //Hilfszaehler
	
	//*********ATTRIBUTE********	
	private String name; 
	private final int secondaryId;  
	
	//*********ATTRIBUTE-BEZEICHNUNGEN********	
	String attrName = "name";
	String attrSecondaryId = "secondaryId";
	
	public Vertex(int vertexValue) {		
		this.vertexValue = vertexValue;
		secondaryId = count++;
		
		attrList.addAll(Arrays.asList(attrName, attrSecondaryId));	
	}
	
	//********************************************** GETTER METHODEN **********************************************
	public int getVertexValue() {
		return vertexValue;
	}
	
	public void addIngoingEdge(Edge edge) {
		ingoingEdge.add(edge);
	}
	
	public List<Edge> getOutgoingEdge() {
		return this.outgoingEdge;
	}
	
	public List<Edge> getIngoingEdge() {
		return this.ingoingEdge;
	}
	
	public List<Edge> getIncidents() {
		return this.incidents;
	}
	
	public int getSecondaryId() {
		return this.secondaryId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<String> getAttrList() {
		return this.attrList;
	}
	
	//********************************************** SETTER METHODEN **********************************************
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	
	//********************************************** IMPLEMENTIERUNGS METHODEN **********************************************
//	public void addEdge(Edge edge) {
//		incidents.add(edge);
//	}
//	
	public void addOutgoingEdge(Edge edge) {
		outgoingEdge.add(edge);
	}
	
	
	public void addIncident(Edge edge) {
		incidents.add(edge);
	}
	
	
	 @Override
	public String toString() {
		return "" + vertexValue;
	}
	 
	 //beliebig erweiterbar, unter Umstaenden koennte man es in einer Map<String, Attribute> verwalten
	public int getAttr(String attr) {
		if (attr == this.attrSecondaryId) {
			return this.secondaryId;
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
	 
//	
//	public int getGrad() {
//		return incidents.size();
//	}
//	
//	/**
//	 * Gibt die Anzahl des Eingang Grades
//	 * 
//	 * @return
//	 */
//	public int getEntryGrad() {
//		return ingoingEdge.size();
//	}
//	
//	/**
//	 * Gibt die Anzahl des Ausgang Grades
//	 * 
//	 * @return
//	 */
//	public int getExitGrad() {
//		return outgoingEdge.size();
//	}
}
