package roc;

import java.util.List;

import clustering.ClusteringMethod;

public class ROC {
	
	private int stepRange = 2;
	private ClusteringMethod clusteringMethod;
	private List<TunableParameter> tunableParameters;

	public ROC(ClusteringMethod clusteringMethod, List<TunableParameter> tunableParameters) {
		this.clusteringMethod = clusteringMethod;
		this.tunableParameters = tunableParameters;
	}
	
	public void evalute(){
		for (TunableParameter t : tunableParameters){
			//if (t.getRange())
		}
	}

}
