package com.store.estore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.store.estore.dto.ArticleDto;
import com.store.estore.mapper.ArticleMapper;
import com.store.estore.model.Article;
import com.store.estore.repository.ArticleRepository;

@Service
public class ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  private final static ArticleMapper ARTICLE_MAPPER = Mappers.getMapper(ArticleMapper.class);

  public List<ArticleDto> findAll() {
    return articleRepository.findAll().stream()
      .map(ARTICLE_MAPPER::articleToArticleDto)
      .collect(Collectors.toList());
  }

  public ArticleDto findById(@NonNull Long id) {
    return ARTICLE_MAPPER.articleToArticleDto(articleRepository.findById(id).orElse(Article.builder().build()));
  }

  public List<ArticleDto> findByName(String name) {
    return articleRepository.findByName(name).stream()
      .map(ARTICLE_MAPPER::articleToArticleDto)
      .collect(Collectors.toList());
  }

}
