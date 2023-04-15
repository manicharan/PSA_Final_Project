package travellingsalesman;



public class Eulerian {
	public static Graph combineGraphs(Graph mst, Graph matching, Graph graph) {
        Graph eulerian = new Graph();
        for(Vertex v: graph.getVertices()){
            eulerian.addVertex(v);
        }
        for(Edge e: mst.getEdges()){
            eulerian.addEdge(e);
        }
        for(Edge e:matching.getEdges()){
            eulerian.addEdge(e);
        }
        return eulerian;
    }

}
