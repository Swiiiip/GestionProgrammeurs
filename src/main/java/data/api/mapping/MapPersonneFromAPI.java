package data.api.mapping;

import data.api.request.GetUserFromAPI;
import personnes.Personne;

import java.util.ArrayList;
import java.util.List;

public abstract class MapPersonneFromAPI<T extends Personne> {

    protected final GetUserFromAPI api;

    public MapPersonneFromAPI() {
        api = new GetUserFromAPI();
    }

    public T map() throws Exception {
        String lastName;
        String firstName;
        do {
            api.generateData();
            lastName = api.getLastName();
            firstName = api.getFirstName();
        } while (isNotEuropean(lastName) && isNotEuropean(firstName));

        return null;
    }

    private List<Character> genererCaracteresEuropeens() {
        List<Character> caracteresEuropeens = new ArrayList<>();

        for (int codePoint = 0x20; codePoint <= 0x7E; codePoint++) {
            caracteresEuropeens.add((char) codePoint);
        }
        for (int codePoint = 0xC0; codePoint <= 0xFF; codePoint++) {
            caracteresEuropeens.add((char) codePoint);
        }
        for (int codePoint = 0x100; codePoint <= 0x17F; codePoint++) {
            caracteresEuropeens.add((char) codePoint);
        }
        for (int codePoint = 0x180; codePoint <= 0x24F; codePoint++) {
            caracteresEuropeens.add((char) codePoint);
        }

        return caracteresEuropeens;
    }

    public boolean isNotEuropean(String texte) {
        List<Character> caracteresEuropeens = genererCaracteresEuropeens();
        for (char c : texte.toCharArray())
            if (!caracteresEuropeens.contains(c))
                return true;

        return false;
    }
}
