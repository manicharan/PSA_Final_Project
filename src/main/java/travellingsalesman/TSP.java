package travellingsalesman;

import java.util.ArrayList;
import java.io.*;
import java.util.HashSet;
import java.util.List;


public class TSP {
    public static Graph graph = new Graph();
    private static ArrayList<Integer> bestPath;
    private static double bestDistance = Double.MAX_VALUE;

    public static void main(String[] args) {
        String inputFile = "src/main/resources/sample.csv";
//------------------------------------------------------------------------------------
        ReadData.readData(inputFile, graph, null, 0, 0);
//------------------------------------------------------------------------------------		

        //printing the original graph
        System.out.println("----------------Original Graph starts here------------------");
        System.out.println("Original graph with " + graph.getNumEdges() + " edges and " + graph.getNumVertices() + " vertices");
        for (Edge e : graph.getEdges()) {
//			System.out.println(e + " " + graph.getIndex(e.getU().getId()) + " " + graph.getIndex(e.getV().getId()));
        }
        System.out.println("----------------Original Graph ends here--------------------");

//-----------------------------------------------------------------------------------------
        //Compute MST and print it
        Graph mst = MST.computeMinimumWeightSpanningTree(graph, null);
        System.out.println("----------------MST Graph starts here----------------------");
//        for (Edge edge : mst.getEdges()) {
//            System.out.println(edge +" Edges: "+graph.getIndex(edge.getU().getId())+" "+graph.getIndex(edge.getV().getId()));
//        }
        System.out.println("Weight of MST " + mst.getWeight() + " vertices " + mst.getNumVertices() + " edges " + mst.getNumEdges());
        System.out.println("----------------MST Graph ends here------------------------");

//--------------------------------------------------------------------------------------
        //Find Odd Degree vertices and build matching graph and print it
        HashSet<Integer> oddVertices = MST.findOddDegreeVertices(mst, graph);
        System.out.println("Odd-degree vertices size: " + oddVertices.size() + " vertices " + oddVertices);
        Graph matching = MST.findMinimumWeightPerfectMatching(oddVertices, graph);
        System.out.println("----------------Matching Graph starts here------------------");
        System.out.println("Matching Graph " + matching.getNumVertices() + " " + matching.getNumEdges());
        for (Edge e : matching.getEdges()) {
//            System.out.println(e +" Edges: "+graph.getIndex(e.getU().getId())+" "+graph.getIndex(e.getV().getId()));
        }
        System.out.println("----------------Matching Graph ends here------------------");

//---------------------------------------------------------------------------------------
        // Combine the MST and the matching graph to form Eulerian Graph and print it
        Graph eulerian = Eulerian.combineGraphs(mst, matching, graph);
        System.out.println("----------------Eulerian Graph starts here------------------");
        System.out.println("Eulerian Graph " + eulerian.getNumVertices() + " " + eulerian.getNumEdges());
        for (Edge edge : eulerian.getEdges()) {
//          System.out.println(edge +" Edges: "+graph.getIndex(edge.getU().getId())+" "+graph.getIndex(edge.getV().getId()));
        }
        System.out.println("----------------Eulerian Graph ends here--------------------");
        List<Integer> eulerianPath = Eulerian.findEulerianCycle(eulerian);
        System.out.println(eulerianPath);
    }


}
