package graph_lib;

import java.util.List;

interface EdgeInterface {

	//GETTER
	int getWhatever();
	String getName();
	int getID();
	List<String> getAttrList();
	Vertex[] getVertices();
	Vertex getSource();
	Vertex getTarget();
	
	
	//SETTER
	void setName(String newName);
	
	//IMPLE
	String getAttrStr(String attr);
	int getAttr(String attr);
	boolean setValE(String attr, int val);
	boolean setStrE(String attr, String val);
}
