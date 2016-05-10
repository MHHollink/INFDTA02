package nl.mehh.dta.algorithm;

import nl.mehh.dta.Main;
import nl.mehh.dta.vector.WineDataVector;

import java.util.Map;

public abstract class AbsClusteringAlgorithm {

    /**
     * Getter to returns {@link nl.mehh.dta.Main#data}
     */
    protected Map<Integer, WineDataVector> getData(){
        return Main.getInstance().getData();
    }

    /**
     * Method to preform algorithm
     *
     * @param k     amount of clusters
     * @param i     amount of iterations
     */
    abstract protected void cluster(int k, int i);
}
