public enum RecyclingCategory {
    PLASTIC("plastic", "Yellow Bin for recyclable and Blue Bin for non-recyclable"),
    PAPER("paper", "Yellow Bin for recyclable and Blue Bin for non-recyclable"),
    METAL("metal", "Yellow Bin"),
    GLASS("glass", "Yellow Bin"),
    E_WASTE("e-waste", "E-Waste Bin"),
    ORGANIC("organic", "Green Bin"),
    NONE("none", "Special Handling");

    private final String name;
    private final String bin;

    RecyclingCategory(String name, String bin) {
        this.name = name;
        this.bin = bin;
    }

    public String getName() {
        return name;
    }
    public String getBin() {
        return bin;
    }

    public static RecyclingCategory fromName(String name) {
        for (RecyclingCategory category: values()) {
            if (category.name.equalsIgnoreCase(name)) {
                return category;
            }
        }
        return NONE;
    }
}