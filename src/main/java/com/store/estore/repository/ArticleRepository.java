package com.store.estore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.estore.model.Article;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
  List<Article> findByName(String name);
  List<Article> findByPrice(Float price);
}
