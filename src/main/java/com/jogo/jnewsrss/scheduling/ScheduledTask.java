package com.jogo.jnewsrss.scheduling;

import com.jogo.jnewsrss.entities.newsEntity;
import com.jogo.jnewsrss.exceptions.ParsingErrorException;
import com.jogo.jnewsrss.parser.renParser;
import com.jogo.jnewsrss.parser.riaParser;
import com.jogo.jnewsrss.repositories.newsRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private final riaParser riaRss;
    private final renParser renRss;
    private final newsRepo repo;

    @Scheduled(fixedRate = 100000)
    @Async
    public void processRss() {
        try {
            List<newsEntity> allNewsFromRss = riaRss.parseData(riaRss.getData());
            allNewsFromRss.addAll(renRss.parseData(renRss.getData()));
            repo.saveAll(getAllNew(allNewsFromRss));

        } catch (ParsingErrorException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<newsEntity> getAllNew(List<newsEntity> rssNews){
        List<newsEntity> allNews = new ArrayList<>();

        for(newsEntity news : rssNews){
            if(repo.findByOrigUrl(news.getOrigUrl())!=null){
                continue;
            }else {
                allNews.add(news);
            }
        }
        return allNews;
    }

}
