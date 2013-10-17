package graph_lib;

import java.util.List;

public interface EdgeInterface {

	//GETTER
	int getWhatever();
	String getName();
	int getID();
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
