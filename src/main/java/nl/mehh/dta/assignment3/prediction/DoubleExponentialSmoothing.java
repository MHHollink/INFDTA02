package nl.mehh.dta.assignment3.prediction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Write class level documentation
 *
 * @author Marcel
 * @since 19-6-2016.
 */
public class DoubleExponentialSmoothing implements SmoothingAlgorithm {

    Map<Integer, Double> values;
    Map<Integer, Double> smoothedValues;
    double dataSmoothingFactor;
    double trendSmoothingFactor;
    int forecastUntilStep;
    List<String> printables;

    public DoubleExponentialSmoothing(Map<Integer, Double> values, double dataSmoothingFactor, double trendSmoothingFactor, int forecastUntilStep) {
        this.values = values;
        this.dataSmoothingFactor = dataSmoothingFactor;
        this.trendSmoothingFactor = trendSmoothingFactor;
        this.forecastUntilStep = forecastUntilStep;

        printables = new ArrayList<>();
    }


    @Override
    public Map<Integer, Double> generateSmoothedValues() {
        if(smoothedValues != null) return smoothedValues;

        Map<Integer, Double> sTValues = new LinkedHashMap<>();
        Map<Integer, Double> trendValues = new LinkedHashMap<>();
        Map<Integer, Double> forecastValues = new LinkedHashMap<>();

        sTValues.put(2, values.get(2));
        trendValues.put(2, values.get(2)-values.get(1));
        for (int i = 3; i <= values.size(); i++) {
            double sT = (dataSmoothingFactor*values.get(i))+(1-dataSmoothingFactor)*(sTValues.get(i-1)+trendValues.get(i-1));
            double bT = trendSmoothingFactor*(sT-sTValues.get(i-1))+((1-trendSmoothingFactor)*trendValues.get(i-1));
            double fT = sTValues.get(i-1)+trendValues.get(i-1);

            sTValues.put(i, sT);
            trendValues.put(i, bT);
            forecastValues.put(i, fT);
        }

        int lastForecast = 0;
        for (Map.Entry<Integer, Double> entry : forecastValues.entrySet()) {
            lastForecast = entry.getKey();
        }
        int lastsT = 0;
        for (Map.Entry<Integer, Double> entry : sTValues.entrySet()) {
            lastsT = entry.getKey();
        }

        int lastbT = 0;
        for (Map.Entry<Integer, Double> entry : trendValues.entrySet()) {
            lastbT = entry.getKey();
        }

        forecastValues.put(lastsT+1, sTValues.get(lastsT)-trendValues.get(lastbT));
        printables.add("DES - Forecasted value of step " + lastsT+1 + ": " + forecastValues.get(lastForecast+1));
        for (int i = lastForecast+1; i <= forecastUntilStep; i++) {
            int stepDiff = i-lastsT;
            double forecast = sTValues.get(lastsT)+(stepDiff*trendValues.get(lastbT));

            forecastValues.put(i, forecast);
            printables.add("DES - Forecasted value of step " + i + ": " + forecast);
        }

        smoothedValues = forecastValues;
        return forecastValues;
    }

    @Override
    public void printPrediction() {
        printables.forEach(System.out::println);
    }

    // TODO: 19-6-2016 Fix this error thingy, not correct formula!
    @Override
    public double calculateError() {
        if(smoothedValues == null) generateSmoothedValues();
        double error = 0;
        int valueCount = 0;
        for (Integer key : values.keySet()) {
            if (values.containsKey(key) && smoothedValues.containsKey(key)) {
                error += Math.pow(values.get(key) - smoothedValues.get(key),2);
                valueCount++;
            }
        }
        return Math.sqrt(error / valueCount);
    }

}
