package graph_lib;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Package-Privat
class DirectedEdge implements Edge {

	private Vertex[] verticesFromEdge = new Vertex[2];
	private List<String> attrList = new ArrayList<>();
	private static int count = 0; //Hilfszaehler
	
	private Map<String, Object> attrMap = new HashMap();
	
	// *********ATTRIBUTE********
	private String name;
	private final int secondaryId;
	private int whatever = 100;

	// *********ATTRIBUTE-BEZEICHNUNGEN********
	String attrName = "name";
	String attrSecondaryId = "secondaryId";
	String attrWhatever = "whatever";
		
	
	public DirectedEdge(Vertex v1, Vertex v2) {
		verticesFromEdge[0] = v1; //StartKnoten
		verticesFromEdge[1] = v2; //EndKnoten
		
		v1.addOutgoingEdge(this);
		v2.addIngoingEdge(this);
		
		v1.addIncident(this);
		v2.addIncident(this);
		
		secondaryId = count++; //Auto increment
		
		//****MAP*****
		attrMap.put(attrName, name);
		attrMap.put(attrSecondaryId, secondaryId);
		attrMap.put(attrWhatever, whatever);

		attrList.addAll(Arrays.asList(attrName, attrSecondaryId, attrWhatever)); 	//ATTRIBUTENLISTE
	}
	
	//********************************************** GETTER METHODEN **********************************************
	@Override
	public Vertex[] getVertices() {
		return verticesFromEdge;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public int getSecondaryId() {
		return this.secondaryId;
	}
	
	@Override
	public List<String> getAttrList() {
		return this.attrList;
	}
	
	@Override
	public int getWhatever() {
		return this.whatever;
	}
	
	//********************************************** SETTER METHODEN **********************************************
	@Override
	public void setName(String newName) {
		this.name = newName;		
	}
	
	//********************************************** IMPLEMENTIERUNGS METHODEN **********************************************

	@Override
	public boolean setValE(String attr, int val) {
		if(attr == attrSecondaryId) {
			//this.secondaryId = val;
			return true;
		}
		if(attr == attrWhatever) {
			this.whatever = val;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String result = "";
		result = "Source: (" + verticesFromEdge[0] + ") Target: (" + verticesFromEdge[1] + ")";
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hatQuelle() {
		Vertex source = verticesFromEdge[0];
		if(source.getIngoingEdge().size() > 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hatSenke() {
		Vertex target = verticesFromEdge[1];
		if(target.getOutgoingEdge().size() > 1) {
			return false;
		}
		return true;
	}

	@Override
	public int getAttr(String attr) {
		if(attr == this.attrSecondaryId) {
			return this.secondaryId;
		}
		return Integer.MAX_VALUE;
	}


	@Override
	public String getAttrStr(String attr) {
		String result = "";
		if(attr == this.attrName) {
			result = this.name;
		}
		return result;
	}
}
