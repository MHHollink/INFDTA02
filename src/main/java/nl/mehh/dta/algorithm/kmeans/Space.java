package nl.mehh.dta.algorithm.kmeans;

import nl.mehh.dta.algorithm.AbsClusteringAlgorithm;
import nl.mehh.dta.vector.WineDataVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
     * This method picks K centroids which are randomly placed in the space
     *
     * @param k     amount of clusters
     * @param i     amount of iterations
     */
    @Override
    protected void cluster(int k, int i) {
        List<Observation> observations = new ArrayList<>();
        for(WineDataVector vector : getData ().values()) {
            observations.add( new Observation(vector) );
        }

        List<WineDataVector> centroids = new ArrayList<>();
        for (int j = 0; j < k; j++) {
            WineDataVector centroid = new WineDataVector(0);
            for (int l = 0; l < 7; l++) {
                centroid.addOffer(new Random().nextInt(32));
            }
        }

        int loops = 0;
        while (relocate(observations, centroids)) {
            cluster(observations, centroids);
            loops++;

            if(loops > i) break;
        }
    }
}
