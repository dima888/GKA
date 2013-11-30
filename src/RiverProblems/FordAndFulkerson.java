/* 
 * Flußproblem beschränkung auf zusammenhängenden, schlichten gerichteten Graphen G(V, E)
 * Längs der Kanten wird ein "GUT" transportiert (Container, Strom, etc.)
 * 
 * Für jede Kante eij zwischen Knoten vi und Knoten vj, gibt es die Kapazität c(eij) = Kapazität cij
 * Diese Kapazität gibt an, welche Menge des "GUTES" entlang einer Kante, von ihrem Anfangsknoten
 * zur ihrem Endknoten transportiert werden kann
 * 
 * Alle cij (Kantenkapazitäten) sind (aus praktischen Gründen) rationale Zahlen
 * Diese Art von Grpahen werden in der Literatur auch oft als "Netzwerk" bezeichnet
 * 
 * Glossar:
 * vi = Knoten vi
 * vj = Knoten vj
 * eij = Kante zwischen vi und vj
 * c(eij) || cij = Kantenkapazität
 * O(vi) = Output vom Knoten vi
 * I(vi) = Input vom Knoten vi
 * s(eij) = Startknoten der Kante eij
 * t(eij) = Zielknoten der Kante eij
 * d+(vi) = Ausgangsgrad vom Knoten vi
 * d-(vi) = Eingangsgrad vom Knoten vi
 * d(vi) = Eckengrad vom Knoten vi
 * q = Quelle (ein Knoten mit d-(vi) == 0) im Graphen
 * s = Senke (ein Knoten mit d+(vi) == 0) im Graphen
 * f(eij) = gibt den tatsächlichen Fluß der Kante eij an
 * 
 * Für jede Kante eij gilt --> f(eij) <= c(eij) (KAPAZITÄTSBESCHRÄNKUNG)
 * 
 * Es gibt nur genau eine QUELLE und SENKE pro Graph
 */
package RiverProblems;
import java.util.ArrayList;
import java.util.List;
import graph_lib.AIGraph;
import java.util.Collections;
/*
 * Der Algorithmus von Ford und Fulkerson IM GRBUCH SEITE 98
 */
public class FordAndFulkerson {
		
	//*****************INSTANZVARIABLEN*******************
	private AIGraph graph;
	private static final int UNDEFINE = -1;
	private static final int INFINITE = Integer.MAX_VALUE;
	private static final String INSPECTED = "*";
	private static final String EMPTY = "empty";
	private int access = 0; //Zugriff
	private int optimalRiver = 0; //Der optimale Fluss
	
	//*************************GETTER**********************
	/**
	 * Gibt die Anzahl der Zugriffe, die auf den Graph erfolgt wurden
	 * @return Integer
	 */
	public int getAccess() {
		return this.access;
	}
	
	/**
	 * Gibt uns den Optimalen Fluss an
	 */
	public int getOptiomalRiver() {
		return this.optimalRiver;
	}
	
