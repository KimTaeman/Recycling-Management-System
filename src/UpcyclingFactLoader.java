import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class UpcyclingFactLoader {
    public static ArrayList<String> loadFacts(String filename) {
        ArrayList<String> facts = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) facts.add(line);
            }
        } catch (IOException e) {
            facts.add("No facts available. (File not found)");
        }
        return facts;
    }
}