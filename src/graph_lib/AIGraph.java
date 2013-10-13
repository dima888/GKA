//TODO: Wichtig, wir brauchen eine ToString z.B. in Enge, wo die ID des jewaligen Objekt zurueck gegeben wird
//      Oder wir entscheiden uns anders und returnen z.B. bei addEdgeU -> einen Integer wert als seine eindeutige ID: 

//********************** WICHTIG, WIR SOLLEN KEINE MEHRFACHKANTEN ZULASSEN --> ÜBERLADENE METHODE INCLUDE IMPLEMENTIERT *********************************
//************************* ES MÜSSEN NOCH ÜBERALL PRECONDITIONS EINGEFÜGT WERDEN, DIE AUF NULL PRÜFEN **************************************************
// ES GIBT EIN PROBLEM BEIM TESTEN UND ZWAR, WIR KÖNNEN KEINEN VERTEX ERSTELLEN DA ES PRIVATE IST ALSO MÜSSEN WIR DOCH MIT ID ARBEITEN

package graph_lib;

import java.util.ArrayList;
import java.util.List;

public class AIGraph {
	
	private List<Vertex> verticesList = new ArrayList<>();
	List<UndirectedEdge> edgesListU = new ArrayList<>();
	List<DirectedEdge> edgesListD = new ArrayList<>();
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
	 * Die Methode fuegt eine EngeU(Ungerichtete Kante) an zwei oder ein vertices(Knotten) an
	 * 
	 * @param v1 ein Vertex
	 * @param v2 ein Vertex
	 * 
	 * @return Edge gibt die Referenz auf die hinzugefügte Edge zurück
	 */
	public Edge addEdgeU(Vertex v1, Vertex v2) {		
		//Precondition		
		//Beide Vertices müssen zu einem Grpahen gehören
		if(!(include(verticesList, v1)) && !(include(verticesList, v2))) {
			throw new IllegalArgumentException("EdgeU (Ungerichtete Kante) kann nicht hinzugefuegt werden, da vermutlich da zwischen keine vertices(Knotten exestieren) ");
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
			throw new IllegalArgumentException("EdgeD (Gerichtete Kante) kann nicht hinzugefuegt werden, da vermutlich da zwischen keine vertices(Knotten exestieren) ");
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
	 * 
	 * @param v1 erwartet ein Vertex-Objekt
	 * @param v2 erwartet ein Vertex-Objekt
	 * 
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
		// TODO: getAdjacent implementieren --> ermittelt alle zur Ecke v1
		// adjazenten Ecken
		return null;
	}
	
	/**
	 * Gibt die Liste Aller Vertexes(Knotten) zurueck
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

	/**
	 * Die Hilfsmethode prüft, ob bereits eine Kante zwischen den übergebenen Parametern existiert
	 * 
	 * @param v1 ein Vertex-Objekt
	 * @param v2 ein Vertex-Objekt
	 * 
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
	
//	private Vertex ermittleVertex(int id) {
//		for(Vertex vertex : verticesList) {
//			if(vertex.getID() == id) {
//				return vertex;
//			}
//		}
//		//Falls die übergebene ID nicht existiert
//		throw new IllegalArgumentException("Es existiert kein Knoten mit der übergebenen ID");
//	}
//	
//	private Edge ermittleEdgeU(int id) {
//		for(Edge edge : edgesListU) {
//			if(((UndirectedEdge) edge).getID() == id) {
//				return edge;
//			}
//		}
//		//Falls die übergebene ID nicht existiert
//		throw new IllegalArgumentException("Es existiert kein Knoten mit der übergebenen ID");
//	}
//	
//	private Edge ermittleEdgeD(int id) {
//		for(Edge edge : edgesListD) {
//			if(((DirectedEdge) edge).getID() == id) {
//				return edge;
//			}
//		}
//		//Falls die übergebene ID nicht existiert
//		throw new IllegalArgumentException("Es existiert kein Knoten mit der übergebenen ID");
//	}
}
