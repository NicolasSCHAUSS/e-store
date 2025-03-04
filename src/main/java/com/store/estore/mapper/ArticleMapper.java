package com.store.estore.mapper;
import org.mapstruct.Mapper;

import com.store.estore.dto.ArticleDto;
import com.store.estore.model.Article;

@Mapper()
public interface ArticleMapper {
  ArticleDto articleToArticleDto(Article article);
}
