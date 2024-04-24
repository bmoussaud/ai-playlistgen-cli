package org.moussaud.playlistgen.rss.cli;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    String title;
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
