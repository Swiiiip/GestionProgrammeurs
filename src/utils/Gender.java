package utils;

public enum Gender {

    MALE("male"),

    FEMALE("female"),

    DEFAULT("unknown");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public boolean isWoman() {
        return getGender().equalsIgnoreCase("female");
    }
}
