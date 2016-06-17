package nl.mehh.dta.algorithm.kmeans;

import nl.mehh.dta.algorithm.AbsClusteringAlgorithm;
import nl.mehh.dta.util.CentroidColors;
import nl.mehh.dta.util.L;
import nl.mehh.dta.vector.WineDataVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
     * This method assigns all the observations to K clusters and creates the centroids of their averages
     *
     * @param k     amount of clusters
     * @param i     amount of iterations
     *
     * @return
     *      ?
     */
    @Override
    protected List<Observation> cluster(int k, int i) {

        // List of all centroids
        List<Centroid> centroids = new ArrayList<>(k);
        for (int j = 0; j < k; j++) {
            Centroid centroid = new Centroid(CentroidColors.values()[centroids.size()]);
            for (int l = 0; l < 7; l++) {
                centroid.addOffer(
                    new Random().nextInt(32)
                );
            }
        }

        // List of all observations
        List<Observation> observations = new ArrayList<>();
        for(WineDataVector vector : getData().values()) {
            observations.add(
                new Observation(
                    centroids.get(
                        new Random().nextInt(
                            centroids.size()
                        )
                    ), vector
                )
            );
        }

        int loops = 0;
        while (relocate(observations, centroids)) {
            cluster(observations, centroids);
            loops++;

            if(loops > i) break;
        }
        L.i("Done in %d iterations", relocatedTimer);
        return observations;
    }
}
