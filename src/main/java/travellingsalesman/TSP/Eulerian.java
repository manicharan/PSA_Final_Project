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

    public static void eulerianUI(List<Integer> eulerianPath, Graph eulerian, GraphicsContext gc, Label label, Label label4, Double lengthOfMst, Color color) {
        double weight = 0;
        String text =  new String();
        text = String.valueOf(label.getText());
        for (int i = 0; i < eulerianPath.size() - 1; i++) {
            Vertex u = eulerian.getVertex(eulerianPath.get(i));
            Vertex v = eulerian.getVertex(eulerianPath.get(i + 1));
            weight += Graph.computeDistance(u, v);

            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (gc != null) {
                double finalWeight = weight;
                String finalText = text;
                Platform.runLater(() -> {
                    gc.setStroke(color);
                    gc.strokeLine(u.getX(), u.getY(), v.getX(), v.getY());
                    Platform.runLater(() -> {
                        if (finalText.contains("Optimal"))
                            label.setText("Length of Optimal TSP tour : " + String.valueOf(finalWeight));
                        else
                            label.setText("Length of TSP tour : " + String.valueOf(finalWeight));

                    });
                });
            }

        }
        double finalWeight1 = weight;
        Platform.runLater(() -> {
            label4.setText("Percentage Difference :" + ((finalWeight1 / lengthOfMst) - 1) * 100);
        });

    }
}

