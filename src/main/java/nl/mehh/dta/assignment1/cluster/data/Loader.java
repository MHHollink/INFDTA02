package nl.mehh.dta.assignment1.cluster.data;

import nl.mehh.dta.assignment1.cluster.util.L;
import nl.mehh.dta.assignment1.cluster.vector.WineDataVector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("ConstantConditions")
public class Loader {
    /**
     * Method that loads a file from the src/main/resources with {@param fileName}
     *
     * @return
     *      Map containing      Key     : {@link WineDataVector#customerIdentifier}
     *                          Value   : {@link WineDataVector}
     */
    public static List<Point> load(String fileName) {
        List<Point> data = new ArrayList<>();
        try {
            String file = Loader.class.getClassLoader().getResource(fileName).getFile().replaceAll("%20", " ");
            Scanner fileScanner = new Scanner(new FileReader(file));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] fields = line.split(" ");
                int x = 0;
                int y = 0;
                for (String field : fields) {
                    if (field.length() != 0 && x == 0) {
                        x = Integer.parseInt(field);
                    } else if(field.length() != 0 && y == 0) {
                        y = Integer.parseInt(field);
                    } else if (field.length() != 0) {
                        System.out.println("Some thing is going wrong!");
                    }
                }
                data.add(new Point(x, y));
                x = 0;
                y = 0;
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

}