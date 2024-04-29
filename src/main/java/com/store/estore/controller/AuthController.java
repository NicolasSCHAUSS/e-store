package com.store.estore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.estore.dto.JwtUserDto;
import com.store.estore.dto.LoginUserDto;
import com.store.estore.dto.RegisterUserDto;
import com.store.estore.model.Role;
import com.store.estore.model.User;
import com.store.estore.repository.RoleRepository;
import com.store.estore.repository.UserRepository;
import com.store.estore.security.JwtUtils;
import com.store.estore.security.UserDetailsImpl;




@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
  
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils JwtUtils;
  
  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody LoginUserDto loginUserDto ) throws Exception {
      
    Authentication auth = authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));
    
    SecurityContextHolder.getContext().setAuthentication(auth);
    String jwt = JwtUtils.generateJwtToken(auth);

    UserDetailsImpl userDetailsImpl = (UserDetailsImpl) auth.getPrincipal();
    Set<String> roles = userDetailsImpl.getAuthorities().stream()
      .map(item -> item.getAuthority())
      .collect(Collectors.toSet()); 
    
    return ResponseEntity.ok(JwtUserDto.builder()
      .token(jwt)
      .id(userDetailsImpl.getId())
      .email(userDetailsImpl.getUsername())
      .roles(roles)
      .build());
  }
  
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto registerUserDto) {
    if(userRepository.existsByEmail(registerUserDto.getEmail())) {
      return ResponseEntity.badRequest()
        .body("Error: Email déja utilisé !");
    }

    User user = User.builder()
      .firstname(registerUserDto.getFirstname())
      .lastname(registerUserDto.getLastname())
      .email(registerUserDto.getEmail())
      .password(encoder.encode(registerUserDto.getPassword()))
      .build();

    Set<String> strRoles = registerUserDto.getRoles();
    Set<Role> roles = new HashSet<>();
    
    if(strRoles == null) {
      Role userRole = roleRepository.findByName("ROLE_USER")
        .orElseThrow(() -> new RuntimeException("Error: Role 'user' non trouvé !"));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "ROLE_ADMIN":
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
              .orElseThrow(() -> new RuntimeException("Error: Role 'admin' non trouvé !"));
            roles.add(adminRole);
            break;
          case "ROLE_STAFF":
            Role staffRole = roleRepository.findByName("ROLE_STAFF")
              .orElseThrow(() -> new RuntimeException("Error: Role 'staff' non trouvé !"));
            roles.add(staffRole);
            break;
          default:
            Role userRole = roleRepository.findByName("ROLE_USER")
              .orElseThrow(() -> new RuntimeException("Error: Role is not found !"));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok("User inscrit avec succès !");
  }
  
}
