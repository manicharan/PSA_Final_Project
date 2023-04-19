package travellingsalesman.Optimizations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import travellingsalesman.TSP.TSP;
import travellingsalesman.graph.Graph;

public class SimulatedAnnealing {

    private static final double INITIAL_TEMPERATURE = 10;
    private static final double COOLING_RATE = 0.999;
    private static final int MAX_ITERATIONS = 10000;

    public static List<Integer> optimize(List<Integer> bestPath, double bestDistance, Graph graph) {
        List<Integer> currentPath = new ArrayList<>(bestPath);
        double currentDistance = TSP.calculateDistance(currentPath, graph);
        double temperature = INITIAL_TEMPERATURE;
        Random random = new Random();
        // Apply the 2-opt method to the path until no further improvements can be made
        boolean improved = true;
        int count=0;
        while (improved) {
            improved = false;
            for (int i = 1; i < bestPath.size() - 2; i++) {
                for (int j = i + 2; j < bestPath.size()-1 ; j++) {
                    List<Integer> newPath = TacticalOptimizations.twoOptSwap(bestPath, i, j);
                    double newDistance = TSP.calculateDistance(newPath, graph);
                    double acceptanceProbability = acceptanceProbability(currentDistance, newDistance, temperature);
                    double ran = random.nextDouble();
                    boolean isValid = acceptanceProbability > ran;
                    if (newDistance < currentDistance || isValid) {
                    	improved = true;
                        currentPath = newPath;
                        currentDistance = newDistance;
                        if (currentDistance < bestDistance) {

                            bestPath = new ArrayList<>(currentPath);
                            bestDistance = currentDistance;
                        }
                    }
                    temperature *= COOLING_RATE;
                }
            }
            if(count==100)
            	break;
            System.out.println(count++);
        }
        return bestPath;
    }

    private static double acceptanceProbability(double currentDistance, double newDistance, double temperature) {
        if (newDistance < currentDistance) {
            return 1.0;
        }
        return Math.exp((currentDistance - newDistance) / temperature);
    }
}
