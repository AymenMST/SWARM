package clustering;

import java.util.List;

import pso.PSO;
import driver.DataPoint;

public class PSOClustering extends ClusteringMethod {
	
	PSO pso;
	int swarmSize = 3;
	int clusters = 2;

	public PSOClustering(List<DataPoint> data) {
		super(data);
	}

	@Override
	public void cluster() {
		int particleSize = clusters * data.get(0).getFeatures().size();
		pso = new PSO(swarmSize, particleSize);
	}

	@Override
	public List<List<Double>> getCenters() {
		
		
		return null;
	}

}
