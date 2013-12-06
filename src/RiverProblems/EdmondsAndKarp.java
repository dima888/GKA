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
import graph_lib.AIGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
/*
 * Der Algorithmus von Ford und Fulkerson IM GRBUCH SEITE 98
 */
public class EdmondsAndKarp {
		
	//*****************INSTANZVARIABLEN*******************
	private AIGraph graph;
	private int source;
	private int target;
	private String SOURCENAME = "source";
	private String TARGETNAME = "target";
	private static final int UNDEFINE = -10;
	private static final int UNDEFINEFORMARKED = -1;
	private static final int INFINITE = Integer.MAX_VALUE;
	private static final String INSPECTED = "*";
	private static final String EMPTY = "empty";
	private int access = 0; //Zugriff
	private int optimalRiver = 0; //Der optimale Fluss
	
	/*
	 * Was macht die Queque aus? 
	 * Zu einem wird in diesen Algorithmus die Breitensuche verwendet
	 * zum suchen des vergroesserten Weges.
	 * Die Q arbeitet nach den FIFO-Prinzip(First in, First out). 
	 * peek() -> Element hollen, wenn keins mehr da ist, wird null return.
	 * poll() -> Element aus der Queque entfernen.
	 * offer() -> Element in die Queque packen.
	 * Die Q werden wir benutzen und unsere Verteces abzuspeichern,
	 * somit koennen wir eine Breitensuche betreiben. 
	 */
	private Queue<Integer> queue = new LinkedList<Integer>();
	private Set<Integer> inspectedVertexSet = new HashSet<>();
	//***************KONSTRUKTOR********************
	public EdmondsAndKarp(AIGraph graph, int source, int target) {
//		super();
		this.graph = graph;
		this.source = source;
		this.target = target;
		
		
		
		//source & target benennen
		graph.setStrV(source, "name", SOURCENAME);
		graph.setStrV(target, "name", TARGETNAME);
		
		//Start Knoten Markieren und inspizieren
		setMarked(source, UNDEFINE, INFINITE);
		setInspected(source);
	}
	
