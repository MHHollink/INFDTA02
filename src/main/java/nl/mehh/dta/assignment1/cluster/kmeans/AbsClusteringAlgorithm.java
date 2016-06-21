package nl.mehh.dta.assignment1.cluster.kmeans;


import nl.mehh.dta.assignment1.Assignment1;
import nl.mehh.dta.assignment1.cluster.data.Centroid;
import nl.mehh.dta.assignment1.cluster.data.Point;
import nl.mehh.dta.assignment1.cluster.util.L;
import nl.mehh.dta.assignment1.cluster.util.Tuple;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbsClusteringAlgorithm {

    public int relocatedTimer = -1;

    /**
     * Getter to returns {@link Assignment1#data}
     */
    protected List<Point> getData() {
        return Assignment1.getInstance().getData();
    }

    /**
     * Method to preform algorithm
     *
     * @param k     amount of clusters
     * @param i     amount of iterations
     *
     * @return
     *      Tuple with SSE and Observations
     */
    abstract protected Tuple<Double, List<Point>> cluster(int k, int i);

    /**
     * Given all observations, set the linked centroid to the closest one.
     *
     * @param observations a list containing all the {@link Point}'s
     * @param centroids    a list containing all centroids
     */
    protected void cluster(List<Point> observations, List<Centroid> centroids) {
        L.t("clustering...");
        observations.forEach(o -> o.setLinked(getNearestCentroid(o, centroids)));
    }

    protected boolean relocate(List<Point> observations, List<Centroid> centroids) {
        L.t("relocating...");
        relocatedTimer++;

        List<Centroid> startingCentroidLocations = new ArrayList<>();
        for (Centroid centroid : centroids) {
            startingCentroidLocations.add(new Centroid(centroid.getColor(), new Point(centroid.getX(), centroid.getY())));
        }

        // Cluster name, All observations in that cluster.
        Map<String, List<Point>> sortedObservations = sortObservations(observations);

        // Move to center of cluster
        sortedObservations.keySet().forEach(key -> {
            List<Point> clusteredSetOfObservations = sortedObservations.get(key);

            double x = clusteredSetOfObservations.stream()
                    .map(Point::getX).reduce(Double::sum).get() / clusteredSetOfObservations.size();
            double y = clusteredSetOfObservations.stream()
                    .map(Point::getY).reduce(Double::sum).get() / clusteredSetOfObservations.size();

            centroids.stream().filter(c -> c.getColor().name().equals(key)).findFirst().get().setPoint(x, y);
        });

        return centroids.stream()
                .map(centroid ->
                        new AbstractMap.SimpleEntry<>(
                                centroid,
                                startingCentroidLocations.stream()
                                        .filter(
                                                cs -> cs.getColor().equals(centroid.getColor())
                                        )
                                        .anyMatch(
                                                c -> c.getX() != centroid.getX() && c.getY() != centroid.getY()
                                        )
                        )
                )
                .anyMatch(AbstractMap.SimpleEntry::getValue);
    }

    public Map<String, List<Point>> sortObservations(List<Point> observations) {
        Map<String, List<Point>> sorted = new HashMap<>();
        observations.forEach(
                observation -> {
                    String cluster = observation.getLinked().getColor().name();
                    if (!sorted.containsKey(cluster)) {
                        sorted.put(cluster, new ArrayList<>());
                    }
                    sorted.get(cluster).add(observation);
                });
        return sorted;
    }

    private double calculateAverage(List<Double> marks) {
        return marks.stream().reduce(Double::sum).get() / marks.size();
    }

    /**
     * Using the distance from EuclideanDistance, get the closest centroid to the given observation
     *
     * @param observation a single observation that will look for the closest centroid
     * @param centroids   a list of all centroids
     * @return returns the closest centroid
     */
    private Centroid getNearestCentroid(Point observation, List<Centroid> centroids) {
        Double shortestDistance = null;
        Centroid closest = null;
        for (Centroid vector : centroids) {
            double distance = calculateDistance(observation, vector);
            if (shortestDistance == null || shortestDistance > distance) {
                shortestDistance = distance;
                closest = vector;
            }
        }
        return closest;
    }

    /**
     * Using the EuclideanDistance, calculate the distance between 2 data objects.
     *
     * @param a a single vector32 (observation)
     * @param b a single vector32 (centroid)
     * @return the distance between both vectors
     */
    private double calculateDistance(Point a, Centroid b) {
        return Math.sqrt(
                Math.pow(b.getY() - a.getX(),2) + Math.pow(b.getY() - a.getY(),2)
        );
    }

    protected double calculateError(Point o) {
        return Math.pow(o.getX() - o.getLinked().getX(),2) + Math.pow(o.getY() - o.getLinked().getY(),2);
    }
}
