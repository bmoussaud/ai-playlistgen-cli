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
    @Description("Find tracks by episode. The result is a string containing the tracks you should parse.")
    Function<ByEpisodeRequest, String> tracksByEpisode(RssService svc) {
        return req -> svc.getTracks(req.episodeTitle());
    }

    @Bean
    @Description("Find tracks by episodse. The result is a list of string containing the tracks you should parse.")
    Function<ByEpisodesRequest, List<String>> tracksByEpisodes(RssService svc) {
        return req -> svc.getAllTracks(req.episodeTitles());
    }

    @JsonClassDescription("A request using an episode title")
    record ByEpisodeRequest(
            @JsonProperty(required = true) @JsonPropertyDescription("episode title") String episodeTitle) {
    }

    @JsonClassDescription("A request using a list of episode title")
    record ByEpisodesRequest(
            @JsonProperty(required = true) @JsonPropertyDescription("episode title") List<String> episodeTitles) {
    }

}
