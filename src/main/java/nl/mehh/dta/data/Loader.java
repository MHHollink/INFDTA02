package nl.mehh.dta.data;

import nl.mehh.dta.vector.WineDataVector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings("ConstantConditions")
public class Loader {

    /**
     * Method that loads a file from the src/main/resources with {@param fileName}
     *
     * @return
     *      Map containing      Key     : {@link nl.mehh.dta.vector.WineDataVector#customerIdentifier}
     *                          Value   : {@link nl.mehh.dta.vector.WineDataVector}
     */
    public static Map<Integer, WineDataVector> load(String fileName) {
        Map<Integer, WineDataVector> data = new HashMap<>();
        try {
            String file = Loader.class.getClassLoader().getResource(fileName).getFile().replaceAll("%20", " ");
            Scanner fileScanner = new Scanner(new FileReader(file));
            int row = 0;
            while (fileScanner.hasNextLine()) {
                row++;
                String line = fileScanner.nextLine();
                String[] fields = line.split(",");
                for (int i = 0; i < fields.length; i++) {
                    WineDataVector vector = data.get(i);
                    if(vector == null) vector = new WineDataVector(i+1);
                    int fieldValue = Integer.parseInt(fields[i]);
                    if (fieldValue == 1) vector.addOffer(row);
                    data.put(i, vector);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (NullPointerException e) {
            System.out.printf("Specified file could not be loaded, no file not found: '%s\\%s'",
                    new File("src/main/resources").getAbsolutePath(), fileName);
            System.exit(-1);
        }
        return data;
    }

}