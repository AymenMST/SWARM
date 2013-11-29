package clustering;

import java.util.List;

import pso.PSO;
import driver.DataPoint;
import fitness.DunnGraphFitness;

public class PSOClustering extends ClusteringMethod {
	
	PSO pso;
	int swarmSize = 3;
	int numClusters = 2;

	public PSOClustering(List<DataPoint> data) {
		super(data);
	}

	@Override
	public void cluster() {
		pso = new PSO(swarmSize, data, numClusters);
		
		int i = 0;
		do {
			System.out.println("Iteration "+i);
			pso.runIteration();
			i++;
		} while (i < 10);
		
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
