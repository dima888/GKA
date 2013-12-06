package RiverProblems;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.html.parser.Parser;

import graph_lib.AIGraph;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import graph_lib_extensions.*;

public class TestEdmondsAndKarp {
	
	AIGraph graph;
//	Q qFord;

	int source;
	int target;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() {
		graph = new AIGraph();
	}
	
	
	/*
	 * Der Pfad, wo Ihre Textdatei mit den Graphen liegt
	 * Example: Pfrad -> c:\\graphen\\graph8
	 */
//	@Test 
//	public void graph8Test() {
//		String path = "c:\\users\\foxhound\\desktop\\graph8.txt";
//		
//	 	graph_lib_extensions.Parser graphParser = new graph_lib_extensions.Parser(Paths.get(path));
//	 	
//	 	AIGraph graph8 = graphParser.createGraph();
//	 	//graph8.toString();
//	 	
//	 	Q qFord = new Q(graph8);
//	 	qFord.startAlgorithmus();
//	 	System.out.println("Optimaler Fluss = " + qFord.getOptiomalFlow());
//	 	System.out.println("**************************ENDE*****************************");	 
//	}
//	
//	@Test
//	public void graph9Test() {
//		String path = "c:\\users\\foxhound\\desktop\\graph9.txt";
//		
//	 	graph_lib_extensions.Parser graphParser = new graph_lib_extensions.Parser(Paths.get(path));
//	 	
//	 	AIGraph graph9 = graphParser.createGraph();
//	 	//graph8.toString();
//	 	
//	 	Q qFord = new Q(graph9);
//	 	qFord.startAlgorithmus();
//	 	System.out.println("Optimaler Fluss = " + qFord.getOptiomalFlow());
//	 	System.out.println("**************************ENDE*****************************");	 
//	}

	@Test
	public void graphGRBuchSeite99() {
		String path = "c:\\users\\foxhound\\desktop\\GRBuch99.txt";
		
	 	graph_lib_extensions.Parser graphParser = new graph_lib_extensions.Parser(Paths.get(path));
	 	
	 	AIGraph GRBuch99 = graphParser.createGraph();
	 	//graph8.toString();
	 	
	 	EdmondsAndKarp qFord = new EdmondsAndKarp(GRBuch99);
	 	qFord.startAlgorithmus();
	 	System.out.println("Optimaler Fluss = " + qFord.getOptiomalFlow());
	 	System.out.println("**************************ENDE*****************************");	 
	}

}
