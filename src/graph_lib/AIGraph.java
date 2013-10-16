
package graph_lib;

import java.util.ArrayList;
import java.util.List;

public class AIGraph {
	
	private List<Vertex> verticesList = new ArrayList<>();
	List<UndirectedEdge> edgesListU = new ArrayList<>();
	List<DirectedEdge> edgesListD = new ArrayList<>();
	
	//********************************* KONSTRUKTOREN *********************************************
	public AIGraph() {
		//NULL Graph
	}
	
	/**
	 * Zum hinzufügen eines Vertex(Knoten)- Objekts zum Graphen
	 * @param newItem Wert, der im Vertex gespeichert werden soll
	 * @return Vertex gibt die Referenz auf die hinzugefügte Vertex zurück
	 */
	public Vertex addVertex(int newItem) {
		Vertex vertex = new Vertex(newItem);
		verticesList.add(vertex);
		return vertex;
	}
	
	/**
	 * Hier wird ein Vertex(Knoten) geloescht 
	 * @param v_id Ein Vertex(Knoten) Objekt wird hier erwartet.
	 * @return boolean true, bei erfolgreichem löschen, sonst false
	 */
	public void deleteVertex(Vertex v_id) {
		//Precondtion
		//Prüfen ob sich der übergebene Knoten überhaupt im Graphen befindet
		if(! (verticesList.contains(v_id))) {
			throw new IllegalArgumentException("Dieser Knoten befindet sich nicht im Graphen");
		}
		
		//löscht man einen Knoten, so auch seine anliegenden Kanten
		for(Edge edge : edgesListU) {
			Vertex[] verticesFromEdge = ((UndirectedEdge) edge).getVertices();
			if((verticesFromEdge[0] == v_id) || (verticesFromEdge[1] == v_id)) {
				edgesListU.remove(edge);
			}
		}
		
		for(Edge edge : edgesListD) {
			Vertex[] verticesFromEdge = ((DirectedEdge) edge).getVertices();
			if((verticesFromEdge[0] == v_id) || (verticesFromEdge[1] == v_id)) {
				edgesListD.remove(edge);
			}
		}
		
		verticesList.remove(v_id);
	}
	
	/**
	 * Die Methode fuegt eine EngeU(Ungerichtete Kante) an zwei oder ein vertices(Knoten) an
	 * 
	 * @param v1 ein Vertex
	 * @param v2 ein Vertex
	 * @return Edge gibt die Referenz auf die hinzugefügte Edge zurück
	 */
	public Edge addEdgeU(Vertex v1, Vertex v2) {		
		//Precondition		
		//Beide Vertices müssen zu einem Grpahen gehören
		if(!(include(verticesList, v1)) && !(include(verticesList, v2))) {
			throw new IllegalArgumentException("EdgeU (Ungerichtete Kante) kann nicht hinzugefuegt werden, da vermutlich da zwischen keine vertices(Knoten exestieren) ");
		}
		
		//Man darf nur dann eine Kante einfügen, falls keine zwischen den Vertices vorhanden ist --> Mehrfachkanten nicht erlaubt
		if(includeEdge(v1, v2)) {
			throw new IllegalArgumentException("Es besteht bereits eine Kante zwischen den beiden Vertices");
		}
		
		UndirectedEdge edge = new UndirectedEdge(v1, v2);
		edgesListU.add(edge);
		return edge;
	}
	
	public Edge addEdgeD(Vertex v1, Vertex v2) {	
		//Precondition
		//Beide Vertices müssen zu einem Grpahen gehören
		if(!(include(verticesList, v1)) && !(include(verticesList, v2))) {
			throw new IllegalArgumentException("EdgeD (Gerichtete Kante) kann nicht hinzugefuegt werden, da vermutlich da zwischen keine vertices(Knoten exestieren) ");
		}
		
		//Man darf nur dann eine Kante einfügen, falls keine zwischen den Vertices vorhanden ist --> Mehrfachkanten nicht erlaubt
		if(includeEdge(v1, v2)) {
			throw new IllegalArgumentException("Es besteht bereits eine Kante zwischen den beiden Vertices");
		}
		
		DirectedEdge edge = new DirectedEdge(v1, v2);
		edgesListD.add(edge);
		return edge;
	}
	
