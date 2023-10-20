package personnes.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"StreetNumber", "streetName", "city", "state", "country", "postcode"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

    private int id;
    private int streetNumber;
    private String streetName;
    private String city;
    private String state;
    private String country;
    private String postcode;

    public Address(){
        this.streetNumber = 0;
        this.streetName = null;
        this.city = null;
        this.state = null;
        this.country = null;
        this.postcode = null;
    }

    public Address(int streetNumber, String streetName, String city, String state, String country, String postcode){
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postcode = postcode;
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
        return this.streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    @JsonProperty("streetName")
    public String getStreetName() {
        return this.streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @JsonProperty("city")
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("state")
    public String getState(){
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("country")
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("postcode")
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString(){
        return this.streetNumber + " " + this.streetName + ", " + this.city +
                ", " + this.state + ", " + this.country + ", " + this.postcode;
    }
}
