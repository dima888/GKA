package graph_lib;

import java.util.ArrayList;
import java.util.List;

public class AIGraph {
	
	private List<Vertex> verticesList = new ArrayList<>();
	private List<Edge> edgesList = new ArrayList<>();
	
	//********************************* KONSTRUKTOREN *********************************************
	
	public AIGraph() {
		//NULL Graph
	}
	
	public Vertex addVertex(int newItem) {
		Vertex vertex = new Vertex(newItem);
		verticesList.add(vertex);
		return vertex;
	}
	
	public void deleteVertex(Vertex v_id) {
		//Löschen des Knotens
		verticesList.remove(v_id);
		//Alle Inzidenten Kanten löschen
		for(Edge edge : edgesList) {
			//Wenn eine der beiden Kantenseiten auf den Knoten, wird die Kante gelöscht
			if((edge.verticesFromEdge[0] == v_id) || (edge.verticesFromEdge[1] == v_id)) {
				edgesList.remove(edge);
			}
		}
	}
	
	public Edge addEdgeU(Vertex v1, Vertex v2) {
		// TODO: addEdgeU implementieren --> für ungerichtete Kanten
		return null;
	}
	
	public Edge addEdgeD(Vertex v1, Vertex v2) {
		// TODO: addEdgeD implementieren --> für gerichtete Kanten
		return null;
	}
	
	public Edge deleteEdge(Vertex v1, Vertex v2) {
		// TODO: deleteEdge --> für gerichtete und ungerichtete
		return null;
	}
	
	//********************************* SELEKTOREN *********************************************
	
	public boolean isEmpty() {
		// TODO: isEmpty implementieren
		return false;
	}
	
	public Edge getSource(Edge e1) {
		// TODO: getSource implementieren --> ermittelt im gerichteten Fall die Quelle der
		// Kante e1 (gegeben als ID) im ungerichteten Fall die linke / erste Ecke
		return null;
	}
	
	public Edge getTarget(Edge e1) {
		// TODO: getTarget implementieren --> ermittelt im gerichteten Fall die Senke der
		// Kante e1 (gegeben als ID) im ungerichteten Fall die rechte / zweite Ecke
		return null;
	}
	
	public List<Edge> getIncident(Vertex v1) {
		// TODO: getIncident implementieren --> ermittelt alle zur Ecke v1
		// inzidenten Kanten
		return null;
	}
	
	public List<Edge> getAdjacent(Vertex v1) {
		// TODO: getAdjacent implementieren --> ermittelt alle zur Ecke v1
		// adjazenten Ecken
		return null;
	}
	
	public List<Vertex> getVertexes() {
		// TODO: getVertexes implementieren --> ermittelt alle Ecken des Graphen
		return null;
	}
	
	public List<Edge> getEdges() {
		// TODO: getEdges implementieren --> emittelt alle Kanten des Graphen
		return null;
	}
	
	public int getValE(Edge e1, String attr) {
		// TODO: getValE implementieren --> ermittelt den Attributwert Kante
		// RETURN MAXINT BEI FEHLER
		return -1;
	}
	
	public int getValV(Vertex v1, String attr) {
		// TODO: getValV implementieren --> ermittelt den Attributwert von der Ecke
		// RETURN MAXINT BEI FEHLER
		return -1;
	}
	
	public String getStrE(Edge e1, String attr) {
		// TODO: getStrE implementieren --> ermittelt den Attributwert von der Kante
		// RETURN LEEREN STRING BEI FEHLER
		return null;
	}
	
	public String getStrV(Vertex v1, String attr) {
		// TODO: getStrV implementieren --> ermittelt den Attributwert von der Ecke
		return null;
	}
	
	public List<?> getAttrV(Vertex v1) {
		// TODO: getAttrV implementieren
		return null;
	}
	
	public List<?> getAttrE(Edge e1) {
		// TODO: getAttrE implementieren
		return null;
	}
	
	//************************************ MUTATOR ********************************************
	
	public void setValE(Edge e1, String attr, int val) {
		// TODO
	}
	
	public void setValV(Vertex v1, String attr, int val) {
		// TODO
	}
	
	public void setStrE(Edge e1, String attr, String val) {
		// TODO
	}
	
	public void setStrV(Vertex v1, String attr, String val) {
		// TODO
	}
	
	//******************************** PRIVATE KLASSEN ****************************************
	
	private class Vertex {
		
		private int vertexValue;
		private List<Edge> incidents = new ArrayList<>();		
		
		public Vertex(int vertexValue) {
			this.vertexValue = vertexValue;
		}
		
		public void addEdge(Edge edge) {
			incidents.add(edge);
		}
		
		public int getGrad() {
			return incidents.size();
		}
		
	}
	
	private class Edge {
		
		private Vertex[] verticesFromEdge = new Vertex[2];
		
		public Edge(Vertex v1, Vertex v2) {
			verticesFromEdge[0] = v1;
			verticesFromEdge[1] = v2;
			
			v1.addEdge(this);
			v2.addEdge(this);
		}
		
	}
}
