package nl.mehh.dta.assignment3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.mehh.dta.assignment1.cluster.util.L;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * TODO: Write class level documentation
 *
 * @author Marcel
 * @since 19-6-2016.
 */
public class App extends Application {

    private static Map<Integer, Double> data;

    public static void main(String[] args) {
        data = load("forecastingWalmart.csv");

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(
                FXMLLoader.load(
                        getClass().getResource(
                                "/graphs.fxml"
                        )
                )
        );

        stage.setTitle("Assignment 3");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static Map<Integer, Double> load(String fileName) {
        Map<Integer, Double> data = new HashMap<>();
        try {
            String file = App.class.getClassLoader().getResource(fileName).getFile().replaceAll("%20", " ");
            Scanner fileScanner = new Scanner(new FileReader(file));
            int row=0;
            fileScanner.nextLine(); // Skip header
            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] fields = line.split(",");

                if((fields[0].equals("9")) && (fields[1].equals("20")))
                    data.put(++row, Double.valueOf(fields[3]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (NullPointerException e) {
            L.e(e, "Specified file could not be loaded, no file not found: '%s\\%s'",
                    new File("src/main/resources").getAbsolutePath(), fileName);
            System.exit(-1);
        }
        return data;
    }

    public static Map<Integer, Double> getData() {
        return data != null && !data.isEmpty() ? data : Collections.emptyMap();
    }
}
