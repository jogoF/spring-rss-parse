package com.jogo.jnewsrss.entities;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "news")
public class newsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String header;
    @Column(columnDefinition = "TEXT")
    private String body;
    private String imgs;
    private String origUrl;
    @Temporal(TemporalType.TIMESTAMP)
    private Date pubDate;

    @PrePersist
    protected void onCreate() {
        pubDate = new Date();
    }
}
