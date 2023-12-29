package com.jogo.jnewsrss.repositories;

import com.jogo.jnewsrss.entities.newsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface newsRepo extends JpaRepository<newsEntity, Long> {
    Page<newsEntity> findAllByOrderByPubDateDesc(Pageable pageable);
    newsEntity findById(int id);
    newsEntity findByOrigUrl(String origUrl);

    Page<newsEntity> findAll(Pageable pageable);
}

