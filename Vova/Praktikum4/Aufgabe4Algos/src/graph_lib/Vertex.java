package graph_lib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author David Asmuth, Vladimir Malikov
 */
public class Vertex {
    
    private static int idCounter = 0; // Counter for unique ids
    
    private int id;
    private String name;
    
    private Set<Integer> edges; 
    private Map<String, Integer> attributesNum;
    private Map<String, String> attributesStr;
    
    /**
     * Creates a new vertex.
     * @param name The name of the vertex.
     */
    public Vertex(String name) {
        this.attributesNum = new HashMap<>();
        this.attributesStr = new HashMap<>();
        this.edges = new HashSet<>();
        this.id = idCounter++;
        this.name = name;        
    }
    
    public void addEdge(int edgeId){
        edges.add(edgeId);
    }
    
    /**
     * Checks if contains a specific edge.
     * @param edgeId The id of the specific edge.
     * @return True if it contains edge.
     */
    public boolean containsEdge(int edgeId) {
        return edges.contains(edgeId);
    }
    
    /**
     * Removes an edge from a vertex internal edge list
     * @param edgeId The edge ID to be removes
     * @return Returns true if successfull
     */
    public boolean removeEdgeFromList(Integer edgeId) {
        return edges.remove(edgeId);
    }
    
    /**
     * Gets all edge ids from a vertex.
     * @return The edge ids as set
     */
    public Set<Integer> getEdgeIds() {
        return new HashSet<>(edges);
    }
    
    public int getAttribValuNum(String key){
        if (!attributesNum.containsKey(key)) return Integer.MAX_VALUE;
        return attributesNum.get(key);
    }
    
    public String getAttribValuStr(String key){
        if (!attributesStr.containsKey(key)) return "";
        return attributesStr.get(key);
    }
    
    public Set<String> getAllAttributes(){
        Set<String> result = new HashSet<>();
        result.addAll(attributesNum.keySet());
        result.addAll(attributesStr.keySet());
        return result;
    }
    
    // Getter Setter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setValAttr(String attribute, int value){
        attributesNum.put(attribute, value);
    }
    
    public void setStrAttr(String attribute, String value){
        attributesStr.put(attribute, value);
    }
    
    
}
