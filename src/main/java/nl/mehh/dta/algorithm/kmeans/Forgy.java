package nl.mehh.dta.algorithm.kmeans;

import nl.mehh.dta.algorithm.AbsClusteringAlgorithm;


public class Forgy extends AbsClusteringAlgorithm{

    protected static Forgy instance;

    public static Forgy getInstance() {
        if (instance == null) {
            instance = new Forgy();
        }
        return instance;
    }

    /**
     * Private constructor for Singleton purpose
     */
    private Forgy(){ }

    /**
     * @param k     amount of clusters
     * @param i     amount of iterations
     */
    @Override
    protected void cluster(int k, int i) {
        // TODO: 10-5-2016
    }
}
