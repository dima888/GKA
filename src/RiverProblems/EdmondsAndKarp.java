package RiverProblems;

import graph_lib.AIGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
		 * Die Queue arbeitet nach den FIFO-Prinzip(First in, First out). 
		 * peek() -> Element hollen, wenn keins mehr da ist, wird null return.
		 * poll() -> Element aus der Queque entfernen.
		 * offer() -> Element in die Queque packen.
		 * Die Queue werden wir benutzen und unsere Verteces abzuspeichern,
		 * somit koennen wir eine Breitensuche betreiben. 
		 * 
		 */
		private Queue<Integer> queue = new LinkedList<Integer>();

		
		//***************KONSTRUKTOR********************
		public EdmondsAndKarp(AIGraph graph, int source, int target) {
			super();
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
				
				//System.out.println("currentVertexname: " + graph.getStrV(currentVertexID, "name") + " |isMarked? " + isMarked(currentVertexID) + " |isInspected? " + isInspected(currentVertexID));
				
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
			
			System.out.println("Fertig");
			
			/*
			 * Schritt 4 Es gibt keinen vergrößernden Weg. Der jetzige Wert von d
			 * ist optimal. Ein Schnitt A(X,X) mit c(X,X) = d wird gebildet von
			 * genau denjenigen Kanten, bei denen entweder die Anfangsecke oder die
			 * Endecke inspiziert ist.
			 */
			
			
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
				
				//Die ID des Vorgaengers hollen
				int predecessorID = graph.getValV(currentVertexID, "predecessorID");
				
				/*
				 * Die Kante zwischen currentID und predecessorID heraus finden
				 * Zuerst hollen wir uns alle Kanten die zur currentVertexID anliegen
				 * Dann suchen nach der Kante die predecessorID und currentVertexID verbindet
				 */
				List<Integer> edgeIDLIst = graph.getIncident(currentVertexID);
				int currentEdgeID = 0;
				for (int edgeID : edgeIDLIst) {
					
					if ((graph.getSource(edgeID) == predecessorID && graph.getTarget(edgeID) == currentVertexID) || (graph.getSource(edgeID) == currentVertexID && graph.getTarget(edgeID) == predecessorID)) {
//						System.out.println("Richtige Kante gefunden");
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
					graph.setValE(currentEdgeID, "actualRiver", newActualRiver);						
				} else {
					//Rueckwaertskante
					int newActualRiver = graph.getValE(currentEdgeID, "actualRiver") - deltaS;
					graph.setValE(currentEdgeID, "actualRiver", newActualRiver);
					predecessorID = (predecessorID * -1);
				}
				
				/*
				 * Markierung und Inspizierung des aktuellen Knoten entfernen
				 * currentVertexID auf predecessor setzten,
				 * somit gehen wir einen Schritt zurueck 
				 */
//				deleteInspected(currentVertexID);
//				deleteMarked(currentVertexID);
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
			
			//TODO: Nur zum testen
			for (int vertexID : markedVertexIDList) {
				System.out.println("Markierten Knoten: " + graph.getStrV(vertexID, "name") + " mit PredecessorID = " + graph.getValV(vertexID, "predecessorID") + "(" + graph.getStrV(graph.getValV(vertexID, "predecessorID"), "name") + ")");
			}
			System.out.println("---------------------------------------------------------------------------");
			
			//TODO: Nur zum testen
			for (int vertexID : markedVertexIDList) {
				if (isInspected(vertexID)) {
					System.out.println("Inspizierten Knoten: " + graph.getStrV(vertexID, "name") + " mit PredecessorID = " + graph.getValV(vertexID, "predecessorID") + "(" + graph.getStrV(graph.getValV(vertexID, "predecessorID"), "name") + ")");
				}
			}
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
			this.queue = new LinkedList<Integer>();
//			//Die Queue muss immer resetet werden, sonst leiden wir in einer endlosschleife. 
//			while(this.queue != null) {
//				//this.queue.poll();
//				this.queue.peek();
//			}
			
			System.out.println("Bertette InspectRandom");
			//Holle mir zuerst alle Knoten die mit currentInspectedVertexID verbunden sind
			List<Integer> vertexIDList = graph.getAdjacent(currentInspectedVertexID);
			List<Integer> uninspectedVertexIDList = new ArrayList<>();
			List<Integer> legitimUninspectedVertexIDList = new ArrayList<>();
			
			//Jetzt filtern wir die nicht inspizierten heraus
			for (int vertexID : vertexIDList) {
				if(!isInspected(vertexID)) {
					uninspectedVertexIDList.add(vertexID);
				}
			}
			
			/*
			 * Jetzt muss geprueft werden, ob die gefilterten markiert sind, sonst duerfen wir den Knoten nicht inspizieren
			 * Etwas redundant, aber sicher
			 */
			for (int vertexID : uninspectedVertexIDList) {
				if(isMarked(vertexID)) {				
					//Sonderregelung, wenn Target mit enthalten ist, dann gehen wir auch dahin!
					if (vertexID == target) {
						setInspected(target);
						return target;
					}
					
					/*
					 * TODO: AB HIER JETZT MIT DER QUEUE
					 * Es duerfen die inspiziert werden, 
					 * die von markierten vorgaenger markiert wurden
					 */
					if (currentInspectedVertexID == graph.getValV(vertexID, "predecessorID")) {
						//legitimUninspectedVertexIDList.add(vertexID);
						this.queue.offer(vertexID);
					}
					
				}
			}			
			
			/*
			 * TODO: HIER JETZT UEBER DIE QUEQUE
			 * Hier kommt der Algorihmus in eine Sackgase
			 * und kehrt zurueck und nimm sich einen 
			 * anderen Knoten
			 */
			//if(legitimUninspectedVertexIDList.size() < 1) {			
			if(this.queue.size() < 1) {			
				return graph.getValV(currentInspectedVertexID, "predecessorID");
			}
				
			/*
			 * Hier werden alle Verteces aus der Liste entfernt, außer nur einer und das per Zufall
			 * Somit haben wir ein Psydo zufallgriff erschaffen.
			 * Dies ist der Collections Klasse zu verdanken
			 */
			//Collections.shuffle(legitimUninspectedVertexIDList); //wuerfelt den Array durch einander
			
			//Jetzt inspizieren wir den ausgewaehlten Knoten
			//setInspected(legitimUninspectedVertexIDList.get(0));
			int inspectedVertex = this.queue.peek();
			setInspected(inspectedVertex);
			
			//Den geben wir anschliessend auch wieder zurueck
			//return legitimUninspectedVertexIDList.get(0);
			return inspectedVertex;
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
					if(currentCapacity <= currentActualRiver) { //<=
						//throw new IllegalArgumentException("currentCapacity <= currentActualRiver" + "    " + currentCapacity + " <= " + currentActualRiver);
						 //throw new IllegalArgumentException( "Kein Alter <= 0 erlaubt!" );
						System.out.println(graph.getStrV(vertexID, "name") + " DARF NICHT MARKIERT WERDEN");
						continue;
					}
					 
					
					//Delta von der inspizierten Knoten hollen
					int inspectedDelta = getDelta(inspectedVertex);
					
					//Das ist ein Sonderfall, gilt nur fuer ersten durchlauf mit source
					if (inspectedDelta == INFINITE) {						
						setMarked(vertexID, inspectedVertex, currentCapacity);
						continue;
					} else {
						/*
						 * Berechnung von Delta
						 * Formel: DeltaJ = MIN( (c(e ij) - f(e ij)) ,  DeltaI)
						 */
						int buffer = currentCapacity - currentActualRiver;
						if (buffer > inspectedDelta) {
							setMarked(vertexID, inspectedVertex, inspectedDelta);
							continue;
						} else {
							setMarked(vertexID, inspectedVertex, buffer);
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
						continue;
					} else {
						/*
						 * Berechnung von Delta
						 * Formel: DeltaJ = MIN(f(e ij), DeltaI)
						 */
						if (currentCapacity > inspectedDelta) {
							setMarked(vertexID, -inspectedVertex, inspectedDelta);
							continue;
						} else {
							setMarked(vertexID, -inspectedVertex, currentCapacity);
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
					deleteMarked(vertexID);
				}
			}
			
			//Alle Inspiezierungen entfernen
			for (int vertexID : graph.getVertexes()) {
				if (vertexID == source) {
					continue;
				}
				if (isInspected(vertexID)) {
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
			return graph.getValE(edgeID, "actualRiver");
		}
		
		/**
		 * Gibt von allen Kanten die Tuple als string aus
		 */
		public void getFromAllEdgesTheTuple() {
			for (int edgeID : graph.getEdges()) {
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
		 * TODO: Auf private setzten
		 * Mit der Methode kann der tatsaechliche Fluss gesetzt werden.
		 * @param Integer currentID - Die ID zu den gehoeriger Kante
		 * @param Integer actualRiverValue - Der tatsaechliche Fluss wird mit diesen Parameter gesetzt
		 */
		public void setActualRiver(int currentID, int actualRiverValue) {
			graph.setValE(currentID, "actualRiver", actualRiverValue);
		}
		
		/**
		 * Entfernt in einer/einem {Ecke, Knoten, Vertex} eines Graphes die Markierung
		 * @param Integer currentID - Die ID von der die Markierung entfernt wird
		 */
		private void deleteMarked(Integer currentID) {
			this.graph.setValV(currentID, "predecessorID", UNDEFINEFORMARKED);
			this.graph.setValV(currentID, "delta", UNDEFINEFORMARKED);
		}
		
		
		/**
		 * Entfernt in einer/einem {Ecke, Knoten, Vertex} eines Graphes die Inspizierung
		 * @param Integer currentID - Die ID von der/dem {Ecke, Knoten, Vertex} aud der die Inspektion entfernt wird 
		 */
		private void deleteInspected(int currentID) {
			this.graph.setStrV(currentID, "inspected", EMPTY);
		}
		
		/**
		 * TODO: auf privet setzen
		 * Methode prueft ob {Ecke, Knoten, Vertex} markiert ist, bei true markiert bei false unmarkiert
		 * @param Integer currentID - ID der/des {Ecke, Knoten, Vertex} was geprueft wird
		 * @return Boolean
		 */
		public boolean isMarked(int currentID) {
			if (graph.getValV(currentID, "predecessorID") == UNDEFINEFORMARKED) {
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
				return false;
			}
			return true;
		}
}
