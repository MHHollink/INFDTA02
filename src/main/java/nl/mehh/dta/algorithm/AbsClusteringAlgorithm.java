package nl.mehh.dta.algorithm;

import nl.mehh.dta.Main;
import nl.mehh.dta.vector.WineDataVector;

import java.util.List;
import java.util.Map;

public abstract class AbsClusteringAlgorithm {

    /**
     * Getter to returns {@link nl.mehh.dta.Main#data}
     */
    protected Map<Integer, WineDataVector> getData(){
        return Main.getInstance().getData();
    }

    /**
     * Method to preform algorithm
     *
     * @param k     amount of clusters
     * @param i     amount of iterations
     *
     * @return
     *      ?
     */
    abstract protected List<Observation> cluster(int k, int i);

    // TODO: 11-5-2016 Method that relocates the centroids (given a boolean if they are moved)
    protected boolean relocate(List<Observation> observations, List<WineDataVector> centroids) {
        System.out.println("relocate");

        return true;
    }

    /**
     * Given all observations, set the linked centroid to the closest one.
     *
     * @param observations      a list containing all the {@link nl.mehh.dta.algorithm.AbsClusteringAlgorithm.Observation}'s
     * @param centroids         a list containing all centroids
     */
    protected void cluster(List<Observation> observations, List<WineDataVector> centroids) {
        System.out.println("clustering...");
        for (Observation observation : observations) {
            observation.setLinkedCentroid(getNearestCentroid(observation,centroids));
        }
    }

    /**
     * Using the distance from EuclideanDistance, get the closest centroid to the given observation
     *
     * @param observation       a single observation that will look for the closest centroid
     * @param centroids         a list of all centroids
     * @return
     *      returns the closest centroid
     */
    private WineDataVector getNearestCentroid(Observation observation, List<WineDataVector> centroids) {
        System.out.println("getting nearest");
        Double shortestDistance = null;
        WineDataVector closest  = null;
        for (WineDataVector vector : centroids) {
            double distance = calculateDistance(observation.getData(), vector);
            if(shortestDistance == null || shortestDistance > distance) {
                shortestDistance = distance;
                closest = vector;
            }
        }
        return closest;
    }

    /**
     * Using the EuclideanDistance, calculate the distance between 2 data objects.
     *
     * @param a             a single vector32 (observation)
     * @param b             a single vector32 (centroid)
     * @return
     *      the distance between both vectors
     */
    private double calculateDistance(WineDataVector a, WineDataVector b) {
        System.out.println("calculating distance");
        double distance = 0;
        for (Integer offerId : a.getPoints().keySet()) {
            distance += Math.pow(a.getPoints().get(offerId) - b.getPoints().get(offerId), 2);
        }
        return Math.sqrt(distance);
    }

    public class Observation {
        public WineDataVector linkedCentroid;
        public WineDataVector data;

        public Observation(WineDataVector linkedCentroid, WineDataVector data) {
            this.linkedCentroid = linkedCentroid;
            this.data = data;
        }

        public Observation(WineDataVector data) {
            this.data = data;
        }

        public WineDataVector getLinkedCentroid() {
            return linkedCentroid;
        }

        public void setLinkedCentroid(WineDataVector linkedCentroid) {
            this.linkedCentroid = linkedCentroid;
        }

        public WineDataVector getData() {
            return data;
        }

        @Override
        public String toString() {
            return "Observation{" +
                    "linkedCentroid=" + linkedCentroid +
                    ", data=" + data +
                    '}';
        }
    }


}
