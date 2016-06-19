package nl.mehh.dta.assignment3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
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
        data = parseFile("SwordForecasting.csv");

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

    private static Map<Integer, Double> parseFile(String file) {
        Map<Integer, Double> values = new HashMap<>();
        try {
            try (
                    Scanner scanner = new Scanner(
                            new File(
                                    App.class.getClassLoader()
                                            .getResource(file).getFile()
                                            .replaceAll("%20", " ")
                            )
                    )
            ) {
                int i = 0;
                while (scanner.hasNextDouble()) {
                    i++;
                    values.put(i, Double.parseDouble(scanner.next()));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("file not found");
        }
        return values;
    }

    public static Map<Integer, Double> getData() {
        return data != null && !data.isEmpty() ? data : Collections.emptyMap();
    }
}
