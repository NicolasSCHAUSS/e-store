package com.store.estore.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class JwtUserDto {

  private Long id;

  private String token;

  private String email;

  private Set<String> roles;

  @Builder.Default
  private String type = "Bearer";

}
