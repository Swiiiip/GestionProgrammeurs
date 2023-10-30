package utils;

/**
 * Cette énumération représente le genre d'une personne, avec les valeurs possibles "male" (masculin),
 * "female" (féminin), et "unknown" (inconnu par défaut).
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 *
 */
public enum Gender {
    MALE("male"),
    FEMALE("female"),
    DEFAULT("unknown");

    /**
     * Le genre.
     */
    private final String gender;

    /**
     * Crée une instance de l'énumération avec une valeur de genre associée.
     *
     * @param gender La valeur du genre.
     */
    Gender(String gender) {
        this.gender = gender;
    }

    /**
     * Obtient la valeur du genre.
     *
     * @return La valeur du genre.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Vérifie si le genre est féminin (female).
     *
     * @return Vrai si le genre est féminin, sinon faux.
     */
    public boolean isWoman() {
        return getGender().equalsIgnoreCase("female");
    }
}

