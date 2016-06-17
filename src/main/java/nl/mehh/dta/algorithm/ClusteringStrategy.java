package nl.mehh.dta.algorithm;

import nl.mehh.dta.algorithm.AbsClusteringAlgorithm.Observation;
import nl.mehh.dta.util.L;

import java.util.List;

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
    public List<Observation> cluster(AbsClusteringAlgorithm algorithm, int k, int l) {
        L.i("cluster with K-Means_%s. Using %d centroids", algorithm.getClass().getSimpleName(), k);
        return algorithm.cluster(k, l);
    }

}
