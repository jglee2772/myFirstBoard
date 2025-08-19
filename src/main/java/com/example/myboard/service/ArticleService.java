package com.example.myboard.service;

import com.example.myboard.domain.Article;
import com.example.myboard.domain.User;
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

    @Transactional
    public void update(Long id, String email, ArticleUpdateRequest req){
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        
        var article = findById(id);
        
        // 관리자이거나 본인 글인 경우에만 수정 가능
        if (!user.isAdmin() && !article.getAuthor().getEmail().equals(email)) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        article.update(req.getTitle(), req.getContent());
    }

    @Transactional
    public void delete(Long id, String email){
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        
        var article = findById(id);
        
        // 관리자이거나 본인 글인 경우에만 삭제 가능
        if (!user.isAdmin() && !article.getAuthor().getEmail().equals(email)) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        
        articleRepository.deleteById(id);
    }
    
    // 관리자 권한 확인 메서드
    public boolean canManageArticle(String email, Long articleId) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        
        if (user.isAdmin()) {
            return true;
        }
        
        var article = findById(articleId);
        return article.getAuthor().getEmail().equals(email);
    }
}
