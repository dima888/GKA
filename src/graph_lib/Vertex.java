package graph_lib;

import java.util.ArrayList;
import java.util.List;

//Package-Private
class Vertex {
	
	private int vertexValue;
	private final int ID;
	
	private static int objCounter = 0;
	//private List<Edge> incidents = new ArrayList<>();
	//private List<Edge> outgoingEdge = new ArrayList<>(); //Ausgehende Kanten
	//private List<Edge> ingoingEdge = new ArrayList<>(); //Eingehende Kanten 	
	
	
	public Vertex(int vertexValue) {
		this.vertexValue = vertexValue;
		ID = objCounter;
		objCounter++;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getVertexValue() {
		return vertexValue;
	}
	
//	public void addEdge(Edge edge) {
//		incidents.add(edge);
//	}
//	
//	public void addOutgoingEdge(Edge edge) {
//		outgoingEdge.add(edge);
//	}
//	
//	public void addIngoingEdge(Edge edge) {
//		ingoingEdge.add(edge);
//	}
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
