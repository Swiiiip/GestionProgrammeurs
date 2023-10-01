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

    private final String hobby;
    private static final Random RANDOM = new Random();

    Hobbies(String hobby) {
        this.hobby = hobby;
    }

    public String getHobby(){
        return this.hobby;
    }
    public static String generateRandomHobby() {
        return values()[RANDOM.nextInt(values().length)].getHobby();
    }
}

