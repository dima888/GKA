package graph_lib;

import java.util.ArrayList;
import java.util.List;

//Package-Private
class Vertex {
	
	private int vertexValue;
	private List<Edge> incidents = new ArrayList<>();
	private List<Edge> outgoingEdge = new ArrayList<>(); //Ausgehende Kanten
	private List<Edge> ingoingEdge = new ArrayList<>(); //Eingehende Kanten 	
	
	
	public Vertex(int vertexValue) {
		this.vertexValue = vertexValue;
	}

	public int getVertexValue() {
		return vertexValue;
	}
	
//	public void addEdge(Edge edge) {
//		incidents.add(edge);
//	}
//	
	public void addOutgoingEdge(Edge edge) {
		outgoingEdge.add(edge);
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
	
	public void addIncident(Edge edge) {
		incidents.add(edge);
	}
	
	public List<Edge> getIncidents() {
		return this.incidents;
	}
	
	 @Override
	 public String toString() {
		 return "" + vertexValue;
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
