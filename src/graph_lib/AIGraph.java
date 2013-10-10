//TODO: Wichtig, wir brauchen eine ToString z.B. in Enge, wo die ID des jewaligen Objekt zurueck gegeben wird
//      Oder wir entscheiden uns anders und returnen z.B. bei addEdgeU -> einen Integer wert als seine eindeutige ID: 

package graph_lib;

import java.util.ArrayList;
import java.util.List;

public class AIGraph {
	
	private List<Vertex> verticesList = new ArrayList<>();
	private List<Edge> edgesList = new ArrayList<>();
	//private List<Edge[]> edgesListD = new ArrayList<>(); für gerichtete Kanten
	
	
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
	 * Wenn Vertex(Knotten) erfolgreich geloescht wurde, dann gibt die Mehotde true zurueck, sonst false. 
	 * 
	 * @param v_id Ein Vertex(Knoten) Objekt wird hier erwartet.
	 * 
	 * @return boolean true, bei erfolgreichem löschen, sonst false
	 */
	public boolean deleteVertex(Vertex v_id) {
		verticesList.remove(v_id); //Löschen des Knotens		
		for(Edge edge : edgesList) { //Ueber die interne endgesLIst iterieren
			if((edge.verticesFromEdge[0] == v_id) || (edge.verticesFromEdge[1] == v_id)) { //Wenn eine der beiden Kantenseiten auf den Knoten, wird die Kante gelöscht
				edgesList.remove(edge); //Alle Inzidenten Kanten löschen
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Die Methode fuegt eine EngeU(Ungerichtete Kante) an zwei oder ein Verticle(Knotten) an
	 * 
	 * @param v1 ein Vertex
	 * @param v2 ein Vertex
	 * 
	 * @return Edge gibt die Referenz auf die hinzugefügte Edge zurück
	 */
	public Edge addEdgeU(Vertex v1, Vertex v2) {		
		//TODO: Wir wissen, ob eine gerichtete oder ungerichtete Kante hinzugefügt wird --> es gibt dafür ja unterschiedliche Methoden
		// addEdgeU und addEdgeD  Die Frage ist, wie möchten wir es verwalten ?
		
		//Precondition // TODO: Das hier macht doch keinen Sinn, da wir ja hier eine Kante hinzufügen wollen und nicht gucken wollen, ob schon eine vorhanden ist
		if(!(include(verticesList, v1)) && !(include(verticesList, v2))) {
			throw new IllegalArgumentException("EdgeU (Ungerichtete Kante kann nicht hinzugefuegt werden, da vermutlich da zwischen keine Verticle(Knotten exestieren) ");
		}
		Edge edge = new Edge(v1, v2); //Edge mit zwei oder einem Verticle verbunden. 
		//Hier muss nichts weiter gemacht werden, da die Klasse intern das jetzt an die Kante(Edge) an den (Verticle)Knotten zufuegt v1.addEdge(this); v2.addEdge(this);
		return edge;
	}

	// TODO: Ich glaube, wir dürfen keine Methoden zum AIGraphen hinzufügen, das verletzt irgendeinen Shit
	// Außerdem brauchen wir diese Methode doch auch gar nicht
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
			if(v == vertex) { //Hierfür brauchen wir kein compareTo, da hier die ObjektIDs verglichen werden --> Identitätsprüfung
				return true;  //Sie haben schon von Haus aus eindeutige IDS und zwar die ObjektID 
			}
		}
		return false;
	}

	
	public Edge addEdgeD(Vertex v1, Vertex v2) {
		// TODO: addEdgeD implementieren --> für gerichtete Kanten
		return null;
	}
	
	/**
	 * Wenn die Methode true zurueck lierft, dann wurde die gerichtete Edge(Kante) geloescht, sonst nicht (false) 
	 * 
	 * @param v1 erwartet ein Vertex-Objekt
	 * @param v2 erwartet ein Vertex-Objekt
	 * 
	 * @return boolean true wenn die Edge gelöscht wurde, false wenn nicht
	 */
	public boolean deleteEdge(Vertex v1, Vertex v2) {
		// TODO: deleteEdge --> für gerichtete und ungerichtete
		if(gerichteterGraph(v1, v2)) {
			
			// Warum werden hier die Vertex gelöscht, wenn doch nur die Edge gelöscht werden sollte ?
			for(Vertex vertex : verticesList) {
				if(vertex == v1) {
					verticesList.remove(v1);
				}
				if(vertex == v2) {
					verticesList.remove(v2);
				}
			}
			
		}		
		return false;
	}
	
	/**
	 * Es ist eine Hilfsmethode, wenn der Graph gerichtet ist, dann wird true zurueck gegeben, sonst false
	 * 
	 * @param v1 Erwartet ein Vertex-Objekt
	 * @param v2 Erwartet ein Vertex-Objekt
	 * 
	 * @return boolean
	 */
	private boolean gerichteterGraph(Vertex v1, Vertex v2) {
		// TODO Auto-generated method stub
		return false;
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
		//private List<Edge[]> adjacents = new ArrayList<>(); Hier könnten wir als Paare die adjazenten Kanten speichern
		
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
		
		//TODO: Hier muessen wir noch eine Fallunterscheidung implementieren, ob es ein gerichteter oder ungerichteter Graph ist. 
	    //		Z.B. gerichtet zeigt nur auf einen Verticle(Knotte) und ungerichteter auf zwei Verticle
		//		Moeechte das nicht alleine implementieren, brauche eine Absprache mit dir Flahchik
		
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
