package travellingsalesman.UI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import travellingsalesman.MST.*;
import travellingsalesman.Optimizations.TacticalOptimizations;
import travellingsalesman.TSP.Eulerian;
import travellingsalesman.TSP.TSP;
import travellingsalesman.Utility.*;
import travellingsalesman.graph.*;


import javax.swing.*;
import java.util.HashSet;
import java.util.List;

public class JavaUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        Canvas canvas;
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();
        canvas = new Canvas(width, height);
        GraphicsContext gc;
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        Label label = new Label("Length of MST : ");
        label.setLayoutX(10); // set X position
        label.setLayoutY(10); // set Y position
        Label label2 = new Label("Length of TSP tour : ");
        label2.setLayoutX(10); // set X position
        label2.setLayoutY(30); // set Y positi
        Label label3 = new Label("Length of Optimal TSP tour : ");
        label3.setLayoutX(10); // set X position
        label3.setLayoutY(50); // set Y position
        Label label4 = new Label("Percentage Difference: ");
        label4.setLayoutX(10); // set X position
        label4.setLayoutY(70); // set Y position
        Pane root = new Pane(canvas);
        root.getChildren().add(label);
        root.getChildren().add(label2);
        root.getChildren().add(label3);
        root.getChildren().add(label4);

        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
        Graph graph = new Graph();

        ReadData.readData("src/main/resources/sample.csv", graph, gc, width, height);

        Timer timer = new Timer(1000, null);
        timer.addActionListener((e) -> {
            Graph mst = MST.computeMinimumWeightSpanningTree(graph, gc);
            Platform.runLater(() -> {
                label.setText("Length of MST : " + String.valueOf(mst.getWeight()));
            });


            HashSet<Integer> oddVertices = MST.findOddDegreeVertices(mst, graph);
            Graph matching = MST.findMinimumWeightPerfectMatching(oddVertices, graph);

            Graph eulerian = Eulerian.combineGraphs(mst, matching, graph);
            List<Integer> eulerianPath = Eulerian.findEulerianCycle(eulerian);
            tourUI(eulerianPath, eulerian, gc, label2, label4, mst.getWeight(), Color.BLUE);


            double bestDistance = Double.MAX_VALUE;
            List<Integer> bestPath = null;
            double distance = TSP.calculateDistance(eulerianPath, eulerian);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestPath = eulerianPath;
            }
            bestPath = TacticalOptimizations.twoOpt(bestPath, bestDistance, eulerian);
            tourUI(bestPath, eulerian, gc, label3, label4, mst.getWeight(), Color.BLACK);


            bestPath = TacticalOptimizations.threeOpt(bestPath, bestDistance, eulerian);
            tourUI(bestPath, eulerian, gc, label3, label4, mst.getWeight(), Color.RED);



            timer.stop();
        });
        timer.start();
        System.out.println(graph.getNumVertices());
    }

    public static void tourUI(List<Integer> eulerianPath, Graph eulerian, GraphicsContext gc, Label label, Label label4, Double lengthOfMst, Color color) {
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


    public static void main(String[] args) {
        launch();
    }
}