	/**
	 * Die Methode loescht eine Kante(Edge)
	 * Es Kann nur höchstens eine Kante zwischen 2 Ecken geben
	 * @param v1 erwartet ein Vertex-Objekt
	 * @param v2 erwartet ein Vertex-Objekt
	 * @return boolean true wenn die Edge gelöscht wurde, false wenn nicht
	 */
	public void deleteEdge(Vertex v1, Vertex v2) {
		//Zuerst alle ungerichteten Kanten durchsuchen
		for(Edge edge : edgesListU) {
			Vertex[] verticesFromEdge = ((UndirectedEdge) edge).getVertices();
			
			boolean temp1 = false;
			boolean temp2 = false;
			
			if((verticesFromEdge[0] == v1) || (verticesFromEdge[1] == v1)) { //Da keine Reihenfolge besteht müssen beide Stellen überprüft werden
				temp1 = true;
			}
			
			if((verticesFromEdge[0] == v2) || (verticesFromEdge[1] == v2)) { //Da keine Reihenfolge besteht müssen beide Stellen überprüft werden
				temp2 = true;
			}
			
			if(temp1 && temp2) {
				edgesListU.remove(edge);
				return;
			}
		}
		
		//Alle Gerichteten Kanten durchsuchen, falls wir bei den gerichteten nicht fündig wurden
		for(Edge edge : edgesListD) {
			Vertex[] verticesFromEdge = ((DirectedEdge) edge).getVertices();
			
			if((verticesFromEdge[0] == v1) && (verticesFromEdge[1] == v2)) {
				edgesListD.remove(edge);
				return;
			}
			
			if((verticesFromEdge[0] == v2) && (verticesFromEdge[1] == v1)) {
				edgesListD.remove(edge);
				return;
			}
		}
		
		//Postcondition für Effizienz um nicht zwei mal die Liste zu durchlaufen
		throw new IllegalArgumentException("Es existiert keine Kante zwischen den übergebenen Knoten");
	}
	
	//********************************* SELEKTOREN *********************************************

