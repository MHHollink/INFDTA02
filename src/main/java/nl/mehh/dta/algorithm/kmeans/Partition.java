package nl.mehh.dta.algorithm.kmeans;

import nl.mehh.dta.algorithm.AbsClusteringAlgorithm;


public class Partition extends AbsClusteringAlgorithm {

    protected static Partition instance;

    public Partition getInstance() {
        if (instance == null) {
            instance = new Partition();
        }
        return instance;
    }

    /**
     * Private constructor for Singleton purpose
     */
    private Partition() { }

    /**
     * @param k     amount of clusters
     * @param i     amount of iterations
     */
    @Override
    protected void cluster(int k, int i) {
        // TODO: 10-5-2016
    }
}
