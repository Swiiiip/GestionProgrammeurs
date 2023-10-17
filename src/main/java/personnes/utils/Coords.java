package personnes.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

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

    public Coords(Float latitude, Float longitude) {
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
