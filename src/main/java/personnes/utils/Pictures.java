package personnes.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Cette classe représente les URLs des images de profil en différentes tailles (large, medium et thumbnail) d'une personne.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
@JsonPropertyOrder({"large", "medium", "thumbnail"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pictures {

    /**
     * L'identifiant unique des images de profil.
     */
    private int id;

    /**
     * L'URL de l'image de profil en grande taille (large).
     */
    private String large;

    /**
     * L'URL de l'image de profil en taille moyenne (medium).
     */
    private String medium;

    /**
     * L'URL de l'image de profil en petite taille (thumbnail).
     */
    private String thumbnail;

    /**
     * Constructeur par défaut de la classe Pictures. Initialise les champs à des valeurs nulles.
     */
    public Pictures() {
        this.large = null;
        this.medium = null;
        this.thumbnail = null;
    }

    /**
     * Constructeur de la classe `Pictures` avec des paramètres pour initialiser les URL des images de profil en différentes tailles.
     *
     * @param large L'URL de l'image de profil en grande taille (large).
     * @param medium L'URL de l'image de profil en taille moyenne (medium).
     * @param thumbnail L'URL de l'image de profil en petite taille (thumbnail).
     */
    public Pictures(String large, String medium, String thumbnail) {
        this.large = large;
        this.medium = medium;
        this.thumbnail = thumbnail;
    }

    /**
     * Obtient l'identifiant unique des images de profil.
     *
     * @return L'identifiant unique des images de profil.
     */
    @JsonIgnore
    public int getId() {
        return this.id;
    }

    /**
     * Définit l'identifiant unique des images de profil.
     *
     * @param generatedId L'identifiant unique des images de profil.
     */
    public void setId(int generatedId) {
        this.id = generatedId;
    }

    /**
     * Obtient l'URL de l'image de profil en grande taille (large).
     *
     * @return L'URL de l'image de profil en grande taille (large).
     */
    @JsonProperty("large")
    public String getLarge() {
        return large;
    }

    /**
     * Définit l'URL de l'image de profil en grande taille (large).
     *
     * @param large L'URL de l'image de profil en grande taille (large).
     */
    public void setLarge(String large) {
        this.large = large;
    }

    /**
     * Obtient l'URL de l'image de profil en taille moyenne (medium).
     *
     * @return L'URL de l'image de profil en taille moyenne (medium).
     */
    @JsonProperty("medium")
    public String getMedium() {
        return medium;
    }

    /**
     * Définit l'URL de l'image de profil en taille moyenne (medium).
     *
     * @param medium L'URL de l'image de profil en taille moyenne (medium).
     */
    public void setMedium(String medium) {
        this.medium = medium;
    }

    /**
     * Obtient l'URL de l'image de profil en petite taille (thumbnail).
     *
     * @return L'URL de l'image de profil en petite taille (thumbnail).
     */
    @JsonProperty("thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Définit l'URL de l'image de profil en petite taille (thumbnail).
     *
     * @param thumbnail L'URL de l'image de profil en petite taille (thumbnail).
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Fournit une représentation textuelle des URLs des images de profil, incluant les URL en grande taille (large), taille moyenne (medium) et petite taille (thumbnail).
     *
     * @return Une chaîne de caractères représentant les URLs des images de profil.
     */
    @Override
    public String toString() {
        return "->large : " + this.large + ", medium : " + this.medium + ", thumbnail : " + this.thumbnail;
    }
}
