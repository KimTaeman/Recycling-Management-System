import java.util.*;
import java.io.*;

public class CenterStorage {
    private static final String CENTER_DATA_FILE = "centers.csv";

    public static void saveCenters(Map < String, RecyclingCenter > centers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CENTER_DATA_FILE))) {
            for (RecyclingCenter center: centers.values()) {
                writer.println(String.join(",",
                    center.getName(),
                    String.valueOf(center.getId()),
                    center.getLocation(),
                    String.valueOf(center.getTotalCollectedWeight()),
                    String.valueOf(center.getTotalCollectedItems()),
                    String.join(";", center.getAcceptedTypes())
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving center data: " + e.getMessage());
        }
    }

    public static Map < String, RecyclingCenter > loadCenters() {
        Map < String, RecyclingCenter > centers = new HashMap < > ();
        File file = new File(CENTER_DATA_FILE);
        if (!file.exists()) return centers;

        try (BufferedReader reader = new BufferedReader(new FileReader(CENTER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    RecyclingCenter center = new RecyclingCenter(
                        parts[0],
                        Integer.parseInt(parts[1]),
                        parts[2]
                    );
                    center.setTotalCollectedWeight(Double.parseDouble(parts[3]));
                    center.setTotalCollectedItems(Integer.parseInt(parts[4]));

                    // Add accepted types
                    String[] types = parts[5].split(";");
                    for (String type: types) {
                        if (!type.isEmpty()) {
                            center.addAcceptedType(type);
                        }
                    }

                    centers.put(center.getName(), center);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading center data: " + e.getMessage());
        }
        return centers;
    }
}