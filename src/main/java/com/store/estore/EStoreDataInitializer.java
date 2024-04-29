package com.store.estore;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.store.estore.model.Article;
import com.store.estore.model.Privilege;
import com.store.estore.model.Role;
import com.store.estore.model.User;
import com.store.estore.repository.ArticleRepository;
import com.store.estore.repository.PrivilegeRepository;
import com.store.estore.repository.RoleRepository;
import com.store.estore.repository.UserRepository;

import io.jsonwebtoken.lang.Collections;

@Component
public class EStoreDataInitializer implements ApplicationListener<ContextRefreshedEvent>{

  @Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ResourceLoader resourceLoader;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @SuppressWarnings("null")
  public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
    Privilege privilegeDelete = Privilege.builder()
    .name("DELETE")
    .build();
    privilegeRepository.save(privilegeDelete);

    Privilege privilegeCreate =  Privilege.builder()
    .name("CREATE")
    .build();
    privilegeRepository.save(privilegeCreate);

    Privilege privilegeWrite =  Privilege.builder()
    .name("WRITE")
    .build();
    privilegeRepository.save(privilegeWrite);

    Privilege privilegeRead = Privilege.builder()
    .name("READ")
    .build();
    privilegeRepository.save(privilegeRead);
    
    Role roleAdmin = Role.builder()
    .name("ROLE_ADMIN")
    .privileges(Arrays.asList(privilegeDelete))
    .build();
    roleRepository.save(roleAdmin);

    Role roleStaff = Role.builder()
    .name("ROLE_STAFF")
    .privileges(Arrays.asList(privilegeWrite, privilegeCreate))
    .build();
    roleRepository.save(roleStaff);

    Role roleUser = Role.builder()
    .name("ROLE_USER")
    .privileges(Arrays.asList(privilegeRead))
    .build();
    roleRepository.save(roleUser);

    User user = User.builder()
    .firstname("admin")
    .lastname("ADMIN")
    .email("user@admin.com")
    .password(passwordEncoder.encode("ADMIN"))
    .roles(Set.of(roleUser, roleStaff, roleAdmin))
    .build();
    userRepository.save(user);
    
    Resource resource = resourceLoader.getResource("classpath:static/images/desktop.jpg");
    Article article;
    try {
      article = Article.builder()
        .name("Desktop")
        .description("Un ordinateur portable")
        .image(resource.getContentAsByteArray())
        .stock(10)
        .price(699.99F)
      .build();
    
      articleRepository.save(article);

      resource = resourceLoader.getResource("classpath:static/images/carte_graphique.jpg");			
      article = Article.builder()
        .name("Carte graphique")
        .description("Une carte graphique")
        .image(resource.getContentAsByteArray())
        .stock(3)
        .price(99.99F)
      .build();
      articleRepository.save(article);

      resource = resourceLoader.getResource("classpath:static/images/souris.jpg");			
      article = Article.builder()
        .name("Souris")
        .description("Une souris pour ordinateur")
        .image(resource.getContentAsByteArray())
        .stock(20)
        .price(9.99F)
      .build();
      articleRepository.save(article);

      resource = resourceLoader.getResource("classpath:static/images/clavier.jpg");			
      article = Article.builder()
        .name("Clavier")
        .description("Un clavier pour ordinateur")
        .image(resource.getContentAsByteArray())
        .stock(30)
        .price(19.99F)
      .build();
      articleRepository.save(article);

      resource = resourceLoader.getResource("classpath:static/images/camera.jpg");			
      article = Article.builder()
        .name("Camera")
        .description("Une camera pour ordinateur")
        .image(resource.getContentAsByteArray())
        .stock(10)
        .price(29.99F)
      .build();
      articleRepository.save(article);
      
    } catch (IOException e) {
      e.printStackTrace();
    }
	}
}
