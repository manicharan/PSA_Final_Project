package travellingsalesman.TSP;

import java.util.ArrayList;
import java.io.*;
import java.util.HashSet;
import java.util.List;

import travellingsalesman.MST.*;
import travellingsalesman.Optimizations.SimulatedAnnealing;
import travellingsalesman.Optimizations.TacticalOptimizations;
import travellingsalesman.Utility.*;
import travellingsalesman.graph.*;


public class TSP {
    public static Graph graph = new Graph();
    static List<Integer> bestPath;
    static double bestDistance = Double.MAX_VALUE;

    public static void main(String[] args) {
        String inputFile = "src/main/resources/sample.csv";
//------------------------------------------------------------------------------------
        ReadData.readData(inputFile, graph, null, 0, 0);
//------------------------------------------------------------------------------------		

        //printing the original graph
        System.out.println("----------------Original Graph starts here------------------");
        System.out.println("Original graph with " + graph.getNumEdges() + " edges and " + graph.getNumVertices() + " vertices");
//        for (Edge e : graph.getEdges()) {
//			System.out.println(e + " " + graph.getIndex(e.getU().getId()) + " " + graph.getIndex(e.getV().getId()));
//        }
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
        //Build matching graph with odd degree vertices and print it
        HashSet<Integer> oddVertices = MST.findOddDegreeVertices(mst, graph);
        System.out.println("Odd-degree vertices size: " + oddVertices.size() + " vertices " + oddVertices);
        Graph matching = MST.findMinimumWeightPerfectMatching(oddVertices, graph);
        System.out.println("----------------Matching Graph starts here------------------");
        System.out.println("Matching Graph " + matching.getNumVertices() + " " + matching.getNumEdges());
//        for (Edge e : matching.getEdges()) {
//            System.out.println(e +" Edges: "+graph.getIndex(e.getU().getId())+" "+graph.getIndex(e.getV().getId()));
//        }
        System.out.println("----------------Matching Graph ends here------------------");

//---------------------------------------------------------------------------------------
        // Combine the MST and the matching graph to form Eulerian Graph and print it
        Graph eulerian = Eulerian.combineGraphs(mst, matching, graph);
        System.out.println("----------------Eulerian Graph starts here------------------");
        System.out.println("Eulerian Graph " + eulerian.getNumVertices() + " " + eulerian.getNumEdges());
//        for (Edge edge : eulerian.getEdges()) {
//          System.out.println(edge +" Edges: "+graph.getIndex(edge.getU().getId())+" "+graph.getIndex(edge.getV().getId()));
//        }
        System.out.println("----------------Eulerian Graph ends here--------------------");

//----------------------------------------------------------------------------------
        //Generate Euler tour by skipping repeating vertices
        System.out.println("----------------Eulerian tour starts here------------------");
        List<Integer> eulerianPath = Eulerian.findEulerianCycle(eulerian);
        System.out.println(eulerianPath);
        System.out.println("----------------Eulerian tour starts here------------------");

//-----------------------------------------------------------------------------------
        // Calculate the distance of the TSP tour and print the tour
        System.out.println("----------------TSP tour starts here------------------");
        double distance = calculateDistance(eulerianPath, eulerian);
        // Set the best path and distance to the TSP found initially
        if (distance < bestDistance) {
            bestDistance = distance;
            bestPath = eulerianPath;
        }
        printPath();
        System.out.println("----------------TSP tour ends here------------------");

//----------------------------------------------------------------------------------
        //Applying 2-OPT optimization technique
        System.out.println("----------------2 Opt Starts here--------------------");
        bestPath = TacticalOptimizations.twoOpt(bestPath, bestDistance, eulerian);
        bestDistance = calculateDistance(bestPath, eulerian);
        printPath();
        System.out.println("------------------2 Opt Ends here--------------------");

//----------------------------------------------------------------------------------
        //Applying the 3-OPT optimization technique
        System.out.println("----------------3 Opt Starts here--------------------");
//        bestPath=TacticalOptimizations.threeOpt(bestPath, bestDistance, eulerian);
//        bestDistance = calculateDistance(bestPath, eulerian);
//        printPath();
        System.out.println("----------------3 Opt Ends here--------------------");

//----------------------------------------------------------------------------------
        //Applying the Simulated Annealing optimization technique
        System.out.println("----------------Simulated Annealing Starts here--------------------");
//        bestPath= SimulatedAnnealing.optimize(bestPath, bestDistance, eulerian);
//        bestDistance = calculateDistance(bestPath, eulerian);
//        printPath();
        System.out.println("----------------Simulated Annealing Ends here--------------------");


    }

    private static void printPath() {
        System.out.println("Shortest Path size " + bestPath.size());
        for (int i = 0; i < bestPath.size(); i++) {
            System.out.print(graph.getIndex(graph.getVertex(bestPath.get(i)).getId()));
            if (i < bestPath.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        System.out.println("Shortest Distance: " + bestDistance);

    }

    public static double calculateDistance(List<Integer> eulerianPath, Graph eulerian) {
        double weight = 0;
        for (int i = 0; i < eulerianPath.size() - 1; i++) {
            Vertex u = eulerian.getVertex(eulerianPath.get(i));
            Vertex v = eulerian.getVertex(eulerianPath.get(i + 1));
            weight += Graph.computeDistance(u, v);
        }
//        weight+=computeDistance(eulerian.getVertex(0),eulerian.getVertex(eu))
        return weight;
    }


}