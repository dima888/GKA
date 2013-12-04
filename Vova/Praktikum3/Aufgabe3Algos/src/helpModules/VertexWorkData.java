/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helpModules;

import java.util.*;
/**
 *
 * @author Vladimir
 */
public class VertexWorkData {
    
   private Map<Integer, Integer[]> dataMatrix;
    
    public VertexWorkData() {
        dataMatrix = new HashMap<>();
    }
    
    public void addNewData(Integer vertexId, Integer direction, Integer otherVertexId, Integer flow){
        Integer[] newData = new Integer[3];
        newData[0] = direction;
        newData[1] = otherVertexId;
        newData[2] = flow;
        dataMatrix.put(vertexId, newData);
    }   
    
    public Integer[] getDataOfVertex(Integer vertexId){
        return dataMatrix.get(vertexId);
    }
    
    public Boolean isMarked(Integer vertexId){
        return dataMatrix.containsKey(vertexId);
    }
    
    public List<Integer> getMarkedList(){
        List<Integer> result = new ArrayList<>(dataMatrix.keySet());
        return result;
    }
    
    public List<Integer> getMarkedListRand(){
        List<Integer> result = new ArrayList<>(dataMatrix.keySet());
        Collections.shuffle(result);
        return result;
    }
    
   @Override
    public String toString(){
        String result = "";
        for (Map.Entry<Integer, Integer[]> entry : dataMatrix.entrySet()) {
            Integer integer = entry.getKey();
            Integer[] integers = entry.getValue();
            result += ("ID " + integer + " is marked as " + "dirc: " + integers[0] + " otherV: " + integers[1] + " flow: " + integers[2] );
            result += " | ";
        }
         return result;
    }
}
