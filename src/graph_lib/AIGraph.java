package graph_lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AIGraph {
	
	//Attribute
	private List<Vertex> verticesList = new ArrayList<>();
	private List<UndirectedEdge> edgesListU = new ArrayList<>();
	private List<DirectedEdge> edgesListD = new ArrayList<>();
	
	//Konstanten
	private final String INFINITE = "∞";
	private final int MAX_VALUE = Integer.MAX_VALUE;
	
	//********************************* KONSTRUKTOR *********************************************
	public AIGraph() {
		//NULL Graph
	}
	
	/**
	 * Berechnet uns den optimalen Weg von Knoten1 nach Knoten2
	 * @param v1_id - ID des Startknotens
	 * @param v2_id - ID des Zielknotens
	 * @return List<Integer> - List der KnotenIDs
	 * Beispiel: v1 nach v2 --> kürzeste weg über: v1 --> v4 --> v3 --> v2
	 */
	public int floyedWarshall(String v1Name, String v2Name) {
		//Index der übergebenen vertices Namen heraus finden
		//Falls nicht vorhanden --> Exception
		Integer v1Index = this.getIndexOfVertexName(v1Name);
		Integer v2Index = this.getIndexOfVertexName(v2Name);
		
		//Distanzmatrix erstellen für den aktuellen "Zustand" des graphen
		Object[][] distanceMatrix = this.createDistanceMatrix();

		//Transitmatrix erstellen
		int[][] transitMatrix = this.createTransitMatrix();
		
		//OUTPUT --> Console
		System.out.println("\n\tFLOYDWARSHALL\n");
		System.out.println("Startwerte:\n");
		this.showDistanceMatrix(distanceMatrix);
		this.showTransitMatrix(transitMatrix);
		
		//Größe der Matrix = | Vertices | 
		int matrixLength = distanceMatrix.length;
		
		//Algorithmus aus GRBUCH
		for(int j = 0; j < matrixLength; j++) {
			
			for(int i = 0; i < matrixLength; i++) {
				//i muss ungleich j sein
				if(i == j) {
					continue;
				}
				for(int k = 0; k < matrixLength; k++) {
					/*Falls Distanzmatrix an Stelle i i < 0 ist wurde ein Kreis
					  negativer länge gefunden */
					//Bestimmen der Zahl an stelle ii
					int elemII = objToInt(distanceMatrix[i][i]); 
					
					if(elemII < 0) {
						throw new IllegalArgumentException("Negativer Kreis wurde entdeckt!");
					}
					
					//k muss ungleich j sein
					if(k == j) {
						continue;
					}
					 
					//Zahlen ermitteln
					int elemIK = objToInt(distanceMatrix[i][k]);
					int elemIJ = objToInt(distanceMatrix[i][j]);
					int elemJK = objToInt(distanceMatrix[j][k]);
					 
					//Speicher für das kleinste Element
					int min;
					 
					//Prüfen ob eins von beiden "Unendlich" ist
					if((elemIJ == MAX_VALUE) || (elemJK == MAX_VALUE)) {
						min = Math.min(elemIK, MAX_VALUE);
					} else {
						min = Math.min(elemIK, (elemIJ + elemJK));
					}
					 
					//Setze elemIK auf min
					//Prüfen ob min = Integer.MAX_VALUE ist --> unedlich
					if(min == MAX_VALUE) {
						distanceMatrix[i][k] = INFINITE;
					} else {
						distanceMatrix[i][k] = min; 
					}
					 
					/*Falls DistanzMatrix an Stelle i k  verändert wurde setze TranzitMatrix 
				      an Stelle i k auf j*/
					if(elemIK != min) {
						transitMatrix[i][k] = j + 1; // hier j + 1, da j bei uns bei 0 anfängt und beim algorithmus bei 1
					}
				}
			}
		}
		
		System.out.println("----------------------------------------------------------------\n");
		System.out.println("Ergebnis:\n");
		this.showDistanceMatrix(distanceMatrix);
		this.showTransitMatrix(transitMatrix);
		
		int resultDistance = objToInt(distanceMatrix[v1Index][v2Index]);
		System.out.println("Der berechnete optimale Weg durch Floys-Warshall von: " + v1Name + " nach: " + v2Name + " hat die Distanz: " + resultDistance);
		return resultDistance;
	}
	
	/**
	 * Berechnet den optimalen (günstigsten) Weg von Knoten 1 nach Knoten 2
	 * @param sID - ID des Startknotens
	 * @param zID - ID des Zielknotens
	 * QUELLE: http://de.wikipedia.org/wiki/Bellman-Ford-Algorithmus
	 */
	public int bellmanFord(String v1Name, String v2Name) {
		//ID der übergebenen vertices Namen heraus finden
		//Falls nicht vorhanden --> Exception
		int v1ID = this.getIDOfVertexName(v1Name);
		int v2ID = this.getIDOfVertexName(v2Name);
		
		//für jeden Knoten aus verticesList --> Distanz(v) = unendlich
		//Key = vID; Value = distanz (= Entfernung vom Startknoten)
		Map<Integer, Integer> distances = new HashMap<>();
		
		//für jeden Knoten aus verticesList --> Vorgänger(v) = keiner
		//Key = vID; Value = vertexID des vorgängers von vID (= KEY)
		Map<Integer, Integer> predecessors = new HashMap<>();
		
		//Anzahl der Knoten im Graph
		int countOfVertices = verticesList.size();
		
		//Maps vorinitialisieren
		for(int i = 0; i < countOfVertices; i++) {
			int vID = verticesList.get(i).getID();
			distances.put(vID, MAX_VALUE);
			predecessors.put(vID, null);
		}
		
		//Distanz von Startknoten auf 0 setzen --> vorheriger Wert wird überschrieben
		distances.put(v1ID, 0);
		
		//OUTPUT --> Console
		System.out.println("\n\tBELLMANFORD\n");
		System.out.println("Startwerte:\n");
		showDistancesMap(distances);
		showPredecessorsMap(predecessors);
		
		//Wiederhole n - 1 mal --> n gleich Anzahl der vertices
		for(int i = 0; i < countOfVertices - 1; i++) {
			//Für jedes (u,v) aus E
			for(Edge e : edgesListD) {
				Vertex[] vertices = e.getVertices();
				Vertex u = vertices[0];
				Vertex v = vertices[1];
				
				//Soll Ergebnis von --> Distanz(u) + Gewicht(u,v) enthalten
				int distance;
				
				//Distanz von Knoten u zum Startknoten herausfinden
				int distanceOfU = distances.get(u.getID());
				
				//prüfen ob Distanz von u "unendlich" ist --> unendlich + unendlich = unendlich
				if(distanceOfU == MAX_VALUE) {
					distance = MAX_VALUE; //repräsentiert unendlich
				} else {
					//Falls nicht unendlich --> Distanz(u) + Gewicht (u,v)
					distance = distanceOfU + e.getValue();
				}
				
				//Distanz von Knoten v zum Startknoten herausfinden
				int distanceOfV = distances.get(v.getID());
				
				//Falls Distanz(u) + Gewicht(u,v) < Distanz(v)
				if(distance < distanceOfV) {
					//Distanz(v) = Distanz(u) + Gewicht(u,v)
					distances.put(v.getID(), distance);
					//Vorgänger(v) = u
					predecessors.put(v.getID(), u.getID());
				}
			}
			
			//Für jedes (u,v) aus E
			for(Edge e : edgesListU) {
				Vertex[] vertices = e.getVertices();
				Vertex u = vertices[0];
				Vertex v = vertices[1];
				
				//Soll Ergebnis von --> Distanz(u) + Gewicht(u,v) enthalten
				int distance;
				
				//Distanz von Knoten u zum Startknoten herausfinden
				int distanceOfU = distances.get(u.getID());
				
				//prüfen ob Distanz von u "unendlich" ist --> unendlich + unendlich = unendlich
				if(distanceOfU == MAX_VALUE) {
					distance = MAX_VALUE; //repräsentiert unendlich
				} else {
					//Falls nicht unendlich --> Distanz(u) + Gewicht (u,v)
					distance = distanceOfU + e.getValue();
				}
				
				//Distanz von Knoten v zum Startknoten herausfinden
				int distanceOfV = distances.get(v.getID());
				
				//Falls Distanz(u) + Gewicht(u,v) < Distanz(v)
				if(distance < distanceOfV) {
					//Distanz(v) = Distanz(u) + Gewicht(u,v)
					distances.put(v.getID(), distance);
					//Vorgänger(v) = u
					predecessors.put(v.getID(), u.getID());
				}
			}
		}
		
		//Prüfung auf negativen Kreis
		//für jede (u,v) aus E
		for(Edge e : edgesListD) {
			Vertex[] vertices = e.getVertices();
			Vertex u = vertices[0];
			Vertex v = vertices[1];
			
			//Soll Ergebnis von --> Distanz(u) + Gewicht(u,v) enthalten
			int distance;
			
			//Distanz von Knoten u zum Startknoten herausfinden
			int distanceOfU = distances.get(u.getID());
			
			//prüfen ob Distanz von u "unendlich" ist --> unendlich + unendlich = unendlich
			if(distanceOfU == MAX_VALUE) {
				distance = MAX_VALUE; //repräsentiert unendlich
			} else {
				//Falls nicht unendlich --> Distanz(u) + Gewicht (u,v)
				distance = distanceOfU + e.getValue();
			}
			
			//Distanz von Knoten v zum Startknoten herausfinden
			int distanceOfV = distances.get(v.getID());
			
			//Falls Distanz(u) + Gewicht(u,v) < Distanz(v) --> STOPP
			if(distance < distanceOfV) {
				throw new IllegalArgumentException("Es gibt einen Kreis negativen Gewichtes!");
			}
		}
		
		//Für jedes (u,v) aus E
		for(Edge e : edgesListU) {
			Vertex[] vertices = e.getVertices();
			Vertex u = vertices[0];
			Vertex v = vertices[1];
			
			//Soll Ergebnis von --> Distanz(u) + Gewicht(u,v) enthalten
			int distance;
			
			//****************RICHTUNG u -- > v**************************************
			
			//Distanz von Knoten u zum Startknoten herausfinden
			int distanceOfU = distances.get(u.getID());
			
			//prüfen ob Distanz von u "unendlich" ist --> unendlich + unendlich = unendlich
			if(distanceOfU == MAX_VALUE) {
				distance = MAX_VALUE; //repräsentiert unendlich
			} else {
				//Falls nicht unendlich --> Distanz(u) + Gewicht (u,v)
				distance = distanceOfU + e.getValue();
			}
			
			//Distanz von Knoten v zum Startknoten herausfinden
			int distanceOfV = distances.get(v.getID());

			//Falls Distanz(u) + Gewicht(u,v) < Distanz(v) --> STOPP
			if(distance < distanceOfV) {
				throw new IllegalArgumentException("Es gibt einen Kreis negativen Gewichtes!");
			}
			
			//**********ANDERE RICHTUNG v --> u DA UNGERICHTET****************************
			
			//Distanz von Knoten u zum Startknoten herausfinden
			distanceOfV = distances.get(v.getID());
			
			//prüfen ob Distanz von u "unendlich" ist --> unendlich + unendlich = unendlich
			if(distanceOfV == MAX_VALUE) {
				distance = MAX_VALUE; //repräsentiert unendlich
			} else {
				//Falls nicht unendlich --> Distanz(u) + Gewicht (u,v)
				distance = distanceOfV + e.getValue();
			}
			
			//Distanz von Knoten v zum Startknoten herausfinden
			distanceOfU = distances.get(u.getID());

			//Falls Distanz(u) + Gewicht(u,v) < Distanz(v) --> STOPP
			if(distance < distanceOfU) {
				throw new IllegalArgumentException("Es gibt einen Kreis negativen Gewichtes!");
			}
		}
		
		System.out.println("----------------------------------------------------------------\n");
		System.out.println("Ergebnis:\n");
		showDistancesMap(distances);
		showPredecessorsMap(predecessors);
		
		int resultDistance = distances.get(v2ID);
		System.out.println("Der berechnete optimale Weg durch Bellman Ford von: " + v1Name + " nach: " + v2Name + " hat die Distanz: " + resultDistance);
		return resultDistance;
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
		//Beinhaltet schon die Precondition
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
			DirectedEdge edgeD = getEdgeD(e1);
			
			if(edgeD.hatQuelle()) {
				Vertex[] verticesFromEdge = edgeD.getVertices();
				vertex = verticesFromEdge[0]; //StartKnoten / Quelle
			}
		}
		
		//Prüfen ob es ein ungerichteter Graph ist
		if(e1 % 2 != 0) {
			Vertex[] verticesFromEdge = ((UndirectedEdge) getEdgeU(e1)).getVertices();
			vertex = verticesFromEdge[0]; //Die Linke Ecke
		}	

		if(vertex == null) {
			throw new IllegalArgumentException("Zur Übergebenen EdgeID existiert keine Quelle!");
		} else {
			return vertex.getID();
		}
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
			if(((DirectedEdge) getEdgeD(e1)).hatSenke()) {
				Vertex[] verticesFromEdge = ((DirectedEdge) getEdgeD(e1)).getVertices();
				vertex = verticesFromEdge[1]; //EndKnoten / Ziel
			}
		}
		
		//Prüfen ob es ein ungerichteter Graph ist
		if(e1 % 2 != 0) {
			Vertex[] verticesFromEdge = ((UndirectedEdge) getEdgeU(e1)).getVertices();
			vertex = verticesFromEdge[1]; //Die rechte Ecke
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
	 * @return
	 */
	public List<String> getAttrV(int v1) {
		return getVertex(v1).getAttrList();
	}
	
	 /**
	  * Ermittelt alle Attribute des Edge(Kante) e1
	  * @param e1
	  * @return
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
	
	private Edge getEdgeBetweenVertices(Vertex v1, Vertex v2) {
		for(Edge edge : edgesListD) {
			Vertex[] verticesFromEdge = ((DirectedEdge) edge).getVertices();
			
			if((verticesFromEdge[0] == v1) && (verticesFromEdge[1] == v2)) { //Nur eine Richtung prüfen
				return edge;
			}
		}
		
		for(Edge edge : edgesListU) {
			Vertex[] verticesFromEdge = ((UndirectedEdge) edge).getVertices();
			
			if((verticesFromEdge[0] == v1) && (verticesFromEdge[1] == v2)) { //Nur eine Richtung prüfen
				return edge;
			}
			
			if((verticesFromEdge[0] == v2) && (verticesFromEdge[1] == v1)) { //Nur eine Richtung prüfen
				return edge;
			}
		}
		//throw new IllegalArgumentException("Es existiert keine Kante zwischen v1 und v2!");
		return null;
	}
	
	/**
	 * Wandelt ein Object mit Inhalt Zahl oder Infinite zu einem int um 
	 * @param elem
	 * @return
	 */
	private Integer objToInt(Object elem) {
		int number;
		 if(elem == INFINITE) {
			 number = MAX_VALUE;
		 } else {
			 number = (Integer) elem;
		 }
		return number;
	}
	
	/**
	 * Gibt uns die Distanzmatrix zu dem aufrufenden Graphen
	 * @return int[][] - Distanzmatrix des aufrufenden Graphen
	 */
	private Object[][] createDistanceMatrix() {
		//Singelpoint of Control --> ermitteln der Matrix größe
		//Da Quadratisch --> Zeilenanzahl = Spaltenanzahl
		int matrixLength = verticesList.size();
		int zero = 0;
		
		//Ergebnis Matrix --> Distanzmatrix des aufrufenden graphen
		Object[][] result = new Object[matrixLength][matrixLength];
		
		//Für alle Zeilen
		for(int i = 0; i < matrixLength; i++) {
			
			//Für alle vertices durchlaufen
			Vertex v1 = verticesList.get(i);
			
			//Für alle Spalten
			for(int j = 0; j < matrixLength; j++) {
				
				//für jeden v1 alle vertices durchlaufen
				Vertex v2 = verticesList.get(j);
				
				//Die selben Knoten
				if(v1 == v2) {
					result[i][j] = zero;
					continue;
				}
				
				//Kante herausfinden um anschließend die Distanz (= value) zu bestimmen
				Edge e = this.getEdgeBetweenVertices(v1, v2);

				//Falls keine Direkte kannte zwischen den vertices besteht
				if(e == null) {
					result[i][j] = INFINITE;
					continue;
				}				
				result[i][j] = e.getAttr("value");
			}
		}			
		return result;
	}
	
	/**
	 * Stellt die DistanzMatrix in der Konsole da
	 */
	private void showDistanceMatrix(Object[][] distanceMatrix) {
		int matrixLength = distanceMatrix.length;
		String showDistanceMatrix = "---DistanceMatrix---\n";		
 		showDistanceMatrix += "Name ";
 		//showDistanceMatrix += "----------------\n";
 		for(Vertex v : verticesList) {
 			showDistanceMatrix += v.getName();
 			showDistanceMatrix += " ";
 		}
		
 		showDistanceMatrix += "\n";
 		
		for(int i = 0; i < matrixLength; i++) {
			showDistanceMatrix += verticesList.get(i).getName();
			//Falls einstellige Zahl --> extra Leerzeichen anfügen
			if(verticesList.get(i).getID() < 10) {
				showDistanceMatrix += " ";
			}
			showDistanceMatrix += " | ";
			for(int j = 0; j < matrixLength; j++) {
				showDistanceMatrix += distanceMatrix[i][j];
				//int zahl = objToInt(distanceMatrix[i][j]);
				if(verticesList.get(i).getID() > 9) {
					showDistanceMatrix += " ";
				}
				showDistanceMatrix += " ";
			}
			showDistanceMatrix += "| ";
			showDistanceMatrix += "\n";
		}
		System.out.println(showDistanceMatrix);
	}
	
	/**
	 * Gibt eine neue (=leere) TransitMatrix
	 * @return int[][] - eine mit Nullen initialisierte Transitmatrix
	 */
	private int[][] createTransitMatrix() {
		//Singelpoint of Control --> ermitteln der Matrix größe
		//Da Quadratisch --> Zeilenanzahl = Spaltenanzahl
		int matrixLength = verticesList.size();
		int zero = 0;
		
		//Ergebnis Matrix --> Transitmatrix befüllt mit Nullen
		int[][] result = new int[matrixLength][matrixLength];
		
		//Für alle Zeilen
		for(int i = 0; i < matrixLength; i++) {
			//Für alle Spalten
			for(int j = 0; j < matrixLength; j++) {
				//Alle Stellen mit 0 initialisieren
				result[i][j] = zero;
			}
		}
		return result;
	}

	
	/**
	 * Stellt die Transitmatrix in der Konsole da
	 */
	private void showTransitMatrix(int[][] transitMatrix) {
		int matrixLength = transitMatrix.length;
		String showTransitMatrix = "---TransitMatrix---\n";
 		showTransitMatrix += "N   ";
 		
 		for(Vertex v : verticesList) {
 			showTransitMatrix += v.getName();
 			showTransitMatrix += "  ";
 		}
 		
 		showTransitMatrix += "\n";
 		
		for(int i = 0; i < matrixLength; i++) {
			showTransitMatrix += verticesList.get(i).getName();
			if(verticesList.get(i).getID() < 10) {
				showTransitMatrix += " ";
			}
			showTransitMatrix += " | ";
			for(int j = 0; j < matrixLength; j++) {
				showTransitMatrix += transitMatrix[i][j];
				showTransitMatrix += " ";
				if(verticesList.get(i).getID() > 10) {
					showTransitMatrix += " ";
				}
			}
			showTransitMatrix += "| ";
			showTransitMatrix += "\n";
		}
		System.out.println(showTransitMatrix);
	}
	
	/**
	 * Stellt die DistanzMap in der Konsole da
	 */
	private void showDistancesMap(Map<Integer, Integer> distances) {
		int count = distances.size() - 1;
		String output = "---Distancesmap---\n"; 
		output += "{ ";
		for(Map.Entry<Integer, Integer> pair : distances.entrySet()) {
			String nameOfVertex = this.getStrV(pair.getKey(), "name");
			int distanceOfVertex = pair.getValue();
			output += nameOfVertex;
			output += " => ";
			if(distanceOfVertex == MAX_VALUE) {
				output += INFINITE;
			} else {
				output += distanceOfVertex;
			}
			if(count != 0) {
				output += "\n  ";
			}
			count--;
		}
		output += " }";
		System.out.println(output + "\n");
	}
	
	/**
	 * Stellt die VorgängerMap in der Konsole da
	 */
	private void showPredecessorsMap(Map<Integer, Integer> predecessors) {
		int count = predecessors.size() - 1;
		String output = "---Predecessorsmap---\n";
		output += "{ ";
		for(Map.Entry<Integer, Integer> pair : predecessors.entrySet()) {
			String nameOfVertex = this.getStrV(pair.getKey(), "name");
			String nameOfPredecessor;
			if(pair.getValue() == null) {
				nameOfPredecessor = "null";
			} else {
				nameOfPredecessor = this.getStrV(pair.getValue(), "name");
			}
			output += nameOfVertex;
			output += " => ";
			output += nameOfPredecessor;
			if(count != 0) {
				output += "\n  ";
			}
			count--;
		}
		output += " }";
		System.out.println(output + "\n");
	}
	
	/**
	 * Sucht den Index zu dem übergebenen Vertexnamen
	 * @param vName - Name des Vertices
	 * @return int - Index in der Distanzmatrix
	 */
	private int getIndexOfVertexName(String vName) {
		for(Vertex v : verticesList) {
			if(v.getName() == vName) {
				return verticesList.indexOf(v);
			}
		}
		throw new IllegalArgumentException("In diesem Graphen existiert kein Vertex mit dem Namen: " + vName);
	}
	
	/**
	 * Sucht die ID zu dem übergebenen Vertexnamen
	 * @param vName - Name des Vertices
	 * @return int - ID des Vertices
	 */
	private int getIDOfVertexName(String vName) {
		for(Vertex v : verticesList) {
			if(v.getName() == vName) {
				return v.getID();
			}
		}
		throw new IllegalArgumentException("In diesem Graphen existiert kein Vertex mit dem Namen: " + vName);
	}
}
