package utils;

import java.util.Random;

/**
 * Cette énumération représente différents loisirs ou hobbies, tels que la musique, les voyages, la cuisine, le sport,
 * la peinture, la programmation, le jardinage, la lecture, et l'escalade.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 *
 */
public enum Hobbies {
    MUSIQUE("Musique"),
    VOYAGES("Voyages"),
    CUISINE("Cuisine"),
    SPORT("Sport"),
    PEINTURE("Peinture"),
    PROGRAMMATION("Programmation"),
    JARDINAGE("Jardinage"),
    LECTURE("Lecture"),
    ESCALADE("Escalade");

    /**
     * Générateur de nombres aléatoires utilisé pour la création de hobbies aléatoires.
     */
    private static final Random RANDOM = new Random();

    /**
     * Le nom du hobby.
     */
    private final String hobby;

    /**
     * Crée une instance de l'énumération avec un nom de hobby associé.
     *
     * @param hobby Le nom du hobby.
     */
    Hobbies(String hobby) {
        this.hobby = hobby;
    }

    /**
     * Génère aléatoirement un hobby parmi les valeurs de l'énumération.
     *
     * @return Un hobby choisi aléatoirement.
     */
    public static Hobbies generateRandomHobby() {
        return values()[RANDOM.nextInt(values().length)];
    }

    /**
     * Obtient le nom du hobby.
     *
     * @return Le nom du hobby.
     */
    public String getHobby() {
        return this.hobby;
    }

    /**
     * Obtient le hobby à partir d'une chaîne de caractères.
     *
     * @param hobbyString Le hobby.
     * @return La valeur du hobby.
     */
    public static Hobbies getHobbyFromString(String hobbyString) {
        for (Hobbies hobbies : Hobbies.values())
            if (hobbies.getHobby().equals(hobbyString)) return hobbies;

        return Hobbies.CUISINE;
    }
}