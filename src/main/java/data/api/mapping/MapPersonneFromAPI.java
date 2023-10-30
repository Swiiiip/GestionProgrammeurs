package data.api.mapping;

import data.api.request.GetUserFromAPI;
import personnes.Personne;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe abstraite est utilisée pour mapper des données de l'API vers des objets de type {@link Personne}.
 *
 * @param <T> Le type concret d'objet {@link Personne} à mapper.
 */
public abstract class MapPersonneFromAPI<T extends Personne> {

    protected final GetUserFromAPI api;

    /**
     * Constructeur de la classe.
     * Il initialise l'objet {@link GetUserFromAPI} utilisé pour obtenir des données de l'API.
     */
    public MapPersonneFromAPI() {
        api = new GetUserFromAPI();
    }

    /**
     * Cette méthode est utilisée pour mapper des données de l'API vers un objet de type {@link Personne}.
     *
     * @return L'objet de type {@link Personne} mappé depuis les données de l'API.
     * @throws Exception En cas d'erreur lors de la récupération ou du mapping des données.
     */
    public T map() throws Exception {
        String lastName;
        String firstName;
        do {
            api.generateData(api.getUserapi());
            lastName = api.getLastName();
            firstName = api.getFirstName();
        } while (isNotEuropean(lastName) && isNotEuropean(firstName));

        return null;
    }

    /**
     * Génère une liste de caractères européens couramment utilisés.
     *
     * @return Une liste de caractères européens couramment utilisés.
     */
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

    /**
     * Vérifie si une chaîne de caractères contient des caractères non européens.
     *
     * @param texte La chaîne de caractères à vérifier.
     * @return true si la chaîne contient des caractères non européens, false sinon.
     */
    public boolean isNotEuropean(String texte) {
        List<Character> caracteresEuropeens = genererCaracteresEuropeens();
        for (char c : texte.toCharArray())
            if (!caracteresEuropeens.contains(c))
                return true;

        return false;
    }
}
