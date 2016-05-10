package nl.mehh.dta.algorithm.kmeans;

import nl.mehh.dta.algorithm.AbsClusteringAlgorithm;
import nl.mehh.dta.algorithm.IClusteringAlgorithm;


public class Forgy extends AbsClusteringAlgorithm {

    public IClusteringAlgorithm get() {
        if (instance == null) {
            instance = new Forgy();
        }
        return instance;
    }

    private Forgy(){

    }

    public void cluster(int k, int i) {

    }
}
