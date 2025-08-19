package com.example.myboard.repository;

import com.example.myboard.domain.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @EntityGraph(attributePaths = "author")
    List<Article> findAll();
    
    @EntityGraph(attributePaths = "author")
    Optional<Article> findById(Long id);

    @EntityGraph(attributePaths = "author")
    Optional<Article> findByIdAndAuthor_Email(Long id, String email);

    long deleteByIdAndAuthor_Email(Long id, String email);
}

