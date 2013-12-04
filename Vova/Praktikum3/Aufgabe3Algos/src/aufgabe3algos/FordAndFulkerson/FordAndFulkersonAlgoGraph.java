/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe3algos.FordAndFulkerson;

import graph_lib.*;
import java.util.*;

/**
 *
 * @author Vladimir
 */
public class FordAndFulkersonAlgoGraph {

    private final AIGraph graph;
    private final int initialValue;
    private int source;
    private int sink;
    private List<Integer> vertexIDs;
    private List<Integer> edgesIDs;

    Map<Integer, String> resMap;

    private final Integer UNDIFINED = -1;
    private final Integer SUCCESSOR = 0;
    private final Integer PREDECESSOR = 1;

    private final Integer VERTEX_POS = 0;
    private final Integer VERTEX_ID = 1;
    private final Integer VERTEX_FLOW = 2;

    private Integer resultValue;

    private Integer debugInteger = 0;

    private final String AFLOW = "atr1";
    private final String WFLOW = "workFlow";
    private final String DIREC = "direction";
    private final String VERTX = "vertex";
    private final String DELTA = "delta";
    private final String INSPC = "inspected";

    /**
     * Initiales Wert entspricht einem Null.
     *
     * @param graph
     */
    public FordAndFulkersonAlgoGraph(AIGraph graph) {
        this.graph = graph;
        this.initialValue = 0;
//        this.inspectedList = new HashMap<>();

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
    public FordAndFulkersonAlgoGraph(AIGraph graph, int initialValue) {
        this.graph = graph;
        this.initialValue = initialValue;

        resMap = graph.getIDtoName();
        init();
    }

    private void init() {
        edgesIDs = graph.getEdges();
        vertexIDs = graph.getVertices();
        searchSource();
        searchSink();
        for (Integer edgeId : edgesIDs) {
            graph.setValE(edgeId, WFLOW, initialValue);
        }
        graph.setValV(source, DIREC, UNDIFINED);
        graph.setValV(source, VERTX, UNDIFINED);
        graph.setValV(source, DELTA, Integer.MAX_VALUE - 1);
        graph.setValV(source, INSPC, 0);
        System.out.println("Jetzt wird gerechnet");
        seconStep();

    }

    private void seconStep() {
        while (hasNotInspected()) {
            if (graph.getValV(sink, INSPC) == 0) {
                thirdStep();
            }
            Integer workVertex = giveMeOneVertex();
            List<List<Integer>> workEdges = makeInOutList(workVertex);
            makeMarkForOut(workVertex, workEdges.get(0));
            makeMarkForIn(workVertex, workEdges.get(1));
            graph.setValV(workVertex, INSPC, 1);

        }
        fourthStep();
    }

    private void thirdStep() {
        Integer pseudoStartVertex = sink;
        Integer deltaFlow = graph.getValV(pseudoStartVertex, DELTA);
        Integer direction = graph.getValV(pseudoStartVertex, DIREC);
        Integer pseudoEndVertex = graph.getValV(pseudoStartVertex, VERTX);
        while (direction != UNDIFINED) {

            if (direction == PREDECESSOR) {
                Integer workEdgeId = getEdge(pseudoStartVertex, pseudoEndVertex, direction);
                Integer workFLow = graph.getValE(workEdgeId, WFLOW) + deltaFlow;
                graph.setValE(workEdgeId, WFLOW, workFLow);
            } else {
                Integer workEdgeId = getEdge(pseudoStartVertex, pseudoEndVertex, direction);
                Integer workFLow = graph.getValE(workEdgeId, WFLOW) - deltaFlow;
                graph.setValE(workEdgeId, WFLOW, workFLow);
            }

            pseudoStartVertex = pseudoEndVertex;
            direction = graph.getValV(pseudoStartVertex, DIREC);
            pseudoEndVertex = graph.getValV(pseudoStartVertex, VERTX);

        }
        resetAllMarker();
    }

    private void fourthStep() {
        Integer outerValue = 0;
        Integer innerValue = 0;
        List<List<Integer>> allEdges = edgesAmongQAndNegQ();
        List<Integer> outerEdges = allEdges.get(0);

        for (Integer edgeId : outerEdges) {
            outerValue += graph.getValE(edgeId, WFLOW);
        }

        List<Integer> innerEdges = allEdges.get(1);

        for (Integer edgeId : innerEdges) {
           outerValue += graph.getValE(edgeId, WFLOW);
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
        List<Integer> listOfMarkedVertices = new ArrayList<>();
        for (Integer vertexId : vertexIDs) {

            if (graph.getValV(vertexId, INSPC) == 0) {
                listOfMarkedVertices.add(vertexId);
            }

        }
        Collections.shuffle(listOfMarkedVertices);
        return listOfMarkedVertices.get(0);
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

            if (!(isMarked(workVertex))) {
                Integer workFlow = graph.getValE(edgeId, WFLOW);
                Integer actualFlow = graph.getValE(edgeId, AFLOW);
                if (workFlow < actualFlow) {
                    graph.setValV(workVertex, DIREC, PREDECESSOR);
                    graph.setValV(workVertex, VERTX, vertexId);
                    graph.setValV(workVertex, DELTA, Math.min((actualFlow - workFlow), graph.getValV(workVertex, DELTA)));
                    graph.setValV(workVertex, INSPC, 0);
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
            if (!(isMarked(workVertex))) {
                Integer workFlow = graph.getValE(edgeId, WFLOW);
                if (workFlow > 0) {
                    graph.setValV(workVertex, DIREC, SUCCESSOR);
                    graph.setValV(workVertex, VERTX, vertexId);
                    graph.setValV(workVertex, DELTA, Math.min((workFlow), graph.getValV(workVertex, DELTA)));
                    graph.setValV(workVertex, INSPC, 0);
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
        for (Integer vertexId : vertexIDs) {
            graph.setValV(vertexId, DIREC, UNDIFINED);
            graph.setValV(vertexId, VERTX, UNDIFINED);
            graph.setValV(vertexId, DELTA, Integer.MAX_VALUE-1);
            graph.setValV(vertexId, INSPC, Integer.MAX_VALUE);
        }
        
        graph.setValV(source, INSPC, 0);
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

        List<Integer> markedVerticesList = new ArrayList<>();
        for (Integer vertexId : vertexIDs) {
            if(isMarked(vertexId)) markedVerticesList.add(vertexId);
        }
        for (Integer vertexId : markedVerticesList) {
            for (Integer edgeId : graph.getIncident(vertexId)) {
                if (!isMarked(graph.getTarget(edgeId))) {
                    outerEdges.add(edgeId);
                } else if (!isMarked(graph.getSource(edgeId))) {
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

    private boolean hasNotInspected() {
        for (Integer integer : vertexIDs) {
            if (graph.getValV(integer, INSPC) == 0) {
                return true;
            }
        }

        return false;

    }

    private boolean isMarked(Integer workVertex) {

        return (graph.getValV(workVertex, INSPC) != Integer.MAX_VALUE);

    }

}
