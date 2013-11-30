package clustering;

import java.util.List;

import pso.PSO;
import driver.DataPoint;
import fitness.GraphFitness;

public class PSOClustering extends ClusteringMethod {
	
	PSO pso;
	int swarmSize = 3;
	int numClusters = 2;
	
	public PSOClustering(List<DataPoint> data, GraphFitness fitnessEvaluation) {
		super(data, fitnessEvaluation);
	}

	@Override
	public void cluster() {
		pso = new PSO(swarmSize, data, numClusters, fitnessEvaluation);
		
		int i = 0;
		do {
			double fitness = pso.runIteration();
			String fitnessDisplay = String.valueOf(fitness);
			if (fitness > 10000000)
				fitnessDisplay = "infinity";
			System.out.println("FITNESS: "+fitnessDisplay);
			i++;
		} while (i < 100);
		
	}

	@Override
	public List<List<Double>> getCenters() {
		
		
		return null;
	}
	
	@Override
	public double evaluate() {
		return pso.getBestFitness();
	}

}
