package graph_lib_extensions;

import graph_lib.AIGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Parser {
	
	private File f;
	private Path path;
	private Scanner scanner;
	private List<String> textList = new ArrayList<>();
	
	private int v1Counter = 0;
	private int v2Counter = 1;
	private int e1Counter = 2;
	
	private AIGraph graph = new AIGraph();
	
	private Set<String> vertexNames = new HashSet<>();
	
	public Parser(Path path) {
		this.path = path;
	}
	
	public AIGraph createGraph() {
		if(path.toString() == "") {
			throw new IllegalArgumentException("So ein Path existiert nicht!!!");
		}
		
		f = new File(path.toString());
		try {
			scanner = new Scanner(f);
			String wort = scanner.next();
			commaToSpace();
			
			if(wort.compareTo("#gerichtet") == 0) {
				return createDirectedGraph();
			}
			else if(wort.compareTo("#ungerichtet") == 0) {
				return createUndirectedGraph();
			}
			else {
				throw new IllegalArgumentException("Die Textdatei muss mit #gerichtet oder #ungerichtet beginnen in der obersten Zeile!");
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("Scanner auf Datei hat einen Fehler");
		}
		return null;
	}
	
	private AIGraph createDirectedGraph() {
		for(int i = 0; i < textList.size(); i+=3) {
			int v1ID = graph.addVertex(textList.get(v1Counter));
			int v2ID = graph.addVertex(textList.get(v2Counter));
			int e1ID = graph.addEdgeD(v1ID, v2ID);
			//graph.setValE(e1ID, "value", Integer.parseInt(textList.get(e1Counter)));
			graph.setValE(e1ID, "capacity", Integer.parseInt(textList.get(e1Counter)));
			
			v1Counter += 3;
			v2Counter += 3;
			e1Counter += 3;			
		}
		System.out.println(graph);
		v1Counter = 0;
		v2Counter = 1;
		e1Counter = 2;
		
		return graph;
	}
	
	private AIGraph createUndirectedGraph() {
		for(int i = 0; i < textList.size(); i+=3) {
			int v1ID = graph.addVertex(textList.get(v1Counter));
			int v2ID = graph.addVertex(textList.get(v2Counter));
			int e1ID = graph.addEdgeU(v1ID, v2ID);
			graph.setValE(e1ID, "value", Integer.parseInt(textList.get(e1Counter)));
			
			v1Counter += 3;
			v2Counter += 3;
			e1Counter += 3;			
		}
		System.out.println("Ungerichtet" + graph);
		v1Counter = 0;
		v2Counter = 1;
		e1Counter = 2;
		
		return graph;
	}
	
	private void commaToSpace() {
		while (scanner.hasNext()) {
			String w = scanner.next();
			String puffer = "";
			
			for(char i : w.toCharArray()) {
				if(i == ',') {
					textList.add(puffer);
					puffer = "";
				} else {
					puffer += i;
				}
			}
			textList.add(puffer);
		}
	}
	
	
	public static void main(String[] args) {
		Parser p = new Parser(Paths.get("C:\\Users\\Sony\\Desktop\\test.txt"));
		AIGraph g = p.createGraph();
	}
}
