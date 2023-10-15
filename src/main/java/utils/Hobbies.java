package utils;

import java.util.Random;

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

    private static final Random RANDOM = new Random();
    private final String hobby;

    Hobbies(String hobby) {
        this.hobby = hobby;
    }

    public static Hobbies generateRandomHobby() {
        return values()[RANDOM.nextInt(values().length)];
    }

    public String getHobby() {
        return this.hobby;
    }
}

