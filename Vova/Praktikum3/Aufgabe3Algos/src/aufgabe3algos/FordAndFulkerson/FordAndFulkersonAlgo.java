/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe3algos.FordAndFulkerson;

import helpModules.EdgeWorkData;
import helpModules.VertexWorkData;
import graph_lib.*;
import java.util.*;

/**
 *
 * @author Vladimir
 */
public class FordAndFulkersonAlgo {

    private final AIGraph graph;
    private final int initialValue;
    private int source;
    private int sink;
    private List<Integer> vertexIDs;
    private List<Integer> edgesIDs;
    private Map<Integer, Boolean> inspectedList;
    private EdgeWorkData edgeData;
    private VertexWorkData vertexData;

    Map<Integer, String> resMap;

    private final Integer UNDIFINED = -1;
    private final Integer SUCCESSOR = 0;
    private final Integer PREDECESSOR = 1;

    private final Integer VERTEX_POS = 0;
    private final Integer VERTEX_ID = 1;
    private final Integer VERTEX_FLOW = 2;

    private Integer resultValue;

    private Integer debugInteger = 0;

    /**
     * Initiales Wert entspricht einem Null.
     *
     * @param graph
     */
    public FordAndFulkersonAlgo(AIGraph graph) {
        this.graph = graph;
        this.initialValue = 0;
        this.inspectedList = new HashMap<>();

        resMap = graph.getIDtoName();
        init();
    }

    /**
     * Initialles Wert kann einem Null entsprechen. Sonst wird Machbarkeit
     * bestimmt.
     *
     * @param graph
     * @param initialValue
     */
    public FordAndFulkersonAlgo(AIGraph graph, int initialValue) {
        this.graph = graph;
        this.initialValue = initialValue;
        this.inspectedList = new HashMap<>();

        resMap = graph.getIDtoName();
        init();
    }

    private void init() {
        edgesIDs = graph.getEdges();
        vertexIDs = graph.getVertices();
        searchSource();
        searchSink();
        edgeData = new EdgeWorkData(edgesIDs, initialValue, graph);
        vertexData = new VertexWorkData();
        vertexData.addNewData(source, UNDIFINED, UNDIFINED, Integer.MAX_VALUE - 1);
        inspectedList.put(source, false);
        System.out.println("Jetzt wird gerechnet");
        secondStep();
    }

    private void secondStep() {
        while ((inspectedList.values().contains(false))) {
            if (vertexData.isMarked(sink)) {
                thirdStep();
               // System.err.println("H: " + inspectedList.toString());
            }
            Integer workVertex = giveMeOneVertex();
            List<List<Integer>> workEdges = makeInOutList(workVertex);
            makeMarkForOut(workVertex, workEdges.get(0));
            makeMarkForIn(workVertex, workEdges.get(1));
            inspectedList.put(workVertex, true);

        }
        fourthStep();
    }

    private void thirdStep() {
        debugInteger++;
        Integer pseudoStartVertex = sink;
        Integer deltaFlow = vertexData.getDataOfVertex(pseudoStartVertex)[VERTEX_FLOW];
        Integer direction = vertexData.getDataOfVertex(pseudoStartVertex)[VERTEX_POS];
        Integer pseudoEndVertex = vertexData.getDataOfVertex(pseudoStartVertex)[VERTEX_ID];
        while (direction != UNDIFINED) {

            if (direction == PREDECESSOR) {
                Integer workEdgeId = getEdge(pseudoStartVertex, pseudoEndVertex, direction);
                edgeData.changeWorkFlow(workEdgeId, (edgeData.getWorkFlow(workEdgeId) + deltaFlow));
            } else {
                Integer workEdgeId = getEdge(pseudoStartVertex, pseudoEndVertex, direction);
                edgeData.changeWorkFlow(workEdgeId, (edgeData.getWorkFlow(workEdgeId) - deltaFlow));
            }

            pseudoStartVertex = pseudoEndVertex;
            direction = vertexData.getDataOfVertex(pseudoStartVertex)[VERTEX_POS];
            pseudoEndVertex = vertexData.getDataOfVertex(pseudoStartVertex)[VERTEX_ID];

        }
        resetAllMarker();
    }

    private void fourthStep() {
        Integer outerValue = 0;
        Integer innerValue = 0;
        List<List<Integer>> allEdges = edgesAmongQAndNegQ();
        List<Integer> outerEdges = allEdges.get(0);

        for (Integer edgeId : outerEdges) {
            outerValue += edgeData.getWorkFlow(edgeId);
        }

        List<Integer> innerEdges = allEdges.get(1);

        for (Integer edgeId : innerEdges) {
            innerValue += edgeData.getWorkFlow(edgeId);
        }

        resultValue = outerValue - innerValue;

    }

    //Hilfsmethoden
    /**
     * Suche nach der Quelle Quelle - eine (1! nur eine!) Ecke die keine
     * eingehenden Kanten hat. Mindesten eine ausgehende Kante soll vorhanden
     * sein.
     */
    private void searchSource() {
        for (Integer vertexId : vertexIDs) {
            Boolean workFlag = true;
            List<Integer> edges = graph.getIncident(vertexId);
            for (Integer edgeId : edges) {
                if (vertexId == graph.getTarget(edgeId)) {
                    workFlag = false;
                }
            }
            if (workFlag) {
                source = vertexId;
                return;
            }
        }
    }

