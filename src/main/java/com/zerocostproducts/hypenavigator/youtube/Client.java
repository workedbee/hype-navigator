package com.zerocostproducts.hypenavigator.youtube;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerocostproducts.hypenavigator.config.ApplicationConfig;
import com.zerocostproducts.hypenavigator.config.ConfigKey;

/**
 * Client to communicate with youtube api
 */
@Service
public class Client {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private final YouTube youtube;

    private final ApplicationConfig config;

    @Autowired
    public Client(ApplicationConfig config) {
        this.config = config;
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null)
                .setApplicationName(config.getProperty(ConfigKey.APPLICATION_NAME))
                .build();
    }

    public List<String> listMostRatedVideoIds(String countryCode, int count) {
        try {
            YouTube.Videos.List listVideosRequest = youtube.videos()
                    .list("snippet, contentDetails");
            listVideosRequest.setChart("mostPopular");
            listVideosRequest.setRegionCode(countryCode);
            listVideosRequest.setKey(config.getProperty(ConfigKey.YOUTUBE_API_KEY));
            listVideosRequest.setMaxResults((long) count);
            VideoListResponse searchResponse = listVideosRequest.execute();

            return searchResponse.getItems()
                    .stream()
                    .map(Video::getId)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new ClientException("Get list of most rated video fails", ex);
        }
    }

    public List<Caption> listCaptions(String videoId) {
        try {
            YouTube.Captions.List captionRequest = youtube.captions()
                    .list("snippet", videoId)
                    .setKey(config.getProperty(ConfigKey.YOUTUBE_API_KEY));

            CaptionListResponse captionResponse = captionRequest.execute();
            return captionResponse.getItems();
        } catch (Exception ex) {
            throw new ClientException("Get list of captions fails", ex);
        }
    }

    public String loadCaption(String captionId) {
        try {
            YouTube.Captions.Download request = youtube.captions()
                    .download(captionId)
                    .setKey(config.getProperty(ConfigKey.YOUTUBE_API_KEY))
                    .setTfmt("srt");

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            request.executeMediaAndDownloadTo(output);
            return new String(output.toByteArray(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new ClientException("Load caption fails:", ex);
        }
    }
}
