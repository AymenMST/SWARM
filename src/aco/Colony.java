package aco;

import java.util.ArrayList;
import java.util.List;

public class Colony {
	
	List<Ant> ants = new ArrayList<Ant>();
	
	/**
	 * Creates a colony of ants.
	 * 
	 * @param size	The number of ants that will be put in the population.
	 */
	public Colony(int size) {
		for (int ant = 0; ant < size; ant++)
			ants.add(new Ant());
	}

}
