package utils;

/**
 * Cette énumération représente les titres de civilité d'une personne, tels que "Monsieur," "Madame," "Mademoiselle," et "Unknown."
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public enum Title {
    MR("Monsieur"),
    MRS("Madame"),
    MS("Mademoiselle"),
    DEFAULT("Unknown");

    /**
     * Le titre de civilité
     */
    private final String title;

    /**
     * Crée une instance de l'énumération avec un titre de civilité associé.
     *
     * @param title Le titre de civilité.
     */
    Title(String title) {
        this.title = title;
    }

    /**
     * Obtient le titre de civilité.
     *
     * @return Le titre de civilité.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Obtient le titre de civilité à partir d'une chaîne de caractères.
     *
     * @param titleString Le titre de civilité.
     * @return Le titre de civilité.
     */
    public static Title getTitleFromString(String titleString) {
        for (Title title : Title.values())
            if (title.getTitle().equals(titleString)) return title;

        return Title.DEFAULT;
    }

}

