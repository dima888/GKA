package graph_lib;

//Package-Private
interface Edge {
	Vertex[] getVertices();
}

//VORHERIGE EDGE KLASSE
//private Vertex[] verticesFromEdge = new Vertex[2];
//private String edgeName = "";
//private boolean directed = true;
//
///**
// * Erstellt eine Kante
// * @param v1
// * @param v2
// * @param directed Bei true, ist die Kante gerichtet und bei false nicht
// */
//public Edge(Vertex v1, Vertex v2, boolean directed) {
//	this.directed = directed;
//	verticesFromEdge[0] = v1;
//	verticesFromEdge[1] = v2;
//	
//	v1.addEdge(this);
//	v2.addEdge(this);
//	
//	if(directed == true) {
//		//Am besten waere es noch, damit dann getGrad nicht aufgerufen werden kann, wenn disrected == true
//		v1.outgoingEdge.add(this);
//		v2.ingoingEdge.add(this);						
//	}			
//}
//
//public boolean getDirected() {
//	return directed;
//}
//
///**
// * 
// * @return
// */
//public Vertex getSource() {
//	return verticesFromEdge[0];
//}
//
///**
// * 
// * @return
// */
//public Vertex getTarget() {
//	return verticesFromEdge[1];
//}
//
//private String generateUniqueEdgeName() {
//	//TODO: Habe mir so gedacht, dass wir einen Counter machen, der in ein String gespeichert wird, Plus dass der Edge noch einen Namen dazu bekommt. 
//	return this.edgeName;
//}
//}
