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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private static final int UNDEFINEFORMARKED = -1;
	private static final int INFINITE = Integer.MAX_VALUE;
	private static final String INSPECTED = "*";
	private static final String EMPTY = "empty";
	private int access = 0; //Zugriff
	private int optimalRiver = 0; //Der optimale Fluss
	private int optimalFlow = 0;
	private Set<Integer> markedVertexSet = new HashSet<>();
	//***************KONSTRUKTOR********************
	public FordAndFulkerson(AIGraph graph, int source, int target) {
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
	
	public FordAndFulkerson(AIGraph graph) {
//		super();
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
		do  {
			
			System.out.println("currentVertexname: " + graph.getStrV(currentVertexID, "name") + " |isMarked? " + isMarked(currentVertexID) + " |isInspected? " + isInspected(currentVertexID));
			
			//Hier werden alle Knoten markiert, die mit den inspizierten Knoten verbunden sind			
			markedAllVertex(currentVertexID);
			
			/*
			 * Jetzt nehmen wir eine beliebigen Knoten und inpezieren den!
			 * So mit ist das jetzt unser neuer aktueller inspizierter Knoten
			 * TODO: Die inspectedRandomVertex Methode vielleicht zu void machen, damit ich nicht auf -1 pruefen muss
			 */
			currentVertexID = inspectedRandomVertex(currentVertexID);
			if(currentVertexID == -1) {
				break;
			}
			
			/*
			 * Alle Knoten markiet und inspiziert, wir kommen nicht mehr weiter
			 * Hier wurde zurueck gegangen bis Source, aehnlich wie bei backtracking
			 */
			if(currentVertexID == UNDEFINE) {
				break;
			}
			System.out.println("*****AktuelleID " + currentVertexID + "*************");
			System.out.println("*****AktuelleID " + graph.getStrV(currentVertexID, "name") + " *************");
			
			
		System.out.println("currentVertexname: " + graph.getStrV(currentVertexID, "name") + " |isMarked? " + isMarked(currentVertexID) + " |isInspected? " + isInspected(currentVertexID) + " AKTUELER KNOTEN");
		
			markedAllVertex(currentVertexID);
			
			
			/*
			 * In der Methode pruefen wir, ob der Zielknoten(Target) gefunden wurde
			 * Wenn ja, dann laufen wir zurueck und entfernen alle Markierungen, sowie
			 * inspizierungen. 
			 * Wir geben auch den vergroesserten Fluss an
			 */
			if(isTarget(currentVertexID)) {
				currentVertexID = backToTheSource(currentVertexID);				
			}
			getFromAllEdgesTheTuple();
		} while (isAllmarkedVertexNotInspected());
		
		/*
		 * Schritt 4 Es gibt keinen vergrößernden Weg. Der jetzige Wert von d
		 * ist optimal. Ein Schnitt A(X,X) mit c(X,X) = d wird gebildet von
		 * genau denjenigen Kanten, bei denen entweder die Anfangsecke oder die
		 * Endecke inspiziert ist.
		 */
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
		
//		List<Integer> edgeIDListFromSource = graph.getIncident(source);  
//		List<Integer> edgeIDListFromTarget = graph.getIncident(target);  
//		for (int edgeID : edgeIDListFromSource) {
//			optimalRiver += graph.getValE(edgeID, "actualRiver");
//		}
//		for (int edgeID : edgeIDListFromTarget) {
//			optimalFlow += graph.getValE(edgeID, "actualRiver");
//		}
//		
//		if (optimalRiver == optimalFlow) {
//			System.out.println("We are ready, Bye bye");
//		}
		
		
		return graph;
	}
	
	
	/**
	 * TODO: auf private setzten, TODO:HIer scheint noch der Fehler zu sein, dass nicht auf source wieder gesetzt wird
	 * Laueft den ganzen Weg zurueck zur Source und entfernt alle inspizierungen und Markierungen,
	 * gibt uns den vergroesserten Weg an
	 * Aktuelle ID wird wieder auf source gesetzt
	 * @param Integer currentVertexID - Aktuelle ID mit der wir arbeiten und zwar der Target(s)
	 */
	public int backToTheSource(int currentVertexID) {		
		//Abspeichern von DeltaS
		int deltaS = graph.getValV(currentVertexID, "delta");
		
		//Abbruchbedinung = Wenn currentVertexID = SourceID ist
		while (isNotSource(currentVertexID)) {
			System.out.println("***********Der weg ueber die IDs  zurueck ID: " + currentVertexID);
			System.out.println("***********Der name zur ID: "+ graph.getStrV(currentVertexID, "name"));
			//Die ID des Vorgaengers hollen
			
			int predecessorID = graph.getValV(currentVertexID, "predecessorID");
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
//					System.out.println("Richtige Kante gefunden");
					currentEdgeID = edgeID;
				}
			}
			
			/*
			 * Nehme alle Kanten von Predecessor, 
			 * wenn der Source oder Target auf CurrentID
			 * zeigt, dann haben wir unsere Kante gefunden
			 * und koennen das Tupel setzten
			 */
			// int actualEdge = graph.getI
			
			/*
			 * Hier pruefen wir, ob wir eine Vorwaerskante oder Ruckwaerskante haben.
			 * Bei Vorwaerskante ist die predecessorID positiv und f(e ij) wird erhoeht um DeltaS.
			 * Bei Rueckwaerts ist die predecessorID negativ und f(e ij) wird vermindert um DeltaS.
			 */
			if(predecessorID >= 0) {
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
			
			/*
			 * Markierung und Inspizierung des aktuellen Knoten entfernen
			 * currentVertexID auf predecessor setzten,
			 * somit gehen wir einen Schritt zurueck 
			 */
//			deleteInspected(currentVertexID);
//			deleteMarked(currentVertexID);
			currentVertexID = predecessorID;
			System.out.println("Auf den Rueckweg der aktuelle Knoten ist: " + graph.getStrV(currentVertexID, "name"));
		}	
		deleteAllMarkedAndInspectedValues();
		return source;
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
			this.access++;
			return true;
		}
		return false;
	}

	/**
	 * TODO: Hier ist ein schwachpunkt, am anfang liefert die methode schon false zurueck und das ist nicht ganz richtig
	 * TODO: auf private schreiben
	 * Methode prueft ob alle markierten Knoten inspeziert sind, 
	 * wenn das der Fall ist, dann wird false zurueck gelieft. 
	 * @return Boolean
	 */
	public boolean isAllmarkedVertexNotInspected() {
		List<Integer> markedVertexIDList = new ArrayList<>();
		List<Integer> inspectedVertexIDList = new ArrayList<>();
		
		//Schritt 1:
		for (int vertexID : graph.getVertexes()) {				
			this.access++;
			if (isMarked(vertexID)) {
				//Bei source eine Ausnahme machen
				if (graph.getValV(vertexID, "ID") == source) {
					continue;
				}
				markedVertexIDList.add(vertexID);
			}			
		}
		
		//Sonderpruefung
		if (markedVertexIDList.size() == 0) {
			return true;
		}
		
//		//TODO: Nur zum testen
//		for (int vertexID : markedVertexIDList) {
//			System.out.println("Markierten Knoten: " + graph.getStrV(vertexID, "name") + " mit PredecessorID = " + graph.getValV(vertexID, "predecessorID") + "(" + graph.getStrV(graph.getValV(vertexID, "predecessorID"), "name") + ")");
//		}
//		System.out.println("---------------------------------------------------------------------------");
//		
//		//TODO: Nur zum testen
//		for (int vertexID : markedVertexIDList) {
//			if (isInspected(vertexID)) {				
//				System.out.println("Inspizierten Knoten: " + graph.getStrV(vertexID, "name") + " mit PredecessorID = " + graph.getValV(vertexID, "predecessorID") + "(" + graph.getStrV(graph.getValV(vertexID, "predecessorID"), "name") + ")");
//			}
//		}
		System.out.println("----------------------------------------------------------------------------");
		
		//Schritt 2:
		for (int vertexID : markedVertexIDList) {
			if (!isInspected(vertexID)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * TODO: auf privet setzen 
	 * Inspiziert einen Knoten, von den wir dann alles weiter betrachten.
	 * Bekommen den neuen inspizierten Knoten zurueck
	 * @param Integer currentInspectedVertexID - Der aktuelle inspizierte Knoten
	 * @return Integer  
	 */
	public int inspectedRandomVertex(int currentInspectedVertexID) {
		//Holle mir zuerst alle Knoten die mit currentInspectedVertexID verbunden sind
		List<Integer> vertexIDList = graph.getAdjacent(currentInspectedVertexID);
		List<Integer> uninspectedVertexIDList = new ArrayList<>();
		List<Integer> legitimUninspectedVertexIDList = new ArrayList<>();
		
 
		
		//Jetzt filtern wir die nicht inspizierten heraus
		for (int vertexID : vertexIDList) {
			this.access++;
			if(!isInspected(vertexID)) {				
				uninspectedVertexIDList.add(vertexID);
			}
		}
		
		/*
		 * TODO: Diese stelle merken
		 * Jetzt muss geprueft werden, ob die gefilterten markiert sind, sonst duerfen wir den Knoten nicht inspizieren
		 * Etwas redundant, aber sicher
		 */
		for (int vertexID : uninspectedVertexIDList) {
			if(isMarked(vertexID)) {				
				//Sonderregelung, wenn Target mit enthalten ist, dann gehen wir auch dahin!
				if (vertexID == target) {
					setInspected(target);
					this.access++;
					return target;
				}
				
				/*
				 * Es duerfen die inspiziert werden, 
				 * die von markierten vorgaenger markiert wurden
				 */
				if (currentInspectedVertexID == graph.getValV(vertexID, "predecessorID")) {
					this.access++;
					legitimUninspectedVertexIDList.add(vertexID);
				}
				//legitimUninspectedVertexIDList.add(vertexID);
			}
		}			
		
		/*
		 * Hier kommt der Algorihmus in eine Sackgase
		 * und kehrt zurueck und nimm sich einen 
		 * anderen Knoten
		 */
		if(legitimUninspectedVertexIDList.size() < 1) {	
			this.access++;
			
			for (int id : legitimUninspectedVertexIDList) {
				if (!isInspected(id)) {
					return -1;
				}
			}
			
			System.out.println("Versagen");
			return graph.getValV(currentInspectedVertexID, "predecessorID");
		}
			
		/*
		 * Hier werden alle Verteces aus der Liste entfernt, außer nur einer und das per Zufall
		 * Somit haben wir ein Psydo zufallgriff erschaffen.
		 * Dies ist der Collections Klasse zu verdanken
		 */
		Collections.shuffle(legitimUninspectedVertexIDList); //wuerfelt den Array durch einander
		
		//Jetzt inspizieren wir den ausgewaehlten Knoten
		setInspected(legitimUninspectedVertexIDList.get(0));
		
		//Den geben wir anschliessend auch wieder zurueck
		return legitimUninspectedVertexIDList.get(0);
	}

	/**
	 * Diese Methode Markiert alle Knoten, die sich zu/von den inspizierten Knoten befinden
	 * Jedoch muessen die Kritaerien des Algorithmus erfuellt sein, damit dies passieren kann
	 * @param Integer inspectedVertex - Ein inspizierter Knoten
	 */
	public void markedAllVertex(int inspectedVertex) {
		//Hier hollen wir uns alle Kanten die an den inspizierten Knoten haengen
		List<Integer> edgesFromCurrentVertex = graph.getIncident(inspectedVertex);
		
		//Sonderregelung
		
		
		/*
		 * Jetzt koennen wir bei den Knoten die Tupelwerte(markieren).
		 * Muessen hier auch in die entgegengesetzte Richtung schauen!
		 * Example: Vertex --------> Vertex || Vertex <------- Vertex 
		 */
		for (int edgeID : edgesFromCurrentVertex) {
			//erst gucken wir uns die Knoten an, die weg von inpizierten Knoten gehen
			int vertexID = graph.getTarget(edgeID);
			//System.out.println("CurrentVertex( " + graph.getStrV(inspectedVertex, "name") +  ")" + " zeigt mit Vorwaertskante auf: " + graph.getStrV(vertexID, "name"));
			if (!isMarked(vertexID)) {
				
				//Kapazitaet der Kante hollen
				int currentCapacity = getCapacity(edgeID);
				
				//Tatsaechlichen Fluss der Kante hollen
				int currentActualRiver = getActualRiver(edgeID);
				
				//Pruefung des Algorithmus, ob der Knoten ueberhaupt markiert werden darf
				System.out.println("aktueler Kanten Tupel: (" + currentCapacity + " | " + currentActualRiver + ")");
				if(currentCapacity <= currentActualRiver) { 
					System.out.println(graph.getStrV(vertexID, "name") + " DARF NICHT MARKIERT WERDEN");
					this.access++;
					continue;
				}
				 
				
				//Delta von der inspizierten Knoten hollen
				int inspectedDelta = getDelta(inspectedVertex);
				
				//Das ist ein Sonderfall, gilt nur fuer ersten durchlauf mit source
				if (inspectedDelta == INFINITE) {						
					setMarked(vertexID, inspectedVertex, currentCapacity);
					markedVertexSet.add(vertexID);
					continue;
				} else {
					/*
					 * Berechnung von Delta
					 * Formel: DeltaJ = MIN( (c(e ij) - f(e ij)) ,  DeltaI)
					 */
					int buffer = currentCapacity - currentActualRiver;
					if (buffer > inspectedDelta) {
						setMarked(vertexID, inspectedVertex, inspectedDelta);
						markedVertexSet.add(vertexID);
						this.access++;
						continue;
					} else {
						setMarked(vertexID, inspectedVertex, buffer);
						this.access++;
						continue;
					}
				}
			}
			
			//jetzt gucken wir uns die Knoten an, ob sie in Richtung des inzipierten Knoten gehen			
			vertexID = graph.getSource(edgeID);
			//System.out.println(graph.getStrV(vertexID, "name") + " zeigt auf CurrentVertex(" + "CurrentVertex( " + graph.getStrV(inspectedVertex, "name") + ")"  + ", somit ist es eine Rueckwaertskante");
			if (!isMarked(vertexID)) {
				
				//Tatsaechlichen Fluss der Kante hollen
				int currentActualRiver = getActualRiver(edgeID);
				
				//Pruefung des Algorithmus, ob der Knoten ueberhaupt markiert werden darf
				if(currentActualRiver <= 0) {
					continue;
				}
				
				//Kapazitaet der Kante hollen
				int currentCapacity = getCapacity(edgeID);
				
				//Delta von der inspizierten Knoten hollen
				int inspectedDelta = getDelta(inspectedVertex);
				
				//Das ist ein Sonderfall, gilt nur fuer ersten durchlauf mit source
				if (inspectedDelta == INFINITE) {						
					setMarked(vertexID, -inspectedVertex, currentCapacity);
					markedVertexSet.add(vertexID);
					this.access++;
					continue;
				} else {
					/*
					 * Berechnung von Delta
					 * Formel: DeltaJ = MIN(f(e ij), DeltaI)
					 */
					if (currentCapacity > inspectedDelta) {
						setMarked(vertexID, -inspectedVertex, inspectedDelta);
						markedVertexSet.add(vertexID);
						this.access++;
						continue;
					} else {
						setMarked(vertexID, -inspectedVertex, currentCapacity);
						markedVertexSet.add(vertexID);
						this.access++;
						continue;
					}
				}
			}
		}
	}
	
	/**
	 * TODO: Auf privet setzten
	 * Methode entfernt alle Markierungen und alle Inpizierungen
	 */
	public void deleteAllMarkedAndInspectedValues() {
		//Alle Markierungen entfernen
		for (int vertexID : graph.getVertexes()) {
			if (vertexID == source) {
				continue;
			}
			if (isMarked(vertexID)) {
				this.access++;
				deleteMarked(vertexID);
			}
			markedVertexSet = new HashSet();
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
	}
	
	/**
	 * TODO: Private machen
	 * Die Methode lieft uns den tatsaechlichen Fluss einer Kante 
	 * @param Integer edgeID - Eine ID von einer Kante, auf die wir zugreifen moechten
	 * @return Integer
	 */
	public int getActualRiver(int edgeID)  {
		this.access++;
		return graph.getValE(edgeID, "actualRiver");
	}
	
	/**
	 * Gibt von allen Kanten die Tuple als string aus
	 */
	public void getFromAllEdgesTheTuple() {
		for (int edgeID : graph.getEdges()) {
			this.access = this.access + 2;
			System.out.println("(" + getCapacityActualRiverTuple(edgeID)[0] + " | " + getCapacityActualRiverTuple(edgeID)[1] + ")");
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
		this.access = this.access + 2;
		return result;
	}
	
	/**
	 * TODO: private machen
	 * Hollt von einem Knoten den Deltawert
	 * @param Integer currentID - Eine ID, womit wir auf einen Knoten zugreifen koennen
	 * @return Integer
	 */
	public int getDelta(int currentID) {
		this.access++;
		return graph.getValV(currentID, "delta");
	}
	
	/**
	 * TODO: private machen
	 * Zeigt die Vorgaenger ID des Knotens, den wir ueber den Parameter uebergeben
	 * @param Integer currentID - aktueller Knoten
	 * @return Integer
	 */
	public int getPredecessorID(int currentID) {
		this.access++;
		return graph.getValV(currentID, "predecessorID");
	}
	
	/**
	 * TODO: private machen
	 * Hollt von einer Kante seine Kapazitaet
	 * @param Integer currentID - ID von einem Knoten, damit wir auf den zugreifen koennen
	 * @return Integer
	 */
	public int getCapacity(int currentID) {
		this.access++;
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
		this.access = this.access + 2;
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
		this.access = this.access + 2; 
	}
	
	/**
	 * TODO: Auf private setzen
	 * Inspiziert eine Ecker eines Graphes
	 * @param Integer currentID - Eine ID von einer/einem {Ecke, Knoten, Vertex}
	 */
	public void setInspected(Integer currentID) {
		this.graph.setStrV(currentID, "inspected", INSPECTED);
		this.access++;
	}
	
	/**
	 * TODO: Auf private setzten
	 * Mit der Methode kann der tatsaechliche Fluss gesetzt werden.
	 * @param Integer currentID - Die ID zu den gehoeriger Kante
	 * @param Integer actualRiverValue - Der tatsaechliche Fluss wird mit diesen Parameter gesetzt
	 */
	public void setActualRiver(int currentID, int actualRiverValue) {
		graph.setValE(currentID, "actualRiver", actualRiverValue);
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
	 * TODO: auf privet setzen
	 * Methode prueft ob {Ecke, Knoten, Vertex} markiert ist, bei true markiert bei false unmarkiert
	 * @param Integer currentID - ID der/des {Ecke, Knoten, Vertex} was geprueft wird
	 * @return Boolean
	 */
	public boolean isMarked(int currentID) {
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
	 * TODO: Methode auf private setzen
	 * Methode prueft ob der uebergebene Paramenter die Quelle ist.
	 * Wenn es nicht die Quelle ist, dann wird true return und sonst false.
	 * @param Integer currendVertexID - Uebergebene ID die wir uns anschauen
	 * @return Boolean
	 */
	public boolean isNotSource(int currendVertexID) {
		if (graph.getValV(currendVertexID, "predecessorID") == UNDEFINE) {
			this.access++;
			return false;
		}
		return true;
	}
	
	public int getSource(AIGraph graph) {
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
				//System.out.println("SOURCE ID = " + vID);
				return vID;
			}
		}
		
		System.out.println("SOURCE NICHT GEFUNDEN");
		return -1;
	}
		
		public int getTarget(AIGraph graph) {
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
					//System.out.println("TARGET ID = " + vID);
					return vID;
				}
			}
			System.out.println("TARGET NICHT GEFUNDEN");
			return -1;
		}
	
	public static void main(String[] args) {
		
	}
}

