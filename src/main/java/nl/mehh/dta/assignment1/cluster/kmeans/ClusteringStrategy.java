package nl.mehh.dta.assignment1.cluster.kmeans;


import nl.mehh.dta.assignment1.cluster.data.Point;
import nl.mehh.dta.assignment1.cluster.util.L;
import nl.mehh.dta.assignment1.cluster.util.Tuple;

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
     * Use the given clustering {@param algorithm} to {@link AbsClusteringAlgorithm#cluster}
     *      the data into {@param k} amount of clusters with a maximum of {@param l} iterations
     */
    public Tuple<Double, List<Point>> cluster(AbsClusteringAlgorithm algorithm, int k, int l) {
        L.d("cluster with K-Means : %s. Using %d centroids", algorithm.getClass().getSimpleName(), k);
        return algorithm.cluster(k, l);
    }

}
