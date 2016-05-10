package nl.mehh.dta.algorithm.kmeans;

import nl.mehh.dta.algorithm.AbsClusteringAlgorithm;
import nl.mehh.dta.algorithm.IClusteringAlgorithm;


public class Partition extends AbsClusteringAlgorithm {

    public IClusteringAlgorithm get() {
        if (instance == null) {
            instance = new Partition();
        }
        return instance;
    }

    private Partition() {

    }

    public void cluster(int k, int i) {

    }
}
