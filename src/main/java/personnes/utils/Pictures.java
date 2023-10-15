package personnes.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"large", "medium", "thumbnail"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pictures {

    private int id;
    private String large;
    private String medium;
    private String thumbnail;

    public Pictures() {
        this.large = null;
        this.medium = null;
        this.thumbnail = null;
    }

    public Pictures(String large, String medium, String thumbnail) {
        this.large = large;
        this.medium = medium;
        this.thumbnail = thumbnail;
    }

    @JsonIgnore
    public int getId() {
        return this.id;
    }

    public void setId(int generatedId) {
        this.id = generatedId;
    }

    @JsonProperty("large")
    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    @JsonProperty("medium")
    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @JsonProperty("thumbnail")
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "->large : " + this.large + ", medium : " + this.medium + ", thumbnail : " + this.thumbnail;
    }
}
