package travellingsalesman;

import java.util.ArrayList;
import java.io.*;
import java.util.HashSet;


public class TSP {

	public static Graph graph = new Graph();
	static int edgeCount=0;
	private static ArrayList<Integer> bestPath;
	private static double bestDistance=Double.MAX_VALUE;
	public static void main(String[] args) {
		String inputFile = "src/main/resources/sample.csv";
		// Create a graph from the list of cities
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String line;
			String header = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split(",");
				String id = tokens[0].substring(tokens[0].length() - 6, tokens[0].length());
				double lat = Double.parseDouble(tokens[1]);
				double lon = Double.parseDouble(tokens[2]);
				graph.addVertex(id, lat, lon);
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
			System.exit(1);
		}
		//Create edges between all vertices
		for (int i = 0; i < graph.getNumVertices(); i++) {
			for (int j = i + 1; j < graph.getNumVertices(); j++) {
				Vertex u = graph.getVertex(i);
				Vertex v = graph.getVertex(j);
				double weight = computeDistance(u, v);
				graph.addEdge(u, v, weight);
			}
		}
		System.out.println("No of edges in full graph " + graph.getNumEdges() + " vertices " + graph.getNumVertices());
		for (Edge e : graph.getEdges()) {
			System.out.println(e + " " + graph.getIndex(e.getU().getId()) + " " + graph.getIndex(e.getV().getId()));
		}
		System.out.println("---------------------------------------");
		
		Graph mst = MST.computeMinimumWeightSpanningTree(graph);
		
		double weight=0.0;
        // Print out the edges of the minimum weight spanning tree
        for (Edge edge : mst.getEdges()) {
            weight+=edge.getWeight();
            System.out.println(edge +" Edges: "+graph.getIndex(edge.getU().getId())+" "+graph.getIndex(edge.getV().getId()));
        }
        System.out.println("Weight of MST " + weight);

		HashSet<Integer> oddVertices = MST.findOddDegreeVertices(mst,graph);
		System.out.println("Odd-degree vertices: "+" "+oddVertices.size() +" "+ oddVertices);
		Graph matching = MST.findMinimumWeightPerfectMatching(oddVertices,graph);
		System.out.println("Matching Graph "+matching.getNumVertices()+" "+matching.getNumEdges());
        for(Edge e: matching.getEdges()){
            System.out.println(e +" Edges: "+graph.getIndex(e.getU().getId())+" "+graph.getIndex(e.getV().getId()));
        }
        
//      // Combine the minimum spanning tree and the matching edges
      Graph eulerian = Eulerian.combineGraphs(mst, matching,graph);
//      System.out.println("----------------------------------");
      System.out.println("Eulerian Graph " +eulerian.getNumVertices() +" "+eulerian.getNumEdges());
      for(Edge edge: eulerian.getEdges()){
//          System.out.println(edge +" Edges: "+graph.getIndex(edge.getU().getId())+" "+graph.getIndex(edge.getV().getId()));
      }
        
	}

	private static double computeDistance(Vertex v1, Vertex v2) {
		return computeDistance(v1.getLatitude(),v1.getLongitude(),v2.getLatitude(),v2.getLongitude());
	}
	private static double computeDistance(double lon1, double lat1, double lon2, double lat2) {

//		return Math.sqrt(Math.pow((lat2-lat1),2)+Math.pow((lon2-lon1),2));
        double R = 6371; // radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
	}


}
