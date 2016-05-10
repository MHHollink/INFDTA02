package nl.mehh.dta.algorithm;

public class ClusteringStrategy {

    private static ClusteringStrategy instance;

    public static ClusteringStrategy get() {
        if (instance == null) {
            instance = new ClusteringStrategy();
        }
        return instance;
    }

    private ClusteringStrategy() {

    }

    public void cluster(IClusteringAlgorithm algorithm, int clusters, int iterations) {



        algorithm.cluster(clusters, iterations);
    }

}
