package graph_lib;

import java.util.List;

//Package-Private
interface Edge {
	
	
	//GETTER
	int getWhatever();
	String getName();
	int getSecondaryId();
	List<String> getAttrList();
	Vertex[] getVertices();
	
	//SETTER
	void setName(String newName);
	
	//IMPLE
	String getAttrStr(String attr);
	int getAttr(String attr);
	boolean setValE(String attr, int val);
	boolean setStrE(String attr, String val);
}