	/**
	 * Such nach maximaler(optimaler) Flusssträke, größte Anzahl an "Paketen"
	 * Den Graphen muessen die Start und Ziel von Hand richtig gesetzt werden, 
	 * sonst kann es zu falschen Ergebnissen fuehren
	 * @param AIGraph graph - Der Graph 
	 * @param Integer source - Start Knoten
	 * @param Integer target - Ziel Knoten
	 * @return AIGraph 
	 */
	public AIGraph startAlgorithmus(AIGraph graph, int source, int target) {
		List<Integer> edgeIDList = new ArrayList<>();
		Collections.shuffle(edgeIDList); //wuerfelt den Array durch einander
		
		//Den Graph setzten, damit die privaten Methode wie setMarked auf den selben Objekt arbeiten
		this.graph = graph;
		
		//Erster Schritt, f(e ij); Ist bei uns schon fertig, da wir den tatsaechlichen Fluss schon mit 0 initialisieren
		
		//Pruefen ob in den Graphen Source und Target vorhanden ist, wenn nicht, dann mit einer Exception die Methode beenden
		//Habe das weggelassen, wurde mir doch zu kompliziert, wenn die Austauschbarkeit noch erhalten bleiben soll
		
		//Start Knoten Markieren und inspizieren
		setMarked(source, UNDEFINE, INFINITE);
		setInspected(source);
		
		//Algorithmus arbeitet intensiv in dieser Phase
		boolean isRunning = true;
		while (isRunning) {
			/*
			 * --------------------------------------ABBRUCHBEDINGUNG---------------------------------------------
			 * Bei bedarf dieses Code teil in eine Methode auslagern
			 * ABBRUCHBEDINGUNG der intensiven Arbeitsphase des Algorithmus. (Laut GRBuch 2a)
			 * Schritt 1) Laufen ueber alle Knoten und sammeln erstmal die markierten ein.
			 * Schritt 2) Dann laufen wir ueber die markierten und pruefen ob sie alle inpiziert sind,
			 * wenn ja, dann wird isRunning auf false gesetzt
			 */
			List<Integer> markedVertexIDList = new ArrayList<>();
			//Schritt 1:
			for (int vertexID : graph.getVertexes()) {				
				if (isMarked(vertexID)) {
					markedVertexIDList.add(vertexID);
				}
			}
			
			//Schritt 2:
			for (int vertexID : markedVertexIDList) {
				if (!isInspected(vertexID)) {
					isRunning = true;
					break;
				}
				isRunning = false;
			}
			//--------------------------------------ENDE VON ABBRUCHBEDINGUNG---------------------------------------------		
			
			/*
			 * --------------------------------------DAS MARKIEREN--------------------------------------
			 * Bei bedarf dieses Code teil in eine Methode auslagern
			 */			
			//Hier hollen wir uns alle Kanten die an den inspizierten Knoten haengen
			List<Integer> edgesFromCurrentVertex = graph.getIncident(source);
			
			/*
			 * Jetzt koennen wir bei den Knoten die Tupelwerte(markieren).
			 * Muessen hier auch in die entgegengesetzte Richtung schauen!
			 * Example: Vertex --------> Vertex || Vertex <------- Vertex 
			 */
			for (int edgeID : edgesFromCurrentVertex) {
				//erst gucken wir uns die Knoten an, die weg von inpizierten Knoten gehen
				int vertexID = graph.getTarget(edgeID);
				if (!isMarked(vertexID)) {
					//Hier muss erstmal das Delta berechnet werden
					
					//Kapazitaet der Kante hollen
					int currentCapacity = getCapacity(vertexID);
					
					//Delta von der inspizierten Kante hollen
					int inspectedDelta = getDelta(source);
					
					//Jetzt findet die Pruefungen statt
					if (inspectedDelta == INFINITE) {						
						setMarked(vertexID, source, currentCapacity);
						continue;
					} else {
						if (currentCapacity > inspectedDelta) {
							setMarked(vertexID, source, inspectedDelta);
							continue;
						} else {
							setMarked(vertexID, source, currentCapacity);
							continue;
						}
					}									
				}
				
				//jetzt gucken wir uns die Knoten an, ob sie in Richtung des inzipierten Knoten gehen
				vertexID = graph.getSource(edgeID);
				if (!isMarked(vertexID)) {
					//Hier muss erstmal das Delta berechnet werden
					
					//Kapazitaet der Kante hollen
					int currentCapacity = getCapacity(vertexID);
					
					//Delta von der inspizierten Kante hollen
					int inspectedDelta = getDelta(source);
					
					//Jetzt findet die Pruefungen statt
					if (inspectedDelta == INFINITE) {						
						setMarked(vertexID, source, currentCapacity);
						continue;
					} else {
						if (currentCapacity > inspectedDelta) {
							setMarked(vertexID, source, inspectedDelta);
							continue;
						} else {
							setMarked(vertexID, source, currentCapacity);
							continue;
						}
					}
				}
			}			
			/*
			 * --------------------------------------DAS ENDE VON MARKIEREN--------------------------------------
			 */

			
			/*
			 * TODO: 
			 * Gucken ob wir die Codestuecke da rueber in Methoden verlagern koennen, 
			 * damit wir sie testen koennen, dies sehe ich als wichtig, 
			 * da logik nicht gerade auf ersten blick ueberschau bar ist. 
			 * Jetzt nehmen wir eine beliebige Kante und inpezieren sie!
			 */
		}
		

		/*
		 * Schritt 4 Es gibt keinen vergrößernden Weg. Der jetzige Wert von d
		 * ist optimal. Ein Schnitt A(X,X) mit c(X,X) = d wird gebildet von
		 * genau denjenigen Kanten, bei denen entweder die Anfangsecke oder die
		 * Endecke inspiziert ist.
		 */
		
		
		return graph;
	}
	
	
	/**
	 * Hollt von einem Knoten den Deltawert
	 * @param Integer currentID - Eine ID, womit wir auf einen Knoten zugreifen koennen
	 * @return Integer
	 */
	private int getDelta(int currentID) {
		return graph.getValV(currentID, "delta");
	}

