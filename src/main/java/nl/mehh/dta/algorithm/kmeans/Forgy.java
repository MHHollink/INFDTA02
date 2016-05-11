package nl.mehh.dta.algorithm.kmeans;

import nl.mehh.dta.algorithm.AbsClusteringAlgorithm;
import nl.mehh.dta.vector.WineDataVector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


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
     * This method picks K centroids which are placed on randomly chosen actual observations
     *
     * @param k     amount of clusters
     * @param i     amount of iterations
     *
     * @return
     *      ?
     */
    @Override
    protected List<Observation> cluster(int k, int i) {
        // List of all observations
        List<Observation> observations = new ArrayList<>();
        for(WineDataVector vector : getData().values()) {
            observations.add(
                    new Observation(
                            vector
                    )
            );
        }
        System.out.println("created list of observations ["+observations.size()+"]");

        Set<Observation> pickedObservations = new HashSet<>();
        // List of all centroids
        List<WineDataVector> centroids = new ArrayList<>(k);
        for (int j = 0; j < k; j++) {
            WineDataVector centroid = new WineDataVector(0);

            boolean picked = false;
            Observation observation = null;
            while (!picked) {
                observation = observations.get(new Random().nextInt(observations.size()));
                picked = !pickedObservations.contains(observation);
            }
            System.out.println("picked one!");
            pickedObservations.add(observation);

            for (Integer offer : observation.getData().getPoints().values()) {
                if(offer == 1) centroid.addOffer(offer);
            }

            centroids.add(centroid);
        }
        System.out.println("created list of centroids");

        cluster(observations, centroids); // Initial clustering

        int loops = 0;
        while (relocate(observations, centroids)) {
            cluster(observations, centroids);
            loops++;

            if(loops > i) break;
        }

        return observations;
    }
}
