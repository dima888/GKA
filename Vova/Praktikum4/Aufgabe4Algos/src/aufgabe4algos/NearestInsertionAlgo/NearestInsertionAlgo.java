/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe4algos.NearestInsertionAlgo;

import graph_lib.AIGraph;
import helpModules.*;
import java.util.*;

/**
 *
 * @author abg627
 */
public class NearestInsertionAlgo {

    private final Matrix edgesValue;
    private final AIGraph graph;
    private final List<Integer> vertices;
    private final List<Integer> edges;
    private List<Integer> way;
    private List<Integer> remain;

    public NearestInsertionAlgo(AIGraph graph) {
        this.graph = graph;
        way = new ArrayList<>();
        remain = new ArrayList<>();
        vertices = graph.getVertices();
        edges = graph.getEdges();
        edgesValue = new MatrixImplB(vertices.size());
        init();
        stepTwo();
    }

    private void init() {
        // Die ersten zwei Elemnte des Weges werden gesetzt. 
        way.add(0);
        way.add(0);
        
        for (Integer edgeID : edges) {
            edgesValue.setBoth(graph.getSource(edgeID), graph.getTarget(edgeID), graph.getValE(edgeID, "atr1"));
        }

        resortRemain();
    }

    private void stepTwo() {
        while(!remain.isEmpty()){
        int workValue = Integer.MAX_VALUE - 1;
        int index = -1;
        //Bestimmen welche Ecke im Restgraphen am billiegsten zu erreichen ist.
        for (Integer vertexID : remain) {
            if (workValue > returnLowerstLocal(vertexID)) {
                index = vertexID;
            }
        }
        way.add(way.size() - 1, index);
        resortRemain();
        //Bestimmen welchen Weg am günstigsten ist.
        permutation();
        }
    }

    private void permutation() {
        List<Integer> workWay = deepCopy(way);
        List<Integer> workResult = deepCopy(way);
        int value = workWay.get(workWay.size()-2);
        workWay.remove(Integer.valueOf(value));
        int summOfValues = Integer.MAX_VALUE-1;
        for (int i = 1; i < way.size()-2; i++) {
            workWay.add(i, value);
            if(summOfAllWayValues(workWay) < summOfValues) workResult = deepCopy(workWay);
            workWay.remove(Integer.valueOf(value));
        }
        
        way = deepCopy(workResult);
    }

// Hilfsmethoden
    /**
     * Gibt den lokalen kleinsten Wert für eine Ecke aus dem Restgraph zum Weg.
     * @param target
     * @return 
     */
    private int returnLowerstLocal(int target) {
        int result = Integer.MAX_VALUE - 1;
        //System.out.println(edgesValue.toString());
        for (Integer vertexID : way) {
            result = Math.min(edgesValue.getAt(target, vertexID), result);
        }

        return result;
    }

    /**
     * Die restlichen Ecken werden neu bestimmt.
     */
    private void resortRemain() {
        List<Integer> workRemain = new ArrayList<>();
        for (Integer vertexID : vertices) {
            if (!way.contains(vertexID)) {
                workRemain.add(vertexID);
            }
        }
        remain = workRemain;
    }

    /**
     * Erstelle eine andere Liste mit den gleichen Werten.
     * @param toCopy
     * @return 
     */
    private List<Integer> deepCopy(List<Integer> toCopy) {
        List<Integer> result = new ArrayList<>();
        for (Integer vertexID : toCopy) {
            result.add(vertexID);
        }
        return result;
    }

    /**
     * Berchnet die Kosten für einen Weg.
     * @param way
     * @return 
     */
    private int summOfAllWayValues(List<Integer> way) {
        int result = 0;
        Iterator<Integer> it = way.iterator();
        Integer wayPointCurrent = it.next();
        Integer wayPointNext = it.next();
        while (it.hasNext()) {
            result += edgesValue.getAt(wayPointCurrent, wayPointNext);
            wayPointCurrent = wayPointNext;
            wayPointNext = it.next();
        }
        return result;
    }
    
    @Override
    public String toString(){
        Map<Integer,String> allVertices = graph.getIDtoName();
        String result = "";
        for (Integer vertexId : way) {
            result += (" -> " + allVertices.get(vertexId));
        }
        
        return result;
    }

}
