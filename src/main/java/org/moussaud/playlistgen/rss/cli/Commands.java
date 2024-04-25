package org.moussaud.playlistgen.rss.cli;

import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class Commands {

    private static final Logger logger = LoggerFactory.getLogger(Commands.class);

    @Autowired
    private ChatClient aiClient;

    @Autowired
    private RssService rssService;

    @Value("classpath:/extract-playlist-prompt.st")
    private Resource promptRes;

    @Value("classpath:/extract-playlist-prompt-large.st")
    private Resource promptLarge;

    @Value("classpath:/extract-playlist-prompt-f.st")
    private Resource promptFunctions;

    @Value("classpath:/system-prompt.st")
    private Resource systemPromptRes;

    public String fetch(String rssUrl, boolean onebyone) {
        logger.info("fetch");
        try {

            BeanOutputParser<Playlist> parser = new BeanOutputParser<>(Playlist.class);
            String format = parser.getFormat();

            PromptTemplate promptTemplate = new PromptTemplate(promptRes, Map.of("format", format));

            Playlists playlists = new Playlists();
            for (Item item : rssService.getRssItem(rssUrl)) {
                var prompt = promptTemplate
                        .render(Map.of("description", item.getDescription(), "title", item.getTitle()));
                var response = aiClient.call(new Prompt(prompt));
                AssistantMessage output = response.getResults().get(0).getOutput();
                String content = output.getContent();
                var playlist = parser.parse(content);
                logger.info("playlist {}", playlist);
                playlists.addPlaylist(playlist);
            }

            return asJsonString(playlists);
        } catch (Exception e) {
            throw new RuntimeException("fetch error " + rssUrl, e);
        }
    }

    public String fetch2(String rssUrl) {
        logger.info("fetch 2");
        try {

            BeanOutputParser<Playlists> parser = new BeanOutputParser<>(Playlists.class);
            String format = parser.getFormat();
            PromptTemplate promptTemplate = new PromptTemplate(promptLarge, Map.of("format", format));

            StringBuffer data = new StringBuffer();
            for (Item item : rssService.getRssItem(rssUrl)) {
                data.append("Title:").append(item.getTitle().get()).append("\n");
                data.append("Description:").append(item.getDescription().get()).append("\n");
                data.append("\n");
            }

            logger.info("Data {}", data);

            var prompt = promptTemplate.render(Map.of("data", data));
            var response = aiClient.call(new Prompt(prompt));
            AssistantMessage output = response.getResults().get(0).getOutput();
            String content = output.getContent();
            var playlists = parser.parse(content);

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

    public Object fetchf(String rssUrl) {
        logger.info("fetch 2");
        try {

            BeanOutputParser<Playlists> parser = new BeanOutputParser<>(Playlists.class);
            String format = parser.getFormat();
            final var sysMsg = new SystemPromptTemplate(systemPromptRes).createMessage();
            final var askMsg = new PromptTemplate(promptFunctions).createMessage(Map.of("format", format));

            final var prompt = new Prompt(List.of(sysMsg, askMsg));
            logger.info("Sending prompt:\n{}", prompt.getContents());
            var response = aiClient.call(prompt);
            AssistantMessage output = response.getResults().get(0).getOutput();
            String content = output.getContent();
            var playlists = parser.parse(content);

            return asJsonString(playlists);
        } catch (Exception e) {
            throw new RuntimeException("fetch error " + rssUrl, e);
        }
    }

}