	/**
	 * Diese Methode repräsentiert einen Tuppel der Markierung durch die unten definierten Attribute
	 * @param Integer currentID - Aktuelle ID von Vertex, den die Werte gesetzt werden sollen
	 * @param Integer predecessorID - Die ID des Vorgaenger Knoten
	 * @param Integer delta - die minimalste Kapazitaet auf den Weg
	 */
	private void setMarked(int currentID, int predecessorID, int delta) {
		this.graph.setValV(currentID, "predecessorID", predecessorID);
		this.graph.setValV(currentID, "delta", delta);
	}
	
	/**
	 * Inspiziert eine Ecker eines Graphes
	 * @param Integer currentID - Eine ID von einer/einem {Ecke, Knoten, Vertex}
	 */
	private void setInspected(Integer currentID) {
		this.graph.setStrV(currentID, "inspected", INSPECTED);
	}
	
	/**
	 * Entfernt in einer/einem {Ecke, Knoten, Vertex} eines Graphes die Markierung
	 * @param Integer currentID - Die ID von der die Markierung entfernt wird
	 */
	private void deleteMarked(Integer currentID) {
		this.graph.setValV(currentID, "predecessorID", UNDEFINE);
		this.graph.setValV(currentID, "delta", UNDEFINE);
	}
	
	/**
	 * Entfernt in einer/einem {Ecke, Knoten, Vertex} eines Graphes die Inspizierung
	 * @param Integer currentID - Die ID von der/dem {Ecke, Knoten, Vertex} aud der die Inspektion entfernt wird 
	 */
	private void deleteInspected(Integer currentID) {
		this.graph.setStrV(currentID, "inspected", EMPTY);
	}
	
	/**
	 * Methode prueft ob {Ecke, Knoten, Vertex} markiert ist, bei true markiert bei false unmarkiert
	 * @param Integer currentID - ID der/des {Ecke, Knoten, Vertex} was geprueft wird
	 * @return Boolean
	 */
	private boolean isMarked(Integer currentID) {
		if (graph.getValV(currentID, "predecessorID") == -1) {
			return false;
		}
		return true;
	}
	
	/**
	 * Methode Prueft ob {Ecke, Knoten, Vertex} inspeziert ist oder nicht, bei inspeziert liefert 
	 * die Methode true zurueck und bei nicht inspeziert false
	 * @param Integer currentID - ID der/des {Ecke, Knoten, Vertex} was geprueft wird
	 * @return
	 */
	private boolean isInspected(Integer currentID) {
		if (graph.getStrV(currentID, "inspected").compareTo(EMPTY) == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Gibt den Tupel von Kapazitaet und tatsaechlichen Fluss in einem Array zurueck.
	 * 0-Stelle im Array steht fuer die Kapaziaet
	 * 1-Stelle im Array steht fuer den tatsaechlichen Fluss
	 * @param Integer currentID - Die ID der Kante, von wo wir die Informationen Beziehen
	 * @return Array[2]
	 */
	private int[] getCapacityActualRiverTuple(int currentID) {
		int[] result = new int[2];
		result[0] = graph.getValE(currentID, "capacity"); 
		result[1] = graph.getValE(currentID, "actualRiver");
		return result;
	}
	
	/**
	 * Hollt von einem Knoten seine Kapazitaet
	 * @param Integer currentID - ID von einem Knoten, damit wir auf den zugreifen koennen
	 * @return Integer
	 */
	private int getCapacity(int currentID) {
		return graph.getValE(currentID, "capacity"); 
	}
	
	/**
	 * Mit der Methode kann der tatsaechliche Fluss gesetzt werden.
	 * @param Integer currentID - Die ID zu den gehoeriger Kante
	 * @param Integer actualRiverValue - Der tatsaechliche Fluss wird mit diesen Parameter gesetzt
	 */
	private void setActualRiver(int currentID, int actualRiverValue) {
		graph.setValE(currentID, "actualRiver", actualRiverValue);
	}
	
	public static void main(String[] args) {
		
	}
}

