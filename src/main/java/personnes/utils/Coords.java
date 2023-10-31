package personnes.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import data.api.request.GetCoordsFromAPI;

import java.util.Objects;

/**
 * Cette classe représente les coordonnées géographiques (latitude et longitude) d'un emplacement. Elle peut être utilisée pour stocker des informations sur la position géographique d'une personne.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
@JsonPropertyOrder({"latitude", "longitude"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coords {

    /**
     * L'identifiant unique des coordonnées.
     */
    private int id;

    /**
     * La latitude de l'emplacement.
     */
    private Float latitude;

    /**
     * La longitude de l'emplacement.
     */
    private Float longitude;

    /**
     * Constructeur par défaut de la classe Coords. Initialise les champs à des valeurs nulles.
     */
    public Coords() {
        this.latitude = null;
        this.longitude = null;
    }

    /**
     * Constructeur de la classe `Coords` avec des paramètres pour initialiser les coordonnées à partir d'une ville et d'un pays.
     * Il utilise la classe `GetCoordsFromAPI` pour récupérer les coordonnées géographiques de l'emplacement en fonction de la ville et du pays spécifiés.
     * Si la récupération échoue, des coordonnées par défaut (Paris, France) sont utilisées.
     *
     * @param city La ville de l'emplacement.
     * @param country Le pays de l'emplacement.
     */
    public Coords(String city, String country) {
        GetCoordsFromAPI mapCoords = new GetCoordsFromAPI(city, country);
        try {
            this.latitude = mapCoords.getLatitude();
            this.longitude = mapCoords.getLongitude();
        } catch (Exception e) {
            // En cas d'erreur, des coordonnées par défaut (l'EFREI, Villejuif) sont utilisées.
            this.latitude = 48.7890f;
            this.longitude = 2.3634f;
        }
    }

    /**
     * Obtient l'identifiant unique des coordonnées.
     *
     * @return L'identifiant unique des coordonnées.
     */
    @JsonIgnore
    public int getId() {
        return this.id;
    }

    /**
     * Définit l'identifiant unique des coordonnées.
     *
     * @param id L'identifiant unique des coordonnées.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtient la latitude de l'emplacement.
     *
     * @return La latitude de l'emplacement.
     */
    @JsonProperty("latitude")
    public Float getLatitude() {
        return latitude;
    }

    /**
     * Définit la latitude de l'emplacement.
     *
     * @param latitude La latitude de l'emplacement.
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    /**
     * Obtient la longitude de l'emplacement.
     *
     * @return La longitude de l'emplacement.
     */
    @JsonProperty("longitude")
    public Float getLongitude() {
        return longitude;
    }

    /**
     * Définit la longitude de l'emplacement.
     *
     * @param longitude La longitude de l'emplacement.
     */
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    /**
     * Compare cet objet Coords avec un autre objet pour déterminer s'ils sont égaux.
     *
     * @param o L'objet à comparer avec cet objet Coords.
     * @return true si les objets sont égaux en termes de latitude et de longitude, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass())
            return false;

        return this.latitude.equals(((Coords) o).getLatitude())
                && this.longitude.equals(((Coords) o).getLongitude());
    }


    /**
     * Fournit une représentation textuelle des coordonnées, incluant la latitude et la longitude.
     *
     * @return Une chaîne de caractères représentant les coordonnées géographiques de l'emplacement.
     */
    @Override
    public String toString() {
        return "->latitude : " + this.latitude + ", longitude : " + this.longitude;
    }
}
