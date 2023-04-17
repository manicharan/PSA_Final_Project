package travellingsalesman;


import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

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

    public static void eulerianUI (List<Integer> eulerianPath,Graph eulerian, GraphicsContext gc, Label label) {
        double weight=0;
        for(int i=0;i<eulerianPath.size()-1;i++){
            Vertex u = eulerian.getVertex(eulerianPath.get(i));
            Vertex v = eulerian.getVertex(eulerianPath.get(i+1));
            weight+=Graph.computeDistance(u,v);

            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (gc != null) {
                double finalWeight = weight;
                Platform.runLater(() -> {
                    gc.setStroke(Color.RED);
                    gc.strokeLine(u.getX(), u.getY(), v.getX(), v.getY());
                    Platform.runLater(() -> {
                        label.setText("Length of TSP tour : " + String.valueOf(finalWeight));
                    });
                });
            }

        }

    }
}

