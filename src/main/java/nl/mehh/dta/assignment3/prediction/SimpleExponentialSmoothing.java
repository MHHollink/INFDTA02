package nl.mehh.dta.assignment3.prediction;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Write class level documentation
 *
 * @author Marcel
 * @since 19-6-2016.
 */
public class SimpleExponentialSmoothing implements SmoothingAlgorithm {

    Map<Integer, Double> values;
    Map<Integer, Double> smoothedValues;
    int startOffset;
    double dataSmoothingFactor;
    int forecastUntilStep;

    public SimpleExponentialSmoothing(Map<Integer, Double> values, int startOffset, double dataSmoothingFactor, int forecastUntilStep) {
        this.values = values;
        this.startOffset = startOffset;
        this.dataSmoothingFactor = dataSmoothingFactor;
        this.forecastUntilStep = forecastUntilStep;
    }

    @Override
    public Map<Integer, Double> generateSmoothedValues() {
        if(smoothedValues != null) return smoothedValues;


        Map<Integer, Double> SESValues = new HashMap<>();
        double avgOfStartOffset = 0;

        for (int i = 1; i <= startOffset; i++) {
            avgOfStartOffset += values.get(i);
        }
        avgOfStartOffset = avgOfStartOffset/startOffset;
        SESValues.put(1, avgOfStartOffset);

        for (int i = 2; i <= values.size()+1; i++) {
            double sT = dataSmoothingFactor*(values.get(i-1))+((1-dataSmoothingFactor)*SESValues.get(i-1));
            SESValues.put(i, sT);
        }

        int lastStep = SESValues.size();
        for (int i = lastStep; i <= forecastUntilStep; i++) {
            SESValues.put(i, SESValues.get(lastStep));
            System.out.println("SES - Forecasted value of step " + i + ": " + SESValues.get(lastStep));
        }

        smoothedValues = SESValues;
        return SESValues;
    }

    @Override
    public double calculateError() {
        if(smoothedValues == null) generateSmoothedValues();
        return smoothedValues.keySet().stream().map(
                key -> values.containsKey(key) && smoothedValues.containsKey(key) ?
                        Math.pow(values.get(key),2)+Math.pow(smoothedValues.get(key),2) :
                        0
        ).reduce(Double::sum).get();
    }


}
