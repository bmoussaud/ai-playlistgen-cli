package org.moussaud.playlistgen.rss.cli;

import java.util.Objects;

public class Track {

    String group;
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Track track = (Track) object;
        return Objects.equals(group, track.group) && Objects.equals(title, track.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, title);
    }

    @Override
    public String toString() {
        return "Track{" +
                "group='" + group + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
