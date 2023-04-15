package travellingsalesman;

import java.util.*;

public class Graph {
    private List<Vertex> vertices;
    private HashMap<Integer,List<Integer>> adjList;

    public List<Integer> getAdjList(int v) {
        return adjList.get(v);
    }

    private List<Edge> edges;
    private double[][] weight;

    public double getWeight(Vertex u, Vertex v) {
        return weight[getIndex(u.getId())][getIndex(v.getId())];
    }

    public Graph() {
    	this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.adjList = new HashMap<>();
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
//        this.weight[getIndex(u.getId())][getIndex(v.getId())] = weight;
//        this.weight[getIndex(v.getId())][getIndex(u.getId())] = weight;
        edges.add(edge);
        int uIndex = vertices.indexOf(u);
        int vIndex = vertices.indexOf(v);
        adjList.put(uIndex,adjList.getOrDefault(uIndex,new ArrayList<>()));
        adjList.get(uIndex).add(vIndex);
        adjList.put(vIndex,adjList.getOrDefault(vIndex,new ArrayList<>()));
        adjList.get(vIndex).add(uIndex);
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
    }
}
