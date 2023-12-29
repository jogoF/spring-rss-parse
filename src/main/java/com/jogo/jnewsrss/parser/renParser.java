package com.jogo.jnewsrss.parser;

import com.jogo.jnewsrss.entities.newsEntity;
import com.jogo.jnewsrss.exceptions.ParsingErrorException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class renParser {
    private final String url = "https://ren.tv/api/0/block/latest-news?page=1";

    public String getData() throws IOException {
        return Jsoup.connect(url).ignoreContentType(true).execute().body();
    }

    public List<newsEntity> parseData(String jsonData) throws IOException, ParsingErrorException {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);

            JSONObject dataObject = (JSONObject) jsonObject.get("data");
            JSONArray itemsArray = (JSONArray) dataObject.get("items");
            List<newsEntity> list = new ArrayList<>();

            for (Object item : itemsArray) {
                if (item instanceof JSONObject) {
                    JSONObject itemObject = (JSONObject) item;
                    String url = "https://ren.tv"+itemObject.get("url");

                    // Создаем новость с прикрепленным фото
                    newsEntity news = newsEntity.builder()
                            .header(itemObject.get("title").toString())
                            .body(getBody(url))
                            .origUrl(url)
                            .imgs(getImage(url))
                            .build();

                    list.add(news);
                }
            }
            return list;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new ParsingErrorException("Error while parse news");
    }

    private String getBody(String url) throws IOException {
        org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
        String textElements = doc.select("p").text();
        String resultText = textElements.substring(0, textElements.indexOf("* Экстремистские и террористические"));
        return resultText;
    }

    private String getImage(String url) throws IOException {
        org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
        Element metaElement = doc.select("meta[property=twitter:image:src]").first();
        String imageUrl = metaElement.attr("content");
        return imageUrl;
    }
}
