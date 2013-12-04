/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helpModules;

import graph_lib.*;
import java.util.*;

/**
 *
 * @author Vladimir
 */
public class EdgeWorkData {

    private final Map<Integer, Integer> actualFlowMap;
    private final Map<Integer, Integer> workFlowMap;
    private final List<Integer> edges;
    public EdgeWorkData(List<Integer> edgeIds, Integer initFlow, AIGraph graph ) {
        this.edges = edgeIds;
        actualFlowMap = new HashMap<>();
        workFlowMap = new HashMap<>();
        for (Integer edgeID : edgeIds) {
            actualFlowMap.put(edgeID, graph.getValE(edgeID, "atr1"));
            workFlowMap.put(edgeID, initFlow);
        }
    }
    
    public void setValues(Integer idEdge, Integer actualFlow, Integer workFlow){
            actualFlowMap.put(idEdge, actualFlow);
            workFlowMap.put(idEdge, workFlow);
    }
    
    public Integer getActualFlow(Integer idEdge){
        return actualFlowMap.get(idEdge);
    }
    
    public Integer getWorkFlow(Integer idEdge){
        return workFlowMap.get(idEdge);
    }
    
    public void changeActualFlow(Integer idEdge, Integer actualFlow){
            actualFlowMap.put(idEdge, actualFlow);
    }
    
    public void changeWorkFlow(Integer idEdge, Integer workFlow){
            workFlowMap.put(idEdge, workFlow);
    }
    
    @Override
    public String toString(){
        String result = ("Die Kanten haben folgende Werte: " + edges.size());
        for (Integer edgeId : edges) {
            result += "(";
            result += actualFlowMap.get(edgeId).toString() + "," + workFlowMap.get(edgeId).toString() + ")|";
        }
        return result;
    }
}
