package travellingsalesman;

import java.util.ArrayList;
import java.io.*;
import java.util.HashSet;


public class TSP {
	public static Graph graph = new Graph();
	private static ArrayList<Integer> bestPath;
	private static double bestDistance=Double.MAX_VALUE;
	public static void main(String[] args) {
		String inputFile = "src/main/resources/info6205.spring2023.teamproject.csv";
//------------------------------------------------------------------------------------
		// Adding vertexes by reading the input data from csv file
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String line;
			String header = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(",");
				String id = tokens[0].substring(tokens[0].length() - 6, tokens[0].length());
				double lon = Double.parseDouble(tokens[1]);
				double lat = Double.parseDouble(tokens[2]);
				graph.addVertex(id, lon, lat);
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
			System.exit(1);
		}
//------------------------------------------------------------------------------------		
		//Create edges between all vertices
		for (int i = 0; i < graph.getNumVertices(); i++) {
			for (int j = i + 1; j < graph.getNumVertices(); j++) {
				Vertex u = graph.getVertex(i);
				Vertex v = graph.getVertex(j);
				double weight = Graph.computeDistance(u, v);
				graph.addEdge(u, v, weight);
			}
		}
		//printing the original graph
        System.out.println("----------------Original Graph starts here------------------");
		System.out.println("Original graph with " + graph.getNumEdges() + " edges and " + graph.getNumVertices() + " vertices");
		for (Edge e : graph.getEdges()) {
//			System.out.println(e + " " + graph.getIndex(e.getU().getId()) + " " + graph.getIndex(e.getV().getId()));
		}
        System.out.println("----------------Original Graph ends here--------------------");
	
//-----------------------------------------------------------------------------------------
		//Compute MST and print it
        Graph mst = MST.computeMinimumWeightSpanningTree(graph);		
		double weight=0.0;
        System.out.println("----------------MST Graph starts here----------------------");		
        for (Edge edge : mst.getEdges()) {
            weight+=edge.getWeight();
//            System.out.println(edge +" Edges: "+graph.getIndex(edge.getU().getId())+" "+graph.getIndex(edge.getV().getId()));
        }
        System.out.println("Weight of MST " + weight+" vertices "+mst.getNumVertices()+" edges "+mst.getNumEdges());
        System.out.println("----------------MST Graph ends here------------------------");	
        
//--------------------------------------------------------------------------------------
    	//Find Odd Degree vertices and build matching graph and print it
        HashSet<Integer> oddVertices = MST.findOddDegreeVertices(mst,graph);
		System.out.println("Odd-degree vertices size: "+oddVertices.size() +" vertices "+ oddVertices);
		Graph matching = MST.findMinimumWeightPerfectMatching(oddVertices,graph);
	    System.out.println("----------------Matching Graph starts here------------------");
		System.out.println("Matching Graph "+matching.getNumVertices()+" "+matching.getNumEdges());
        for(Edge e: matching.getEdges()){
//            System.out.println(e +" Edges: "+graph.getIndex(e.getU().getId())+" "+graph.getIndex(e.getV().getId()));
        }
	    System.out.println("----------------Matching Graph ends here------------------");
        
//---------------------------------------------------------------------------------------
      // Combine the MST and the matching graph to form Eulerian Graph and print it
      Graph eulerian = Eulerian.combineGraphs(mst, matching,graph);
      System.out.println("----------------Eulerian Graph starts here------------------");
      System.out.println("Eulerian Graph " +eulerian.getNumVertices() +" "+eulerian.getNumEdges());
      for(Edge edge: eulerian.getEdges()){
//          System.out.println(edge +" Edges: "+graph.getIndex(edge.getU().getId())+" "+graph.getIndex(edge.getV().getId()));
      }
      System.out.println("----------------Eulerian Graph ends here--------------------");        
	}

}
