/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe4algos;

import aufgabe4algos.NearestInsertionAlgo.NearestInsertionAlgo;
import aufgabe4algos.hierholzerAlgo.HierholzerAlgo;
import graph_lib.*;
import helpModules.GraphAnalyse;

/**
 *
 * @author abg627
 */
public class Aufgabe4Algos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AIGraph newGraph = graph_lib.GraphLoader.CreateGraphFromFile("..\\..\\Graphen\\graph_10.graph", false);
        GraphAnalyse workGraph = new GraphAnalyse(newGraph);
        System.out.println("Graph: " + newGraph.getIDtoName().toString());
        
//        HierholzerAlgo holzerAlgo = new HierholzerAlgo(workGraph);
//        System.out.println(holzerAlgo.toString());
        NearestInsertionAlgo nearAlgo = new NearestInsertionAlgo(workGraph);
        System.out.println(nearAlgo.toString());

        
    }
}
