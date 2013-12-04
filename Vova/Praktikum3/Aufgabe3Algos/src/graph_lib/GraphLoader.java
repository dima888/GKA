package graph_lib;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Parses graph data from files.
 * @author David Asmuth, Vladimir Malikov
 */
public class GraphLoader {

    private final String path;
    private AIGraph graph;
    private final List<String> sourceLines;
    private boolean debugMode = false;
    private final Map<String, Integer> vertices;

    /**
     * Creates a new AIGraph from a file.
     * @param path Path to the file.
     * @param debugMode If true, shows debug information,
     * @return The AIGraph based on the information in the file.
     */
    public static AIGraph CreateGraphFromFile(String path, boolean debugMode) {
        
        GraphLoader gl = new GraphLoader(path);
        gl.debugMode = debugMode;
        gl.parseFile();
        
        
        return gl.getGraph();
    }

    private GraphLoader(String path) {
        this.sourceLines = new ArrayList<>();
        this.vertices = new HashMap<>();
        this.graph = new Graph();
        this.path = path;
    }
    
    /**
     * Parses the source and applys it on the graph
     */
    public void parseFile() {
       read();
       Pattern p = Pattern.compile("(?<vertex1>\\S+)\\s*,\\s*(?<vertex2>\\S+)\\s*,\\s*(?<atr1>-?\\d+)\\s*,*\\s*(?<atr2>-?\\d+)?");
       boolean directed = false; 
       
       for (String line : sourceLines) {
            // Read Direction
            if (line.charAt(0)=='#') {
                directed = (line.trim().equalsIgnoreCase("#gerichtet"));
                if (debugMode) System.out.println("Direction: " + ((directed) ? "directed" : "undirected"));
            }
            
            // Read Vertices and Edges 
            Matcher m = p.matcher(line);
            if (m.matches()) {
                addToGraph(m.group("vertex1"), m.group("vertex2"), m.group("atr1"), m.group("atr2"), directed);
                if (debugMode) System.out.println("v1: " + m.group("vertex1") +  "\tv2: " + m.group("vertex2") +
                        "\tatr1: " + m.group("atr1") + "\tatr2: " + m.group("atr2"));
            }
        }
    }
    
    /**
     * Reads in a file line by line and adds these as strings to sourceList
     */
    private void read() {
        FileReader fileReader;
        try {
            fileReader = new FileReader(path);
        } catch (FileNotFoundException ex) {
            System.err.println(this.getClass() + ": " + ex.getMessage());
            return;
        }

        BufferedReader bufferReader = new BufferedReader(fileReader);
        String line;
        try {
            while ((line = bufferReader.readLine()) != null) {
                sourceLines.add(line);
            }
        } catch (IOException ex) {
            System.err.println(this.getClass() + ": " + ex.getMessage());
        }
    }
    
    /**
     * Adds 2 vertices to the graph and creates a edge between them. If vertices already exist,
     * only missing vertices and edges are added. The edge receives up to two attributes names
     * atr1 and atr2.
     * @param vertex1 The first (source if directed) vertex.
     * @param vertex2 The second (target if directed) vertex.
     * @param atr1 First attribute for the graph. Name is atr1.
     * @param atr2 Second attribute for the graph. Name is atr2.
     * @param directed If the new edge is directed. 
     */
    private void addToGraph(String vertex1, String vertex2, String atr1, String atr2, boolean directed) {
        if (vertex1 == null || vertex2 == null || atr1 == null) return;
        
        // Add vertices if not already in place.
        int v1Id;
        int v2Id;
        if (vertices.containsKey(vertex1)){
            v1Id = vertices.get(vertex1);
        } else {
            v1Id = graph.addVertex(vertex1);
            vertices.put(vertex1, v1Id);
        }
        if (vertices.containsKey(vertex2)){
            v2Id = vertices.get(vertex2);
        } else {
            v2Id = graph.addVertex(vertex2);
            vertices.put(vertex2, v2Id);
        }
        
        int eId = (directed)? graph.addEdgeD(v1Id, v2Id) : graph.addEdgeU(v1Id, v2Id);
        graph.setValE(eId, "atr1", Integer.parseInt(atr1));
        //System.err.println(graph.getValE(eId, "atr1"));
        if (atr2 != null) graph.setValE(eId, "atr2", Integer.parseInt(atr1));
        //System.err.println("Vertex " + v1Id + " source: " +  graph.getSource(v1Id) + " Vertex "+ v2Id + " source: " + graph.getSource(v2Id));
        
    }    
    
    public AIGraph getGraph(){
        return graph;
    }
}
