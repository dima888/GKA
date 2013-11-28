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

/*
 * Der Algorithmus von Ford und Fulkerson IM GRBUCH SEITE 98
 */
public class FordAndFulkerson {
		
	//*****************INSTANZVARIABLEN*******************
	private static final String INFINITE = "(undefine, ∞)";
	private static final String INSPECTED = "*";
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
	 * @param args
	 */
	public int getOptiomalRiver() {
		return this.optimalRiver;
	}
	
	/**
	 * Such nach maximaler(optimaler) Flusssträke, größte Anzahl an "Paketen"
	 * @param AIGraph graph - Der Graph braucht den richtigen Start und Ziel, 
	 * 		  sonst koennen falsche Ergebnisse auftrette
	 * @param Integer source - Start Knoten
	 * @param Integer target - Ziel Knoten
	 * @return AIGraph 
	 */
	public AIGraph startAlgorithmus(AIGraph graph, int source, int target) {
		AIGraph resultGraph = graph;
		
		//Erster Schritt, f(e ij); Ist bei uns schon fertig, da wir den tatsaechlichen Fluss schon mit 0 initialisieren
		
		//Pruefen ob in den Graphen Source und Target vorhanden ist, wenn nicht, dann mit einer Exception die Methode beenden
		//Habe das weggelassen, wurde mir doch zu kompliziert, wenn die Austauschbarkeit noch erhalten bleiben soll
		
		//Start Knoten Markieren und inspizieren
		graph.setStrV(source, "marked", INFINITE);
		graph.setStrV(source, "inspected", INSPECTED);
		
		return resultGraph;
	}
	
//	/**
//	 * Methode gibt uns true zurueck, wenn in den Graphen genau eine Quelle exestiert
//	 * @param AIGraph graph - Ein Graph
//	 * @return boolean
//	 */
//	public boolean hasSource(AIGraph graph) {
//		List<Integer> buffer = new ArrayList<>();
//		List<Integer> vertexIds = graph.getVertexes();
//		graph.getV
//		return true;
//	}
//	
//	/**
//	 * Prüft für eine gerichtete Kante, ob sie eine Senke hat
//	 * @return boolean true, wenn die aufrufende Kante eine Senke hat, sonst false
//	 */
//	public boolean hatSenke() {
//		List<Integer> buffer = new ArrayList<>();	
//					
//		Vertex target = this.getVertices()[1];
//		if(target.getOutgoingEdge().size() > 1) {
//			return false;
//		}
//		return true;
//	}
//	
//	public static void main(String[] args) {
//		
//	}
	
}

