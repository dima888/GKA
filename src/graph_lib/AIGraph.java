//TODO: Wichtig, wir brauchen eine ToString z.B. in Enge, wo die ID des jewaligen Objekt zurueck gegeben wird
//      Oder wir entscheiden uns anders und returnen z.B. bei addEdgeU -> einen Integer wert als seine eindeutige ID: 

//TODO: Duerfen wir die ALGraph mit unseren privaten Methoden versehen?????????????????????????

package graph_lib;

import java.util.ArrayList;
import java.util.List;

public class AIGraph {
	
	private List<Vertex> verticesList = new ArrayList<>();
	private List<Edge> edgesListU = new ArrayList<>();
	private List<Vertex[]> edgesListD = new ArrayList<>();//[0] von Knotten[0] zu Knotten[1]
	//private List<Map<Edge, Vertex[]>> edgesListD = new ArrayList<>(); //[0] von Knotten[0] zu Knotten[1]
	
	
	//********************************* KONSTRUKTOREN *********************************************
	
	public AIGraph() {
		//NULL Graph
	}
	
	/**
	 * Zum hinzufügen eines Vertex(Knoten)- Objekts zum Graphen
	 * 
	 * @param newItem Wert, der im Vertex gespeichert werden soll
	 * 
	 * @return Vertex gibt die Referenz auf die hinzugefügte Vertex zurück
	 */
	public Vertex addVertex(int newItem) {
		Vertex vertex = new Vertex(newItem);
		verticesList.add(vertex);
		return vertex;
	}
	
	/**
	 * Hier wird ein Vertex(Knotten) geloescht 
	 * 
	 * @param v_id Ein Vertex(Knoten) Objekt wird hier erwartet.
	 * 
	 * @return boolean true, bei erfolgreichem löschen, sonst false
	 */
	public void deleteVertex(Vertex v_id) {
		//TODO: Hier muss  noch aus der LIste edgesLIstD geloescht werden
		verticesList.remove(v_id); //Löschen des Knotens		
		for(Edge edge : edgesListU) { //Ueber die interne endgesLIst iterieren
			if((edge.verticesFromEdge[0] == v_id) || (edge.verticesFromEdge[1] == v_id)) { //Wenn eine der beiden Kantenseiten auf den Knoten, wird die Kante gelöscht
				edgesListU.remove(edge); //Alle Inzidenten Kanten löschen
			}
		}
	}
	
	/**
	 * Die Methode fuegt eine EngeU(Ungerichtete Kante) an zwei oder ein vertices(Knotten) an
	 * 
	 * @param v1 ein Vertex
	 * @param v2 ein Vertex
	 * 
	 * @return Edge gibt die Referenz auf die hinzugefügte Edge zurück
	 */
	public Edge addEdgeU(Vertex v1, Vertex v2) {			
		if(!(include(verticesList, v1)) && !(include(verticesList, v2))) {
			throw new IllegalArgumentException("EdgeU (Ungerichtete Kante) kann nicht hinzugefuegt werden, da vermutlich da zwischen keine vertices(Knotten exestieren) ");
		}
		Edge edge = new Edge(v1, v2); //Edge mit zwei oder einem vertices verbunden. 										
		edgesListU.add(edge);
		return edge;				  //Hier muss nichts weiter gemacht werden, da die Klasse intern das jetzt an die Kante(Edge) an den (vertices)Knotten zufuegt v1.addEdge(this); v2.addEdge(this);
	}

	/**
	 * Die Hilfsmethode prueft, ob ein Vertex(Knotten) in einer Liste vorhanden ist
	 * 
	 * @param verticesList erwartet eine Liste von Vertex(Knotten)
	 * @param v erwaratet ein Vertex Object
	 * 
	 * @return boolean true, wenn ein Vertex sich im Graphen befindet, sonst false
	 */
	private boolean include(List<Vertex> verticesList, Vertex v) {
		for(Vertex vertex : verticesList) {
			if(v == vertex) { 
				return true;   
			}
		}
		return false;
	}

	
	public Edge addEdgeD(Vertex v1, Vertex v2) {	
		if(!(include(verticesList, v1)) && !(include(verticesList, v2))) {
			throw new IllegalArgumentException("EdgeD (Gerichtete Kante) kann nicht hinzugefuegt werden, da vermutlich da zwischen keine vertices(Knotten exestieren) ");
		}
		Edge edge = new Edge(v1, v2); //Edge mit zwei oder einem vertices verbunden. 	
		Vertex[] edgeD = new Vertex[2];
		edgeD[0] = v1;
		edgeD[1] = v2;		
		this.edgesListD.add(edgeD);
		return edge;
	}
	
	/**
	 * Die Methode loescht eine Kante(Edge)
	 * 
	 * @param v1 erwartet ein Vertex-Objekt
	 * @param v2 erwartet ein Vertex-Objekt
	 * 
	 * @return boolean true wenn die Edge gelöscht wurde, false wenn nicht
	 */
	public void deleteEdge(Vertex v1, Vertex v2) {
		for(Edge edge : edgesListU) { //FALL Ungerichtet
			if(edge.verticesFromEdge[0] == v1 && edge.verticesFromEdge[1] == v2) {
				edgesListU.remove(edge);
			}
		}
		
		for(Vertex[] vertexL : edgesListD) { //FALL Gerichtet
			if(vertexL[0] == v1 && vertexL[1] == v2) {
				edgesListD.remove(vertexL);
			}
		}
	}
	
	//********************************* SELEKTOREN *********************************************

	public boolean isEmpty() {
		if(verticesList.isEmpty()) {
			return true;
		}
		return false;
	}
	
	// return Wert ver Edge auf Vertex geaendert
	public Vertex getSource(Edge e1) {
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
	
	/**
	 * Gibt die Liste Aller Vertexes(Knotten) zurueck
	 * 
	 * @return
	 */
	public List<Vertex> getVertexes() {
		return this.verticesList;
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
		private List<Edge[]> adjacents = new ArrayList<>(); //Hier könnten wir als Paare die adjazenten Kanten speichern
		
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
		private String edgeName = "";
		
		public Edge(Vertex v1, Vertex v2) {
			verticesFromEdge[0] = v1;
			verticesFromEdge[1] = v2;
			
			v1.addEdge(this);
			v2.addEdge(this);
		}
		
		private String generateUniqueEdgeName() {
			//TODO: Habe mir so gedacht, dass wir einen Counter machen, der in ein String gespeichert wird, Plus dass der Edge noch einen Namen dazu bekommt. 
			return this.edgeName;
		}
	}
}
