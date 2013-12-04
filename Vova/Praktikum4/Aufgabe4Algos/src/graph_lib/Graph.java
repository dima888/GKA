package graph_lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author David Asmuth, Vladimir Malikov
 */
public class Graph implements AIGraph {
    
    private Map<Integer, Vertex> vertices;
    private Map<Integer, Edge> edges;
    private boolean isDirected;
    
    public static Graph init(boolean isDirected) {
        return new Graph();
    }

    public Graph() {
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
        
    }  
    
    /**
     * Adds a vertex to the graph, by name
     * @param vertexName Name of the new vertex
     * @return return The id of the vertex
     */
    @Override
    public int addVertex(String vertexName) {
        Vertex newVertex = new Vertex(vertexName);
        vertices.put(newVertex.getId(), newVertex);
        return newVertex.getId();
    }
    
    /**
     * Deletes a vertex by its id
     * @param id ID of the vertex to delete  
     */
    @Override
    public void deleteVertex(int id) {
        Set<Integer> edgeIds;
        if (vertices.containsKey(id)) {
            edgeIds = vertices.remove(id).getEdgeIds();
        } else return;
        
        for (Integer edgeId : edgeIds) {
            edges.remove(edgeId).remove();
        }
    }
    
    /**
     * Creates a edge between 2 vertices. Undirected.
     * @param vertex1Id First vertex ID
     * @param vertex2Id Second vertex ID
     * @return The ID of the edge
     */
    @Override
    public int addEdgeU(int vertex1Id, int vertex2Id) {
        Edge newEdge = Edge.Create(vertices.get(vertex1Id), vertices.get(vertex2Id), false);
        edges.put(newEdge.getId(), newEdge);
        return newEdge.getId();
    }
    
    /**
     * Creates a edge between 2 vertices. Directed.
     * @param vertex1Id First vertex ID
     * @param vertex2Id Second vertex ID
     * @return The ID of the edge
     */
    @Override
    public int addEdgeD(int vertex1Id, int vertex2Id) {
        Edge newEdge = Edge.Create(vertices.get(vertex1Id), vertices.get(vertex2Id), true);
        edges.put(newEdge.getId(), newEdge);
        return newEdge.getId();
    }
    
    /**
     * Deletes a edge between 2 vertices.
     * @param vertex1Id First vertex ID. When it's directed this is the start vertex.
     * @param vertex2Id Second vertex ID. When it's directed this is the end vertex.
     */
    @Override
    public void deleteEdge(int vertex1Id, int vertex2Id) {
        Vertex vertex;
        if ((vertex = vertices.get(vertex1Id)) == null) return;
        Set<Integer> edgeIds = vertex.getEdgeIds();
        for (Integer edgeId : edgeIds) {
            if(edges.get(edgeId).containsVertexId(vertex2Id)) {
                edges.remove(edgeId).remove();
                return;
            }
        }
    }
    
