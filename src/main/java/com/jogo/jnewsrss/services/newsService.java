package com.jogo.jnewsrss.services;

import com.jogo.jnewsrss.advice.AppResp;
import com.jogo.jnewsrss.entities.newsEntity;
import com.jogo.jnewsrss.exceptions.NotFoundException;
import com.jogo.jnewsrss.repositories.newsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class newsService {
    private final newsRepo repo;

    public ResponseEntity getRss(int page) throws NotFoundException {
        if(page<=0){
            throw new NotFoundException("Page index must not be less than one");
        }else {
            PageRequest pageable = PageRequest.of(page-1, 10);
            return ResponseEntity.status(200).body(new AppResp(200, repo.findAllByOrderByPubDateDesc(pageable).toList()));
        }
    }

    public ResponseEntity getById(int id) throws NotFoundException {
        newsEntity news = repo.findById(id);
        if(news!=null){
            return ResponseEntity.status(200).body(new AppResp(200,news));
        }else {
            throw new NotFoundException("Sorry, news not found.");
        }
    }
}
