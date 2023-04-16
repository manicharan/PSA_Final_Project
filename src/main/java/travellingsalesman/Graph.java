package travellingsalesman;

import java.util.*;

public class Graph {
    private List<Vertex> vertices;
    private HashMap<Integer,List<Integer>> adjList;
    private List<Edge> edges;
    private double weight;

    public Graph() {
    	this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.adjList = new HashMap<>();
        this.weight=0;
    }

    public void addVertex(String id, double latitude, double longitude) {
        Vertex vertex = new Vertex(id, latitude, longitude);
        vertices.add(vertex);
    }
    public void addVertex(Vertex v){
        vertices.add(v);
    }

    public void addEdge(Vertex u, Vertex v, double weight) {
        Edge edge = new Edge(u, v, weight);
        edges.add(edge);
        this.weight+=weight;
        int uIndex = vertices.indexOf(u);
        int vIndex = vertices.indexOf(v);
        adjList.put(uIndex,adjList.getOrDefault(uIndex,new ArrayList<>()));
        adjList.get(uIndex).add(vIndex);
        adjList.put(vIndex,adjList.getOrDefault(vIndex,new ArrayList<>()));
        adjList.get(vIndex).add(uIndex);
    }
    
    public List<Integer> getAdjList(int v) {
        return adjList.get(v);
    }
    
    public int getNumVertices() {
        return vertices.size();
    }

    public int getNumEdges() {
        return edges.size();
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getDegree(String id) {
        int degree = 0;
        for (Vertex vertex : vertices) {
            if (vertex.getId().equals(id)) {
                for (Edge edge : edges) {
                    if (edge.getU().equals(vertex) || edge.getV().equals(vertex)) {
                        degree++;
                    }
                }
                break;
            }
        }
        return degree;
    }
    
    public double getWeight() {
    	return this.weight;
    }

    public int getIndex(String id) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
    public Vertex getVertex(int index) {
        return vertices.get(index);
    }


    public void addEdge(Edge e) {
        edges.add(e);
        int uIndex = vertices.indexOf(e.getU());
        int vIndex = vertices.indexOf(e.getV());
        adjList.put(uIndex,adjList.getOrDefault(uIndex,new ArrayList<>()));
        adjList.get(uIndex).add(vIndex);
        adjList.put(vIndex,adjList.getOrDefault(vIndex,new ArrayList<>()));
        adjList.get(vIndex).add(uIndex);
        this.weight+=e.getWeight();
    }
    public static double computeDistance(Vertex v1, Vertex v2) {
		//formula to calculate distance between two points identified by longitude and latitude
		double earthRadius = 6371000; // in meters
        double dLat = Math.toRadians(v2.getLatitude() - v1.getLatitude());
        double dLon = Math.toRadians(v2.getLongitude() - v1.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(v1.getLatitude())) * Math.cos(Math.toRadians(v2.getLatitude())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        return distance;
	}
}
