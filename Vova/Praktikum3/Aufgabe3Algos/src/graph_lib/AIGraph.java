package graph_lib;

import java.util.List;
import java.util.Map;

/**
 *
 * @author David Asmuth, Vladimir Malikov
 */
public interface AIGraph {

    /**
     * Creates a edge between 2 vertices. Directed.
     * @param vertex1Id First vertex ID
     * @param vertex2Id Second vertex ID
     * @return The ID of the edge
     */
    int addEdgeD(int vertex1Id, int vertex2Id);

    /**
     * Creates a edge between 2 vertices. Undirected.
     * @param vertex1Id First vertex ID
     * @param vertex2Id Second vertex ID
     * @return The ID of the edge
     */
    int addEdgeU(int vertex1Id, int vertex2Id);

    /**
     * Adds a vertex to the graph, by name
     * @param vertexName Name of the new vertex
     * @return return The id of the vertex
     */
    int addVertex(String vertexName);
    
    /**
     * Deletes a edge between 2 vertices.
     * @param vertex1Id First vertex ID. When it's directed this is the start vertex.
     * @param vertex2Id Second vertex ID. When it's directed this is the end vertex.
     * @return true if deletion was succesful
     */
    void deleteEdge(int vertex1Id, int vertex2Id);

    /**
     * Deletes a vertex by its id
     * @param id ID of the vertex to delete
     * @return Returns true if deletion was successful
     */
    void deleteVertex(int id);

    /**
     * Get all adjacent vertices from a given vertex.
     * @param vertexId The vertex id
     * @return The list of adjacent vertex ids.
     */
    List<Integer> getAdjacent(int vertexId);

    /**
     * Determines all attributes from a edge.
     * @param edgeId The edge to look at.
     * @return All attributes as a list.
     */
    List<String> getAttrE(int edgeId);

    /**
     * Determines all attributes from a vertex.
     * @param vertexId The vertex to look at.
     * @return All attributes as a list.
     */
    List<String> getAttrV(int vertexId);

    /**
     * Gets all edges from the graph.
     * @return List of all edge´ids.
     */
    List<Integer> getEdges();

    /**
     * Get all incident edges from a given vertex.
     * @param vertexId The vertex id
     * @return The list of incident edge ids
     */
    List<Integer> getIncident(int vertexId);

    /**
     * Get the source vertex of a given edge.
     * @param edgeId The edge to look at
     * @return Directed edge: Start vertex. Undirected edge: the left/first vertex.
     * returns -1 when not found.
     */
    int getSource(int edgeId);

    /**
     * Determines the value of a given attribute from a edge.
     * @param edgeId The edge to look at.
     * @param attribute The attribute to get the value from.
     * @return The attribute value as string. Is empty when fails.
     */
    String getStrE(int edgeId, String attribute);

    /**
     * Determines the value of a given attribute from a vertex.
     * @param vertexId The vertex to look at.
     * @param attribute The attribute to get the value from.
     * @return The attribute value as string. Is empty when fails.
     */
    String getStrV(int vertexId, String attribute);

    /**
     * Get the target vertex of a given edge.
     * @param edgeId The edge to look at
     * @return Directed edge: End vertex. Undirected edge: the right/second vertex.
     */
    int getTarget(int edgeId);

    /**
     * Determines the value of a given attribute from a edge.
     * @param edgeId The edge to look at.
     * @param attribute The attribute to get the value from.
     * @return The attribute value. Is Integer.MaxValue when fails.
     */
    int getValE(int edgeId, String attribute);

    /**
     * Determines the value of a given attribute from a vertex.
     * @param vertexId The vertex to look at.
     * @param attribute The attribute to get the value from.
     * @return The attribute value. Is Integer.MaxValue when fails.
     */
    int getValV(int vertexId, String attribute);

    /**
     * Gets all vertices from the graph.
     * @return List of all vertex ids.
     */
    List<Integer> getVertices();

    /**
     * Checks if the graph is empty.
     * @return True if graph is empty
     */
    boolean isEmpty();

    /**
     * Sets an attribute for a given edge as string. Overwrites the value, if the
     * attribute already exists.
     * @param edgeId The edge that the atribute will be added to.
     * @param attribute The attribute name.
     * @param value The attribute value.
     */
    void setStrE(int edgeId, String attribute, String value);

    /**
     * Sets an attribute for a given vertex as string. Overwrites the value, if the
     * attribute already exists.
     * @param vertexId The vertex that the atribute will be added to.
     * @param attribute The attribute name.
     * @param value The attribute value.
     */
    void setStrV(int vertexId, String attribute, String value);

    /**
     * Sets an attribute for a given edge. Overwrites the value, if the
     * attribute already exists.
     * @param edgeId The edge that the atribute will be added to.
     * @param attribute The attribute name.
     * @param value The attribute value.
     */
    void setValE(int edgeId, String attribute, int value);

    /**
     * Sets an attribute for a given vertex. Overwrites the value, if the
     * attribute already exists.
     * @param vertexId The vertex that the atribute will be added to.
     * @param attribute The attribute name.
     * @param value The attribute value.
     */
    void setValV(int vertexId, String attribute, int value);
    
    /**
     * Return hashmap with ids as keays and vertex-name as values.
     * 
     * @return HashMap(ID, Vertex-Name);
     */
    Map<Integer,String> getIDtoName();
    
    /**
     * Return information about graphtyp.
     * @return true if directed; false if undirechted.
     */
    boolean isDirected();
}
