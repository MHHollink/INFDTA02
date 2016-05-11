package nl.mehh.dta.vector;

import java.util.HashMap;
import java.util.Map;

public class WineDataVector {

    /**
     * Constant value for that represents the offers that are not taken by a customer
     */
    private static final int NOT_TAKEN = 0;

    /**
     * Constant value for that represents the offers that are taken by a customer
     */
    private static final int     TAKEN = 1;

    /**
     * Identifier for the customer
     */
    private Integer customerIdentifier;

    /**
     * List  of all the offers
     *
     * Key      = Offer identifier
     * Value    = 0: Offer not taken,
     *            1: Offer taken
     */
    private Map<Integer, Integer> points;

    /**
     * Constructor for a new WineDataVector.
     *
     * Sets {@link nl.mehh.dta.vector.WineDataVector#customerIdentifier} to the {@param cid}
     * Initialize the {@link  nl.mehh.dta.vector.WineDataVector#points} Map with a size of 32
     *      and fills it with 32 items with value 0
     */
    public WineDataVector(int cid) {
        this.customerIdentifier = cid;
        points = new HashMap<>(32);
        for (int i = 0; i < 32; i++) points.put(i, NOT_TAKEN);
    }

    /**
     * sets the {@param id} to {@link nl.mehh.dta.vector.WineDataVector#TAKEN}
     */
    public void addOffer(int id) {
        if (points.containsKey(id)) return;
        points.put(id, TAKEN);
    }

    /**
     * sets the {@param id} to {@link nl.mehh.dta.vector.WineDataVector#NOT_TAKEN}
     */
    public void removeOffer(int id) {
        if (points.containsKey(id)) return;
        points.put(id, NOT_TAKEN);
    }

    /**
     * Checks if the {@link nl.mehh.dta.vector.WineDataVector#points} contains {@param id} and if
     *          this id has {@link nl.mehh.dta.vector.WineDataVector#TAKEN}
     *
     * @return
     *      {@link java.lang.Boolean}   : TRUE if offer == {@link nl.mehh.dta.vector.WineDataVector#TAKEN}
     *                                  : FALSE if offer == {@link nl.mehh.dta.vector.WineDataVector#NOT_TAKEN}
     */
    public boolean hasTakenOffer(int id) {
        return (points.get(id) != null) && (points.get(id) == TAKEN);
    }

    /**
     * @return
     *      {@link nl.mehh.dta.vector.WineDataVector#customerIdentifier}
     */
    public Integer getCustomerIdentifier() {
        return customerIdentifier;
    }

    /**
     * Sets the {@link nl.mehh.dta.vector.WineDataVector#customerIdentifier} to the {@param customerIdentifier}
     */
    public void setCustomerIdentifier(Integer customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
    }

    public Map<Integer, Integer> getPoints() {
        return new HashMap<>(points);
    }

    @Override
    public String toString() {
        return "WineDataVector{" +
                "cid=" + customerIdentifier +
                ", offers=" + points +
                '}';
    }
}


