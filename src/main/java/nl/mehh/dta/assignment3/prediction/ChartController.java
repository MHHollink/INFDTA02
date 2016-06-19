package nl.mehh.dta.assignment3.prediction;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import nl.mehh.dta.assignment3.App;

import java.util.ArrayList;
import java.util.List;
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

        List<SimpleExponentialSmoothing> sesSmoothings = new ArrayList<>();
        List<DoubleExponentialSmoothing> desSmoothings = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            sesSmoothings.add(new SimpleExponentialSmoothing(App.getData(), 12, 0.005*i, 48));
            for (int j = 0; j < 200; j++) {
                desSmoothings.add(new DoubleExponentialSmoothing(App.getData(), 0.005*i, 0.005*j, 48));
            }
        }

        SimpleExponentialSmoothing ses = sesSmoothings.stream().sorted(
                (a, b) -> a.calculateError() < b.calculateError() ? -1 : 1)
                .findFirst().get();
        System.out.println(ses.calculateError());

        DoubleExponentialSmoothing des = null;
        for (DoubleExponentialSmoothing desSmoothing : desSmoothings) {
            if(des == null || des.calculateError() > desSmoothing.calculateError())
                des = desSmoothing;
        }
        assert des != null;
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
