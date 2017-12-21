package project2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class GraphCanvas extends Canvas {

    static final int diameter = 30;
    static final int arrowWidth = 6;
    static final int arrowLength = 15;

    protected Graph G;
    Boolean selection;

    public GraphCanvas(Graph G, Boolean selection) {
        this.G = G;
        //true is djikstra, false for bellman
        this.selection = selection;
    }


    public void drawDValue(Graphics graphics, Vertex v) {
	  /* Draws dValue of vertex in upper left corner */
        graphics.setColor(Color.RED);
        graphics.drawString(Integer.toString(v.dValue), v.x - diameter / 2, v.y - diameter / 2);
    }

    public void drawVertex(Graphics graphics, Vertex v) {
	  /* Draws vertexID in circle centered at (vertex.x,vertex.y) */
        graphics.setColor(Color.BLACK);
        graphics.drawOval(v.x - diameter / 2, v.y - diameter / 2, diameter, diameter);
        graphics.drawString(Integer.toString(v.ID), v.x - diameter / 4, v.y + diameter / 4);
    }

    public void drawEdge(Graphics graphics, Vertex v, Vertex u, int weight) {
	  /* Draws a weighted DIRECTED edge v->u as an arrow from v to u . 
	   * Note that edges v->u and u->v will be side-to-side and not right on top of each other. 
	   */
        double l = Math.sqrt((u.x - v.x) * (u.x - v.x) + (u.y - v.y) * (u.y - v.y));
        graphics.setColor(new Color(110, 60, 240));
        // edge
        graphics.drawLine((int) (u.x + (l - diameter / 2) * (v.x - u.x) / l - arrowWidth * (u.y - v.y) / l),
                (int) (u.y + (l - diameter / 2) * (v.y - u.y) / l + arrowWidth * (u.x - v.x) / l),
                (int) (v.x + (l - diameter / 2) * (u.x - v.x) / l - arrowWidth * (u.y - v.y) / l),
                (int) (v.y + (l - diameter / 2) * (u.y - v.y) / l + arrowWidth * (u.x - v.x) / l));

        // arrow
        Polygon arrowhead = new Polygon();
        arrowhead.addPoint((int) (v.x + (l - diameter / 2) * (u.x - v.x) / l - arrowWidth * (u.y - v.y) / l),
                (int) (v.y + (l - diameter / 2) * (u.y - v.y) / l + arrowWidth * (u.x - v.x) / l));
        arrowhead.addPoint((int) (v.x + (l - diameter / 2 - arrowLength) * (u.x - v.x) / l - 2 * arrowWidth * (u.y - v.y) / l),
                (int) (v.y + (l - diameter / 2 - arrowLength) * (u.y - v.y) / l + 2 * arrowWidth * (u.x - v.x) / l));
        arrowhead.addPoint((int) (v.x + (l - diameter / 2 - arrowLength) * (u.x - v.x) / l + 0 * arrowWidth * (u.y - v.y) / l),
                (int) (v.y + (l - diameter / 2 - arrowLength) * (u.y - v.y) / l - 0 * arrowWidth * (u.x - v.x) / l));
        graphics.fillPolygon(arrowhead);

        // weight
        graphics.drawString(Integer.toString(weight),
                (int) (v.x + (l - diameter / 2 - 2 * arrowLength) * (u.x - v.x) / l - 2 * arrowWidth * (u.y - v.y) / l),
                (int) (v.y + (l - diameter / 2 - 2 * arrowLength) * (u.y - v.y) / l + 2 * arrowWidth * (u.x - v.x) / l));

    }

    public void highlightEdge(Graphics graphics, Vertex v, Vertex u) {
	  /* Highlights the line segment of the DIRECTED edge v->u.  
	   * Note that edges v->u and u->v will be side-to-side and not right on top of each other.
	   */
        double l = Math.sqrt((u.x - v.x) * (u.x - v.x) + (u.y - v.y) * (u.y - v.y));
        graphics.setColor(new Color(240, 20, 110));
        // edge
        graphics.drawLine((int) (u.x + (l - diameter / 2) * (v.x - u.x) / l - arrowWidth * (u.y - v.y) / l),
                (int) (u.y + (l - diameter / 2) * (v.y - u.y) / l + arrowWidth * (u.x - v.x) / l),
                (int) (v.x + (l - diameter / 2) * (u.x - v.x) / l - arrowWidth * (u.y - v.y) / l),
                (int) (v.y + (l - diameter / 2) * (u.y - v.y) / l + arrowWidth * (u.x - v.x) / l));
    }

    public void highlightVertex(Graphics graphics, Vertex v) {
	  /* Highlights circle around the vertex v. 
	   */
        graphics.setColor(Color.GREEN);
        graphics.drawOval(v.x - diameter * 5 / 12, v.y - diameter * 5 / 12, diameter * 10 / 12, diameter * 10 / 12);
    }

    public void drawTitle(Graphics graphics, String s) {
	  /* Draws title in large font on screen.
	   * Could use this method to draw algorithms type on screen (e.g., "Dijkstra") 	  
	   */
        Font oldFont = graphics.getFont();
        graphics.setFont(oldFont.deriveFont((float) 40));
        graphics.setColor(new Color(240, 20, 110));
        graphics.drawString(s, 200, 500);
        graphics.setFont(oldFont);
    }

    public void paintBellman(Graphics graphics) {

    }

    public void paint(Graphics graphics) {
        // Don't confuse "Graphics" (which is used to draw on) with "Graph" (which stores your graph)
        // This method has to contain all the drawing/painting code
		
	
      /*
	   * TO DO:
	   * All the paint code needs to go here. Need to draw various structures stored in G:
	   *   - Draw vertices and edges of G, once read from file.
	   *   - Draw results from the algorithms (shortest path tree, dValues), once computed.
	   *     (Only attempt to draw non-null structures...)
	   */

        //Iterate through G.vertices(may need a getter) and G.vertexLists
        //106-126 will be replaced
        // Example code below:
        // Create and draw vertices

        ArrayList<Vertex> vertexLists = G.getVertices();
        ArrayList<Edge[]> edgeList = G.getEdgeList();
        int numEdges = edgeList.size();
        int length = vertexLists.size();
        int i = 0;

        //Draws vertices
        while (i < length) {
            int ID = vertexLists.get(i).getID();
            int xCoor = vertexLists.get(i).getX();
            int yCoor = vertexLists.get(i).getY();
            Vertex vert = new Vertex(ID, xCoor, yCoor);
            drawVertex(graphics, vert);
            Edge[] edge = edgeList.get(i);
            int vertEdges = edge.length;
            for (int x = 0; x < vertEdges; x++) {
                Edge drawEdge = edge[x];
                int fromID = drawEdge.getFromID();
                int toID = drawEdge.gettoID();
                int weight = drawEdge.getEdgeWeight();
                Vertex fromVert = vertexLists.get(fromID);
                Vertex toVert = vertexLists.get(toID);
                drawEdge(graphics, fromVert, toVert, weight);
            }
            i++;
        }
        Vertex[] piArray = G.getPiArray();

        highlightVertex(graphics, vertexLists.get(0));
        for (int k = 0; k < piArray.length; k++) {
            if (piArray[k] != null && G.vertices.get(k) != null && k!=0) {
                highlightEdge(graphics, piArray[k], G.vertices.get(k));
                drawDValue(graphics, vertexLists.get(k));
            }


        }
    }
}
