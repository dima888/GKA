package graph_lib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author David Asmuth, Vladimir Malikov
 */
public class Edge {
    
    private static int idCounter = 0; // Counter for unique ids

    private int id;
    private boolean isDirected;
    private Vertex start; // aka. left
    private Vertex end; // aka. right
    
    private Map<String, Integer> attributesNum;
    private Map<String, String> attributesStr;
    
    private Edge(Vertex start, Vertex end, boolean isDirected) {
        this.attributesNum = new HashMap<>();
        this.attributesStr = new HashMap<>();
        this.id = idCounter++;
        this.isDirected = isDirected;
        this.start = start;
        this.end = end;
    }
    
    /**
     * Creates a new Edge object and adds it to two given vertices.
     * @param start Start vertex. When is directed, this is the source.
     * @param end End vertex. When its directed, this is the target.
     * @param isDirected If the edge has a direction.
     * @return The instance of the edge.
     */
    public static Edge Create(Vertex start, Vertex end, boolean isDirected) {
        Edge edge = new Edge(start, end, isDirected);
        start.addEdge(edge.id);
        end.addEdge(edge.id);
        return edge;
    }
    
    /**
     * Removes itself correctly from all adjacent vertices. 
     * @return True if removing was successful.
     */
    public boolean remove() {
        return start.removeEdgeFromList(id) && end.removeEdgeFromList(id);
    }
    
    /**
     * Checks if a vertex is adjacent to this edge.
     * @param vertexId The given vertex id to look at.
     * @return True when vertex is adjacent.
     */
    public boolean containsVertexId(int vertexId) {
        return (start.getId() == vertexId) || (end.getId() == vertexId);
    }
    
    /**
     * Gets the other side of the edge.
     * @param self The caller
     * @return The other vertex
     */
    public int getCounterpart(int selfId) {
        if (selfId == start.getId()) return end.getId(); else return start.getId();
    }
    
    /**
     * Returns a Integer value from a given attribute name.
     * @param name Attribute name
     * @return Value of the attribute 
     */
    public int getAttribValuNum(String name){
        if (attributesNum.containsKey(name)) return attributesNum.get(name);
        return Integer.MAX_VALUE;
    }
    
    /**
     * Returns a String value from a given attribute name.
     * @param name Attribute name
     * @return Value of the attribute 
     */
    public String getAttribValuStr(String name){
        if (attributesStr.containsKey(name)) return attributesStr.get(name);
        return "";
    }
    
    /**
     * Returns a list of all attribute names for this edge.
     * @return The list of names.
     */
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

    public boolean isIsDirected() {
        return isDirected;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setValAttr(String attribute, int value){
        attributesNum.put(attribute, value);
    }
    
    public void setStrAttr(String attribute, String value){
        attributesStr.put(attribute, value);
    }
    
    
    
}
