package nl.mehh.dta.assignment3.prediction;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import nl.mehh.dta.assignment3.App;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TODO: Write class level documentation
 *
 * @author Marcel
 * @author Evert-Jan
 * @since 19-6-2016
 */
public class ChartController {

    @FXML
    public LineChart<Integer, Double> chart;

    @FXML
    public void initialize() {
        chart.setTitle("Demand of Swords over time");
        chart.getData().add(createSeries(App.getData(), "Swords"));
        chart.getData().add(createSeries(createSESValues(App.getData(), 12, 0.6, 48), "SES"));
        chart.getData().add(createSeries(createDESValues(App.getData(), 0.6, 0.2, 48), "DES"));
        chart.getXAxis().setLabel("Time");
        chart.getYAxis().setLabel("Demand");
        chart.setCreateSymbols(false);
    }

    private XYChart.Series<Integer, Double> createSeries(Map<Integer, Double> values, String name){
        XYChart.Series<Integer, Double> series = new XYChart.Series<>();
        series.setName(name);

        for (Integer i : values.keySet()) {
            series.getData().add(new XYChart.Data<>(i, values.get(i)));
        }
        return series;
    }

    private Map<Integer, Double> createSESValues(Map<Integer, Double> values, int startOffset, double dataSmoothingFactor, int forecastUntillStep){
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
        for (int i = lastStep; i <= forecastUntillStep; i++) {
            SESValues.put(i, SESValues.get(lastStep));
            System.out.println("SES - Forecasted value of step " + i + ": " + SESValues.get(lastStep));
        }

        return SESValues;
    }

    private Map<Integer, Double> createDESValues(Map<Integer, Double> values, double dataSmoothingFactor, double trendSmoothingFactor, int forecastUntillStep) {
        Map<Integer, Double> sTValues = new LinkedHashMap<>();
        Map<Integer, Double> TrendValues = new LinkedHashMap<>();
        Map<Integer, Double> ForecastValues = new LinkedHashMap<>();

        sTValues.put(2, values.get(2));
        TrendValues.put(2, values.get(2)-values.get(1));
        for (int i = 3; i <= values.size(); i++) {
            double sT = (dataSmoothingFactor*values.get(i))+(1-dataSmoothingFactor)*(sTValues.get(i-1)+TrendValues.get(i-1));
            double bT = trendSmoothingFactor*(sT-sTValues.get(i-1))+((1-trendSmoothingFactor)*TrendValues.get(i-1));
            double fT = sTValues.get(i-1)+TrendValues.get(i-1);

            sTValues.put(i, sT);
            TrendValues.put(i, bT);
            ForecastValues.put(i, fT);
        }

        int lastForecast = 0;
        for (Map.Entry<Integer, Double> entry : ForecastValues.entrySet()) {
            lastForecast = entry.getKey();
        }
        int lastsT = 0;
        for (Map.Entry<Integer, Double> entry : sTValues.entrySet()) {
            lastsT = entry.getKey();
        }

        int lastbT = 0;
        for (Map.Entry<Integer, Double> entry : TrendValues.entrySet()) {
            lastbT = entry.getKey();
        }

        ForecastValues.put(lastsT+1, sTValues.get(lastsT)-TrendValues.get(lastbT));
        System.out.println("DES - Forecasted value of step " + lastsT+1 + ": " + ForecastValues.get(lastForecast+1));
        for (int i = lastForecast+1; i <= forecastUntillStep; i++) {
            int stepDiff = i-lastsT;
            double forecast = sTValues.get(lastsT)+(stepDiff*TrendValues.get(lastbT));

            ForecastValues.put(i, forecast);
            System.out.println("DES - Forecasted value of step " + i + ": " + forecast);
        }

        return ForecastValues;
    }
}
