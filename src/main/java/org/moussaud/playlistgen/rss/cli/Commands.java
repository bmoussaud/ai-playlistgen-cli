package org.moussaud.playlistgen.rss.cli;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Commands {

    @Autowired
    private ChatClient aiClient;

    @Value("classpath:/extract-playlist-prompt.st")
    private Resource promptRes;

    @Value("classpath:/sample-rss.rss")
    private Resource defaultRss;


    public String fetch(
            String rssUrl,
            boolean onebyone
    ) {
        try {

            BeanOutputParser<Playlist> parser = new BeanOutputParser<>(Playlist.class);
            String format = parser.getFormat();
            System.out.println(" x");
            PromptTemplate promptTemplate = new PromptTemplate(promptRes, Map.of("format", format));

            /*
            RssReader rssReader = new RssReader();
            List<Item> items = rssReader.read(rssUrl)
                    .toList();

             */

            List<Item> items = new RssReader().read(defaultRss.getInputStream()).toList();

            Playlists playlists = new Playlists();

            for (Item item : items) {
                System.out.println(item.getTitle());
                var prompt = promptTemplate.render(Map.of("description", item.getDescription()));
                var response = aiClient.call(new Prompt(prompt));
                AssistantMessage output = response.getResults().get(0).getOutput();
                String content = output.getContent();
                var playlist = parser.parse(content);
                System.out.println(playlist);
                playlists.addPlaylist(playlist);
            }
        
            return asJsonString(playlists);
        } catch (Exception e) {
            throw new RuntimeException("fetch error " + rssUrl, e);
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
