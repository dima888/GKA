package graph_lib;

import java.util.List;

//Package-Private
interface Edge {
	
	Vertex[] getVertices();
	int getAttr(String attr);
	String getAttrStr(String attr);
	int getSecondaryId();
	String getName();
	void setName(String newName);
	List<String> getAttrList();
}

