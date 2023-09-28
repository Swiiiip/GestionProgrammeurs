package utils;

import java.util.Random;

public enum Departments {
    RH("Ressources Humaines"),
    FINANCES("Finances"),
    VENTES("Ventes"),
    MARKETING("Marketing"),
    INFO("Informatique"),
    PRODUCTION("Production");

    private static final Random RANDOM = new Random();
    private final String department;

    Departments(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public static String generateRandomDepartment() {
        return values()[RANDOM.nextInt(values().length)].getDepartment();
    }
}
