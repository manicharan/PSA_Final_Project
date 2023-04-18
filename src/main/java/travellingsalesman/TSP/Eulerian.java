package travellingsalesman.TSP;


import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import travellingsalesman.graph.Edge;
import travellingsalesman.graph.Graph;
import travellingsalesman.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Eulerian {
    private static double bestDis = Double.POSITIVE_INFINITY;

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
        List<Integer> bestCycle = new ArrayList<>();
        for (int i = 0; i < eulerian.getNumVertices(); i++) {
            List<Integer> curCycle = dfs(eulerian, i, new ArrayList<>());
            curCycle.add(i);
            if (TSP.calculateDistance(curCycle, eulerian) < bestDis) {
                bestDis = TSP.calculateDistance(curCycle, eulerian);
                bestCycle = new ArrayList<>(curCycle);
            }
        }
        return bestCycle;
    }

    private static List<Integer> dfs(Graph eulerian, int i, List<Integer> cycle) {
        if (cycle.size() == 0)
            cycle.add(i);
        for (int x : eulerian.getAdjList(i)) {
            if (!cycle.contains(x)) {
                cycle.add(x);
                dfs(eulerian, x, cycle);
            }
        }
        return new ArrayList<>(cycle);
    }

}

