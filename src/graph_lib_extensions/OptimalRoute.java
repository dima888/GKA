package graph_lib_extensions;

import graph_lib.AIGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimalRoute {
	
	//Konstanten
	private static final String INFINITE = "∞";
	private static final int MAX_VALUE = Integer.MAX_VALUE;
	
	/**
	 * Berechnet uns den optimalen Weg von Knoten1 nach Knoten2
	 * @param v1_id - ID des Startknotens
	 * @param v2_id - ID des Zielknotens
	 * @return List<Integer> - List der KnotenIDs
	 * Beispiel: v1 nach v2 --> kürzeste weg über: v1 --> v4 --> v3 --> v2
	 */
	public static int floyedWarshall(AIGraph graph, String v1Name, String v2Name) {
		//Index der übergebenen vertices Namen heraus finden
		//Falls nicht vorhanden --> Exception
		Integer v1Index = OptimalRoute.getIndexOfVertexName(graph, v1Name);
		Integer v2Index = OptimalRoute.getIndexOfVertexName(graph, v2Name);
		
		//Distanzmatrix erstellen für den aktuellen "Zustand" des graphen
		Object[][] distanceMatrix = OptimalRoute.createDistanceMatrix(graph);

		//Transitmatrix erstellen
		int[][] transitMatrix = OptimalRoute.createTransitMatrix(graph);
		
		//OUTPUT --> Console
		System.out.println("\n\tFLOYDWARSHALL\n");
		System.out.println("Startwerte:\n");
		OptimalRoute.showDistanceMatrix(graph, distanceMatrix);
		OptimalRoute.showTransitMatrix(graph, transitMatrix);
		
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
		OptimalRoute.showDistanceMatrix(graph, distanceMatrix);
		OptimalRoute.showTransitMatrix(graph, transitMatrix);
		
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
	public static int bellmanFord(AIGraph graph, String v1Name, String v2Name) {
		//ID der übergebenen vertices Namen heraus finden
		//Falls nicht vorhanden --> Exception
		int v1ID = OptimalRoute.getIDOfVertexName(graph, v1Name);
		int v2ID = OptimalRoute.getIDOfVertexName(graph, v2Name);
		
		//für jeden Knoten aus verticesList --> Distanz(v) = unendlich
		//Key = vID; Value = distanz (= Entfernung vom Startknoten)
		Map<Integer, Integer> distances = new HashMap<>();
		
		//für jeden Knoten aus verticesList --> Vorgänger(v) = keiner
		//Key = vID; Value = vertexID des vorgängers von vID (= KEY)
		Map<Integer, Integer> predecessors = new HashMap<>();
		
		//Liste der vertices holen
		List<Integer> verticesList = graph.getVertexes();
		
		//Anzahl der Knoten im Graph
		int countOfVertices = verticesList.size();
		
		//Maps vorinitialisieren
		for(int i = 0; i < countOfVertices; i++) {
			int vID = verticesList.get(i);
			distances.put(vID, MAX_VALUE);
			predecessors.put(vID, null);
		}
		
		//Distanz von Startknoten auf 0 setzen --> vorheriger Wert wird überschrieben
		distances.put(v1ID, 0);
		
		//OUTPUT --> Console
		System.out.println("\n\tBELLMANFORD\n");
		System.out.println("Startwerte:\n");
		showDistancesMap(graph, distances);
		showPredecessorsMap(graph, predecessors);
		
		//Edges List beschaffen
		List<Integer> edgesList = graph.getEdges();
		
		//Wiederhole n - 1 mal --> n gleich Anzahl der vertices
		for(int i = 0; i < countOfVertices - 1; i++) {
			
			//Für jedes (u,v) aus E
			for(Integer eID : edgesList) {
				int[] vertices = graph.getVerticesIDs(eID);
				int u = vertices[0];
				int v = vertices[1];
				
				//Soll Ergebnis von --> Distanz(u) + Gewicht(u,v) enthalten
				int distance;
				
				//Distanz von Knoten u zum Startknoten herausfinden
				int distanceOfU = distances.get(u);
				
				//prüfen ob Distanz von u "unendlich" ist --> unendlich + unendlich = unendlich
				if(distanceOfU == MAX_VALUE) {
					distance = MAX_VALUE; //repräsentiert unendlich
				} else {
					//Falls nicht unendlich --> Distanz(u) + Gewicht (u,v)
					distance = distanceOfU + Integer.parseInt(graph.getStrE(eID, "value"));
				}
				
				//Distanz von Knoten v zum Startknoten herausfinden
				int distanceOfV = distances.get(v);
				
				//Falls Distanz(u) + Gewicht(u,v) < Distanz(v)
				if(distance < distanceOfV) {
					//Distanz(v) = Distanz(u) + Gewicht(u,v)
					distances.put(v, distance);
					//Vorgänger(v) = u
					predecessors.put(v, u);
				}
			}
		}
		
		//Prüfung auf negativen Kreis
		//für jede (u,v) aus E
		for(Integer eID : edgesList) {
			int[] vertices = graph.getVerticesIDs(eID);
			int u = vertices[0];
			int v = vertices[1];
			
			//Soll Ergebnis von --> Distanz(u) + Gewicht(u,v) enthalten
			int distance;
			
			//Distanz von Knoten u zum Startknoten herausfinden
			int distanceOfU = distances.get(u);
			
			//prüfen ob Distanz von u "unendlich" ist --> unendlich + unendlich = unendlich
			if(distanceOfU == MAX_VALUE) {
				distance = MAX_VALUE; //repräsentiert unendlich
			} else {
				//Falls nicht unendlich --> Distanz(u) + Gewicht (u,v)
				distance = distanceOfU + Integer.parseInt(graph.getStrE(eID, "value"));
			}
			
			//Distanz von Knoten v zum Startknoten herausfinden
			int distanceOfV = distances.get(v);
			
			//Falls Distanz(u) + Gewicht(u,v) < Distanz(v) --> STOPP
			if(distance < distanceOfV) {
				throw new IllegalArgumentException("Es gibt einen Kreis negativen Gewichtes!");
			}
			
			//Für ungerichtete Kanten noch eine Prüfung in die andere Richtung
			if(eID % 2 != 0) {				
				//Distanz von Knoten u zum Startknoten herausfinden
				distanceOfV = distances.get(v);
				
				//prüfen ob Distanz von u "unendlich" ist --> unendlich + unendlich = unendlich
				if(distanceOfV == MAX_VALUE) {
					distance = MAX_VALUE; //repräsentiert unendlich
				} else {
					//Falls nicht unendlich --> Distanz(u) + Gewicht (u,v)
					distance = distanceOfV + Integer.parseInt(graph.getStrE(eID, "value"));
				}
				
				//Distanz von Knoten v zum Startknoten herausfinden
				distanceOfU = distances.get(u);
				
				//Falls Distanz(u) + Gewicht(u,v) < Distanz(v) --> STOPP
				if(distance < distanceOfV) {
					throw new IllegalArgumentException("Es gibt einen Kreis negativen Gewichtes!");
				}
			}
		}
		
		System.out.println("----------------------------------------------------------------\n");
		System.out.println("Ergebnis:\n");
		OptimalRoute.showDistancesMap(graph, distances);
		OptimalRoute.showPredecessorsMap(graph, predecessors);
		
		int resultDistance = distances.get(v2ID);
		System.out.println("Der berechnete optimale Weg durch Bellman Ford von: " + v1Name + " nach: " + v2Name + " hat die Distanz: " + resultDistance);
		return resultDistance;
	}
	
	//******************************** PRIVATE METHODEN *********************************************************
	
	/**
	 * Wandelt ein Object mit Inhalt Zahl oder Infinite zu einem int um 
	 * @param elem
	 * @return
	 */
	private static Integer objToInt(Object elem) {
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
	private static Object[][] createDistanceMatrix(AIGraph graph) {
		//VerticesIds List
		List<Integer> verticesList = graph.getVertexes();
		//Singelpoint of Control --> ermitteln der Matrix größe
		//Da Quadratisch --> Zeilenanzahl = Spaltenanzahl
		int matrixLength = verticesList.size();
		int zero = 0;
		
		//Ergebnis Matrix --> Distanzmatrix des aufrufenden graphen
		Object[][] result = new Object[matrixLength][matrixLength];
		
		//Für alle Zeilen
		for(int i = 0; i < matrixLength; i++) {
			
			//Für alle vertices durchlaufen
			int v1 = verticesList.get(i);
			
			//Für alle Spalten
			for(int j = 0; j < matrixLength; j++) {
				
				//für jeden v1 alle vertices durchlaufen
				int v2 = verticesList.get(j);
				
				//Die selben Knoten
				if(v1 == v2) {
					result[i][j] = zero;
					continue;
				}
				
				//Kante herausfinden um anschließend die Distanz (= value) zu bestimmen
				Integer eID = graph.getEdgeBetweenVertices(v1, v2);

				//Falls keine Direkte kannte zwischen den vertices besteht
				if(eID == null) {
					result[i][j] = INFINITE;
					continue;
				}				
				result[i][j] = Integer.parseInt(graph.getStrE(eID, "value"));
			}
		}			
		return result;
	}
	
	/**
	 * Stellt die DistanzMatrix in der Konsole da
	 */
	private static void showDistanceMatrix(AIGraph graph, Object[][] distanceMatrix) {
		//VerticesIds List
		List<Integer> verticesList = graph.getVertexes();
		int matrixLength = distanceMatrix.length;
		String showDistanceMatrix = "---DistanceMatrix---\n";		
 		showDistanceMatrix += "Name\n";
 		//showDistanceMatrix += "----------------\n";
// 		for(Vertex v : verticesList) {
// 			showDistanceMatrix += v.getName();
// 			showDistanceMatrix += " ";
// 		}
//		
// 		showDistanceMatrix += "\n";
 		
		for(int i = 0; i < matrixLength; i++) {
			showDistanceMatrix += graph.getStrV(verticesList.get(i), "name");
			//Falls einstellige Zahl --> extra Leerzeichen anfügen
			if(verticesList.get(i) < 10) {
				showDistanceMatrix += " ";
			}
			showDistanceMatrix += " | ";
			for(int j = 0; j < matrixLength; j++) {
				showDistanceMatrix += distanceMatrix[i][j];
				//int zahl = objToInt(distanceMatrix[i][j]);
				if(verticesList.get(i) > 9) {
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
	private static int[][] createTransitMatrix(AIGraph graph) {
		//VerticesIds List
		List<Integer> verticesList = graph.getVertexes();
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
	private static void showTransitMatrix(AIGraph graph, int[][] transitMatrix) {
		//VerticesIds List
		List<Integer> verticesList = graph.getVertexes();
		int matrixLength = transitMatrix.length;
		String showTransitMatrix = "---TransitMatrix---\n";
 		showTransitMatrix += "Name\n";
 		
// 		for(Vertex v : verticesList) {
// 			showTransitMatrix += v.getName();
// 			showTransitMatrix += "  ";
// 		}
// 		
// 		showTransitMatrix += "\n";
 		
		for(int i = 0; i < matrixLength; i++) {
			showTransitMatrix += graph.getStrV(verticesList.get(i), "name");
			if(verticesList.get(i) < 10) {
				showTransitMatrix += " ";
			}
			showTransitMatrix += " | ";
			for(int j = 0; j < matrixLength; j++) {
				showTransitMatrix += transitMatrix[i][j];
				showTransitMatrix += " ";
				if(verticesList.get(i) > 10) {
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
	private static void showDistancesMap(AIGraph graph, Map<Integer, Integer> distances) {
		int count = distances.size() - 1;
		String output = "---Distancesmap---\n"; 
		output += "{ ";
		for(Map.Entry<Integer, Integer> pair : distances.entrySet()) {
			String nameOfVertex = graph.getStrV(pair.getKey(), "name");
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
	private static void showPredecessorsMap(AIGraph graph, Map<Integer, Integer> predecessors) {
		int count = predecessors.size() - 1;
		String output = "---Predecessorsmap---\n";
		output += "{ ";
		for(Map.Entry<Integer, Integer> pair : predecessors.entrySet()) {
			String nameOfVertex = graph.getStrV(pair.getKey(), "name");
			String nameOfPredecessor;
			if(pair.getValue() == null) {
				nameOfPredecessor = "null";
			} else {
				nameOfPredecessor = graph.getStrV(pair.getValue(), "name");
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
	private static int getIndexOfVertexName(AIGraph graph, String vName) {
		int idOfVertexName = OptimalRoute.getIDOfVertexName(graph, vName);
		List<Integer> verticesIDs = graph.getVertexes();
		return verticesIDs.indexOf(idOfVertexName);
	}
	
	/**
	 * Sucht die ID zu dem übergebenen Vertexnamen
	 * @param vName - Name des Vertices
	 * @return int - ID des Vertices
	 */
	private static int getIDOfVertexName(AIGraph graph, String vName) {
		List<Integer> verticesIDs = graph.getVertexes();
		for(Integer vID : verticesIDs) {
			if(graph.getStrV(vID, "name").equals(vName)) {
				return vID;
			}
		}
		throw new IllegalArgumentException("In diesem Graphen existiert kein Vertex mit dem Namen: " + vName);
	}	
}