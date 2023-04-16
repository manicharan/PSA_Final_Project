package travellingsalesman;


import java.util.ArrayList;
import java.util.List;

public class Eulerian {
    public static Graph combineGraphs(Graph mst, Graph matching, Graph graph) {
        Graph eulerian = new Graph();
        for (Vertex v : graph.getVertices()) {
            eulerian.addVertex(v);
        }
        for (Edge e : mst.getEdges()) {
            eulerian.addEdge(e);
        }
        for (Edge e : matching.getEdges()) {
            eulerian.addEdge(e);
        }
        return eulerian;
    }

    public static List<Integer> findEulerianCycle(Graph eulerian) {
        List<Integer> cycle = new ArrayList<>();
        cycle.add(0);
        dfs(eulerian, 0, cycle);
        cycle.add(0);
        return cycle;
    }

    private static void dfs(Graph eulerian, int i, List<Integer> cycle) {
        for (int x : eulerian.getAdjList(i)) {
            if (!cycle.contains(x)) {
                cycle.add(x);
                dfs(eulerian, x, cycle);
            }
        }
    }
}

