package nl.mehh.dta.assignment3.prediction;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import nl.mehh.dta.assignment3.App;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

        SimpleExponentialSmoothing ses = sesSmoothings.stream()
                .sorted((o1, o2) -> ((Double)o1.calculateError()).compareTo((Double)o2.calculateError()))
                .findFirst().get();
        System.out.println("Lowest calculated error for SES: " + ses.getCalculatedError() + " with smoothing factor " + ses.getDataSmoothingFactor());
        ses.printPrediction();

        DoubleExponentialSmoothing des = desSmoothings.stream()
                .sorted((o1, o2) -> ((Double)o1.calculateError()).compareTo((Double)o2.calculateError()))
                .findFirst()
                .get();
        System.out.println("Lowest calculated error for DES: " + des.getCalculatedError() + " with smoothing factor " + des.getDataSmoothingFactor() + " and trend smoothing factor " + des.getTrendSmoothingFactor());
        des.printPrediction();

        chart.getData().add(createSeries(ses.generateSmoothedValues(), "SES"));
        chart.getData().add(createSeries(des.generateSmoothedValues(), "DES"));
        chart.getXAxis().setLabel("Time");
        chart.getYAxis().setLabel("Demand");
        chart.setCreateSymbols(false);
    }

    private XYChart.Series<Integer, Double> createSeries(Map<Integer, Double> values, String name){
        return new XYChart.Series<Integer, Double>(name, values.entrySet().stream()
                .map(entry -> new XYChart.Data<Integer, Double>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toCollection(() -> FXCollections.observableList(new LinkedList<>()))));
    }
}
