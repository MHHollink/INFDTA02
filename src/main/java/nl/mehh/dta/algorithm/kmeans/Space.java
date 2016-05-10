package nl.mehh.dta.algorithm.kmeans;

import nl.mehh.dta.algorithm.AbsClusteringAlgorithm;


public class Space extends AbsClusteringAlgorithm {

    protected static Space instance;

    public Space getInstance() {
        if (instance == null) {
            instance = new Space();
        }
        return instance;
    }

    /**
     * Private constructor for Singleton purpose
     */
    private Space() { }

    /**
     * @param k     amount of clusters
     * @param i     amount of iterations
     */
    @Override
    protected void cluster(int k, int i) {
        // TODO: 10-5-2016
    }
}
