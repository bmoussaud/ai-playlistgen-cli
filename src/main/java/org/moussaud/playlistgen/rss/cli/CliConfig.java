package org.moussaud.playlistgen.rss.cli;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;
import java.util.function.Function;

@Configuration(proxyBeanMethods = false)
public class CliConfig {

    @Bean
    @Description("get the associated tracks. The result is a string containing the tracks you should parse.")
    Function<ByEpisodeRequest, String> getTheAssociatedTracks(RssService svc) {
        return req -> svc.getTracks(req.episodeTitle());
    }

    @Bean
    @Description("get the tracks for a list of episodes. The result is a list of string containing the tracks you should parse.")
    Function<ByEpisodesRequest, List<String>> tracksByEpisodes(RssService svc) {
        return req -> svc.getAllTracks(req.episodeTitles());
    }

    @Bean
    @Description("get the spotify track id using the name and the artist.")
    Function<ByTrack, String> getSpotifyTrackIdByNameAndArtist(SpotifyService svc) {
        return req -> svc.getSpotifyTrackId(req.name(), req.artist());
    }

    @Bean
    @Description("get the spotify track id based on a track.")
    Function<Track, String> getSpotifyTrackIdByTrack(SpotifyService svc) {
        return req -> svc.getSpotifyTrackId(req);
    }

    @JsonClassDescription("A request using an episode title")
    record ByEpisodeRequest(
            @JsonProperty(required = true) @JsonPropertyDescription("episode title") String episodeTitle) {
    }

    @JsonClassDescription("A request using a list of episode title")
    record ByEpisodesRequest(
            @JsonProperty(required = true) @JsonPropertyDescription("episode title") List<String> episodeTitles) {
    }

    @JsonClassDescription("A request using a name and artist")
    record ByTrack(
            @JsonProperty(required = true) @JsonPropertyDescription("name") String name,
            @JsonProperty(required = true) @JsonPropertyDescription("artist") String artist) {
    }

}
