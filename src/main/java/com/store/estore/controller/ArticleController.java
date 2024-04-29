package com.store.estore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.estore.dto.ArticleDto;
import com.store.estore.service.ArticleService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/article")
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @GetMapping("/all")
  public List<ArticleDto> findAll() {
    return articleService.findAll();
  }
  
  @GetMapping("/{id}")
  public ArticleDto getMethodName(@NonNull @PathVariable Long id) {
      return articleService.findById(id);
  }

  @GetMapping("/search/{name}")
  public List<ArticleDto> getMethodName(@PathVariable String name) {
      return articleService.findByName(name);
  }
    
}
