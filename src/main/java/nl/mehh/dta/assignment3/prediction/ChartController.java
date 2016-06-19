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
 * @since 19-6-2016.
 */
public class ChartController {

    @FXML
    public LineChart<Integer, Double> sesChart;

    @FXML
    public LineChart<Integer, Double> desChart;

    @FXML
    public void initialize() {
        sesChart.setTitle("SimpleExponentialSmoothing");
        desChart.setTitle("DoubleExponentialSmoothing");

        sesChart.getData().add(createSeries(App.getData()));
//        desChart.getData().add(createSeries(App.getData()));
    }

    private XYChart.Series<Integer, Double> createSeries(Map<Integer, Double> values){
        XYChart.Series<Integer, Double> series = new XYChart.Series<>();

        for (Integer i : values.keySet()) {
            series.getData().add(new XYChart.Data<>(i, values.get(i)));
        }

//        values.keySet().forEach( key -> {
//            series.getData().add(new XYChart.Data<>(key, values.get(key)));
//        });

        return series;
    }
}
