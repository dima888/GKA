package graph_lib;

import java.util.ArrayList;
import java.util.List;

public class AIGraph {
	
	//Attribute
	private List<Vertex> verticesList = new ArrayList<>();
	private List<UndirectedEdge> edgesListU = new ArrayList<>();
	private List<DirectedEdge> edgesListD = new ArrayList<>();
	
	//********************************* KONSTRUKTOR *********************************************
	public AIGraph() {
		//NULL Graph
	}
	
	/**
	 * Zum hinzufügen eines Vertex(Knoten)- Objekts zum Graphen
	 * @param newItem Wert, der im Vertex gespeichert werden soll
	 * @return VertexID gibt die ID des hinzugefügten Vertices zurück
	 */
	public int addVertex(String newItem) {
		//Prüfen ob Vertex mit vertexNamen (newItem) schon existiert
		for(Vertex v : verticesList) {
			//Falls existiert, gib ID des bestehenden Knoten zurück
			if(v.getName().equals(newItem)) {
				return v.getID();
			}
		}
		Vertex vertex = new Vertex(newItem);
		verticesList.add(vertex);
		return vertex.getID();
	}
	
	/**
	 * Hier wird ein Vertex(Knoten) geloescht 
	 * @param v_id hier wird eine VertexID erwartet
	 */
	public void deleteVertex(int v_id) {	
		//Beinhaltet schon die Precondition, 
		Vertex vertex = getVertex(v_id);
		
		//löscht man einen Knoten, so auch seine anliegenden Kanten
		for(Edge edge : edgesListU) {
			Vertex[] verticesFromEdge = ((UndirectedEdge) edge).getVertices();
			if((verticesFromEdge[0] == vertex) || (verticesFromEdge[1] == vertex)) {
				edgesListU.remove(edge);
			}
		}
		
		for(Edge edge : edgesListD) {
			Vertex[] verticesFromEdge = ((DirectedEdge) edge).getVertices();
			if((verticesFromEdge[0] == vertex) || (verticesFromEdge[1] == vertex)) {
				edgesListD.remove(edge);
			}
		}

		verticesList.remove(vertex);
	}
	
	/**
	 * Die Methode fuegt eine EngeU(Ungerichtete Kante) an zwei oder ein vertices(Knoten) an
	 * 
	 * @param v1 eine VertexID
	 * @param v2 eine VertexID
	 * @return int gibt die ID der hinzugefügten Edge zurück
	 */
	public int addEdgeU(int v1, int v2) {
		//Beinhaltet schon die Precondition
		Vertex vertex1 = getVertex(v1);
		Vertex vertex2 = getVertex(v2);
		
		//Man darf nur dann eine Kante einfügen, falls keine zwischen den Vertices vorhanden ist --> Mehrfachkanten nicht erlaubt
		if(includeEdge(v1, v2)) throw new IllegalArgumentException("Es besteht bereits eine Kante zwischen den beiden Vertices");
		
		UndirectedEdge edge = new UndirectedEdge(vertex1, vertex2);
		
		edgesListU.add(edge);
		
		return edge.getID();
	}
	
	/**
	 * Die Methode fuegt eine EngeD(gerichtete Kante) an zwei oder ein vertices(Knoten) an
	 * 
	 * @param v1 eine VertexID
	 * @param v2 eine VertexID
	 * @return int gibt die ID der hinzugefügten Edge zurück
	 */
	public int addEdgeD(int v1, int v2) {	
		//Beinhaltet schon die Precondition
		Vertex vertex1 = getVertex(v1);
		Vertex vertex2 = getVertex(v2);
		
		//Man darf nur dann eine Kante einfügen, falls keine zwischen den Vertices vorhanden ist --> Mehrfachkanten nicht erlaubt
		if(includeEdge(v1, v2)) throw new IllegalArgumentException("Es besteht bereits eine Kante zwischen den beiden Vertices");
		
		DirectedEdge edge = new DirectedEdge(vertex1, vertex2);
		
		edgesListD.add(edge);
		
		return edge.getID();
	}
	
