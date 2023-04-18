package travellingsalesman.Optimizations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import travellingsalesman.TSP.TSP;

public class SimulatedAnnealing {

    private static final double INITIAL_TEMPERATURE = 10;
    private static final double COOLING_RATE = 0.999;
    private static final int MAX_ITERATIONS = 10000;

    public static List<Integer> optimize(List<Integer> path) {
        List<Integer> currentPath = new ArrayList<>(path);
        double currentDistance = TSP.calculateDistance(currentPath);
        List<Integer> bestPath = new ArrayList<>(currentPath);
        double bestDistance = currentDistance;
        double temperature = INITIAL_TEMPERATURE;
        Random random = new Random();
        // Apply the 2-opt method to the path until no further improvements can be made
        boolean improved = true;
        int count=0;
        while (improved) {
            improved = false;
            for (int i = 1; i < path.size() - 2; i++) {
                for (int j = i + 2; j < path.size()-1 ; j++) {
                    List<Integer> newPath = TSP.twoOptSwap(path, i, j);
                    double newDistance = TSP.calculateDistance(newPath);
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


//    private static List<Integer> twoOptSwap(List<Integer> path, int i, int j) {
//        if (i == 0 || j == path.size() - 1)
//            return path;
//        List<Integer> newPath = new ArrayList<>(path.subList(0, i));
//        List<Integer> subList = path.subList(i, j + 1);
//        Collections.reverse(subList);
//        newPath.addAll(subList);
//        newPath.addAll(path.subList(j + 1, path.size()));
//        return newPath;
//    }

    private static double acceptanceProbability(double currentDistance, double newDistance, double temperature) {
        if (newDistance < currentDistance) {
            return 1.0;
        }
        return Math.exp((currentDistance - newDistance) / temperature);
    }
}
