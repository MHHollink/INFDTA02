package nl.mehh.dta.assignment3.prediction;

import java.util.*;

/**
 * TODO: Write class level documentation
 *
 * @author Marcel
 * @author Evert-Jan
 * @since 19-6-2016.
 */
public class DoubleExponentialSmoothing implements SmoothingAlgorithm {

    private Map<Integer, Double> values;
    private double dataSmoothingFactor;
    private double trendSmoothingFactor;
    private int forecastUntilStep;
    private List<String> printables;
    private double calculatedError;

    private Map<Integer, Double> sTValues;
    private Map<Integer, Double> trendValues;
    private Map<Integer, Double> forecastValues;

    public DoubleExponentialSmoothing(Map<Integer, Double> values, double dataSmoothingFactor, double trendSmoothingFactor, int forecastUntilStep) {
        this.values = values;
        this.dataSmoothingFactor = dataSmoothingFactor;
        this.trendSmoothingFactor = trendSmoothingFactor;
        this.forecastUntilStep = forecastUntilStep;

        printables      = new ArrayList<>();
        sTValues        = new HashMap<>();
        trendValues     = new HashMap<>();
        forecastValues  = new HashMap<>();
    }


    @Override
    public Map<Integer, Double> generateSmoothedValues() {
        if(!forecastValues.isEmpty())
            return forecastValues;

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

        int lastForecast = forecastValues.entrySet().stream()
                .sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey()))
                .findFirst()
                .get().getKey();

        int lastsT = sTValues.entrySet().stream()
                .sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey()))
                .findFirst()
                .get().getKey();

        int lastbT = trendValues.entrySet().stream()
                .sorted((o1, o2) -> o2.getKey().compareTo(o1.getKey()))
                .findFirst()
                .get().getKey();

        forecastValues.put(lastsT+1, sTValues.get(lastsT)-trendValues.get(lastbT));
        printables.add("DES - Forecasted value of step " + lastsT+1 + ": " + forecastValues.get(lastForecast+1));
        for (int i = lastForecast+1; i <= forecastUntilStep; i++) {
            int stepDiff = i-lastsT;
            double forecast = sTValues.get(lastsT)+(stepDiff*trendValues.get(lastbT));

            forecastValues.put(i, forecast);
            printables.add("DES - Forecasted value of step " + i + ": " + forecast);
        }

        return forecastValues;
    }

    @Override
    public void printPrediction() {
        printables.forEach(System.out::println);
    }

    @Override
    public double calculateError() {
        if(forecastValues.isEmpty())
            generateSmoothedValues();

        int valueCount = (int)forecastValues.entrySet().stream().filter(entry -> values.containsKey(entry.getKey())).count();
        double error = forecastValues.entrySet().stream()
                .filter(entry -> values.containsKey(entry.getKey()))
                .map(entry -> {
                    return Math.pow(entry.getValue()-values.get(entry.getKey()), 2);
                })
                .reduce(0.0, (partialResult, nextElem) -> partialResult+nextElem);

        calculatedError = Math.sqrt(error/valueCount-2);
        return calculatedError;
    }

    public double getDataSmoothingFactor() {
        return dataSmoothingFactor;
    }

    public double getTrendSmoothingFactor() {
        return trendSmoothingFactor;
    }

    public double getCalculatedError() {
        return calculatedError;
    }
}
