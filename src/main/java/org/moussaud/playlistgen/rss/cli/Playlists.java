package org.moussaud.playlistgen.rss.cli;

import java.util.ArrayList;
import java.util.List;

public class Playlists {

    List<Playlist> playlists = new ArrayList<>();

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

}
