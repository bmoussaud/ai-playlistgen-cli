package org.moussaud.playlistgen.rss.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SpotifyService {

    private static final Logger logger = LoggerFactory.getLogger(SpotifyService.class);

    public String getSpotifyTrackId(String name, String artist) {
        logger.info("getTrackId  {}/{}", name, artist);
        return "BMSpotifyID:[" + name + " by " + artist + "]";
    }

    public String getSpotifyTrackId(Track track) {
        return getSpotifyTrackId(track.getName(), track.getArtist());
    }
}
