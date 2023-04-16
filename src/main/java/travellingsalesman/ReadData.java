package travellingsalesman;

import javafx.scene.canvas.GraphicsContext;

import java.io.*;

public class ReadData {


    public static Graph readData(String inputFile, Graph graph, GraphicsContext gc, double width, double height) {

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            String header=br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String id = tokens[0].substring(tokens[0].length()-6, tokens[0].length());
                double lon = Double.parseDouble(tokens[1]);
                double lat = Double.parseDouble(tokens[2]);
                double x = longitudeToX(lon, width);
                double y = latitudeToY(lat, height);
                if(gc!=null)
                gc.fillOval(x, y, 5, 5);
                graph.addVertex(new Vertex(tokens[0].substring(tokens[0].length()-6, tokens[0].length()), lon, lat, x, y));

            }

            //Create edges between all vertices
            for (int i = 0; i < graph.getNumVertices(); i++) {
                for (int j = i + 1; j < graph.getNumVertices(); j++) {
                    Vertex u = graph.getVertex(i);
                    Vertex v = graph.getVertex(j);
                    double weight = Graph.computeDistance(u, v);
                    graph.addEdge(u, v, weight);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + inputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return graph;
    }

    public static double longitudeToX(double longitude, double canvasWidth) {
        double xFactor = canvasWidth / (0.224117 + 0.477183);
        return xFactor * (longitude + 0.477183);
    }

    public static double latitudeToY(double latitude, double canvasHeight) {
        double yFactor = canvasHeight / (51.670564 - 51.338396);
        return canvasHeight - yFactor * (latitude - 51.338396);
    }

}
