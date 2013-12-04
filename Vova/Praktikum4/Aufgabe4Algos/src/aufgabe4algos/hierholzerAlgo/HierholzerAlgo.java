/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe4algos.hierholzerAlgo;

import graph_lib.AIGraph;
import helpModules.AdjazenzMatrix;
import java.util.*;

/**
 *
 * @author abg627
 */
public class HierholzerAlgo {

    private final AIGraph graph;
    private final AdjazenzMatrix matrix;
    private List<Integer> result;
    private final List<Integer> verticesIDs;

    public HierholzerAlgo(AIGraph graph) {
        this.graph = graph;
        verticesIDs = graph.getVertices();
        matrix = new AdjazenzMatrix(graph);
        stepOne();
    }

    private void stepOne() {
        result = matrix.searchCircle(verticesIDs.get(0));
        stepTwo();
    }

    private void stepTwo() {
            
        while (matrix.edgesExt()) {
            Integer workVertexId = matrix.firszWithNoZero(result);
            
            if(workVertexId != -1){
            List<Integer> workResult = matrix.searchCircle(workVertexId);
            mergeTwoList(workVertexId, workResult);
                
            }
        }

    }

    //Hilfsmethoden
    private void mergeTwoList(Integer szartEndVertex, List<Integer> withList) {
        List<Integer> workList = new ArrayList<>();
        Boolean isDone = false;
        if(!withList.isEmpty()){
        for (Integer vertexID : result) {
            if (vertexID == szartEndVertex && !isDone) {
                isDone = true;
                workList.addAll(withList);
            }else{
                workList.add(vertexID);
            }
       }
           
          result = workList;
           System.out.println("Result is -> " + result);
        }
    }

    @Override
    public String toString(){
        String resultString = "";
        Map<Integer, String> allVerticex = graph.getIDtoName();
        
        for (Integer vertexId : result) {
            resultString += " -> " + allVerticex.get(vertexId);
        }
        System.err.println(result.size());
        return resultString;
    }
    
}
