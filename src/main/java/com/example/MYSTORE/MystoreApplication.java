package com.example.MYSTORE;

import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.*;

import com.example.MYSTORE.SECURITY.Model.Role;
import com.example.MYSTORE.SECURITY.Model.User;

import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomJWTRTokenRepositoryImpl;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomUserRepositoryImpl;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomVTokenRepositoryImpl;
import com.example.MYSTORE.SECURITY.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@SpringBootApplication
public class MystoreApplication implements CommandLineRunner {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomTeaRepositoryImpl customTeaRepository;
	@Autowired
	private CustomUserRepositoryImpl customUserRepository;
	@Autowired
	private CustomVTokenRepositoryImpl customVTokenRepository;
	@Autowired
	private CustomJWTRTokenRepositoryImpl customJWTRTokenRepository;
	@Autowired
	private CustomReviewRepositoryImpl customReviewRepository;
	@Autowired
	private CustomTeaImageRepositoryImpl customTeaImageRepository;
	@Autowired
	private CustomSlaiderRepositoryImpl customSlaiderRepository;
	@Autowired
	private CustomCategoryRepositoryImpl customCategoryRepository;
	@Autowired
	private CustomTeaListRepositoryImpl customTeaListRepository;
	public static void main(String[] args) {
		SpringApplication.run(MystoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User("email","password");
		user.setRoles(Set.of(new Role("ROLE_ADMIN")));
		userService.SaveUser(user);
	}
}
