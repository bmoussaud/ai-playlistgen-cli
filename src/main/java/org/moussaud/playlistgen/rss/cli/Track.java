package org.moussaud.playlistgen.rss.cli;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("track")
public class Track {

    @JsonProperty(required = true)
    @JsonPropertyDescription("artist")
    String artist;

    @JsonProperty(required = true)
    @JsonPropertyDescription("name")
    String name;

    @JsonProperty(required = false)
    @JsonPropertyDescription("id")
    String id;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
