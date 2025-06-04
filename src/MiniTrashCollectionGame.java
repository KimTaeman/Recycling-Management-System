import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class MiniTrashCollectionGame {
  // Attributes
  private static final int DEFAULT_ROUNDS = 5;
  private static final int PENALTY_POINTS = 5;

  private static final Map < String, RecyclingCategory > ITEM_CATEGORIES = new HashMap < > ();
  private static List < String > ITEM_NAMES = new ArrayList < > ();
  private static final Map < String, String > SPECIAL_ART = new HashMap < > ();

  private static final String TRASH_BIN_ART =
    "      _____________\n" +
    "     /             \\\n" +
    "    /   TRASH BIN   \\\n" +
    "   /_________________\\\n" +
    "   |   |   |   |   | |\n" +
    "   |___|___|___|___|_|\n";

  private User user;
  private final String gameId;
  private int duration;
  private final List < RecyclableItem > collectedItems;
  private final int rounds;
  private final Random random = new Random();

  // Static initializer block
  static {
    initializeGameData();
  }

  public MiniTrashCollectionGame(User user, String gameId, int duration) {
    this(user, gameId, duration, DEFAULT_ROUNDS);
  }

  public MiniTrashCollectionGame(User user, String gameId, int duration, int rounds) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    if (gameId == null || gameId.trim().isEmpty()) {
      throw new IllegalArgumentException("Game ID cannot be null or empty");
    }
    if (duration <= 0) {
      throw new IllegalArgumentException("Duration must be positive");
    }
    if (rounds <= 0) {
      throw new IllegalArgumentException("Rounds must be positive");
    }

    this.user = user;
    this.gameId = gameId;
    this.duration = duration;
    this.rounds = rounds;
    this.collectedItems = new ArrayList < > ();
  }

  // Fixed initialization method
  private static void initializeGameData() {
    // Clear existing data
    ITEM_CATEGORIES.clear();
    ITEM_NAMES.clear();
    SPECIAL_ART.clear();

    // ASCII art for items
    // Plastic items
    addItem("Water Bottle", RecyclingCategory.PLASTIC, "  ___\n" +
      " |___|\n" +
      " )___(\n" +
      " /   \\\n" +
      "|     |\n" +
      "(_____)\n" +
      "| H20 |\n" +
      "(_____)    \n" +
      "|     |\n" +
      "|     |\n" +
      "'-----'\n");
    addItem("Soda Bottle", RecyclingCategory.PLASTIC,
      "   _    \n" +
      " .!.!.   \n" +
      "  ! !   \n" +
      "  ; : \n" +
      " ;   :  \n" +
      ";_____:  \n" +
      "! Coca!   \n" +
      "!_____!   \n" +
      ":     :\n" +
      ":     ;   \n" +
      ".'   '.  \n" +
      ":     :   \n" +
      "''''' \n"
    );
    //crd: https://ascii.co.uk/art/coke
    addItem("Shampoo Bottle", RecyclingCategory.PLASTIC, " ==[_ ]\n" +
      " .-'. '-.\n" +
      "/:;/ _.-'\\\n" +
      "|:._   .-|\n" +
      "|:._     |\n" +
      "|:._     |\n" +
      "|:._     |\n" +
      "|:._     |\n" +
      "`-.____.-' \n"
    );
    //  crd: https://asciiart.website/index.php?art=objects/bottles
    addItem("Plastic Bag", RecyclingCategory.PLASTIC, "   .--._ .\n" +
      "    \\ ).'\n" +
      "     )|/\n" +
      "  _.'''-._\n" +
      "  (        \\\n" +
      "  \\        )\n" +
      "   )'-.    (\n" +
      "  /     _.-'\\\n" +
      " /           )\n" +
      "('-._       /\n" +
      " \\        _/  \n" +
      "  '-.__==''\n"
    );
    // crd: https://ascii.co.uk/art/sack
    // Paper items
    addItem("Newspaper", RecyclingCategory.PAPER, " __________\n" +
      "|DAILY NEWS|\n" +
      "|&&& ======|\n" +
      "|=== ======|\n" +
      "|=== == %%$|\n" +
      "|[_] ======|\n" +
      "|=== ===!##|\n" +
      "|__________|\n"

    );
    //  crd: https://ascii.co.uk/art/newspaper
    addItem("Cardboard Box", RecyclingCategory.PAPER, "    +------+\n" +
      "  .'|    .'|\n" +
      "+---+--+'  |\n" +
      "|   |  |   |\n" +
      "|   +--+---+\n" +
      "| .'   | .'\n" +
      "+------+'\n"
      // crd: https://ascii.co.uk/art/boxes
    );
    addItem("Pizza Box", RecyclingCategory.PAPER, " +_________+\n" +
      "'~          ~\n" +
      "  '~        '~\n" +
      "   +_________+\n" +
      " ..  pizza  . + \n" +
      "+_________+ .\n" +
      "|_________|\n");

    // Metal items
    addItem("Soda Can", RecyclingCategory.METAL, "     __\n" +
      " .-'`` _``'-.\n" +
      "/'.   '.(##)'\\\n" +
      "|  `'----'`  |\n" +
      "|        ----|\n" +
      "|        . .-|\n" +
      "| .::::. |_| |\n" +
      "|::::''':.-. |\n" +
      "|;,,;;;;;|_|_|\n" +
      "| ';;;;' . . |\n" +
      "|        |_|_|\n" +
      "|        .-. |\n" +
      "\\        |_|_/\n" +
      " `.________.'\n"
    );
    // crd: https://www.angelfire.com/ca/mathcool/fooddrink.html
    addItem("Tuna Can", RecyclingCategory.METAL, " (__)(__)(__)\n" +
      "(__)(__)(__)|\n" +
      "|  ||  ||  ||\n" +
      "|O<||O<||O<|'\n" +
      "'--''--''--'\n");

    // Glass items
    addItem("Wine Bottle", RecyclingCategory.GLASS,
      " [=]\n" +
      " | |\n" +
      " }@{\n" +
      "/   \\      .\n" +
      ":___;     '  \n" +
      "|&&&|    \\~~~/ \n" +
      "|&&&|     \\_/\n" +
      "|---|      Y\n" +
      "'---'     _|_\n"
    );
    // crd: https://www.angelfire.com/ca/mathcool/fooddrink.html
    addItem("Broken Glass", RecyclingCategory.GLASS,
      "  [~]\n" +
      "  [~]\n" +
      "  |=|\n" +
      ".-' '-.\n" +
      "|-----|\n" +
      "|/\\  \\|\n" +
      "|' \\ /|\n" +
      "|   Y |\n" +
      "|-----|\n" +
      "'-----' \n");
    // crd: https://www.angelfire.com/ca/mathcool/fooddrink.html
    // E-waste
    addItem("Battery", RecyclingCategory.E_WASTE,
      "    ╔══╗ \n" +
      "  |-----| \n" +
      "  | ___ | \n" +
      "  | ___ | \n" +
      "  | ___ | \n" +
      "  |_____| \n");

    // Organic
    addItem("Banana Peel", RecyclingCategory.ORGANIC,
      "             ,  \n" +
      "|\\   /\\/ \\/|   ,_\n" +
      "; \\/`     '; , \\_',\n" +
      " \\        / \n" +
      "  '.    .'    /`.\n" +
      "    `~~` , /\\ `'`\n");
    // crd: https://www.angelfire.com/ca/mathcool/fooddrink.html

    // Make item names list unmodifiable
    ITEM_NAMES = Collections.unmodifiableList(ITEM_NAMES);
  }

  // Add Item without defined ASCII art 
  private static void addItem(String name, RecyclingCategory category) {
    addItem(name, category, "");
  }
  // Add Item with defined ASCII art
  private static void addItem(String name, RecyclingCategory category, String art) {
    ITEM_CATEGORIES.put(name, category);
    ITEM_NAMES.add(name);
    if (!art.isEmpty()) {
      SPECIAL_ART.put(name, art);
    }
  }

  // Game play method
  public void play() {
    printGameIntroduction();

    Scanner sc = new Scanner(System.in);
    int correctAnswers = 0;

    // Play game for 5 rounds by default (if no input)
    for (int i = 0; i < rounds; i++) {
      System.out.printf("\nRound %d of %d:\n", i + 1, rounds);
      correctAnswers += playRound(sc);
    }

    endGame(correctAnswers);
  }

  
  private int playRound(Scanner scanner) {
    GameRound round = createGameRound();

    displayRoundInfo(round);

    try {
      RecyclingCategory userChoice = getUserChoice(scanner);
      return processUserChoice(round, userChoice);
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid category. Please try again.");
      return 0;
    }
  }

  private void printGameIntroduction() {
    System.out.println("\n=== Trash Sorting Challenge ===");
    System.out.println("Sort items into these categories:");
    for (RecyclingCategory category: RecyclingCategory.values()) {
      if (category != RecyclingCategory.NONE) {
        System.out.printf("- %s (%s)%n", category.getName(), category.getBin());
      }
    }
    System.out.println("Wrong answer? Lose " + PENALTY_POINTS + " points!\n");
  }
  
  private void displayRoundInfo(GameRound round) {
    System.out.println(TRASH_BIN_ART);
    System.out.println("-----------------------------");
    System.out.println(SPECIAL_ART.getOrDefault(round.getItemName(), ""));
    System.out.println("Item: " + round.getItemName());
    System.out.printf("Weight: %.2f kg | Points: %d | %s%n",
      round.getWeight(),
      round.getBasePoints(),
      round.isContaminated() ? "Dirty!" : "Clean");

    System.out.println("Tip: " + getRecyclingTip(round));
  }

  private String getRecyclingTip(GameRound round) {
    return new RecyclableItem(
        round.getItemName(),
        round.getCategory().getName(),
        0, 0, false)
      .getRecyclingInstructions();
  }

  private String getCategoryExplanation(String itemName, String correctType) {
    switch (correctType) {
    case "plastic":
      if (itemName.contains("Bottle")) return "Plastic bottles are made of recyclable plastic polymers.";
      if (itemName.contains("Bag")) return "Plastic bags are made of thin plastic film (usually LDPE).";
      return "This plastic item should be cleaned and recycled separately.";

    case "paper":
      if (itemName.contains("Box")) return "Cardboard boxes are made of corrugated paper fibers.";
      if (itemName.equals("Pizza Box")) return "Pizza boxes are paper but can't be recycled if greasy.";
      return "Paper products should be kept dry and clean for recycling.";

    case "metal":
      if (itemName.contains("Can")) return "Food cans are typically made of steel or aluminum.";
      if (itemName.contains("Foil")) return "Aluminum foil can be recycled if clean and balled up.";
      return "Metal items are infinitely recyclable without quality loss.";

    case "glass":
      if (itemName.contains("Bottle")) return "Glass bottles are 100% recyclable and don't degrade.";
      if (itemName.contains("Jar")) return "Glass jars should be rinsed and lids removed.";
      return "Glass should be separated by color when possible.";

    case "e-waste":
      return "Electronics contain valuable metals and toxic materials that need special handling.";

    case "organic":
      if (itemName.contains("Peel")) return "Fruit peels are compostable organic material.";
      if (itemName.equals("Coffee Grounds")) return "Coffee grounds are great for composting.";
      return "Organic waste can be composted to create nutrient-rich soil.";

    default:
      return "This item requires special recycling consideration.";
    }
  }

  private void handleCorrectAnswer(GameRound round) {
    RecyclableItem item = round.createRecyclableItem();
    collectedItems.add(item);
    System.out.printf("Correct! %s goes in the %s! +%d points.%n%n",
      round.getItemName(),
      round.getCategory().getBin(),
      (int) item.calculatePoints());
  }

  private void handleWrongAnswer(GameRound round) {
    System.out.printf("Wrong bin! -%d points.%n", PENALTY_POINTS);
    System.out.printf("The correct category was: %s (%s)%n",
      round.getCategory().getName(),
      round.getCategory().getBin());

    System.out.println("Explanation: " +
      getCategoryExplanation(round.getItemName(), round.getCategory().getName()));

    collectedItems.add(round.createPenaltyItem());
    System.out.println();
  }

  public String displayGameStats() {
    StringBuilder stats = new StringBuilder();
    stats.append("Game ID: ").append(gameId).append("\n");
    stats.append("User: ").append(user.getName()).append("\n");
    stats.append("Duration: ").append(duration).append(" minutes\n");
    stats.append("Collected Items:\n");
    for (RecyclableItem item: collectedItems) {
      stats.append("- ").append(item.getName()).append(" (").append(item.getType()).append(")\n");
    }
    stats.append("Total Points: ").append(calculateTotalPoints()).append("\n");
    return stats.toString();
  }

  private void endGame(int correctAnswers) {
    // Calculate total points gained after playing game
    int netPoints = calculateTotalPoints();  
    System.out.printf("Game Over! You sorted %d out of %d items correctly.%n", correctAnswers, rounds);
    System.out.println("Net Points for this game: " + netPoints);
    System.out.println(displayGameStats());

    updateUserStats(netPoints); // can accept netagive value
  }

  private void updateUserStats(int netPoints) {
    user.incrementGamesPlayed();
    user.incrementTotalPoints(netPoints);
    List<RecyclableItem> userRecycledItems = user.getRecycledItems();
    if (userRecycledItems != null) {
        for (RecyclableItem item : collectedItems) {
            if (item != null && !"Penalty".equals(item.getName())) {
                userRecycledItems.add(item);
            }
        }
    }
  }

  // Handle user answer for category
  private int processUserChoice(GameRound round, RecyclingCategory userChoice) {
    if (userChoice == round.getCategory()) {
      handleCorrectAnswer(round);
      return 1;
    } else {
      handleWrongAnswer(round);
      return 0;
    }
  }

  // ===== Helper Methods =====
  // Creating random item, weight and base points for each game round
  private GameRound createGameRound() {
    String itemName = ITEM_NAMES.get(random.nextInt(ITEM_NAMES.size()));
    double weight = 0.1 + (0.9) * random.nextDouble();
    int basePoints = 10 + random.nextInt(10);
    boolean contaminated = random.nextInt(5) == 0;
    return new GameRound(itemName, ITEM_CATEGORIES.get(itemName),
      weight, basePoints, contaminated);
  }

  // Asking user which bin category the items is
  private RecyclingCategory getUserChoice(Scanner scanner) {
    System.out.print("Category? ");
    return RecyclingCategory.fromName(scanner.nextLine().trim());
  }

  public int calculateTotalPoints() {
    int totalPoints = 0;
    for (RecyclableItem item: collectedItems) {
      totalPoints += item.calculatePoints();
    }
    return totalPoints;
  }

  // Fixed getters and setters
  public void setUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    this.user = user;
  }

  public List < RecyclableItem > getCollectedItems() {
    return Collections.unmodifiableList(collectedItems);
  }

  public void setCollectedItems(List < RecyclableItem > items) {
    if (items == null) {
      throw new IllegalArgumentException("Items list cannot be null");
    }
    this.collectedItems.clear();
    this.collectedItems.addAll(items);
  }

  //  GAMEROUND CLASS
  private static final class GameRound {
    private final String itemName;
    private final RecyclingCategory category;
    private final double weight;
    private final int basePoints;
    private final boolean contaminated;

    // Constructor
    public GameRound(String itemName, RecyclingCategory category,
      double weight, int basePoints, boolean contaminated) {
      this.itemName = itemName;
      this.category = category;
      this.weight = weight;
      this.basePoints = basePoints;
      this.contaminated = contaminated;
    }

    // Methods
    public RecyclableItem createRecyclableItem() {
      return new RecyclableItem(itemName, category.getName(),
        weight, basePoints, contaminated);
    }

    public RecyclableItem createPenaltyItem() {
      return new RecyclableItem("Penalty", RecyclingCategory.NONE.getName(), 0, -PENALTY_POINTS, false);
    }

    // Getters
    public String getItemName() {
      return itemName;
    }
    public RecyclingCategory getCategory() {
      return category;
    }
    public double getWeight() {
      return weight;
    }
    public int getBasePoints() {
      return basePoints;
    }
    public boolean isContaminated() {
      return contaminated;
    }
  }
}