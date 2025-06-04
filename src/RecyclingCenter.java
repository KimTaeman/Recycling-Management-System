import java.util.ArrayList;

public class RecyclingCenter {
    private String name;
    private int id;
    private String location;
    private ArrayList < String > acceptedTypes;
    private double totalCollectedWeight;
    private int totalCollectedItems;

    // Constructor
    public RecyclingCenter(String name, int id, String location) {
        this.name = name;
        this.id = id;
        this.location = location;
        this.acceptedTypes = new ArrayList < String > ();
        this.totalCollectedWeight = 0.0;
        this.totalCollectedItems = 0;
    }
    // Setters and Getters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public ArrayList < String > getAcceptedTypes() {
        return acceptedTypes;
    }
    public void setAcceptedTypes(ArrayList < String > acceptedTypes) {
        this.acceptedTypes = acceptedTypes;
    }
    public double getTotalCollectedWeight() {
        return totalCollectedWeight;
    }
    public void setTotalCollectedWeight(double totalCollectedWeight) {
        this.totalCollectedWeight = totalCollectedWeight;
    }
    public int getTotalCollectedItems() {
        return totalCollectedItems;
    }
    public void setTotalCollectedItems(int totalCollectedItems) {
        this.totalCollectedItems = totalCollectedItems;
    }
    // Methods
    public void addAcceptedType(String type) {
        acceptedTypes.add(type);
    }
    public void removeAcceptedType(String type) {
        acceptedTypes.remove(type);
    }
    public boolean isTypeAccepted(String type) {
        return acceptedTypes.contains(type);
    }
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Recycling Center Report:\n")
            .append("Name: ").append(name).append("\n")
            .append("ID: ").append(id).append("\n")
            .append("Location: ").append(location).append("\n")
            .append("Recycling Tips:\n");

        for (String type: acceptedTypes) {
            RecyclableItem dummyItem = new RecyclableItem("sample", type, 0, 0, false);
            report.append("- ").append(type).append(": ").append(dummyItem.getRecyclingInstructions()).append("\n");
        }

        report.append("Total Collected Weight: ").append(totalCollectedWeight).append(" kg\n")
            .append("Total Collected Items: ").append(totalCollectedItems);
        return report.toString();
    }

}