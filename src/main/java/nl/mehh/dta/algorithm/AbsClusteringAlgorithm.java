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

    // TODO: 11-5-2016 Method that divides everything into clusters
    protected boolean relocate(List<Observation> observations, List<WineDataVector> centroids) {
        return false;
    }

    // TODO: 11-5-2016 Method that relocates the centroids (given a boolean if they are moved)
    protected void cluster(List<Observation> observations, List<WineDataVector> centroids) {

    }


    protected class Observation {
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
    }


}
