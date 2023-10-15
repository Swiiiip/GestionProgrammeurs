package utils;

import java.util.Random;

public enum Departments {
    RH("Ressources Humaines"),
    FINANCES("Finances"),
    VENTES("Ventes"),
    MARKETING("Marketing"),
    INFO("Informatique"),
    RESEAU("Réseau"),
    COMMUNICATION("Communication"),
    PRODUCTION("Production");


    private static final Random RANDOM = new Random();
    private final String department;

    Departments(String department) {
        this.department = department;
    }

    public static Departments generateRandomDepartment() {
        return values()[RANDOM.nextInt(values().length)];
    }

    public String getDepartment() {
        return department;
    }
}
