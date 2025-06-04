import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.Random;

public class App {
    private static final Map < String, User > users = UserStorage.loadUsers();
    private static final Map < String, RecyclingCenter > centers = CenterStorage.loadCenters();

    public static void main(String[] args) {
        // Inputs
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        ArrayList < String > upcyclingFacts = UpcyclingFactLoader.loadFacts("src/UpcyclingFacts.txt");

        // Printing welcome message
        while (true) {
            System.out.println("********************************************");
            System.out.println("   Welcome to the Recycling Management System!");
            System.out.println("              ____");
            System.out.println("             / _\\ \\");
            System.out.println("           .'\\/  \\ \\");
            System.out.println("         ,'   \\   \\ \\");
            System.out.println("          / /-'    \\ \\ .");
            System.out.println("         / /       ,\\ '|");
            System.out.println("        / /        '-._|");
            System.out.println("       / /_.'|________\\_\\");
            System.out.println("       \\/_<  ___________/");
            System.out.println("           '.|");
            System.out.println("********************************************");
            // crd: https://www.asciiart.eu/logos/recycle


            // Main mode selection
            System.out.println("\nSelect Mode:");
            System.out.println("1. User Mode");
            System.out.println("2. Center Mode");
            System.out.println("0. Exit Program");
            System.out.print("Choose an option: ");
            String modeChoice = sc.nextLine().trim();

            switch (modeChoice) {
                case "1":
                    userMode(sc, random, upcyclingFacts);
                    break;
                case "2":
                    centerMode(sc);
                    break;
                case "0": // Save data before exiting
                    UserStorage.saveUsers(users);
                    CenterStorage.saveCenters(centers);
                    sc.close();
                    System.out.println("Thank you for using the Recycling Management System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void userMode(Scanner sc, Random random, ArrayList < String > upcyclingFacts) {
        System.out.println("\n=== USER MODE ===");

        // User login/registration
        System.out.print("Enter your username (or 'new' to register): ");
        String userName = sc.nextLine();

        User user;
        if (userName.equalsIgnoreCase("new")) {
            System.out.print("Enter your new username: ");
            userName = sc.nextLine();
            int newId = users.size() + 1;
            user = new User(userName, newId);
            users.put(userName, user);
            System.out.println("New user created!");
        } else {
            user = users.get(userName);
            if (user == null) {
                System.out.println("User not found. Creating new user...");
                int newId = users.size() + 1;
                user = new User(userName, newId);
                users.put(userName, user);
            } else {
                System.out.println("Welcome back, " + userName + "!");
            }
        }

        // User main menu
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View Your Stats");
            System.out.println("2. Recycle an Item");
            System.out.println("3. View Available Centers");
            System.out.println("4. Play Mini Trash Sorting Game");
            System.out.println("5. Show Today's Upcycling Fact");
            System.out.println("0. Back to Mode Selection");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n--- User Stats ---");
                    user.printUserInfo();
                    break;
                case "2":
                    recycleItemMenu(sc, user);
                    break;
                case "3":
                    viewCentersMenu();
                    break;
                case "4":
                    System.out.println("\n--- Mini Trash Sorting Game ---");
                    MiniTrashCollectionGame game = new MiniTrashCollectionGame(user, "G1", 10);
                    game.play();
                    break;
                case "5":
                    String fact = upcyclingFacts.get(random.nextInt(upcyclingFacts.size()));
                    String[] types = {
                        "plastic",
                        "paper",
                        "metal",
                        "glass",
                        "e-waste",
                        "organic"
                    };
                    String randomType = types[random.nextInt(types.length)];
                    RecyclableItem tipItem = new RecyclableItem("item", randomType, 0, 0, false);

                    System.out.println("\nUpcycling Fact of the Day ");
                    System.out.println(fact);
                    System.out.println("\n Recycling Tip for " + randomType + ": " + tipItem.getRecyclingInstructions());
                    break;
                case "0":
                    return; // return to the main mode selection
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static boolean isYesResponse(String input) {
        return input.trim().matches("(?i)y(es)?"); // matches "y", "Y", "yes", "Yes", etc.
        }

    private static void recycleItemMenu(Scanner sc, User user) {
        System.out.println("\n--- Recycle an Item ---");

        // Show available centers         
        System.out.println("Available Centers:");
        for (RecyclingCenter center : centers.values()) {
            System.out.println("- " + center.getName() + " (ID: " + center.getId() + ")");
        }
        // Select center
        System.out.print("Enter Center ID: ");
        int centerId = Integer.parseInt(sc.nextLine());

        RecyclingCenter center = null;
        for (RecyclingCenter c : centers.values()) {
            if (c.getId() == centerId) {
                center = c;
                break; 
            }
        }

        // RecyclingCenter center = centers.values().stream()
        //     .filter(c -> c.getId() == centerId)
        //     .findFirst()
        //     .orElse(null);

        if (center == null) {
            System.out.println("Invalid Center ID!");
            return;
        }

        // Display accepted types before item entry
        System.out.println("\nThis center accepts: " + String.join(", ", center.getAcceptedTypes()));

        // Item details
        System.out.print("Enter item name: ");
        String itemName = sc.nextLine();

        System.out.print("Enter item type (plastic/paper/metal/glass/e-waste/organic/none): ");
        String itemType = sc.nextLine().toLowerCase();

        if (!center.isTypeAccepted(itemType)) {
            System.out.println("Sorry, this center does not accept " + itemType);
            return;
        }

        System.out.print("Enter weight (kg): ");
        double weight = Double.parseDouble(sc.nextLine());

        System.out.print("Enter base points: ");
        int basePoints = Integer.parseInt(sc.nextLine());

        System.out.print("Is it contaminated? (yes/no): ");
        boolean contaminated = isYesResponse(sc.nextLine());

        // Create item
        RecyclableItem item = new RecyclableItem(itemName, itemType, weight, basePoints, contaminated);

        System.out.println("\nWould you like recycling tips for this item? (yes/no)");
        if (isYesResponse(sc.nextLine())) {
            System.out.println("\nRecycling Instructions:");
            System.out.println(item.getRecyclingInstructions());
        }

        // Process recycling item & add collected weight + items in the center
        user.recycleItem(item);
        center.setTotalCollectedWeight(center.getTotalCollectedWeight() + weight); 
        center.setTotalCollectedItems(center.getTotalCollectedItems() + 1);

        System.out.println("\nItem recycled at " + center.getName() + "! Points earned: " + (int) item.calculatePoints());
    }

    private static void viewCentersMenu() {
        System.out.println("\n--- Available Recycling Centers ---");
        for (RecyclingCenter center : centers.values()) {
            System.out.println("\n" + center.generateReport());
            System.out.println("Accepted types: " + center.getAcceptedTypes());
        }
    }

    private static void centerMode(Scanner sc) {
        System.out.println("\n=== CENTER MODE ===");

        // Center login/registration
        System.out.print("Enter center ID (or 'new' to register): ");
        String centerInput = sc.nextLine();

        RecyclingCenter center;
        // For New Center
        if (centerInput.equalsIgnoreCase("new")) {
            System.out.print("Enter center name: ");
            String name = sc.nextLine();

            System.out.print("Enter location: ");
            String location = sc.nextLine();

            int newId = centers.size() + 101; // Start IDs from 101
            center = new RecyclingCenter(name, newId, location);

            // Add accepted types
            System.out.println("Add accepted types (comma separated - plastic,paper,metal,glass,e-waste,organic,none): ");
            String[] types = sc.nextLine().split(",");
            for (String type: types) {
                center.addAcceptedType(type.trim());
            }

            centers.put(name, center);
            System.out.println("New center registered!");
        } 
        else {
            // For Existing Center
            try {
            int centerId = Integer.parseInt(centerInput); 
            center = null;
            for (RecyclingCenter c : centers.values()) {
                if (c.getId() == centerId) {
                    center = c;
                    break; // Exit early once found
                }
            }

            if (center == null) {
                System.out.println("Center not found!");
                return;
            }
            System.out.println("Welcome, " + center.getName() + "!");
            // For Invalid Center
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID! Please enter a number or 'new'.");
                return; // Back to main mode
            }
        }

        // Center main menu
        while (true) {
            System.out.println("\n=== Center Menu ===");
            System.out.println("1. View Center Report");
            System.out.println("2. Update Accepted Types");
            System.out.println("3. View All Centers");
            System.out.println("0. Exit to Main Menu");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n--- Center Report ---");
                    System.out.println(center.generateReport());
                    break;
                case "2":
                    updateAcceptedTypes(sc, center);
                    break;
                case "3":
                    viewCentersMenu();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void updateAcceptedTypes(Scanner sc, RecyclingCenter center) {
        System.out.println("\nCurrent accepted types: " + center.getAcceptedTypes());
        System.out.println("1. Add type");
        System.out.println("2. Remove type");
        System.out.println("3. Back");
        System.out.print("Choose an option: ");
        String choice = sc.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Enter type to add: ");
                String addType = sc.nextLine().toLowerCase();
                center.addAcceptedType(addType);
                System.out.println("Added " + addType);
                break;
            case "2":
                System.out.print("Enter type to remove: ");
                String removeType = sc.nextLine().toLowerCase();
                center.removeAcceptedType(removeType);
                System.out.println("Removed " + removeType);
                break;
            case "3":
                return;
            default:
                System.out.println("Invalid option");
        }
    }
}