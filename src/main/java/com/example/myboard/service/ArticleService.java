package com.example.myboard.service;

import com.example.myboard.domain.Article;
import com.example.myboard.repository.ArticleRepository;
import com.example.myboard.repository.UserRepository;
import com.example.myboard.web.dto.ArticleCreateRequest;
import com.example.myboard.web.dto.ArticleUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

//    @Transactional
//    public void create(ArticleCreateRequest req, String email) {
//        articleRepository.save(
//                Article.builder()
//                        .title(req.getTitle())
//                        .content(req.getContent())
//                        .build()
//        );
//    }

    @Transactional
    public Long create(ArticleCreateRequest req, String email){
        var author = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        return articleRepository.save(
                Article.builder()
                        .title(req.getTitle())
                        .content(req.getContent())
                        .author(author)
                        .build()
        ).getId();
    }

    public Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found: " + id));
    }

//    @Transactional
//    public void update(Long id, ArticleUpdateRequest req) {
//        var article = findById(id);
//        article.update(req.getTitle(), req.getContent());
//    }

    @Transactional
    public void update(Long id, String email, ArticleUpdateRequest req){
        var article = articleRepository.findByIdAndAuthor_Email(id, email)
                .orElseThrow(() -> new AccessDeniedException("본인 글이 아닙니다."));

        article.update(req.getTitle(), req.getContent());
    }

//    @Transactional
//    public void delete(Long id) {
//        articleRepository.deleteById(id);
//    }

    @Transactional
    public void delete(Long id, String email){

        if (articleRepository.deleteByIdAndAuthor_Email(id, email) == 0)
            throw new AccessDeniedException("본인 글이 아닙니다.");
    }
}
