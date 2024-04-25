package org.moussaud.playlistgen.rss.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;

@Service
public class RssService {

    private static final Logger logger = LoggerFactory.getLogger(Commands.class);

    @Value("classpath:/sample-rss.rss")
    private Resource defaultRss;

    @Value("classpath:/rss.rss")
    private Resource largeRss;

    private List<Item> items;

    public List<Item> getRssItem(String rssUrl) throws IOException {

        if (rssUrl != null) {
            items = new RssReader().read(rssUrl).toList();
        } else {
            items = new RssReader().read(largeRss.getInputStream()).toList();
        }
        return items;
    }

    public String getTracks(String episodeTitle) {
        try {
            logger.info("** getTracks({})", episodeTitle);
            List<Item> found = new RssReader().read(largeRss.getInputStream())
                    .filter(i -> i.getTitle().equals(Optional.of(episodeTitle))).toList();
            logger.info("found size {}", found.size());
            return found.get(0).getDescription().get();
        } catch (Exception e) {
            logger.error("getTracks error on " + episodeTitle, e);
            throw new RuntimeException("getTrack error " + episodeTitle, e);
        }
    }

    public List<String> getAllTracks(List<String> episodeTitles) {
        logger.info("getAllTracks({})", episodeTitles);
        List<String> response = new ArrayList<>();
        for (String title : episodeTitles) {
            response.add(getTracks(title));
        }
        return response;
    }

}
