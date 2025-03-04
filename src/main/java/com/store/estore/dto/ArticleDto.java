package com.store.estore.dto;

import lombok.Data;

@Data
public class ArticleDto {
  private Long id;
  private String name;
  private String description;
  private byte[] image;
  private Integer stock;
  private Float price;
}
