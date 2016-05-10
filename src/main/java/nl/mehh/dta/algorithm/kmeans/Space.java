package nl.mehh.dta.algorithm.kmeans;

import nl.mehh.dta.algorithm.AbsClusteringAlgorithm;
import nl.mehh.dta.algorithm.IClusteringAlgorithm;


public class Space extends AbsClusteringAlgorithm {
    public IClusteringAlgorithm get() {
        if (instance == null) {
            instance = new Space();
        }
        return instance;
    }

    private Space() {

    }

    public void cluster(int k, int i) {

    }
}
