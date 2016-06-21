package nl.mehh.dta.assignment1.cluster.kmeans;

import nl.mehh.dta.assignment1.cluster.data.Centroid;
import nl.mehh.dta.assignment1.cluster.data.Point;
import nl.mehh.dta.assignment1.cluster.util.CentroidColors;
import nl.mehh.dta.assignment1.cluster.util.L;
import nl.mehh.dta.assignment1.cluster.util.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Forgy extends AbsClusteringAlgorithm {

    public Forgy(){ }

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
    protected Tuple<Double, List<Point>> cluster(int k, int i) {
        relocatedTimer = -1;

        L.t("created list of observations [%d]", getData().size());

        Set<Point> pickedObservations = new HashSet<>();
        // List of all centroids
        List<Centroid> centroids = new ArrayList<>(k);
        for (int j = 0; j < k; j++) {
            Centroid centroid = new Centroid(CentroidColors.values()[centroids.size()], new Point(0,0));

            boolean picked = false;
            Point observation = null;
            while (!picked) {
                observation = getData().get(new Random().nextInt(getData().size()));
                picked = !pickedObservations.contains(observation);
            }
            L.t("created centroid at [%s]", observation);
            pickedObservations.add(observation);

            centroid.setPoint(observation.getX(), observation.getY());

            centroids.add(centroid);
        }
        L.t("created list of centroids");
        L.t("%s", centroids);

        cluster(getData(), centroids); // Initial clustering

        int loops = 0;
        while (relocate(getData(), centroids)) {
            cluster(getData(), centroids);
            loops++;

            if(loops > i) break;
        }

        double sse = getData().parallelStream().map(o -> Math.abs(calculateError(o))).reduce(Double::sum).get();
        L.d("Done in %d iterations", relocatedTimer);
        L.d("Calculated SSE: %f", sse);

        return new Tuple<>(sse, getData());
    }


}
