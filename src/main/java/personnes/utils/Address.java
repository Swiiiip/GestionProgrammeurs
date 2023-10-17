package personnes.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"streetNumber", "streetName", "city", "country"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

    private int id;
    private int streetNumber;
    private String streetName;
    private String city;
    private String country;

    public Address(){
        this.streetNumber = 0;
        this.streetName = null;
        this.city = null;
        this.country = null;
    }

    public Address(int streetNumber, String streetName, String city, String country){
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.country = country;
    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("streetNumber")
    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    @JsonProperty("streetName")
    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString(){
        return this.streetNumber + " " + this.streetName + ", " + this.city + ", " + this.country;
    }
}
