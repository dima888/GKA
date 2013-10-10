//TODO: Wichtig, wir brauchen eine ToString z.B. in Enge, wo die ID des jewaligen Objekt zurueck gegeben wird
//      Oder wir entscheiden uns anders und returnen z.B. bei addEdgeU -> einen Integer wert als seine eindeutige ID: 

//TODO: Duerfen wir die ALGraph mit unseren privaten Methoden versehen?????????????????????????

package graph_lib;

import java.util.ArrayList;
import java.util.List;

public class AIGraph {
	
	private List<Vertex> verticesList = new ArrayList<>();
	private List<Edge> edgesList = new ArrayList<>();
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
		for(Edge edge : edgesList) { //Ueber die interne endgesLIst iterieren
			if((edge.verticesFromEdge[0] == v_id) || (edge.verticesFromEdge[1] == v_id)) { //Wenn eine der beiden Kantenseiten auf den Knoten, wird die Kante gelöscht
				edgesList.remove(edge); //Alle Inzidenten Kanten löschen
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
		Edge edge = new Edge(v1, v2, false); //Edge mit zwei oder einem vertices verbunden. 										
		edgesList.add(edge);
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
		Edge edge = new Edge(v1, v2, true); //Edge mit zwei oder einem vertices verbunden. 	
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
		for(Edge edge : edgesList) { //FALL Ungerichtet
			if(edge.verticesFromEdge[0] == v1 && edge.verticesFromEdge[1] == v2) {
				edgesList.remove(edge);
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
		for(Edge edge : edgesList) {
			if(edge == e1) {
				if(edge.getDirected() == true) { //Fall gerichtet
					
					if(edge.getSource().getEntryGrad() == 0) {
						return edge.getSource();
					}
					
				} else { //Fall ungerichtet
					return edge.getSource();
				}
			}
		}
		return null;
	}
	
	public Edge getTarget(Edge e1) {
		// TODO: getTarget implementieren --> ermittelt im gerichteten Fall die Senke der
		// Kante e1 (gegeben als ID) im ungerichteten Fall die rechte / zweite Ecke
		return null;
	}
	
	/**
	 * ermittelt alle zur Ecke v1 nzidenten Kanten
	 * @param v1
	 * @return
	 */
	public List<Edge> getIncident(Vertex v1) {
		return v1.incidents;
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
	
	/**
	 * TODO: Kann jedoch noch nicht implementiert werden, aufgrund unserer nicht guter Architektur 
	 * Gibt die Liste aller Edge(Kanten) zurueck
	 * @return
	 */
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
	
	public void setValE(Edge e1, String ü, int val) {
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
		
 		private List<Edge> outgoingdEdge = new ArrayList<>(); //Ausgehende Kanten
 		private List<Edge> ingoingEdge = new ArrayList<>(); //Ausgehende Kanten 	
		
		
		public Vertex(int vertexValue) {
			this.vertexValue = vertexValue;
		}
		
		public void addEdge(Edge edge) {
			incidents.add(edge);
		}
		
		public void addOutgoingEdge(Edge edge) {
			outgoingdEdge.add(edge);
		}
		
		public void addIngoingEdge(Edge edge) {
			ingoingEdge.add(edge);
		}
		
		public int getGrad() {
			return incidents.size();
		}
		
		/**
		 * Gibt die Anzahl des Eingang Grades
		 * @return
		 */
		public int getEntryGrad() {
			return ingoingEdge.size();
		}
		
		/**
		 * Gibt die Anzahl des Ausgang Grades
		 * @return
		 */
		public int getExitGrad() {
			return outgoingdEdge.size();
		}
		
	}
	
	
	/*
	 * TODO: Ich schlage vor, wir implementieren Edge wirklich zwei mal, einmal als gerichtet und einmal als ungerichtet, gerade ist so ein misch masch.
	 * 		 Schlage hier ein Interface vor, was die wichtigsten Methoden vorgibt. 
	 * 		 Dann koennen wir auch alle Edge(Kanten) in eine Liste tuen, ob die jetzt gerichtet oder ungerichtet sind wuerde uns dabei nicht mehr interessieren.
	 * 	     Mit einer einfachen Intanzvariablen koennten wir die Edge(Kante) abfragen was sie ist, wenn wir es brauechen.  
	 * 
	 * 		 ***Oder auch nicht, vielleicht reicht es mit boolean aus, ich weiß es doch auch nicht wie so einige Kommilitonen von uns sagen wuerden*** :D
	 */
		
	private class Edge {
		
		
		private Vertex[] verticesFromEdge = new Vertex[2];
		private String edgeName = "";
		private boolean directed = true;
		
		/**
		 * Erstellt eine Kante
		 * @param v1
		 * @param v2
		 * @param directed Bei true, ist die Kante gerichtet und bei false nicht
		 */
		public Edge(Vertex v1, Vertex v2, boolean directed) {
			this.directed = directed;
			verticesFromEdge[0] = v1;
			verticesFromEdge[1] = v2;
			
			v1.addEdge(this);
			v2.addEdge(this);
			
			if(directed == true) {
				//Am besten waere es noch, damit dann getGrad nicht aufgerufen werden kann, wenn disrected == true
				v1.outgoingdEdge.add(this);
				v2.ingoingEdge.add(this);						
			}			
		}
		
		public boolean getDirected() {
			return directed;
		}
		
		/**
		 * 
		 * @return
		 */
		public Vertex getSource() {
			return verticesFromEdge[0];
		}
		
		/**
		 * 
		 * @return
		 */
		public Vertex getTarget() {
			return verticesFromEdge[1];
		}
		
		private String generateUniqueEdgeName() {
			//TODO: Habe mir so gedacht, dass wir einen Counter machen, der in ein String gespeichert wird, Plus dass der Edge noch einen Namen dazu bekommt. 
			return this.edgeName;
		}
	}
}