    /**
     * Checks if the graph is empty.
     * @return True if graph is empty 
     */
    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }
    
    /**
     * Get the source vertex of a given edge.
     * @param edgeId The edge to look at
     * @return Directed edge: Start vertex. Undirected edge: the left/first vertex.
     * returns -1 when not found.
     */
    @Override
    public int getSource(int edgeId) {
        try {
            return edges.get(edgeId).getStart().getId();
        } catch (Exception e) {
            return -1;
        }
    }
    
    /**
     * Get the target vertex of a given edge.
     * @param edgeId The edge to look at
     * @return Directed edge: End vertex. Undirected edge: the right/second vertex. 
     */
    @Override
    public int getTarget(int edgeId) {
        try {
            return edges.get(edgeId).getEnd().getId();
        } catch (Exception e) {
            return -1;
        }
    } 
    
    /**
     * Get all incident edges from a given vertex.
     * @param vertexId The vertex id
     * @return The list of incident edge ids
     */
    @Override
    public List<Integer> getIncident(int vertexId) {
        List<Integer> result = new ArrayList<>();
        result.addAll(vertices.get(vertexId).getEdgeIds());
        return result;
    }
    
    /**
     * Get all adjacent vertices from a given vertex.
     * @param vertexId The vertex id
     * @return The list of adjacent vertex ids.
     */
    @Override
    public List<Integer> getAdjacent(int vertexId) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> edgeIds = vertices.get(vertexId).getEdgeIds();
        for (Integer edgeId : edgeIds) {
            result.add(vertices.get(edges.get(edgeId).getCounterpart(vertexId)).getId());
        }
        return result;
    }
    
    /**
     * Gets all vertices from the graph.
     * @return List of all vertex ids.
     */
    @Override
    public List<Integer> getVertices() {
        List<Integer> result = new ArrayList<>();
        result.addAll(vertices.keySet());
        return result;
    }
   
    /**
     * Gets all edges from the graph.
     * @return List of all edge´ids.
     */
    @Override
    public List<Integer> getEdges() {
        Set<Integer> preResult = new HashSet<>();
        for (Vertex vertex : vertices.values()) {
            preResult.addAll(vertex.getEdgeIds());
        }
        List<Integer> result = new ArrayList<>();
        result.addAll(preResult);
        return result;
    }
    
    /**
     * Determines the value of a given attribute from a edge.
     * @param edgeId The edge to look at.
     * @param attribute The attribute to get the value from.
     * @return The attribute value. Is Integer.MaxValue when fails.
     */
    @Override
    public int getValE(int edgeId, String attribute) {
        try {
            return edges.get(edgeId).getAttribValuNum(attribute);
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }
    
    /**
     * Determines the value of a given attribute from a vertex.
     * @param vertexId The vertex to look at.
     * @param attribute The attribute to get the value from.
     * @return The attribute value. Is Integer.MaxValue when fails.
     */
    @Override
    public int getValV(int vertexId, String attribute) {
        if (!vertices.containsKey(vertexId))
            return Integer.MAX_VALUE;
        
        return vertices.get(vertexId).getAttribValuNum(attribute);
    }
    
    /**
     * Determines the value of a given attribute from a edge.
     * @param edgeId The edge to look at.
     * @param attribute The attribute to get the value from.
     * @return The attribute value as string. Is empty when fails.
     */
    @Override
    public String getStrE(int edgeId, String attribute) {
        try {
            return edges.get(edgeId).getAttribValuStr(attribute);
        } catch (Exception e) {
            return "";
        }   
    }
    
    /**
     * Determines the value of a given attribute from a vertex.
     * @param vertexId The vertex to look at.
     * @param attribute The attribute to get the value from.
     * @return The attribute value as string. Is empty when fails.
     */
    @Override
    public String getStrV(int vertexId, String attribute) {
        if (!vertices.containsKey(vertexId))
            return "";
 
        return vertices.get(vertexId).getAttribValuStr(attribute);
    }
    
    /**
     * Determines all attributes from a vertex.
     * @param vertexId The vertex to look at.
     * @return All attributes as a list.
     */
    @Override
    public List<String> getAttrV(int vertexId) {
        List<String> result = new ArrayList<>();
        result.addAll(vertices.get(vertexId).getAllAttributes());
        return result;
    }
    
    /**
     * Determines all attributes from a edge.
     * @param edgeId The edge to look at.
     * @return All attributes as a list.
     */
    @Override
    public List<String> getAttrE(int edgeId) {
        List<String> result = new ArrayList<>();
        result.addAll(edges.get(edgeId).getAllAttributes());
        return result;
    }
    
    /**
     * Sets an attribute for a given edge. Overwrites the value, if the
     * attribute already exists.
     * @param edgeId The edge that the atribute will be added to.
     * @param attribute The attribute name.
     * @param value The attribute value.
     */
    @Override
    public void setValE(int edgeId, String attribute, int value) {
        edges.get(edgeId).setValAttr(attribute, value);
    }
    
    /**
     * Sets an attribute for a given vertex. Overwrites the value, if the
     * attribute already exists.
     * @param vertexId The vertex that the atribute will be added to.
     * @param attribute The attribute name.
     * @param value The attribute value.
     */
    @Override
    public void setValV(int vertexId, String attribute, int value) {
        vertices.get(vertexId).setValAttr(attribute, value);
    }
    
    /**
     * Sets an attribute for a given edge as string. Overwrites the value, if the
     * attribute already exists.
     * @param edgeId The edge that the atribute will be added to.
     * @param attribute The attribute name.
     * @param value The attribute value.
     */
    @Override
    public void setStrE(int edgeId, String attribute, String value) {
        edges.get(edgeId).setStrAttr(attribute, value);        
    }
    
    /**
     * Sets an attribute for a given vertex as string. Overwrites the value, if the
     * attribute already exists.
     * @param vertexId The vertex that the atribute will be added to.
     * @param attribute The attribute name.
     * @param value The attribute value.
     */
    @Override
    public void setStrV(int vertexId, String attribute, String value) {
        vertices.get(vertexId).setStrAttr(attribute, value);
    }

    @Override
    public Map<Integer, String> getIDtoName() {
       Map<Integer,String> result = new HashMap<>();
        for (Map.Entry<Integer, Vertex> entry : vertices.entrySet()) {
            Integer integer = entry.getKey();
            Vertex vertex = entry.getValue();
            result.put(integer, vertex.getName());
            
        }
       return result;
    }

    @Override
    public boolean isDirected() {
       return isDirected;
    }
}
