import java.io.*;
import java.util.HashMap;
import java.util.Map;
public class UserStorage {
    private static final String USER_DATA_FILE = "users.csv";

    public static void saveUsers(Map < String, User > users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_DATA_FILE))) {
            for (User user: users.values()) {
                writer.println(String.join(",",
                    user.getName(),
                    String.valueOf(user.getId()),
                    String.valueOf(user.getTotalPoints()),
                    String.valueOf(user.getGamesPlayed())
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
        }
    }

    public static Map < String, User > loadUsers() {
        Map < String, User > users = new HashMap < > ();
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    User user = new User(parts[0], Integer.parseInt(parts[1]));
                    user.setTotalPoints(Integer.parseInt(parts[2]));
                    user.setGamesPlayed(Integer.parseInt(parts[3]));
                    users.put(user.getName(), user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading user data: " + e.getMessage());
        }
        return users;
    }
}