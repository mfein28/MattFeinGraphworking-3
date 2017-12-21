package project2;

import java.io.*;
import java.util.*;


public class Graph {


    // Class variables
    protected int n;// Number of vertices in the graph
    protected ArrayList<Edge[]> edgeList;//Adjacency Lists
    protected ArrayList<Vertex> vertices;
    Vertex[] piArray;

    /*
     * TO DO:
     * Create class variables to store basic graph information:
     *   - All information about each of the vertices
     *   - The adjacency lists (= all information about the edges, including their weight)
     *   
     * Create class variables to store results computed by the graph algorithms:
     *   - The predecessor array
     *   - The priority queue
     *   - Any other information that you might want to draw. For example, a string
     *   specifying which of the two algorithms you have run for which you are showing
     *   its results.
     */

    public Graph() {
        /*
         * TO DO:
         * Initialize class variables.
         */
        edgeList = new ArrayList<Edge[]>();
        vertices = new ArrayList<Vertex>();

    }

    public ArrayList<Edge[]> getEdgeList() {
        return edgeList;
    }

    public void readGraphFromFile() {
        // Optional; asks for keyboard input.
        String filename;
        System.out.print("Please enter name for graph file: ");
        Scanner scanner = new Scanner(System.in);
        filename = scanner.nextLine();
        readGraphFromFile(filename);
        scanner.close();
    }

    public void readGraphFromFile(String filename) {
        Scanner scanner;
        ArrayList<Edge> tempEdgeList = new ArrayList<Edge>();
        Edge[] indEdge;

        try {
            scanner = new Scanner(new File(filename));
            n = scanner.nextInt();

            for (int i = 0; i < n; i++) {
                //ID of vertex that is read in
                int ID = scanner.nextInt();
                //XCoor of vertex that is read in
                int xCoor = scanner.nextInt();
                //YCoor of vertex that is read in
                int yCoor = scanner.nextInt();
                //Degree of Vertex that is read in
                int degree = scanner.nextInt();
                Vertex temp = new Vertex(ID, xCoor, yCoor);
                //Adds the vertex to an array of vertices
                vertices.add(temp);
                //Array of edges that is the size of the degree of the vertex
                indEdge = new Edge[degree];
                //Loop to add edges into the array
                for (int j = 0; j < degree; j++) {
                    int toID = scanner.nextInt();
                    int edgeWeight = scanner.nextInt();
                    Edge tempEdge = new Edge(toID, ID, edgeWeight);
                    indEdge[j] = tempEdge;//j is index that edge is placed in
                }
                edgeList.add(indEdge);
            }



        } catch (FileNotFoundException e) {
            System.err.println("Could not find file " + filename + ". " + e);
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Error reading integer from file " + filename + ". " + e);
            System.exit(0);
        }
    }


    public ArrayList<Vertex> getVertices() {
        return vertices;
    }


    public Vertex[] getPiArray() {
        return piArray;
    }


    public Boolean bellmanFord() {
        piArray = new Vertex[vertices.size()];
        piArray[0] = new Vertex(0, 0, 0);
        Vertex source = vertices.get(0);
        source.setdValue(0);
        //All other dvalues initially set to infinity
        //Need to reset d value if use other shortest path algorithm
        for (int i = 0; i < vertices.size() - 1; i++) {
            //Iterate through all edges and update d values
            for (int j = 0; j < edgeList.size(); j++) {
                for (int k = 0; k < edgeList.get(j).length; k++) {

                    Edge e = edgeList.get(j)[k];
                    Vertex fromVert = vertices.get(e.getFromID());
                    Vertex toVert = vertices.get(e.gettoID());
                    int edgeWeight = e.getEdgeWeight();
                    //if destination d value is larger than the source d val plus weight of edge
                    if (toVert.dValue > fromVert.dValue + edgeWeight) {
                        //destination d val is equal to source d val plus weight of edge
                        toVert.setdValue(fromVert.dValue + edgeWeight);
                        piArray[toVert.getID()] = fromVert;
                        //for loop which has if statement for negative
                    }

                }

            }
        }

        //Negative edge weight
        for (int x = 0; x < edgeList.size() - 1; x++) {
            for (int y = 0; y < edgeList.get(x).length; y++) {
                if (vertices.get(x).dValue > vertices.get(x).dValue + edgeList.get(x)[y].getEdgeWeight()) {
                    System.out.println("Graph Contains a Negative Edge Weight Cycle");
                    return false;
                }
            }
        }
        //Output Source Vertex and Shortest Path Tree
        System.out.println("Source Vertex is: " + source.getID());
        return true;
    }


    public void dijkstra() {
        ArrayListHeap priorityQueue = new ArrayListHeap();
        Vertex source = vertices.get(0);
        //Set source dVal to 0
        source.setdValue(0);
        //Visited vertices
        ArrayListHeap S = new ArrayListHeap();
        //Populates priorityQueue with vertices
        piArray = new Vertex[vertices.size()];
        piArray[0] = new Vertex(0, 0, 0);

        //Add vertices to the priority queue
        for(int i = 0; i < vertices.size(); i++){
            priorityQueue.insert(vertices.get(i));
        }
        //While the priorityQueue isn't empty extract minimum Vertex
        while(priorityQueue.isEmpty() != true){
            //min is fromVert
            Vertex min = priorityQueue.extractMin();
            int ID = min.getID();
            for(int j = 0; j < edgeList.get(ID).length; j++) {
                Edge tempEdge = edgeList.get(ID)[j];
                int toVertID = tempEdge.gettoID();
                Vertex toVert = vertices.get(toVertID);
                int fromVertD = min.dValue;
                int edgeWeight = tempEdge.getEdgeWeight();
                if (toVert.dValue > fromVertD + edgeWeight ) {
                    //Set dValue
                    toVert.setdValue(fromVertD + edgeWeight);
                    priorityQueue.decreaseKey(toVert, fromVertD + edgeWeight);
                    //Add vertex to piArray after it is visited
                    piArray[toVertID] = min;
                    //Add visited vertex to S
                    S.insert(min);
                }

            }

        }

    }
}
    
    

  
	



