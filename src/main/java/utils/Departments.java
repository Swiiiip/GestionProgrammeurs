package utils;

import java.util.Random;

/**
 * Cette énumération représente les différents départements d'une organisation, tels que les ressources humaines, les finances,
 * les ventes, le marketing, l'informatique, le réseau, la communication, et la production.
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 *
 *  */
public enum Departments {
    RH("Ressources Humaines"),
    FINANCES("Finances"),
    VENTES("Ventes"),
    MARKETING("Marketing"),
    INFO("Informatique"),
    RESEAU("Réseau"),
    COMMUNICATION("Communication"),
    PRODUCTION("Production");

    /**
     * Générateur de nombres aléatoires utilisé pour la création de départements aléatoires.
     */
    private static final Random RANDOM = new Random();

    /**
     * Le nom du département.
     */
    private final String department;

    /**
     * Crée une instance de l'énumération avec un nom de département associé.
     *
     * @param department Le nom du département.
     */
    Departments(String department) {
        this.department = department;
    }

    /**
     * Génère aléatoirement un département parmi les valeurs de l'énumération.
     *
     * @return Un département choisi aléatoirement.
     */
    public static Departments generateRandomDepartment() {
        return values()[RANDOM.nextInt(values().length)];
    }

    /**
     * Obtient le nom du département.
     *
     * @return Le nom du département.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Obtient le département à partir d'une chaîne de caractères.
     *
     * @param departmentString Le département.
     * @return La valeur du département.
     */
    public static Departments getDepartmentFromString(String departmentString) {
        for (Departments department : Departments.values())
            if (department.getDepartment().equals(departmentString)) return department;

        return generateRandomDepartment();
    }
}
