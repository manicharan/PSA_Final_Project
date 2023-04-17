package travellingsalesman;

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
        Label label3 = new Label("Percentage difference : ");
        label3.setLayoutX(10); // set X position
        label3.setLayoutY(50); // set Y position
        Button newButton = new Button("View TSP Path");
        newButton.setLayoutX(10);
        newButton.setLayoutY(70);
        Pane root = new Pane(canvas);
        root.getChildren().add(label);
        root.getChildren().add(label2);
        root.getChildren().add(label3);
        root.getChildren().add(newButton);

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
            Eulerian.eulerianUI(eulerianPath, eulerian, gc, label2);


            timer.stop();
        });
        timer.start();
        System.out.println(graph.getNumVertices());
    }

    public static void main(String[] args) {
        launch();
    }
}
