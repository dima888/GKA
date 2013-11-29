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
			 * -----------------------------------------------------------------------------------
			 * TODO: Muss vielleicht nach unten gepackt werden, muss ich noch schauen
			 * Abbruchbedingung der intensiven Arbeitsphase des Algorithmus.
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
			//-----------------------------------------------------------------------------------			
			
			//Hier hollen wir uns alle Knoten die mit den inspizierten Knoten verbunden sind
			List<Integer> adjacentVertexIDList = new ArrayList<>();
			adjacentVertexIDList = graph.getAdjacent(source);
			
			//Auf den Weg den minimalste Kapazitaet ermitteln (delta)
			
			//TODO: Hier bin ich stehen geblieben Das Delta ermitteln
			//Jetzt markieren wir die Knoten
			for (int vertexID : adjacentVertexIDList) {
				//setMarked(vertexID, source, delta);
			}
			
			//Jetzt entscheiden wir uns fuer eine Kante die legetim ist und inspektieren sie
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