    /**
     * Suche nach Senke Senke - eine(1! nur eine!) Ecke die keine ausgehenden
     * Kanten hat. Mindestens eine eingehende Kante soll vorhanden sein.
     */
    private void searchSink() {
        for (Integer vertexId : vertexIDs) {
            Boolean workFlag = true;
            List<Integer> edges = graph.getIncident(vertexId);
            for (Integer edgeId : edges) {
                if (vertexId == graph.getSource(edgeId)) {
                    workFlag = false;
                }
            }
            if (workFlag) {
                sink = vertexId;
                return;
            }
        }
    }

    /**
     * Gibt einen beliebige Ecke zurück, die noch nicht inspeziert wurde.
     *
     * @return
     */
    private Integer giveMeOneVertex() {
        List<Integer> listOfMarkedVertices = vertexData.getMarkedListRand();
        for (Integer vertexId : listOfMarkedVertices) {
            if (inspectedList.get(vertexId) == false) {
                return vertexId;
            }
        }
        return null;
    }

    /**
     * Erstelle zwei Listen von Kanten die rausgehen und reingehen
     *
     * @param vertexId
     * @return
     */
    private List<List<Integer>> makeInOutList(Integer vertexId) {
        List<Integer> inList = new ArrayList<>();
        List<Integer> outList = new ArrayList<>();
        List<List<Integer>> resultList = new ArrayList<>();

        List<Integer> edges = graph.getIncident(vertexId);
       
        for (Integer edgeId : edges) {
            if (graph.getSource(edgeId) == vertexId) {
                outList.add(edgeId);
            } else {
                inList.add(edgeId);
            }
        }

        resultList.add(outList);
        resultList.add(inList);
        return resultList;
    }

    /**
     * Die Zielecken werden entsprechend Algorithmus markiert. Alles Zielecken
     * von ausgehenden Kanten werden markiert.
     */
    private void makeMarkForOut(Integer vertexId, List<Integer> workEdges) {
        for (Integer edgeId : workEdges) {
            Integer workVertex = graph.getTarget(edgeId);
            
            if (!(vertexData.isMarked(workVertex))) {
              
                if (edgeData.getWorkFlow(edgeId) < edgeData.getActualFlow(edgeId)) {
                    vertexData.addNewData(workVertex, PREDECESSOR, vertexId, Math.min((edgeData.getActualFlow(edgeId) - edgeData.getWorkFlow(edgeId)), vertexData.getDataOfVertex(vertexId)[VERTEX_FLOW]));

                    inspectedList.put(workVertex, false);
                }
            }
        }

    }

    /**
     * Die Quelecken von allen eingehenden Kanten von angegebener Ecke werden
     * entsprechen den Algorithmus markiert
     *
     * @param vertexId
     * @param workEdges
     */
    private void makeMarkForIn(Integer vertexId, List<Integer> workEdges) {

        for (Integer edgeId : workEdges) {
            Integer workVertex = graph.getSource(edgeId);
            if (!(vertexData.isMarked(workVertex))) {
                if (edgeData.getWorkFlow(edgeId) > 0) {
                    vertexData.addNewData(workVertex, SUCCESSOR, vertexId, Math.min((edgeData.getWorkFlow(edgeId)), vertexData.getDataOfVertex(vertexId)[VERTEX_FLOW]));
                    inspectedList.put(workVertex, false);
                }
            }
        }

    }

    /**
     * Bestimmt anhand von zwei Ecken ID die Kanten ID, die dazwischen verläuft.
     * Funktioniert nur wenn es eine Kante ist!
     *
     * @param source
     * @param target
     * @param direction
     * @return
     */
    private Integer getEdge(Integer source, Integer target, Integer direction) {
        for (Integer edgeId : graph.getIncident(source)) {
            if (direction == PREDECESSOR) {
                if (target == graph.getSource(edgeId)) {
                    return edgeId;
                }
            } else if (direction == SUCCESSOR) {
                if (target == graph.getTarget(edgeId)) {
                    return edgeId;
                }
            }
        }
        return null;
    }

    /**
     * Lösche alle vorher gestze Markirungen mit Ausnahme von source-Markierung.
     * Die Liste der Inspezierten wird vollständig zurückgesetzt.
     */
    private void resetAllMarker() {
        vertexData = new VertexWorkData();
        vertexData.addNewData(source, UNDIFINED, UNDIFINED, Integer.MAX_VALUE - 1);
        inspectedList = new HashMap<>();
        inspectedList.put(source, false);
    }

    /**
     * Erstellt zwei Listen mit Kanten IDs. Eine Liste mit Kanten die aus der
     * Menge Q rausgehen. ANdere Liste mit Kanten die in die Menge Q reingehen.
     *
     * @return
     */
    private List<List<Integer>> edgesAmongQAndNegQ() {
        List<Integer> outerEdges = new ArrayList<>();
        List<Integer> innerEdges = new ArrayList<>();
        List<List<Integer>> listOfEdges = new ArrayList<>();

        List<Integer> markedVerticesList = vertexData.getMarkedListRand();
        for (Integer vertexId : markedVerticesList) {
            for (Integer edgeId : graph.getIncident(vertexId)) {
                if (!vertexData.isMarked(graph.getTarget(edgeId))) {
                    outerEdges.add(edgeId);
                } else if (!vertexData.isMarked(graph.getSource(edgeId))) {
                    System.out.println("HALLO");
                    innerEdges.add(edgeId);
                }
            }
        }

        listOfEdges.add(outerEdges);
        listOfEdges.add(innerEdges);
        return listOfEdges;
    }

   public String getResultValue() {
        String result = "";
        result += ("Von " + resMap.get(source) + " nach " + resMap.get(sink) + " fließt max. " + resultValue + " durch");
        return result;
    }

}
