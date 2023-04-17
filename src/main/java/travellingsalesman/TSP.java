package travellingsalesman;

import java.util.ArrayList;
import java.io.*;
import java.util.HashSet;
import java.util.List;


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

        // Calculate the distance of the Hamiltonian circuit
        double distance = calculateDistance(eulerianPath, eulerian);

        // Set the best path and distance if it's the shortest found so far
        if (distance < bestDistance) {
            bestDistance = distance;
            bestPath = eulerianPath;
        }

        // Print the shortest path and distance
        System.out.print("Shortest Path: ");
        for (int i = 0; i < bestPath.size(); i++) {
            System.out.print(graph.getIndex(graph.getVertex(bestPath.get(i)).getId()));
            if (i < bestPath.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        System.out.println("Shortest Distance: " + bestDistance);


        System.out.println("----------------2 Opt Starts here--------------------");

        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 1; i < bestPath.size() - 2; i++) {
                for (int j = i + 2; j < bestPath.size() - 1; j++) {
                    List<Integer> newPath = twoOptSwap(bestPath, i, j);
                    double newDistance = calculateDistance(newPath, eulerian);
                    if (newDistance < bestDistance) {
                        bestPath = newPath;
                        bestDistance = newDistance;
                        improved = true;
                    }
                }
            }
        }

        System.out.println("Best route size " + bestPath.size());
        // Print the shortest path and distance
        System.out.print("Shortest Path: ");
        for (int i = 0; i < bestPath.size(); i++) {
            System.out.print(graph.getIndex(graph.getVertex(bestPath.get(i)).getId()));
            if (i < bestPath.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        System.out.println("Shortest Distance: " + bestDistance);
        System.out.print("----------------2 Opt Ends here--------------------");


        System.out.println();
        System.out.println("----------------3 Opt Starts here--------------------");

        boolean improved2 = true;
        while (improved2) {
            improved2 = false;
            for (int i = 1; i < bestPath.size() -4; i++) {
                for (int j = i + 2; j < bestPath.size() - 2; j++) {
                    for (int k = j + 2; k < bestPath.size() - 1; k++) {
                        List<Integer> newPath = threeOptSwap(bestPath, i, j, k);
                        double newDistance = calculateDistance(newPath, eulerian);
                        if (newDistance < bestDistance) {
                            bestPath = newPath;
                            bestDistance = newDistance;
                            improved2 = true;
                        }
                    }
                }
            }
        }

        System.out.println("Best route size " + bestPath.size());
        // Print the shortest path and distance
        System.out.print("Shortest Path: ");
        for (int i = 0; i < bestPath.size(); i++) {
            System.out.print(graph.getIndex(graph.getVertex(bestPath.get(i)).getId()));
            if (i < bestPath.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        System.out.println("Shortest Distance: " + bestDistance);

        System.out.print("----------------3 Opt Ends here--------------------");


    }

    public static List<Integer> threeOptSwap(List<Integer> path, int i, int j, int k) {
        List<Integer> newPath = new ArrayList<>();
        // 1. take route[0] to route[i-1] and add them in order to new_route
        for (int l = 0; l < i; l++) {
            newPath.add(path.get(l));
        }
        // 2. take route[i] to route[j-1] and add them in reverse order to new_route
        for (int l = j - 1; l >= i; l--) {
            newPath.add(path.get(l));
        }
        // 3. take route[j] to route[k-1] and add them in order to new_route
        for (int l = k - 1; l >= j; l--) {
            newPath.add(path.get(l));
        }
        // 4. take route[k] to end and add them in order to new_route
        for (int l = k; l < path.size(); l++) {
            newPath.add(path.get(l));
        }
        return newPath;
    }


    public static List<Integer> twoOptSwap(List<Integer> path, int i, int j) {
        List<Integer> newPath = new ArrayList<>();
        // 1. take route[0] to route[i-1] and add them in order to new_route
        for (int k = 0; k <= i - 1; k++) {
            newPath.add(path.get(k));
        }
        // 2. take route[i] to route[k] and add them in reverse order to new_route
        for (int k = j; k >= i; k--) {
            newPath.add(path.get(k));
        }
        // 3. take route[k+1] to end and add them in order to new_route
        for (int k = j + 1; k < path.size(); k++) {
            newPath.add(path.get(k));
        }
        return newPath;
    }

    private static double calculateDistance(List<Integer> eulerianPath, Graph eulerian) {
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
