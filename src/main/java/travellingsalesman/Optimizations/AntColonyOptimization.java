package travellingsalesman.Optimizations;



import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import travellingsalesman.graph.Graph;

public class AntColonyOptimization {

    private int numAnts;
    private int numIterations;
    private int numCities;
    private double[][] distanceMatrix;
    private double[][] pheromoneMatrix;
    private double[][] delta;

    public AntColonyOptimization(int numAnts, int numIterations, Graph g,List<Integer> tour) {
        this.numAnts = numAnts;
        this.numIterations = numIterations;
        this.numCities = g.getNumVertices();
        this.distanceMatrix = buildDistanceMatrix(g);
        this.pheromoneMatrix = new double[numCities][numCities];
        this.delta = new double[numCities][numCities];

        for(int i=0; i<numCities-1;i++){
            pheromoneMatrix[tour.get(i)][tour.get(i+1)]  = 2;
            pheromoneMatrix[tour.get(i+1)][tour.get(i)]  = 2;
        }
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if(pheromoneMatrix[i][j] != 2){
                    pheromoneMatrix[i][j] = 0.01;
                    pheromoneMatrix[j][i] = 0.01;
                }
            }
        }
    }

    private double[][] buildDistanceMatrix(Graph g) {
		int size=g.getNumVertices();
		double[][] result = new double[size][size];
		for(int i=0;i<size-1;i++) {
			for(int j=i+1;j<size;j++) {
				double weight = Graph.computeDistance(g.getVertex(i), g.getVertex(j));
				result[i][j]=weight;
				result[j][i]=weight;
			}
		}
		return result;
	}

    public Ant run(Graph g) {
        System.out.println();
        Ant bestAnt = null;
        int i =0;
        while (i<numIterations){
            i++;
            Ant[] ants = new Ant[numAnts];
            for (int j = 0; j < numAnts; j++) {
                ants[j] = new Ant(numCities, new Random().nextInt(numCities));
            }
            for (int j = 0; j < numAnts; j++) {
                for (int k = 0; k < numCities - 1; k++) {
                    ants[j].selectNextCity(pheromoneMatrix,distanceMatrix);
                }
                ants[j].getTour().add(ants[j].getTour().get(0));
            }
            for (int j = 0; j < numAnts; j++) {
                ants[j].calculateCost(distanceMatrix);
            }
            for (int j = 0; j < numAnts; j++) {
                if(bestAnt == null){
                    bestAnt = ants[j];
                }else if (ants[j].getCost() < bestAnt.getCost()) {
                    bestAnt = ants[j];
                }
            }
            for (int  j= 0; j < numCities; j++) {
                for (int k = 0; k < numCities; k++) {
                    delta[j][k] = 0;
                }
            }
            for (Ant ant : ants) {
                double contribution = 1 / ant.getCost();
                for (int c = 0; c < numCities - 1; c++) {
                    delta[ant.getTour().get(c)][ant.getTour().get(c + 1)] += contribution;
                    delta[ant.getTour().get(c+1)][ant.getTour().get(c)] =delta[ant.getTour().get(c)][ant.getTour().get(c + 1)];
                }
            }

            for (int j = 0; j < numCities; j++) {
                for (int k = 0; k < numCities; k++) {
                    pheromoneMatrix[j][k] = 0.5*pheromoneMatrix[j][k];
                    pheromoneMatrix[j][k] += delta[j][k];
                }
            }
            if (isConverged()) {
                break;
            }
        }
        return bestAnt;
        
    }

    private boolean isConverged() {
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (delta[i][j] > 0.0001) {
                    return false;
                }
            }
        }
        return true;
    }

    public class Ant {
        private ArrayList<Integer> tour;
        private double cost;

        public Ant(int numCities, int start) {
            tour = new ArrayList<Integer>();
            tour.add(start);
        }

        public void selectNextCity(double[][] pheromoneMatrix,double[][] distance) {
            int currentCity = tour.get(tour.size() - 1);

            double[] probabilities = new double[numCities];
            double sum = 0.0;
            for (int i = 0; i < numCities; i++) {
                if (!tour.contains(i) && i != currentCity &&  distanceMatrix[currentCity][i] >0) {
                    probabilities[i] = Math.pow(pheromoneMatrix[currentCity][i], 10) / distanceMatrix[currentCity][i];
                    sum += probabilities[i];
                }
            }

            for (int i = 0; i < numCities; i++) {
                probabilities[i] /= sum;
            }

            Random random = new Random();
            double p = random.nextDouble();
            sum = 0.0;
            for (int i = 0; i < numCities; i++) {
                sum += probabilities[i];
                if (p <= sum) {
                    tour.add(i);
                    break;
                }
            }

        }

        public void calculateCost(double[][] distanceMatrix) {
            cost = 0;
            for (int i = 0; i < tour.size() - 1; i++) {
                cost += distanceMatrix[tour.get(i)][tour.get(i + 1)];
            }
        }

        public ArrayList<Integer> getTour() {
            return tour;
        }

        public double getCost() {
            return cost;
        }
    }

}