	public EdmondsAndKarp(AIGraph graph) {
		this.graph = graph;
		System.out.println("Start Knoten ist : " + graph.getStrV(getSource(graph), "name"));
		System.out.println("Ende Knoten ist : " + graph.getStrV(getTarget(graph), "name"));
		this.source = getSource(graph);
		this.target = getTarget(graph);
		
		//source & target benennen
		graph.setStrV(source, "name", SOURCENAME);
		graph.setStrV(target, "name", TARGETNAME);
		
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
	public int getOptiomalFlow() {
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
		
		
		int currentVertexID = source;
		markiereAlle(currentVertexID);
		System.out.println("AKTUELER INSPIZIERTER KNOTEN = " + graph.getStrV(currentVertexID, "name") + "     ++++++++++++++++++++++++");
		/*
		 * --------------------------------------ABBRUCHBEDINGUNG---------------------------------------------
		 * ABBRUCHBEDINGUNG der intensiven Arbeitsphase des Algorithmus. (Laut GRBuch 2a)
		 * Schritt 1) Laufen ueber alle Knoten und sammeln erstmal die markierten ein.
		 * Schritt 2) Dann laufen wir ueber die markierten und pruefen ob sie alle inpiziert sind,
		 * wenn ja, dann lieft die Methode false zurueck und die whileschleife wird verlassen
		 */
		do {
			//Markierenden IDs anzeigen
			for (int i : queue) {
				System.out.println("ID = " + i + " mit den Namen = " + graph.getStrV(i, "name") + " ist markiert");
			}
			
			//Inspizierte Knoten anzeigen
			for (int i : inspectedVertexSet) {
				System.out.println("ID = " + i + " mit den Namen = " + graph.getStrV(i, "name") + " ist Inspiziert");
			}
			
			currentVertexID = inspectWithQueue(currentVertexID);
			System.out.println("AKTUELER INSPIZIERTER KNOTEN = " + graph.getStrV(currentVertexID, "name") + "     ++++++++++++++++++++++++");
						
			System.out.println("Liste der Inspizierten IDs " + this.inspectedVertexSet);
			System.out.println("Liste der Markierten IDs " + this.queue);
			
			if (wurdeTargetMarkiert()) {
				currentVertexID = backToTheSource();
				markiereAlle(currentVertexID);
			}	
			showAllEdgesInfos();
		} while (!alleMarkiertenVerteciesSindInspiziert());
		
		
		System.out.println(queue);
		System.out.println(inspectedVertexSet);
		/*
		 * Schritt 4 Es gibt keinen vergrößernden Weg. Der jetzige Wert von d
		 * ist optimal. Ein Schnitt A(X,X) mit c(X,X) = d wird gebildet von
		 * genau denjenigen Kanten, bei denen entweder die Anfangsecke oder die
		 * Endecke inspiziert ist.
		 */
		calculateOptimalFlow();
				
		return graph;
	}
	
	/**
	 * Ist nur eine hilfsmethode fuer die Ausgaben
	 */
	private void showAllEdgesInfos() {
		for (int edgeID : graph.getEdges()) {
			int source = graph.getSource(edgeID);
			String sourceName = graph.getStrV(source, "name");
			int target = graph.getTarget(edgeID);
			String targetName = graph.getStrV(target, "name");
			int capacity = getCapacity(edgeID);
			int actualFlow = getActualRiver(edgeID);
			
			System.out.println("Vertex = " + sourceName + "----->" + "Vertex = " + targetName + " (" + capacity + " | " + actualFlow + ")");
		}
	}
	
	private void markiereAlle(int vertexID) {
		List<Integer> edgesFromCurrentVertex = graph.getIncident(vertexID);
		for (int edgeID : edgesFromCurrentVertex) {
			if (istVorwaertsKante(vertexID, edgeID)) {
				//Vorwaertskante
				int vertex = graph.getTarget(edgeID);
				
				//der nicht inspiziert sein
				if (isInspected(vertex) || isMarked(vertex)) {
					continue;
				}
				
				//Markiert darf nur, wenn f(eij) < c(eij)
				int actualFlow = getActualRiver(edgeID);
				int capacity = getCapacity(edgeID);
				
				//Hier darf nicht markiert werden!
				if (actualFlow < capacity) {									
				
				//DeltaJ ermitteln 
				int deltaJ = 0;
				
				//DeltaI = DeltaJ von der Inspizierten Ecke
				int deltaI = getDelta(vertexID);				
				int minRiver = capacity - actualFlow;
				
				if (minRiver < deltaI) {
					deltaJ = minRiver;
				} else {
					deltaJ = deltaI;
				}
								
				//Markierung setzten und die das markiertenSet packen. bedingung erfuellt!
				setMarked(vertex, vertexID, deltaJ);
				queue.add(vertex);
				
				} else {
					//Hier darf nicht markiert werden
					continue;
				}
				
			} else {
				//Rueckwearskante, hier beachten die ID negativ setzten
				int vertex = graph.getSource(edgeID);
				
				//Darf nicht inspiziert sein
				if (isInspected(vertex) || isMarked(vertex)) {
					continue;
				}
				
				//Hier darf markiert werden!
				if (graph.getValE(edgeID, "actualRiver") > 0) {				
					//DeltaJ ermitteln
					int deltaJ = 0;
					
					int deltaI = getDelta(vertexID);
					int actualFlow = getActualRiver(edgeID);
					
					deltaJ = Math.min(deltaI, actualFlow);
					
					//Markierung setzten und die das markiertenSet packen. bedingung erfuellt!
					System.out.println("Vertex: " + graph.getStrV(vertex, "name"));
					//System.out.println("--------------22222222222222222222222222222222------------------------" + "PREDECESSOR = " + graph.getStrV(getPredecessorID(vertex), "name"));
					System.out.println("Seine Kante: " + edgeID + " Capacity = " + getCapacity(edgeID) + " Flow = " + getActualRiver(edgeID));
					//setMarked(vertex * - 1, vertexID, deltaJ);
					setMarked(vertex, vertexID * - 1, deltaJ);
					queue.offer(vertex);
					
				} else {
					//HIER DARF NICHT MARKIERT WERDEN!
					continue;
				}	
			}
		}
		//System.out.println("Die markierten IDs " + queue);
		//Markieren IDs anzeigen
//		for (int i : queue) {
//			System.out.println("ID = " + i + " mit den Namen = " + graph.getStrV(i, "name") + " ist markiert");
//		}
	}
	
	/**
	 * Methode gibt true zurueck, wenn das Ziel markiert wurde
	 * @return
	 */
	private boolean wurdeTargetMarkiert() {
		//if (queue.contains(target)) {
		if (isMarked(target)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Sucht sich per Zufall einen markierten Knoten aus, inspiziert den und markiert von den 
	 * neuen inspizierten alle weiteren Knoten
	 * @param Integer vertexID - Eine Knoten ID
	 * @return Integer
	 */
	private int inspectWithQueue(int vertexID) {		
		Queue<Integer> queueResult = new LinkedList<Integer>();
		
		if (queue.contains(target)) {
			setInspected(target);
			return target;
		}
		
		for (int v : queue) {
			if (!isInspected(v)) {
				queueResult.offer(v);
			}			
		}
		
		int selectedVertexID = queueResult.peek();
		
		//Jetzt inspizieren wir den ausgewaehlten Knoten
		setInspected(selectedVertexID);
		this.inspectedVertexSet.add(selectedVertexID);
		
		//Alle des Neuen inspizierten Knotens markieren
		System.out.println("***************" + selectedVertexID + "************************");
		markiereAlle(selectedVertexID);
		
		//Den geben wir anschliessend auch wieder zurueck
		return selectedVertexID;
	}
	
	/**
	 * Methode ermittelt ob es eine Vorwaertskante ist, wenn ja dann true, sonst false.
	 * @param Integer - vertexID Eine Knoten ID
	 * @param Integer edgeID - eine Kanten ID 
	 * @return
	 */
	private boolean istVorwaertsKante(int vertexID, int edgeID) {
		if (vertexID == graph.getSource(edgeID)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Methode prueft ob alle markierten Vertices auch inspiziert sind, 
	 * wenn ja wird true zurueck gegeben, sonst false.
	 * @return boolean
	 */
	private boolean alleMarkiertenVerteciesSindInspiziert() {
		for (int vID : queue) {
			if (!isInspected(vID)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Schritt 4 Es gibt keinen vergrößernden Weg. Der jetzige Wert von d
 	 * ist optimal. Ein Schnitt A(X,X) mit c(X,X) = d wird gebildet von
	 * genau denjenigen Kanten, bei denen entweder die Anfangsecke oder die
	 * Endecke inspiziert ist.
	 */
	private void calculateOptimalFlow() {
		Set<Integer> markedVertices = new HashSet<>();
		
		
		for (int vertexID : graph.getVertexes()) {
			if (isMarked(vertexID)) {
				markedVertices.add(vertexID);
			}					
		}
		for (int edgeID : graph.getEdges()) {
			if (isMarked(graph.getSource(edgeID)) != isMarked(graph.getTarget(edgeID))) {
				int flowFromEdge = graph.getValE(edgeID, "actualRiver");
				if (markedVertices.contains(graph.getSource(edgeID))) {
					optimalRiver += flowFromEdge;
				} else {
					optimalRiver -= flowFromEdge;
				}
			}
		}	
	}
	
	/**
	 * Laueft den ganzen Weg zurueck zur Source und entfernt alle inspizierungen und Markierungen,
	 * gibt uns den vergroesserten Weg an
	 * Aktuelle ID wird wieder auf source gesetzt
	 * @param Integer currentVertexID - Aktuelle ID mit der wir arbeiten und zwar der Target(s)
	 */
	private int backToTheSource() {
		int currentVertexID = target;
		//Abspeichern von DeltaS
		int deltaS = graph.getValV(currentVertexID, "delta");
		System.out.println("**********DELTA************* =      " + deltaS);
		
		//Abbruchbedinung = Wenn currentVertexID = SourceID ist
		while (isNotSource(currentVertexID)) {
			
			System.out.println("ID: " + currentVertexID + "    Der name zur ID: "+ graph.getStrV(currentVertexID, "name"));
			//Die ID des Vorgaengers hollen
			
			int predecessorID = getPredecessorID(currentVertexID);				
			this.access++;
			
			/*
			 * Die Kante zwischen currentID und predecessorID heraus finden
			 * Zuerst hollen wir uns alle Kanten die zur currentVertexID anliegen
			 * Dann suchen nach der Kante die predecessorID und currentVertexID verbindet
			 */
			List<Integer> edgeIDLIst = graph.getIncident(currentVertexID);
			int currentEdgeID = 0;
			for (int edgeID : edgeIDLIst) {
				this.access++;
				if ((graph.getSource(edgeID) == predecessorID && graph.getTarget(edgeID) == currentVertexID) || (graph.getSource(edgeID) == currentVertexID && graph.getTarget(edgeID) == predecessorID)) {
					currentEdgeID = edgeID;
				}
			}
			
			/*
			 * Hier pruefen wir, ob wir eine Vorwaerskante oder Ruckwaerskante haben.
			 * Bei Vorwaerskante ist die predecessorID positiv und f(e ij) wird erhoeht um DeltaS.
			 * Bei Rueckwaerts ist die predecessorID negativ und f(e ij) wird vermindert um DeltaS.
			 */					
				if (predecessorID >= 0) {
					//Vorwaerskante				
					int newActualRiver = graph.getValE(currentEdgeID, "actualRiver") + deltaS;
					this.access++;
					graph.setValE(currentEdgeID, "actualRiver", newActualRiver);
					this.access++;
				} else {
					//Rueckwaertskante
					int newActualRiver = graph.getValE(currentEdgeID, "actualRiver") - deltaS;
					this.access++;
					graph.setValE(currentEdgeID, "actualRiver", newActualRiver);
					this.access++;
					predecessorID = (predecessorID * -1);
				}
				
			 //Hier gehen wir einen Schritt zurueck 
			currentVertexID = predecessorID;
			System.out.println("Auf den Rueckweg der aktuelle Knoten ist: " + graph.getStrV(currentVertexID, "name"));
		}	
		deleteAllMarkedAndInspectedValues();
		return source;
	}

	/**
	 * Methode entfernt alle Markierungen und alle Inpizierungen
	 * Inklusivie die beiden Sets (Attribute der Klasse)
	 */
	private void deleteAllMarkedAndInspectedValues() {
		//Alle Markierungen entfernen
		for (int vertexID : graph.getVertexes()) {
			if (vertexID == source) {
				continue;
			}
			if (isMarked(vertexID)) {
				this.access++;
				deleteMarked(vertexID);
			}
			
		}
		
		//Alle Inspiezierungen entfernen
		for (int vertexID : graph.getVertexes()) {
			if (vertexID == source) {
				continue;
			}
			if (isInspected(vertexID)) {
				this.access++;
				deleteInspected(vertexID);
			}
		}
		this.queue = new LinkedList<Integer>();
		this.inspectedVertexSet = new HashSet<>();
	}
	
	/**
	 * Die Methode lieft uns den tatsaechlichen Fluss einer Kante 
	 * @param Integer edgeID - Eine ID von einer Kante, auf die wir zugreifen moechten
	 * @return Integer
	 */
	private int getActualRiver(int edgeID)  {
		this.access++;
		return graph.getValE(edgeID, "actualRiver");
	}
	
	/**
	 * Hollt von einem Knoten den Deltawert
	 * @param Integer currentID - Eine ID, womit wir auf einen Knoten zugreifen koennen
	 * @return Integer
	 */
	private int getDelta(int currentID) {
		this.access++;
		return graph.getValV(currentID, "delta");
	}
	
	/**
	 * Zeigt die Vorgaenger ID des Knotens, den wir ueber den Parameter uebergeben
	 * Wenn die Vorgaenger ID negativ ist, dann wird sie wieder nach positiv abgebildet,
	 * sonst kann es zu Fehlern kommen, die man nicht so schnell findet
	 * @param Integer currentID - aktueller Knoten
	 * @return Integer
	 */
	private int getPredecessorID(int currentID) {
		this.access++;
		int result = graph.getValV(currentID, "predecessorID");
		if (result < 0) {
			result = result * - 1;
		}
		return result;
	}
	
	/**
	 * Hollt von einer Kante seine Kapazitaet
	 * @param Integer currentID - ID von einem Knoten, damit wir auf den zugreifen koennen
	 * @return Integer
	 */
	private int getCapacity(int currentID) {
		this.access++;
		return graph.getValE(currentID, "capacity"); 
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
		this.access = this.access + 2;
	}
	
	/**
	 * Inspiziert eine Ecker eines Graphes
	 * @param Integer currentID - Eine ID von einer/einem {Ecke, Knoten, Vertex}
	 */
	private void setInspected(Integer currentID) {
		this.graph.setStrV(currentID, "inspected", INSPECTED);
		this.access++;
	}
	
	/**
	 * Entfernt in einer/einem {Ecke, Knoten, Vertex} eines Graphes die Markierung
	 * @param Integer currentID - Die ID von der die Markierung entfernt wird
	 */
	private void deleteMarked(Integer currentID) {
		this.graph.setValV(currentID, "predecessorID", UNDEFINEFORMARKED);
		this.graph.setValV(currentID, "delta", UNDEFINEFORMARKED);
		this.access = this.access + 2;
	}
	
	
	/**
	 * Entfernt in einer/einem {Ecke, Knoten, Vertex} eines Graphes die Inspizierung
	 * @param Integer currentID - Die ID von der/dem {Ecke, Knoten, Vertex} aud der die Inspektion entfernt wird 
	 */
	private void deleteInspected(int currentID) {
		this.graph.setStrV(currentID, "inspected", EMPTY);
		this.access++;
	}
	
	/**
	 * Methode prueft ob {Ecke, Knoten, Vertex} markiert ist, bei true markiert bei false unmarkiert
	 * @param Integer currentID - ID der/des {Ecke, Knoten, Vertex} was geprueft wird
	 * @return Boolean
	 */
	private boolean isMarked(int currentID) {
		if (graph.getValV(currentID, "predecessorID") == UNDEFINEFORMARKED) {
			this.access++;
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
	private boolean isInspected(int currentID) {
		if (graph.getStrV(currentID, "inspected").compareTo(EMPTY) == 0) {
			this.access++;
			return false;
		}
		return true;
	}
	
	/**
	 * Methode prueft ob der uebergebene Paramenter die Quelle ist.
	 * Wenn es nicht die Quelle ist, dann wird true return und sonst false.
	 * @param Integer currendVertexID - Uebergebene ID die wir uns anschauen
	 * @return Boolean
	 */
	private boolean isNotSource(int currendVertexID) {
		if (graph.getValV(currendVertexID, "predecessorID") == UNDEFINE) {
			this.access++;
			return false;
		}
		return true;
	}
	
	/**
	 * Findet den Start Knoten im einen Graphen
	 * Gibt seine ID zurueck oder wenn kein Start 
	 * vorhanden ist, dann -1
	 * @param AIGraph graph - Ein uebergebener Graph
	 * @return Integer
	 */
	private int getSource(AIGraph graph) {
		List<Integer> verticesList = graph.getVertexes();
		List<Integer> edgesList = graph.getEdges();
		boolean flag = true;
		Integer result;

		for (Integer vID : verticesList) {
			flag = true;
			for (Integer eID : edgesList) {
				if (graph.getTarget(eID) == vID) {
					flag = false;
				}
			}
			if (flag) {
				return vID;
			}
		}
		
		System.out.println("SOURCE NICHT GEFUNDEN");
		return -1;
	}
		
	/**
	 * Findet den Ziel Knoten im einen Graphen
	 * Gibt seine ID zurueck oder wenn kein Ziel 
	 * vorhanden ist, dann -1
	 * @param AIGraph graph - Ein uebergebener Graph
	 * @return Integer
	 */
		private int getTarget(AIGraph graph) {
			List<Integer> verticesList = graph.getVertexes();
			List<Integer> edgesList = graph.getEdges();
			boolean flag = true;
			Integer result;

			for (Integer vID : verticesList) {
				flag = true;
				for (Integer eID : edgesList) {
					if (graph.getSource(eID) == vID) {
						//System.out.println("SOURCE: " + graph.getSource(eID) + " TARGET: " + graph.getTarget(eID));
						flag = false;
					}
				}
				if (flag) {
					return vID;
				}
			}
			System.out.println("TARGET NICHT GEFUNDEN");
			return -1;
		}
}

