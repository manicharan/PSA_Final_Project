package travellingsalesman.Optimizations;

import java.util.ArrayList;
import java.util.List;

import travellingsalesman.TSP.TSP;
import travellingsalesman.graph.Graph;

public class TacticalOptimizations {
	private static boolean improved;
	
	public static double twoOpt(List<Integer> bestPath,double bestDistance,Graph graph) {
		improved=true;
		while (improved) {
            improved = false;
            for (int i = 1; i < bestPath.size() - 2; i++) {
                for (int j = i + 2; j < bestPath.size() - 1; j++) {
                    List<Integer> newPath = twoOptSwap(bestPath, i, j);
                    double newDistance = TSP.calculateDistance(newPath, graph);
                    if (newDistance < bestDistance) {
                        bestPath = newPath;
                        bestDistance = newDistance;
                        improved = true;
                    }
                }
            }            
        }
		return bestDistance;
	}
	
	public static double threeOpt(List<Integer> bestPath,double bestDistance,Graph graph) {
      improved = true;
      int count=0;
      while (improved) {
    	  improved = false;
          for (int i = 1; i < bestPath.size() -4; i++) {
              for (int j = i + 2; j < bestPath.size() - 2; j++) {
                  for (int k = j + 2; k < bestPath.size() - 1; k++) {
                      List<Integer> newPath = threeOptSwap(bestPath, i, j, k);
                      double newDistance = TSP.calculateDistance(newPath, graph);
                      if (newDistance < bestDistance) {
                          bestPath = newPath;
                          bestDistance = newDistance;
                          improved = true;
                      }
                  }
              }
//              System.out.println("Hey");
          }
          count++;
          System.out.println(count);
      }      
      return bestDistance;
	}
	
	public static List<Integer> threeOptSwap(List<Integer> path, int i, int j, int k) {
        List<Integer> newPath = new ArrayList<>();
        // 1. take route[0] to route[i-1] and add them in order to new_route
        for (int l = 0; l < i; l++) {
            newPath.add(path.get(l));
        }
        // 2. take route[i] to route[j-1] and add them in reverse order to new_route
        for (int l = j - 1; l >= i; l--) {
            newPath.add(path.get(l));
        }
        // 3. take route[j] to route[k-1] and add them in order to new_route
        for (int l = k - 1; l >= j; l--) {
            newPath.add(path.get(l));
        }
        // 4. take route[k] to end and add them in order to new_route
        for (int l = k; l < path.size(); l++) {
            newPath.add(path.get(l));
        }
        return newPath;
    }
	
	public static List<Integer> twoOptSwap(List<Integer> path, int i, int j) {
		List<Integer> newPath = new ArrayList<>();
        // 1. take route[0] to route[i-1] and add them in order to new_route
        for (int k = 0; k <= i - 1; k++) {
            newPath.add(path.get(k));
        }
        // 2. take route[i] to route[k] and add them in reverse order to new_route
        for (int k = j; k >= i; k--) {
            newPath.add(path.get(k));
        }
        // 3. take route[k+1] to end and add them in order to new_route
        for (int k = j + 1; k < path.size(); k++) {
            newPath.add(path.get(k));
        }
        return newPath;
	}

}
