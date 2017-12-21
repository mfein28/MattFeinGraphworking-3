package project2;
import java.awt.Color;
import java.util.Scanner;

import javax.swing.JFrame;
//Matt Fein Part 1

public class Project2 {


	public static void main(String[] args){
	    // Set up application frame
	    JFrame window = new JFrame("Matt Fein");
	    window.setBounds(50,50,800,800); // Dimensions of frame
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    System.out.println("\nWritten by Matt Fein\n");

	    System.out.println("Shortest Path Algorithm");
	    System.out.println("1: Bellman Ford");
	    System.out.println("2: Djikstra");
	    Scanner scanner = new Scanner(System.in);
	    System.out.printf("Enter Selection: ");

	    int selection = scanner.nextInt();
		Graph G = new Graph();
		//Change file here to test other examples
		G.readGraphFromFile("graph.dat");
		GraphCanvas canvas = null;
		if(selection == 1){
			G.bellmanFord();
			canvas = new GraphCanvas(G, false);
		}
		if(selection == 2){
			G.dijkstra();
			canvas = new GraphCanvas(G, true);
		}





	    // Display frame
	    canvas.setBackground(new Color(245,241,222));
	    window.getContentPane().add(canvas); // component added to content pane
	    window.setVisible(true); // displays the frame
	}

}
