package org.moussaud.playlistgen.rss.cli;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("playlist")
public class Playlist {

    @JsonProperty(required = true)
    @JsonPropertyDescription("title")
    String title;

    @JsonProperty(required = true)
    @JsonPropertyDescription("tracks")
    List<Track> tracks = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "title='" + title + '\'' +
                ", tracks=" + tracks +
                '}';
    }
}
