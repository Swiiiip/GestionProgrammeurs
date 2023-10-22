package utils;

public enum Title {

    MR("Monsieur"),

    MRS("Madame"),

    MS("Mademoiselle"),

    DEFAULT("Inconnu");

    private final String title;

    Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
