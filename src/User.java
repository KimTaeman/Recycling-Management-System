import java.util.ArrayList;

public class User {
    // Attributes
    private String name;
    private int id;
    private int totalPoints;
    private int gamesPlayed;
    ArrayList < RecyclableItem > recycledItems;

    // Constructors
    public User(String name, int id) {
        this.setName(name);
        this.setId(id);
        this.setTotalPoints(0);
        this.setGamesPlayed(0);
        this.recycledItems = new ArrayList < RecyclableItem > ();
    }

    //Setters and Getters
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
    public int getTotalPoints() {
        return totalPoints;
    }
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
    public int getGamesPlayed() {
        return gamesPlayed;
    }
    public void setRecycledItems(ArrayList < RecyclableItem > recycledItems) {
        this.recycledItems = recycledItems;
    }
    public ArrayList < RecyclableItem > getRecycledItems() {
        return recycledItems;
    }

    // Methods
    public void addRecycledItem(RecyclableItem item) {
        recycledItems.add(item);
    }
    public void removeRecycledItem(RecyclableItem item) {
        recycledItems.remove(item);
    }
    public void incrementTotalPoints(int points) {
        totalPoints += points;
    }
    public void incrementGamesPlayed() {
        gamesPlayed++;
    }
    public void decrementGamesPlayed() {
        gamesPlayed--;
    }
    public void resetTotalPoints() {
        totalPoints = 0;
    }
    public void resetGamesPlayed() {
        gamesPlayed = 0;
    }
    public void resetRecycledItems() {
        recycledItems.clear();
    }
    public void resetUser() {
        totalPoints = 0;
        gamesPlayed = 0;
        recycledItems.clear();
    }
    public void printUserInfo() {
        System.out.println("User Name: " + name);
        System.out.println("User ID: " + id);
        System.out.println("Total Points: " + totalPoints);
        System.out.println("Games Played: " + gamesPlayed);
        System.out.println("Recycled Items: " + recycledItems.size());
    }
    public void printRecycledItems() {
        System.out.println("Recycled Items: ");
        for (RecyclableItem item: recycledItems) {
            System.out.println(item.getName() + " - " + item.calculatePoints() + " points");
            System.out.println("   " + item.getRecyclingInstructions());
        }
        System.out.println("Total Recycled Items: " + recycledItems.size());

    }
    public void playGame() {
        gamesPlayed++;
    }
    public void recycleItem(RecyclableItem item) {
        recycledItems.add(item);
        totalPoints += item.calculatePoints();
    }

}