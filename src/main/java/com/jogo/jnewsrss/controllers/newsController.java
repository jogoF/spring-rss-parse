package com.jogo.jnewsrss.controllers;

import com.jogo.jnewsrss.exceptions.NotFoundException;
import com.jogo.jnewsrss.repositories.newsRepo;
import com.jogo.jnewsrss.services.newsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class newsController {
    private final newsService service;

    @GetMapping("/{id}")
    public ResponseEntity createTodo(@PathVariable Integer id) throws NotFoundException {
        return service.getById(id);
    }

    @GetMapping("/rss")
    public ResponseEntity createTodo(@RequestParam(defaultValue = "1") int page) throws NotFoundException {
        return service.getRss(page);
    }
}
