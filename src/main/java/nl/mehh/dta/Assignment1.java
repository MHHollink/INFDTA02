package nl.mehh.dta;

import nl.mehh.dta.cluster.data.Loader;
import nl.mehh.dta.cluster.kmeans.AbsClusteringAlgorithm.Observation;
import nl.mehh.dta.cluster.kmeans.ClusteringStrategy;
import nl.mehh.dta.cluster.kmeans.Forgy;
import nl.mehh.dta.cluster.util.L;
import nl.mehh.dta.cluster.vector.WineDataVector;

import java.util.List;
import java.util.Map;


public class Assignment1 {

    /**
     * Instance of the Assignment1 class
     */
    private static Assignment1 instance;

    /**
     * Getter for {@link Assignment1#instance}
     * Creates the instance if it is null
     *
     * @return
     *      {@link Assignment1#instance}
     */
    public static Assignment1 getInstance() {
        if (instance == null) instance = new Assignment1();
        return instance;
    }

    /**
     * The data set for the application.
     *
     * Key      = CustomerIdentifier
     * Value    = {@link WineDataVector}
     */
    private Map<Integer, WineDataVector> data;

    /**
     * Assignment1 entry point for the application.
     *
     * @param args
     *      A list of all commandline arguments
     */
    public static void main(String[] args) {
        Assignment1.getInstance().init();
        int k = 0, l = 0;
        try {
            k = Integer.parseInt(args[0]);
            l = Integer.parseInt(args[1]);
        } catch (Exception e) {
            if(args.length != 2) L.w("You should pass 2 Numeric arguments!");
            if(args.length == 2) L.w("The passed arguments should both be numbers!");
            System.exit(0);
        }
        Assignment1.getInstance().start(k,l);
    }

    /**
     * Private constructor to deny any more instances
     */
    private Assignment1() {}

    /**
     * init method for the application, Initiates the data set
     */
    public void init() {
        L.i("* Initiated");
        data = Loader.load("WineData.csv");
    }

    /**
     * start method that does everything from calculation till printing
     */
    public void start(int k, int l) {
        L.i("* Started");

        List<Observation> observations = ClusteringStrategy.getInstance().cluster(
                new Forgy(),
                k,
                l
        );

        observations.forEach(o -> L.i("Customer [%d] lies in cluster [%s]", o.getData().getCustomerIdentifier(), o.getLinkedCentroid().getColor()));
    }

    /**
     * Getter for the data set {@link Assignment1#data}
     * @return
     *      {@link Assignment1#data}
     */
    public Map<Integer, WineDataVector> getData() {
        return data;
    }
}
