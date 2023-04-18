package travellingsalesman.TSP;

import java.util.*;

import travellingsalesman.graph.*;

public class TSPTour {
    private List<Vertex> vertices;
    private List<Edge> edges;
    private double weight;

    public TSPTour(List<Vertex> vertices, List<Edge> edges, double weight) {
        this.vertices = vertices;
        this.edges = edges;
        this.weight = weight;
    }

    public static Graph constructTour(List<Integer> path, Graph graph) {
        Graph result = new Graph();
        int i=0;
        for(i=0;i<path.size()-1;i++) {
            Vertex u = graph.getVertex(path.get(i));
            Vertex v = graph.getVertex(path.get(i + 1));
            Edge e = new Edge(u,v,Graph.computeDistance(u, v));
            result.addEdge(e);
            result.addVertex(u);
        }
        result.addVertex(graph.getVertex(path.get(i)));
        return result;
    }

    public static void printPath(Graph graph) {
        System.out.println("Shortest Path size " + graph.getNumVertices());
        for (int i = 0; i < graph.getNumVertices(); i++) {
            System.out.print(graph.getVertex(i).getId().substring(1));
            if (i < graph.getNumVertices() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        System.out.println("Shortest Distance: " + graph.getWeight());

    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


//	@Override
//	public String toString() {
//		return "TSPTour [vertices=" + vertices + ", edges=" + edges + ", weight=" + weight + "]";
//	}



}