	/**
	 * Prueft ob der Graph leer ist. 
	 * @return boolean
	 */
	public boolean isEmpty() {
		if(verticesList.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 *	getSource implementieren --> ermittelt im gerichteten Fall die Quelle der
	 *	Kante e1 (gegeben als ID) im ungerichteten Fall die linke / erste Ecke
	 *  return Wert ver Edge auf Vertex geaendert
	 *  Hier Hollen wir uns die Ecke von der die Kante weggeht und bestimmen ihre Quelle
	 *  Erklaerung: 
	 *	Quelle = Eine Ecke, deren Eingangsgrad 0 ist
	 *  Senke =  Ecke,deren Ausgangsgrad 0 is
	 * @param e1
	 * @return
	 */
	public Vertex getSource(Edge e1) {
		//Precondition
		//Gehört die übergebene Kante überhaupt zu diesem Graphen? Beide Listen durchsuchen
		if(! (edgesListU.contains(e1) || (edgesListD.contains(e1)))) {
			throw new IllegalArgumentException("Die übergebene Kante gehört nicht zu diesem Graphen");
		}
		
		Vertex vertex = null;
		
		if(e1 instanceof DirectedEdge) {
			if(((DirectedEdge) e1).hatQuelle()) {
				Vertex[] verticesFromEdge = ((DirectedEdge) e1).getVertices();
				vertex = verticesFromEdge[0]; //StartKnoten / Quelle
			}
		}
		
		if(e1 instanceof UndirectedEdge) {
			Vertex[] verticesFromEdge = ((UndirectedEdge) e1).getVertices();
			vertex = verticesFromEdge[0]; //Die Linke Ecke
		}
		
		return vertex;
	}
	
	public Vertex getTarget(Edge e1) {
		//Precondition
		//Gehört die übergebene Kante überhaupt zu diesem Graphen ? Beide Listen durchsuchen
		if(! (edgesListU.contains(e1) || (edgesListD.contains(e1)))) {
			throw new IllegalArgumentException("Die übergebene Kante gehört nicht zu diesem Graphen");
		}
		
		Vertex vertex = null;
		
		if(e1 instanceof DirectedEdge) {
			if(((DirectedEdge) e1).hatSenke()) {
				Vertex[] verticesFromEdge = ((DirectedEdge) e1).getVertices();
				vertex = verticesFromEdge[1]; //EndKnoten / Ziel
			}
		}
		
		if(e1 instanceof UndirectedEdge) {
			Vertex[] verticesFromEdge = ((UndirectedEdge) e1).getVertices();
			vertex = verticesFromEdge[1]; //Die rechte Ecke
		}
		
		return vertex;
	}
	
	/**
	 * ermittelt alle zur Ecke v1 nzidenten Kanten
	 * @param v1
	 * @return
	 */
	public List<Edge> getIncident(Vertex v1) {
		return v1.getIncidents();
	}
	
	public List<Vertex> getAdjacent(Vertex v1) {
		List<Vertex> result = new ArrayList<>();
		
		//Holen aller anliegenden Kanten --> incidents
		List<Edge> incidents = v1.getIncidents();
		
		//Holen aller adjazenten
		for(Edge edge : incidents) {
			Vertex[] vertices = edge.getVertices();
			
			if(vertices[0] != v1) {
				result.add(vertices[0]);
			} else {
				result.add(vertices[1]);
			}
		}
		
		return result;
	}
	
	/**
	 * Gibt die Liste Aller Vertexes(Knoten) zurueck
	 * 
	 * @return List<Vertex> gibt eine Liste von Vertex-Objekten zurück
	 */
	public List<Vertex> getVertexes() {
		return verticesList;
	}
	
	/**
	 * Gibt die Liste aller Edge(Kanten) zurueck
	 * 
	 * @return List<Edge> gibt eine Liste von Edge-Objekten zurück
	 */
	public List<Edge> getEdges() {
		List<Edge> result = new ArrayList<>();
		
		result.addAll(edgesListU);
		result.addAll(edgesListD);
		
		return result;
	}
	
	/**
	 * Ermittelt den Attributwert von attr der Kante e1
	 * @param e1 Edge(Kante) 
	 * @param attr String 
	 * @return int
	 */
	public int getValE(Edge e1, String attr) {
		return e1.getAttr(attr);
	}
	
	/**
	 * Ermittelt den Attributwert von attr der Ecke v1
	 * @param v1
	 * @param attr
	 * @return int
	 */
	public int getValV(Vertex v1, String attr) {
		return v1.getAttr(attr);
	}
	
	/**
	 * Ermittelt den Attributwert von attr der Kante e1
	 * @param e1
	 * @param attr
	 * @return String
	 */
	public String getStrE(Edge e1, String attr) {
		return e1.getAttrStr(attr);
	}
	
	/**
	 * Ermittelt den Attributwert von attr der Ecke v1
	 * @param v1
	 * @param attr
	 * @return String
	 */
	public String getStrV(Vertex v1, String attr) {
		return v1.getAttrStr(attr);
	}

	/**
	 * Ermittelt alle Attribute des Vertex(Ecke) v1
	 * @param v1
	 * @return
	 */
	public List<String> getAttrV(Vertex v1) {
		return v1.getAttrList();
	}
	 /**
	  * Ermittelt alle Attribute des Edge(Kante) e1
	  * @param e1
	  * @return
	  */
	public List<?> getAttrE(Edge e1) {
		return e1.getAttrList();
	}
	
	//************************************ MUTATOR ********************************************
	
	/**
	 * Setzt den Attributwert von attr der Kante e1 auf val
	 * Wenn true ? erfolgreich gesetzt : false
	 * @param e1
	 * @param attr
	 * @param val
	 */
	public boolean setValE(Edge e1, String attr, int val) {
		return e1.setValE(attr, val);
	}
	
	/**
	 * Setzt den Attributwert von attr der Ecke v1 auf val
	 * @param v1
	 * @param attr
	 * @param val
	 * @return
	 */
	public boolean setValV(Vertex v1, String attr, int val) {
		return v1.setValV(attr, val);
	}
	
	/**
	 * Setzt den Attributwert von attr der Kante e1 auf val
	 * @param e1
	 * @param attr
	 * @param val
	 * @return bool
	 */
	public boolean setStrE(Edge e1, String attr, String val) {
		return e1.setStrE(attr, val);
	}
	
	/**
	 * Setzt den Attributwert von attr der Ecke v1 auf val
	 * @param v1
	 * @param attr
	 * @param val
	 * @return bool
	 */
	public boolean setStrV(Vertex v1, String attr, String val) {
		return v1.setStrV(attr, val);
	}
	
	@Override
	public String toString() {
		String result = "";
		result += "Alle Vertices: " + verticesList + "\n";
		result += "Alle gerichteten Edges: " + edgesListD + "\n";
		result += "Alle ungerichteten Edges: " + edgesListU;
		return result;
	}
	
	//********************************* PRIVATE METHODEN DIESER KLASSE *********************************
	/**
	 * Die Hilfsmethode prueft, ob ein Vertex(Knoten) in einer Liste vorhanden ist
	 * @param verticesList erwartet eine Liste von Vertex(Knoten)
	 * @param v erwaratet ein Vertex Object
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

	/**
	 * Die Hilfsmethode prüft, ob bereits eine Kante zwischen den übergebenen Parametern existiert
	 * @param v1 ein Vertex-Objekt
	 * @param v2 ein Vertex-Objekt
	 * @return boolean true, wenn eine Kante zwischen den beiden übergebenen parametern existiert, sonst false
	 */
	private boolean includeEdge(Vertex v1, Vertex v2) {
		//Zuerst alle ungerichteten Kanten durchsuchen
		for(Edge edge : edgesListU) {
			Vertex[] verticesFromEdge = ((UndirectedEdge) edge).getVertices();
			
			boolean temp1 = false;
			boolean temp2 = false;
			
			if((verticesFromEdge[0] == v1) || (verticesFromEdge[1] == v1)) { //Da keine Reihenfolge besteht müssen beide Stellen überprüft werden
				temp1 = true;
			}
			
			if((verticesFromEdge[0] == v2) || (verticesFromEdge[1] == v2)) { //Da keine Reihenfolge besteht müssen beide Stellen überprüft werden
				temp2 = true;
			}
			
			if(temp1 && temp2) {
				return true;
			}
		}
		
		//Alle Gerichteten Kanten durchsuchen, falls wir bei den gerichteten nicht fündig wurden
		for(Edge edge : edgesListD) {
			Vertex[] verticesFromEdge = ((DirectedEdge) edge).getVertices();
			
			if((verticesFromEdge[0] == v1) && (verticesFromEdge[1] == v2)) { //Nur eine Richtung prüfen
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Diese Methode soll uns ermöglichen zwei Graphen auf Wertgleichheit zu überprüfen
	 * @param Object o erwartet ein Objekt
	 * @return boolean true wenn der übergeben Graph mit dem aufrufenden Wertgleich ist, sonst false
	 */
	@Override
	public boolean equals(Object o) {
		//Prüfen, ob der übergebene Parameter überhaupt ein Graph ist
		if( !(o instanceof AIGraph)) {return false;}
		//Sicher gestellt das der übergebene Parameter ein AIGraph ist
		AIGraph graph = (AIGraph) o;
		
		//Sind beide Graphen NULL-Graphen, dann sind sie gleich
		if((this.verticesList.isEmpty()) && (graph.verticesList.isEmpty())) {return true;}
		
		//Prüfen ob die Anzahl der vertices übereinstimmt
		if((this.getVertexes().size()) != (graph.getVertexes().size())) {return false;}
		
		//Temporäre variable für die Schleife
		boolean temp = false;
		
		//Prüfen ob bei beiden Graphen jeder Vertex die gleichen eigenschaften aufweisen(Attribute != Eigenschaften)
		for(Vertex v1 : this.verticesList) {			
			for(Vertex v2 : graph.verticesList) {
				if((v1.getIngoingEdge().size()) == (v2.getIngoingEdge().size())) {
					if((v1.getOutgoingEdge().size()) == (v2.getOutgoingEdge().size())) {
						temp = true;
					}
				}
			}
			//Hier ist mindestens ein Fall, der zu einen Graphen nicht überein stimmt, somit ist unsere Struktur nicht gleich
			if(! (temp)) {return false;} 
		}
		return true;
	}
}
