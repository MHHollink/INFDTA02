package nl.mehh.dta.cluster.kmeans;

import nl.mehh.dta.Assignment1;
import nl.mehh.dta.cluster.util.CentroidColors;
import nl.mehh.dta.cluster.util.L;
import nl.mehh.dta.cluster.vector.WineDataVector;

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
    protected Map<Integer, WineDataVector> getData() {
        return Assignment1.getInstance().getData();
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

    /**
     * Given all observations, set the linked centroid to the closest one.
     *
     * @param observations a list containing all the {@link AbsClusteringAlgorithm.Observation}'s
     * @param centroids    a list containing all centroids
     */
    protected void cluster(List<Observation> observations, List<Centroid> centroids) {
        L.t("clustering...");
        observations.parallelStream().forEach(o -> o.setLinkedCentroid(getNearestCentroid(o, centroids)));
    }

    protected boolean relocate(List<Observation> observations, List<Centroid> centroids) {
        L.t("relocating...");
        relocatedTimer++;

        List<Centroid> startingCentroidLocations = new ArrayList<>();
        for (Centroid centroid : centroids) {
            Centroid newCentroid = new Centroid(centroid.color);
            newCentroid.setCustomerIdentifier(centroid.getCustomerIdentifier());
            Map<Integer, Double> points = new HashMap<>();

            for (Map.Entry<Integer, Double> entry : centroid.getPoints().entrySet()) {
                points.put(entry.getKey(), entry.getValue());
            }
            newCentroid.setPoints(points);

            startingCentroidLocations.add(newCentroid);
        }

        // Cluster name, All observations in that cluster.
        Map<String, List<Observation>> sortedObservations = sortObservations(observations);

        sortedObservations.keySet().forEach(key -> {
            List<Observation> clusteredSetOfObservations = sortedObservations.get(key);

            // Offer id, All values.
            Map<Integer, List<Double>> offers = new HashMap<>();
            clusteredSetOfObservations.forEach(observation -> {
                for (int i = 1; i <= observation.getData().getPoints().size(); i++) {
                    if (!offers.containsKey(i))
                        offers.put(i, new ArrayList<>());
                    offers.get(i).add(observation.getData().getPoint(i));
                }
            });

            Map<Integer, Double> averages = new HashMap<>();
            offers.keySet().forEach(offer -> {
                averages.put(offer, calculateAverage(offers.get(offer)));
            });

            centroids.stream().filter(c -> c.getColor().equals(key)).findFirst().get().setPoints(averages);
        });

        return centroids.stream()
                .map(centroid ->
                        new AbstractMap.SimpleEntry<>(centroid, startingCentroidLocations.stream()
                                .filter(
                                        cs -> cs.getColor().equals(centroid.getColor())
                                )
                                .findFirst().get()
                                .getPoints().entrySet().stream()
                                .anyMatch(
                                        p -> p.getValue() != centroid.getPoint(p.getKey())
                                )
                        )
                )
                .anyMatch(AbstractMap.SimpleEntry::getValue);
    }

    public Map<String, List<Observation>> sortObservations(List<Observation> observations) {
        Map<String, List<Observation>> sorted = new HashMap<>();
        observations.forEach(
                observation -> {
                    String cluster = observation.getLinkedCentroid().getColor();
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
    private Centroid getNearestCentroid(Observation observation, List<Centroid> centroids) {
        L.t("getting nearest for %d", observation.getData().getCustomerIdentifier());
        Double shortestDistance = null;
        Centroid closest = null;

        for (Centroid vector : centroids) {
            double distance = calculateDistance(observation.getData(), vector);
            if (shortestDistance == null || shortestDistance > distance) {
                shortestDistance = distance;
                closest = vector;
            }
        }
        L.d("nearest centroid for %d is [%s]", observation.getData().getCustomerIdentifier(), closest != null ? closest.getColor() : "NONE");
        return closest;
    }

    /**
     * Using the EuclideanDistance, calculate the distance between 2 data objects.
     *
     * @param a a single vector32 (observation)
     * @param b a single vector32 (centroid)
     * @return the distance between both vectors
     */
    private double calculateDistance(WineDataVector a, Centroid b) {
        double distance = 0;
        for (Integer offerId : a.getPoints().keySet()) {
            distance += Math.pow(a.getPoint(offerId) - b.getPoint(offerId), 2);
        }
        distance = Math.sqrt(distance);
        return distance;
    }

    public class Observation {
        public Centroid linkedCentroid;
        public WineDataVector data;

        public Observation(WineDataVector data) {
            this.data = data;
        }

        public Centroid getLinkedCentroid() {
            return linkedCentroid;
        }

        public void setLinkedCentroid(Centroid linkedCentroid) {
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

    public class Centroid extends WineDataVector {

        private CentroidColors color;

        public Centroid(CentroidColors color) {
            super(0);
            this.color = color;
        }

        public String getColor() {
            return color != null ? color.name() : "NONE";
        }

        @Override
        public String toString() {
            return "Centroid{" +
                    "color=" + color +
                    "}";
        }
    }

    protected double calculateError(Observation o) {
        return calculateDistance(o.getData(), o.getLinkedCentroid());
    }

}
