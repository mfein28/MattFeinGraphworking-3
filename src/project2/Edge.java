package project2;

public class Edge {

	private int edgeWeight;
	private int toID;
	private int fromID;
	
	public Edge(int toID, int fromID, int edgeWeight){
		this.toID = toID;
		this.fromID = fromID;
		this.edgeWeight = edgeWeight;
	}
	
	
	public int gettoID(){
		return toID;
	}
	
	public int getFromID(){
		return fromID;
	}
	
	public int getEdgeWeight(){
		return edgeWeight;
	}

}
