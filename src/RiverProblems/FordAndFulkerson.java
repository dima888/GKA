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
	private int source;
	private int target;
	private String SOURCENAME = "source";
	private String TARGETNAME = "target";
	private static final int UNDEFINE = -10;
	private static final int INFINITE = Integer.MAX_VALUE;
	private static final String INSPECTED = "*";
	private static final String EMPTY = "empty";
	private int access = 0; //Zugriff
	private int optimalRiver = 0; //Der optimale Fluss
	
	//***************KONSTRUKTOR********************
	public FordAndFulkerson(AIGraph graph, int source, int target) {
		super();
		this.graph = graph;
		this.source = source;
		this.target = target;
		
		//source & target benennen
		graph.setStrV(source, "name", SOURCENAME);
		graph.setStrV(source, "name", TARGETNAME);
		
		//Start Knoten Markieren und inspizieren
		setMarked(source, UNDEFINE, INFINITE);
		setInspected(source);
	}

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
	public AIGraph startAlgorithmus() {		
		//Erster Schritt, f(e ij); Ist bei uns schon fertig, da wir den tatsaechlichen Fluss schon mit 0 initialisieren
		
		/*
		 * TODO: Evtl das noch implementieren
		 * Pruefen ob in den Graphen Source und Target vorhanden ist, wenn nicht, dann mit einer Exception die Methode beenden
		 * Habe das weggelassen, wurde mir doch zu kompliziert, wenn die Austauschbarkeit noch erhalten bleiben soll
		 */
		
		int currentVertexID = source;
		
		/*
		 * --------------------------------------ABBRUCHBEDINGUNG---------------------------------------------
		 * ABBRUCHBEDINGUNG der intensiven Arbeitsphase des Algorithmus. (Laut GRBuch 2a)
		 * Schritt 1) Laufen ueber alle Knoten und sammeln erstmal die markierten ein.
		 * Schritt 2) Dann laufen wir ueber die markierten und pruefen ob sie alle inpiziert sind,
		 * wenn ja, dann lieft die Methode false zurueck und die whileschleife wird verlassen
		 */
		while (isAllmarkedVertexNotInspected()) {
			
			//Hier werden alle Knoten markiert, die mit den inspizierten Knoten verbunden sind			
			merkedAll(currentVertexID);
			
			/*
			 * Jetzt nehmen wir eine beliebigen Knoten und inpezieren den!
			 * So mit ist das jetzt unser neuer aktueller inspizierter Knoten
			 */
			currentVertexID = inspectedRandomVertex(currentVertexID);
			
			/*
			 * In der Methode pruefen wir, ob der Zielknoten(Target) gefunden wurde
			 * Wenn ja, dann laufen wir zurueck und entfernen alle Markierungen, sowie
			 * inspizierungen. 
			 * Wir geben auch den vergroesserten Fluss an
			 */
			if(isTarget(currentVertexID)) {
				runBack();
			}
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
	 * TODO: auf private setzten
	 * Laueft den ganzen Weg zurueck zur Source und entfernt alle inspizierungen,
	 * Markierungen und gibt uns den vergroesserten Weg an
	 */
	public void runBack() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * TODO: auf private setzten
	 * Methode ueberprueft ob wir unser Ziel(target) erreicht haben, 
	 * wenn ja, dann wird true return, sonst false
	 * @param Integer currentVertexID - Eine VertexID die wir betrachten 
	 * @return Boolean
	 */
	public boolean isTarget(int currentVertexID) {
		if (graph.getStrV(currentVertexID, "name").compareTo(TARGETNAME) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * TODO: auf private schreiben
	 * Methode prueft ob alle markierten Knoten inspeziert sind, 
	 * wenn das der Fall ist, dann wird false zurueck gelieft. 
	 * @return Boolean
	 */
	public boolean isAllmarkedVertexNotInspected() {
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
				return true;
			}
		}
		return false;
	}
	
	/**
	 * TODO: Implementieren und auf privet setzen
	 * Inspiziert einen Knoten, von den wir dann alles weiter betrachten.
	 * Bekommen den neuen inspizierten Knoten zurueck
	 * @param Integer currentInspectedVertexID - Der aktuelle inspizierte Knoten
	 * @return Integer  
	 */
	public int inspectedRandomVertex(int currentInspectedVertexID) {
		//Holle mir zuerst alle Knoten die mit currentInspectedVertexID verbunden sind
		List<Integer> vertexIDList = graph.getAdjacent(currentInspectedVertexID);
		List<Integer> uninspectedIDList = new ArrayList<>();
		
		//Jetzt filtern wir die nicht inspizierten heraus
		for (int vertexID : vertexIDList) {
			if(!isInspected(vertexID)) {
				uninspectedIDList.add(vertexID);
			}
		}
		
		/*
		 * jetzt mische ich die vertexIDList durch und nehme mir den ersten da raus.
		 * Somit haben wir ein Psydo zufallgriff erschaffen.
		 * Dies ist der Collections Klasse zu verdanken
		 */
		Collections.shuffle(uninspectedIDList); //wuerfelt den Array durch einander
		
		//Jetzt inspizieren wir den ausgewaehlten Knoten
		setInspected(uninspectedIDList.get(0));
		
		//Den geben wir anschliessend auch wieder zurueck
		return uninspectedIDList.get(0);
	}

	/**
	 * Diese Methode Markiert alle Knoten, die sich zu/von den inspizierten Knoten sich befinden
	 * @param Integer inspectedVertex - Ein inspizierter Knoten
	 */
	public void merkedAll(int inspectedVertex) {
		//Hier hollen wir uns alle Kanten die an den inspizierten Knoten haengen
		List<Integer> edgesFromCurrentVertex = graph.getIncident(inspectedVertex);
		
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
				int currentCapacity = getCapacity(edgeID);
				
				//Delta von der inspizierten Kante hollen
				int inspectedDelta = getDelta(inspectedVertex);
				
				//Jetzt findet die Pruefungen statt
				if (inspectedDelta == INFINITE) {						
					setMarked(vertexID, inspectedVertex, currentCapacity);
					continue;
				} else {
					if (currentCapacity > inspectedDelta) {
						setMarked(vertexID, inspectedVertex, inspectedDelta);
						continue;
					} else {
						setMarked(vertexID, inspectedVertex, currentCapacity);
						continue;
					}
				}									
			}
			
			//jetzt gucken wir uns die Knoten an, ob sie in Richtung des inzipierten Knoten gehen			
			vertexID = graph.getSource(edgeID);
			if (!isMarked(vertexID)) {
				//Hier muss erstmal das Delta berechnet werden
				
				//Kapazitaet der Kante hollen
				int currentCapacity = getCapacity(edgeID);
				
				//Delta von der inspizierten Kante hollen
				int inspectedDelta = getDelta(inspectedVertex);
				
				//Jetzt findet die Pruefungen statt
				if (inspectedDelta == INFINITE) {						
					setMarked(vertexID, inspectedVertex, currentCapacity);
					continue;
				} else {
					if (currentCapacity > inspectedDelta) {
						setMarked(vertexID, inspectedVertex, inspectedDelta);
						continue;
					} else {
						setMarked(vertexID, inspectedVertex, currentCapacity);
						continue;
					}
				}
			}
		}
	}
	
	
	/**
	 * TODO: private machen
	 * Gibt den Tupel von Kapazitaet und tatsaechlichen Fluss von einer Kante in einem Array zurueck.
	 * 0-Stelle im Array steht fuer die Kapaziaet
	 * 1-Stelle im Array steht fuer den tatsaechlichen Fluss
	 * @param Integer currentID - Die ID der Kante, von wo wir die Informationen Beziehen
	 * @return Array[2]
	 */
	public int[] getCapacityActualRiverTuple(int currentID) {
		int[] result = new int[2];
		result[0] = graph.getValE(currentID, "capacity"); 
		result[1] = graph.getValE(currentID, "actualRiver");
		return result;
	}
	
	/**
	 * TODO: private machen
	 * Hollt von einem Knoten den Deltawert
	 * @param Integer currentID - Eine ID, womit wir auf einen Knoten zugreifen koennen
	 * @return Integer
	 */
	public int getDelta(int currentID) {
		return graph.getValV(currentID, "delta");
	}
	
	/**
	 * TODO: private machen
	 * Zeigt die Vorgaenger ID des Knotens, den wir ueber den Parameter uebergeben
	 * @param Integer currentID - aktueller Knoten
	 * @return Integer
	 */
	public int getPredecessorID(int currentID) {
		return graph.getValV(currentID, "predecessorID");
	}
	
	/**
	 * TODO: private machen
	 * Hollt von einer Kante seine Kapazitaet
	 * @param Integer currentID - ID von einem Knoten, damit wir auf den zugreifen koennen
	 * @return Integer
	 */
	public int getCapacity(int currentID) {
		return graph.getValE(currentID, "capacity"); 
	}

	/**
	 * TODO: Auf private am Ende Setzen
	 * Diese Methode repräsentiert einen Tuppel der Markierung durch die unten definierten Attribute
	 * @param Integer currentID - Aktuelle ID von Vertex, den die Werte gesetzt werden sollen
	 * @param Integer predecessorID - Die ID des Vorgaenger Knoten
	 * @param Integer delta - die minimalste Kapazitaet auf den Weg
	 */
	public void setMarked(int currentID, int predecessorID, int delta) {
		this.graph.setValV(currentID, "predecessorID", predecessorID);
		this.graph.setValV(currentID, "delta", delta);
	}
	
	/**
	 * TODO: loeschen oder private machen
	 * Diese Methode ist nur fuer den Test da
	 * Setzt die Kapazitaet und den tatsaechlichen Fluss von einem Knoten
	 * @param Integer currentID -
	 * @param Integer capacity -
	 * @param Integer actualRiver -
	 */
	public void setCapacityActualRiverTuple(int currentID, int capacity, int actualRiver) {
		this.graph.setValE(currentID, "capacity", capacity);
		this.graph.setValE(currentID, "actualRiver", actualRiver);
	}
	
	/**
	 * TODO: Auf private setzen
	 * Inspiziert eine Ecker eines Graphes
	 * @param Integer currentID - Eine ID von einer/einem {Ecke, Knoten, Vertex}
	 */
	public void setInspected(Integer currentID) {
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
	 * TODO: Auf private setzten
	 * Mit der Methode kann der tatsaechliche Fluss gesetzt werden.
	 * @param Integer currentID - Die ID zu den gehoeriger Kante
	 * @param Integer actualRiverValue - Der tatsaechliche Fluss wird mit diesen Parameter gesetzt
	 */
	public void setActualRiver(int currentID, int actualRiverValue) {
		graph.setValE(currentID, "actualRiver", actualRiverValue);
	}
	
	/**
	 * Entfernt in einer/einem {Ecke, Knoten, Vertex} eines Graphes die Inspizierung
	 * @param Integer currentID - Die ID von der/dem {Ecke, Knoten, Vertex} aud der die Inspektion entfernt wird 
	 */
	private void deleteInspected(Integer currentID) {
		this.graph.setStrV(currentID, "inspected", EMPTY);
	}
	
	/**
	 * TODO: auf privet setzen
	 * Methode prueft ob {Ecke, Knoten, Vertex} markiert ist, bei true markiert bei false unmarkiert
	 * @param Integer currentID - ID der/des {Ecke, Knoten, Vertex} was geprueft wird
	 * @return Boolean
	 */
	public boolean isMarked(Integer currentID) {
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
	
	public static void main(String[] args) {
		
	}
}

