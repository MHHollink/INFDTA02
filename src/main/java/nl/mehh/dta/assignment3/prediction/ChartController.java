package nl.mehh.dta.assignment3.prediction;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import nl.mehh.dta.assignment3.App;

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

        SimpleExponentialSmoothing ses = new SimpleExponentialSmoothing(App.getData(), 12, 0.6, 48);
        DoubleExponentialSmoothing des = new DoubleExponentialSmoothing(App.getData(), 0.6, 0.2, 48);

        System.out.println(des.calculateError());

        chart.getData().add(createSeries(ses.generateSmoothedValues(), "SES"));
        chart.getData().add(createSeries(des.generateSmoothedValues(), "DES"));
        chart.getXAxis().setLabel("Time");
        chart.getYAxis().setLabel("Demand");
        chart.setCreateSymbols(false);
    }

    private XYChart.Series<Integer, Double> createSeries(Map<Integer, Double> values, String name){
        XYChart.Series<Integer, Double> series = new XYChart.Series<>();
        series.setName(name);
        values.keySet().stream().forEach(k -> series.getData().add(new XYChart.Data<>(k, values.get(k))));
        return series;
    }
}
