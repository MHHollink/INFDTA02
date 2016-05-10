package nl.mehh.dta.algorithm;

public class ClusteringStrategy {

    private static ClusteringStrategy instance;

    public static ClusteringStrategy getInstance() {
        if (instance == null) {
            instance = new ClusteringStrategy();
        }
        return instance;
    }

    private ClusteringStrategy() {

    }

    /**
     * Use the given clustering {@param algorithm} to {@link nl.mehh.dta.algorithm.AbsClusteringAlgorithm#cluster}
     *      the data into {@param k} amount of clusters with a maximum of {@param l} iterations
     */
    public void cluster(AbsClusteringAlgorithm algorithm, int k, int l) {
        algorithm.cluster(k, l);
    }

}
