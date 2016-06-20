package nl.mehh.dta.assignment3.prediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write class level documentation
 *
 * @author Marcel
 * @author Evert-Jan
 * @since 19-6-2016.
 */
public class SimpleExponentialSmoothing implements SmoothingAlgorithm {
    private Map<Integer, Double> values;
    private Map<Integer, Double> smoothedValues;
    private int startOffset;
    private double dataSmoothingFactor;
    private int forecastUntilStep;
    private List<String> printables;
    private double calculatedError;

    public SimpleExponentialSmoothing(Map<Integer, Double> values, int startOffset, double dataSmoothingFactor, int forecastUntilStep) {
        this.values = values;
        this.startOffset = startOffset;
        this.dataSmoothingFactor = dataSmoothingFactor;
        this.forecastUntilStep = forecastUntilStep;

        printables = new ArrayList<>();
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

        int lastStep = SESValues.entrySet().stream().sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey())).findFirst().get().getKey();
        for (int i = lastStep; i <= forecastUntilStep; i++) {
            SESValues.put(i, SESValues.get(lastStep));
            printables.add("SES - Forecasted value of step " + i + ": " + SESValues.get(lastStep));
        }

        smoothedValues = SESValues;
        return SESValues;
    }

    @Override
    public void printPrediction() {
        printables.forEach(System.out::println);
    }

    @Override
    public double calculateError() {
        if(smoothedValues == null)
            generateSmoothedValues();

        double error = smoothedValues.entrySet().stream()
                .filter(entry -> values.containsKey(entry.getKey()))
                .map(entry -> Math.pow(entry.getValue()-values.get(entry.getKey()), 2))
                .reduce(0.0, (partialResult, nextElem) -> partialResult+nextElem);

        int valueCount = (int)smoothedValues.entrySet().stream()
                .filter(entry -> values.containsKey(entry.getKey()))
                .count();

        calculatedError = Math.sqrt(error/valueCount-1);
        return calculatedError;
    }

    public double getCalculatedError() {
        return calculatedError;
    }

    public double getDataSmoothingFactor() {
        return dataSmoothingFactor;
    }
}
