/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpModules;

import graph_lib.AIGraph;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author abg627
 */
public class AdjazenzMatrix {

    private final ConcurrentMap<Integer, List<Integer>> matrix;
    private final ConcurrentMap<Integer, Boolean> visited;
    private final ConcurrentMap<Integer, Boolean> finished;
    private final List<Integer> marked;
    private final List<Integer> verticesIDs;
    private List<Integer> result;
    private Boolean runFlag;

    public AdjazenzMatrix(AIGraph graph) {
        matrix = new ConcurrentHashMap<>();
        visited = new ConcurrentHashMap<>();
        finished = new ConcurrentHashMap<>();
        marked = new ArrayList<>();
        verticesIDs = graph.getVertices();
        for (Integer vertexID : verticesIDs) {
            List<Integer> adjezenzList = graph.getAdjacent(vertexID);
            matrix.put(vertexID, adjezenzList);
            visited.put(vertexID, false);
            finished.put(vertexID, false);
        }
        System.out.println(matrix.toString());
        runFlag = true;
    }

    public List<Integer> searchCircle(Integer startVertex) {
        result = new ArrayList<>();
        helpmeSearchCircle(startVertex, null);
        cleanAll(result);
         System.out.println(result.toString());
        return result;
    }

    public void helpmeSearchCircle(Integer vertexId, Integer withOut) {
        if (runFlag) {
            if (!finished.get(vertexId)) {
                if (visited.get(vertexId)) {
                    result.add(vertexId);
                    runFlag = false;
                } else {

                    visited.put(vertexId, true);
                    result.add(vertexId);
                    for (Integer integerV : matrix.get(vertexId)) {
                        if (integerV != withOut) {
                            helpmeSearchCircle(integerV, vertexId);
                        }
                    }
                    finished.put(vertexId, true);
                }
            }
        }
        if (!result.isEmpty()) {
           
            if (result.get(0) != result.get(result.size() - 1)) {
                 
                result = new ArrayList<>();
            }
        }
        
        if(!result.isEmpty()){
           marked.clear();
        }
    }

    private void cleanAll(List<Integer> toClean) {
        Iterator<Integer> it = toClean.iterator();
        if ((!toClean.isEmpty()) && toClean.size() > 1) {
            Integer work = it.next();
            Integer next = it.next();
            for (int i = 0; i < toClean.size(); i++) {

                List<Integer> newEdges = matrix.get(work);
                newEdges.remove(Integer.valueOf(next));
                matrix.put(work, newEdges);
                newEdges = matrix.get(next);
                newEdges.remove(Integer.valueOf(work));
                matrix.put(next, newEdges);
                if (it.hasNext()) {
                    work = next;
                    next = it.next();
                }

            }
        }

        for (Integer vertexID : verticesIDs) {
            visited.put(vertexID, false);
            finished.put(vertexID, false);
        }
 
       
        runFlag = true;

    }

    public Integer firszWithNoZero(List<Integer> startPoints) {

        for (Integer integer : startPoints) {
            if (matrix.get(integer).size() > 0 && !marked.contains(integer)) {
                marked.add(integer);
                return integer;
            }
           
        }

        return -1;

    }

    public Boolean edgesExt() {
        
        Boolean resilt = false;
        for (Map.Entry<Integer, List<Integer>> entry : matrix.entrySet()) {
            Integer integer = entry.getKey();
            List<Integer> list = entry.getValue();
            resilt = resilt || (!list.isEmpty());
        }
      
        return resilt;
    }
}
