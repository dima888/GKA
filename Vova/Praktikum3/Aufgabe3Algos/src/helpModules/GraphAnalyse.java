/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpModules;

import graph_lib.AIGraph;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vladimir
 */
public class GraphAnalyse implements AIGraph {

    private final AIGraph graph;
    private int papagei = 0;

    public GraphAnalyse(AIGraph graph) {
        this.graph = graph;
    }

    @Override
    public int addEdgeD(int vertex1Id, int vertex2Id) {
        papagei++;
        return graph.addEdgeD(vertex1Id, vertex2Id);
    }

    @Override
    public int addEdgeU(int vertex1Id, int vertex2Id) {
        papagei++;
        return graph.addEdgeU(vertex1Id, vertex2Id);
    }

    @Override
    public int addVertex(String vertexName) {
        papagei++;
        return graph.addVertex(vertexName);
    }

    @Override
    public void deleteEdge(int vertex1Id, int vertex2Id) {
        papagei++;
        graph.deleteEdge(vertex1Id, vertex2Id);
    }

    @Override
    public void deleteVertex(int id) {
        papagei++;
        graph.deleteVertex(id);
    }

    @Override
    public List<Integer> getAdjacent(int vertexId) {
        papagei++;
        return graph.getAdjacent(vertexId);
    }

    @Override
    public List<String> getAttrE(int edgeId) {
        papagei++;
        return graph.getAttrE(edgeId);
    }

    @Override
    public List<String> getAttrV(int vertexId) {
        papagei++;
        return graph.getAttrV(vertexId);
    }

    @Override
    public List<Integer> getEdges() {
        papagei++;
        return graph.getEdges();
    }

    @Override
    public List<Integer> getIncident(int vertexId) {
        papagei++;
        return graph.getIncident(vertexId);
    }

    @Override
    public int getSource(int edgeId) {
        papagei++;
        return graph.getSource(edgeId);
    }

    @Override
    public String getStrE(int edgeId, String attribute) {
        papagei++;
        return graph.getStrE(edgeId, attribute);
    }

    @Override
    public String getStrV(int vertexId, String attribute) {
        papagei++;
        return graph.getStrV(vertexId, attribute);
    }

    @Override
    public int getTarget(int edgeId) {
        papagei++;
        return graph.getTarget(edgeId);
    }

    @Override
    public int getValE(int edgeId, String attribute) {
        papagei++;
        return graph.getValE(edgeId, attribute);
    }

    @Override
    public int getValV(int vertexId, String attribute) {
        papagei++;
        return graph.getValV(vertexId, attribute);
    }

    @Override
    public List<Integer> getVertices() {
        papagei++;
        return graph.getVertices();
    }

    @Override
    public boolean isEmpty() {
        papagei++;
        return graph.isEmpty();
    }

    @Override
    public void setStrE(int edgeId, String attribute, String value) {
        papagei++;
        graph.setStrE(edgeId, attribute, value);
    }

    @Override
    public void setStrV(int vertexId, String attribute, String value) {
        papagei++;
        graph.setStrV(vertexId, attribute, value);
    }

    @Override
    public void setValE(int edgeId, String attribute, int value) {
        graph.setValE(edgeId, attribute, value);
        papagei++;
    }

    @Override
    public void setValV(int vertexId, String attribute, int value) {
        papagei++;
        graph.setValV(vertexId, attribute, value);
    }

    @Override
    public Map<Integer, String> getIDtoName() {
        return graph.getIDtoName();
    }

    public int getCounter() {
        return papagei;
    }
    
    public void resetCounter(){
        papagei = 0;
    }

    @Override
    public boolean isDirected() {
        papagei++;
        return graph.isDirected();
    }
}
