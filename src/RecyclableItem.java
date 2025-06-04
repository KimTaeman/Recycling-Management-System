public class RecyclableItem {
    // Attributes
    private String name;
    private String type;
    private double weight;
    private int basePoints;
    private boolean isContaminated;;

    // Constructor
    public RecyclableItem(String name, String type, double weight, int basePoints, boolean isContaminated) {
        this.setName(name);
        this.setType(type);
        this.setWeight(weight);
        this.setBasePoints(basePoints);
        this.setContaminated(isContaminated);
    }

    // Setters and Getters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public int getBasePoints() {
        return basePoints;
    }
    public void setBasePoints(int basePoints) {
        this.basePoints = basePoints;
    }
    public boolean isContaminated() {
        return isContaminated;
    }
    public void setContaminated(boolean isContaminated) {
        this.isContaminated = isContaminated;
    }

    // Methods
    public double calculatePoints() {
        if (isContaminated) {
            return 0;
        } else {
            // For penalty items (weight = 0), return basePoints directly
            if (weight == 0 && basePoints < 0) {
                return basePoints;
            }
            return weight * basePoints;
        }
    }
    public String getRecyclingInstructions() {
        switch (type) {
            case "plastic":
                return "Rinse and remove caps/labels. Flatten containers to save space.";
            case "paper":
                return "Keep dry and clean. Remove any plastic windows from envelopes.";
            case "metal":
                return "Rinse cans. Aluminum foil should be clean and balled up.";
            case "glass":
                return "Rinse and remove lids. Don't recycle broken glass in curbside bins.";
            case "e-waste":
                return "Take to special e-waste collection points. Never throw in regular trash!";
            case "organic":
                return "Can be composted. Remove any stickers from fruit peels.";
            case "none":
                return "Requires special disposal. Check local hazardous waste facilities.";
            default:
                return "Check local recycling guidelines for proper disposal.";
        }
    }
}