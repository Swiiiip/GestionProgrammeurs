package personnes.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"latitude", "longitude"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coords {

    private int id;
    private String latitude;
    private String longitude;

    public Coords() {
        this.latitude = null;
        this.longitude = null;
    }

    public Coords(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @JsonIgnore
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "->latitude : " + this.latitude + ", longitude : " + this.longitude;
    }
}
