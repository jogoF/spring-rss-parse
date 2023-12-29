package com.jogo.jnewsrss.parser;

import com.jogo.jnewsrss.entities.newsEntity;
import com.jogo.jnewsrss.exceptions.ParsingErrorException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class riaParser {
    private final String url = "https://ria.ru/export/rss2/index.xml";

    public String getData() throws IOException {
        String doc = String.valueOf(Jsoup.connect(url).get());
        return doc;
    }

    public List<newsEntity> parseData(String xmlData) throws ParsingErrorException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Преобразование строки в InputStream
            InputSource inputSource = new InputSource(new java.io.StringReader(xmlData));
            Document w3cDocument = builder.parse(inputSource);
            Element root = w3cDocument.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("item");
            List<newsEntity> list = new ArrayList<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                NodeList titleList = element.getElementsByTagName("title");
                NodeList urlList = element.getElementsByTagName("link");
                NodeList imgList = element.getElementsByTagName("enclosure");

                if (titleList.getLength() > 0 && urlList.getLength() > 0 && imgList.getLength() > 0) {
                    String title = titleList.item(0).getTextContent();
                    String url = urlList.item(0).getTextContent();

                    Element imgElement = (Element) imgList.item(0);
                    String imgUrl = imgElement.getAttribute("url");

                    // Создаем новую сущность фото
                    // Создаем новость с прикрепленным фото
                    newsEntity news = newsEntity.builder()
                            .header(title)
                            .body(getBody(url))
                            .origUrl(url)
                            .imgs(imgUrl)
                            .build();

                    list.add(news);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ParsingErrorException("Error while parse news");
    }

    private String getBody(String url) throws IOException {
        org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
        String articleTextElements = doc.select(".article__text").text();
        return articleTextElements;
    }
}