	/**
	 * Die Methode loescht eine Kante(Edge)
	 * Es Kann nur höchstens eine Kante zwischen 2 Ecken geben
	 * @param v1 erwartet eine VertexID
	 * @param v2 erwartet eine VertexID
	 */
	public void deleteEdge(int v1, int v2) {
		//Beinhaltet schon die Precondition
		Vertex vertex1 = getVertex(v1);
		Vertex vertex2 = getVertex(v2);
		
		//Zuerst alle ungerichteten Kanten durchsuchen
		for(Edge edge : edgesListU) {
			Vertex[] verticesFromEdge = ((UndirectedEdge) edge).getVertices();
			
			boolean temp1 = false;
			boolean temp2 = false;
			
			if((verticesFromEdge[0] == vertex1) || (verticesFromEdge[1] == vertex1)) { //Da keine Reihenfolge besteht müssen beide Stellen überprüft werden
				temp1 = true;
			}
			
			if((verticesFromEdge[0] == vertex2) || (verticesFromEdge[1] == vertex2)) { //Da keine Reihenfolge besteht müssen beide Stellen überprüft werden
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
			
			if((verticesFromEdge[0] == vertex1) && (verticesFromEdge[1] == vertex2)) {
				edgesListD.remove(edge);
				return;
			}
			
			if((verticesFromEdge[0] == vertex2) && (verticesFromEdge[1] == vertex1)) {
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
	 * @param e1 EdgeID
	 * @return int ID des Source Vertices, Falls nicht vorhanden ErrorCODE -1
	 */
	public int getSource(int e1) {
		//Precondition
		//Gehört die übergebene Kante überhaupt zu diesem Graphen? Beide Listen durchsuchen	
		if(! (getEdges().contains(e1))) throw new IllegalArgumentException("Die übergebene Kante gehört nicht zu diesem Graphen");
		
		Vertex vertex = null;
		
		//Prüfen ob es ein gerichteter Graph ist
		if(e1 % 2 == 0) {
			vertex = getEdgeD(e1).getSource();
		} else {
			vertex = getEdgeU(e1).getSource();
		}	

		return vertex.getID();
	}
	
	/**
	 *  Hier Hollen wir uns die Ecke in welche die Kante eingeht
	 *  Erklaerung: 
	 *	Quelle = Eine Ecke, deren Eingangsgrad 0 ist
	 *  Senke =  Ecke,deren Ausgangsgrad 0 is
	 * @param e1 EdgeID
	 * @return int ID des Target Vertices
	 */
	public int getTarget(int e1) {
		//Precondition
		//Gehört die übergebene Kante überhaupt zu diesem Graphen? Beide Listen durchsuchen	
		if(! (getEdges().contains(e1))) throw new IllegalArgumentException("Die übergebene Kante gehört nicht zu diesem Graphen");
		
		Vertex vertex = null;
		
		//Prüfen ob es ein gerichteter Graph ist
		if(e1 % 2 == 0) { 
			vertex = getEdgeD(e1).getTarget();
		} else {
			vertex = getEdgeU(e1).getTarget();
		}
		
		return vertex.getID();
	}
	
	/**
	 * ermittelt alle zur EckenID v1 izidenten Kanten
	 * @param v1 EckenID
	 * @return List<Integer> Liste der EckenID´s
	 */
	public List<Integer> getIncident(int v1) {
		return getVertex(v1).getIncidents();
	}
	
	/**
	 * ermittelt alle zur EckenID v1 adjazenten Kanten
	 * @param v1 EckenID
	 * @return List<Integer> Liste der EckenID´s
	 */
	public List<Integer> getAdjacent(int v1) {
		
		Vertex vertex = getVertex(v1);
		
		List<Integer> result = new ArrayList<>();
		
		//Holen aller anliegenden Kanten --> incidents
		List<Integer> incidents = vertex.getIncidents();
		
		//Holen aller adjazenten
		for(Integer edgeID : incidents) {
			Edge edge = null;
			
			//Es kann nur ein gerichteter oder ungerichteter sein
			if(edgeID % 2 == 0) {
				//System.out.println(incidents);
				//System.out.println(edgeID);
				edge = getEdgeD(edgeID);
			} else {
				edge = getEdgeU(edgeID);
			}
				
			Vertex[] vertices = edge.getVertices();
			
			if(vertices[0] != vertex) {
				result.add(vertices[0].getID());
			} else {
				result.add(vertices[1].getID());
			}
		}
		
		return result;
	}	

	
	/**
	 * Gibt die Liste Aller Vertexes(Knoten)ID´s zurueck
	 * @return List<Integer> gibt eine Liste von VertexID´s zurück
	 */
	public List<Integer> getVertexes() {
		List<Integer> result = new ArrayList<>();
		for(Vertex v : verticesList) {
			result.add(v.getID());
		}
		return result;
	}
	
	/**
	 * Gibt die Liste aller Edge(Kanten)ID´s zurueck
	 * @return List<Integer> gibt eine Liste von EdgeID´s zurück
	 */
	public List<Integer> getEdges() {
		List<Edge> puffer = new ArrayList<>();
		List<Integer> result = new ArrayList<>();
		
		puffer.addAll(edgesListU);
		puffer.addAll(edgesListD);
		
		for(Edge e : puffer) {
			result.add(e.getID());
		}
		
		return result;
	}
	
	/**
	 * Ermittelt den Attributwert von attr der Kante e1
	 * @param e1 Edge(Kante) 
	 * @param attr String 
	 * @return int
	 */
	public int getValE(int e1, String attr) {
		if(e1 % 2 == 0) {
			return getEdgeD(e1).getAttr(attr);
		}
		return getEdgeU(e1).getAttr(attr);
	}
	
	/**
	 * Ermittelt den Attributwert von attr der Ecke v1
	 * @param v1
	 * @param attr
	 * @return int
	 */
	public int getValV(int v1, String attr) {
		return getVertex(v1).getAttr(attr);
	}
	
	
	/**
	 * Ermittelt den Attributwert von attr der Kante e1
	 * @param e1
	 * @param attr
	 * @return String
	 */
	public String getStrE(int e1, String attr) {
		if(e1 % 2 == 0) {
			return getEdgeD(e1).getAttrStr(attr);
		}
		return getEdgeU(e1).getAttrStr(attr);
	}
	
	/**
	 * Ermittelt den Attributwert von attr der Ecke v1
	 * @param v1
	 * @param attr
	 * @return String
	 */
	public String getStrV(int v1, String attr) {
		return getVertex(v1).getAttrStr(attr);
	}

	/**
	 * Ermittelt alle Attribute des Vertex(Ecke) v1
	 * @param v1
	 * @return List<String>
	 */
	public List<String> getAttrV(int v1) {
		return getVertex(v1).getAttrList();
	}
	
	 /**
	  * Ermittelt alle Attribute des Edge(Kante) e1
	  * @param e1
	  * @return List<?>
	  */
	public List<?> getAttrE(int e1) {
		if(e1 % 2 == 0) {
			return getEdgeD(e1).getAttrList();
		}
		return getEdgeU(e1).getAttrList();
	}
	
	//************************************ MUTATOR ********************************************
	
	/**
	 * Setzt den Attributwert von attr der Kante e1 auf val
	 * Wenn true ? erfolgreich gesetzt : false
	 * @param e1
	 * @param attr
	 * @param val
	 */
	public boolean setValE(int e1, String attr, int val) {
		if(e1 % 2 == 0) {
			return getEdgeD(e1).setValE(attr, val);
		}
		return getEdgeU(e1).setValE(attr, val);
	}
	
	/**
	 * Setzt den Attributwert von attr der Ecke v1 auf val
	 * @param v1
	 * @param attr
	 * @param val
	 * @return
	 */
	public boolean setValV(int v1, String attr, int val) {
		return getVertex(v1).setValV(attr, val);
	}
	
	/**
	 * Setzt den Attributwert von attr der Kante e1 auf val
	 * @param e1
	 * @param attr
	 * @param val
	 * @return bool
	 */
	public boolean setStrE(int e1, String attr, String val) {
		if(e1 % 2 == 0) {
			return getEdgeD(e1).setStrE(attr, val);
		}
		return getEdgeU(e1).setStrE(attr, val);
	}
	
	/**
	 * Setzt den Attributwert von attr der Ecke v1 auf val
	 * @param v1
	 * @param attr
	 * @param val
	 * @return bool
	 */
	public boolean setStrV(int v1, String attr, String val) {
		return getVertex(v1).setStrV(attr, val);
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
	 * Bestimmt die gerichtete Kante zu welcher die übergebene EdgeID übereinstimmt
	 * @param int e_id ID der Kante
	 * @return Edge
	 */
	private DirectedEdge getEdgeD(int e_id) {
		for(DirectedEdge e : edgesListD) {
			if(e.getID() == e_id) {
				return e;
			}
		}
		throw new IllegalArgumentException("In diesem Graphen existiert keine gerichtete Kante mit der übergebenen EdgeID");
	}
	
	/**
	 * Bestimmt die ungerichtete Kante zu welcher die übergebene EdgeID übereinstimmt
	 * @param int e_id ID der Kante
	 * @return Edge
	 */
	private UndirectedEdge getEdgeU(int e_id) {
		for(UndirectedEdge e : edgesListU) {
			if(e.getID() == e_id) {
				return e;
			}
		}
		throw new IllegalArgumentException("In diesem Graphen existiert keine ungerichtete Kante mit der übergebenen EdgeID");
	}

	/**
	 * Bestimmt den Vertex der mit der übergebenen VertexID übereinstimmt
	 * @param int v_id ID des Vertices
	 * @return Vertex
	 */
	private Vertex getVertex(int v_id) {
		for(Vertex v : verticesList) {
			if(v.getID() == v_id) {
				return v;
			}
		}
		throw new IllegalArgumentException("In diesem Graphen existiert kein Vertex mit der übergebenen VertexID");
	}

	/**
	 * Die Hilfsmethode prüft, ob bereits eine Kante zwischen den übergebenen Parametern existiert
	 * @param v1 eine VertexID
	 * @param v2 eine VertexID
	 * @return boolean true, wenn eine Kante zwischen den beiden übergebenen parametern existiert, sonst false
	 */
	private boolean includeEdge(int v1, int v2) {
		Vertex vertex1 = getVertex(v1);
		Vertex vertex2 = getVertex(v2);
		
		//Zuerst alle ungerichteten Kanten durchsuchen
		for(Edge edge : edgesListU) {
			Vertex[] verticesFromEdge = ((UndirectedEdge) edge).getVertices();
			
			boolean temp1 = false;
			boolean temp2 = false;
			
			if((verticesFromEdge[0] == vertex1) || (verticesFromEdge[1] == vertex1)) { //Da keine Reihenfolge besteht müssen beide Stellen überprüft werden
				temp1 = true;
			}
			
			if((verticesFromEdge[0] == vertex2) || (verticesFromEdge[1] == vertex2)) { //Da keine Reihenfolge besteht müssen beide Stellen überprüft werden
				temp2 = true;
			}
			
			if(temp1 && temp2) {
				return true;
			}
		}
		
		//Alle Gerichteten Kanten durchsuchen, falls wir bei den gerichteten nicht fündig wurden
		for(Edge edge : edgesListD) {
			Vertex[] verticesFromEdge = ((DirectedEdge) edge).getVertices();
			
			if((verticesFromEdge[0] == vertex1) && (verticesFromEdge[1] == vertex2)) { //Nur eine Richtung prüfen
				return true;
			}
		}
		return false;
	}
}