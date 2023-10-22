package personnes.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import data.api.request.GetCoordsFromAPI;

@JsonPropertyOrder({"latitude", "longitude"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coords {

    private int id;
    private Float latitude;
    private Float longitude;

    public Coords() {
        this.latitude = null;
        this.longitude = null;
    }

    public Coords(String city, String country) {
        GetCoordsFromAPI mapCoords = new GetCoordsFromAPI(city, country);
        try {
            this.latitude = mapCoords.getLatitude();
            this.longitude = mapCoords.getLongitude();
        } catch (Exception e){
            this.latitude = 48.7890f;
            this.longitude = 2.3634f;
            System.err.println("Adresse introuvable -> EFREI par défaut");
        }
    }

    @JsonIgnore
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("latitude")
    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "->latitude : " + this.latitude + ", longitude : " + this.longitude;
    }
}
