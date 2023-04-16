package travellingsalesman;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;


public class MST {
    public static Graph computeMinimumWeightSpanningTree(Graph graph, GraphicsContext gc) {
        // Use Kruskal's algorithm to compute minimum weight spanning tree
        UnionFind uf = new UnionFind(graph.getNumVertices());

        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(sortedEdges);


        Graph mst = new Graph();
        for (Vertex v : graph.getVertices()) {
            mst.addVertex(v);
        }

        for (Edge edge : sortedEdges) {
            int uIndex = graph.getIndex(edge.getU().getId());
            int vIndex = graph.getIndex(edge.getV().getId());

            if (uf.find(uIndex) != uf.find(vIndex)) {
                uf.union(uIndex, vIndex);

                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if (gc != null) {
                    Platform.runLater(() -> {

                        gc.strokeLine(graph.getVertex(uIndex).getX(), graph.getVertex(uIndex).getY(), graph.getVertex(vIndex).getX(), graph.getVertex(vIndex).getY());
                    });
                }
                mst.addEdge(edge);
            }
        }

        return mst;
    }

    public static HashSet<Integer> findOddDegreeVertices(Graph mst, Graph graph) {
        HashSet<Integer> oddVertices = new HashSet<>();
        for (int i = 0; i < mst.getNumVertices(); i++) {
            Vertex v = mst.getVertex(i);
            int deg = mst.getDegree(v.getId());
            if (mst.getDegree(mst.getVertex(i).getId()) % 2 == 1) {
//                System.out.println("Degree " +i+" "+mst.getDegree(mst.getVertex(i).getId()));
                oddVertices.add(graph.getIndex(mst.getVertex(i).getId()));
            }
        }
        return oddVertices;
    }

    public static Graph findMinimumWeightPerfectMatching(HashSet<Integer> oddVertices, Graph graph) {
        Graph matching = new Graph();

        // If there are no odd degree vertices, return an empty matching
        if (oddVertices.isEmpty()) {
            return matching;
        }

        // Create a subgraph containing only the odd degree vertices and their edges
        Graph oddGraph = new Graph();
        for (int v : oddVertices) {
            oddGraph.addVertex(graph.getVertex(v));
            matching.addVertex(graph.getVertex(v));
        }
        for (Edge e : graph.getEdges()) {
            if (oddVertices.contains(graph.getIndex(e.getU().getId())) &&
                    oddVertices.contains(graph.getIndex(e.getV().getId())))
                oddGraph.addEdge(e);
        }
//        System.out.println(oddGraph.getNumEdges() +" "+oddGraph.getNumVertices());
        for (Edge e : oddGraph.getEdges()) {
//            System.out.println(e);
        }
        List<Edge> oddEdges = oddGraph.getEdges();
        Collections.sort(oddEdges);
        HashSet<Vertex> oddVer = new HashSet<Vertex>();
        for (Vertex v : oddGraph.getVertices()) {
            oddVer.add(v);
        }
        for (Edge e : oddEdges) {
            if (oddVer.contains(e.getU()) && oddVer.contains(e.getV())) {
                matching.addEdge(e);
                oddVer.remove(e.getU());
                oddVer.remove(e.getV());
            }
        }
        return matching;
    }
}
