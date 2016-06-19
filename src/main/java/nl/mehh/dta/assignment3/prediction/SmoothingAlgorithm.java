package nl.mehh.dta.assignment3.prediction;

import java.util.Map;

/**
 * TODO: Write class level documentation
 *
 * @author Marcel
 * @since 19-6-2016.
 */
public interface SmoothingAlgorithm {
    Map<? extends Number, ? extends Number> generateSmoothedValues();
    double calculateError();
}
