package com.store.estore.dto;

import java.util.Set;

import com.store.estore.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterUserDto extends LoginUserDto {

  private String firstname;

  private String lastname;

  private Set<String> roles;
}
