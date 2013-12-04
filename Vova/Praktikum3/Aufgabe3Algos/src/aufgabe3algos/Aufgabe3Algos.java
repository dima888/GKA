/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe3algos;

import graph_lib.*;
import helpModules.GraphAnalyse;
import aufgabe3algos.FordAndFulkerson.*;
import aufgabe3algos.EdmondsAndKarp.*;
import aufgabe3algos.FordAndFulkerson.FordAndFulkersonAlgoGraph;

/**
 *
 * @author Vladimir
 */
public class Aufgabe3Algos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AIGraph newGraph = graph_lib.GraphLoader.CreateGraphFromFile("..\\..\\Graphen\\graph_08.graph", false);
        GraphAnalyse workGraph = new GraphAnalyse(newGraph);
        System.out.println("Graph: " + newGraph.getIDtoName().toString());
            
        // Start von FordAndFulkerson mit Schreibzugriff auf Graph
        Long time  = System.currentTimeMillis();
        System.out.println("\r\nFF Classic:");
        FordAndFulkersonAlgoGraph algoFF = new FordAndFulkersonAlgoGraph(workGraph);
        System.out.println(algoFF.getResultValue());
        System.out.println("Zugriffe FF Classic: " + workGraph.getCounter());
        System.out.println("Laufzeit (ms): " + (System.currentTimeMillis() - time));
        workGraph.resetCounter();

        // Start von FordAndFulkerson OHNE Schreibzugriff auf Graph
        time  = System.currentTimeMillis();
        System.out.println("\r\nFF Otimiert:");
        FordAndFulkersonAlgo algoFFO = new FordAndFulkersonAlgo(workGraph);
        System.out.println(algoFFO.getResultValue());
        System.out.println("Zugriffe FF Optimiert: " + workGraph.getCounter());
        System.out.println("Laufzeit (ms): " + (System.currentTimeMillis() - time));
        workGraph.resetCounter();

        // Start von EdmondsAndKarp OHNE Schreibzugriff auf Graph
        time  = System.currentTimeMillis();
        System.out.println("\r\nEK");
        EdmondsAndKarp secondAlgo = new EdmondsAndKarp(workGraph);
        System.out.println(secondAlgo.getResultValue());
        System.out.println("Zugriffe EK: " + workGraph.getCounter());
        System.out.println("Laufzeit (ms): " + (System.currentTimeMillis() - time));
    }
}
