package com.example.MYSTORE;
import com.example.MYSTORE.PRODUCTS.Model.Reviews;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Model.TeaImage;
import com.example.MYSTORE.PRODUCTS.Repository.LazyTeaRepository;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomReviewRepositoryImpl;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomTeaImageRepositoryImpl;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomTeaRepositoryImpl;
import com.example.MYSTORE.SECURITY.JWT.JWTRefreshToken;
import com.example.MYSTORE.SECURITY.Model.Role;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Model.VerificationToken;
import com.example.MYSTORE.SECURITY.Repository.UserRepository;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomJWTRTokenRepositoryImpl;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomUserRepositoryImpl;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomVTokenRepositoryImpl;
import com.example.MYSTORE.SECURITY.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class MystoreApplication implements CommandLineRunner {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomTeaRepositoryImpl customTeaRepository;
	@Autowired
	private LazyTeaRepository lazyTeaRepository;
	@Autowired
	private CustomUserRepositoryImpl customUserRepository;
	@Autowired
	private CustomVTokenRepositoryImpl customVTokenRepository;
	@Autowired
	private CustomJWTRTokenRepositoryImpl customJWTRTokenRepository;
	@Autowired
	private CustomReviewRepositoryImpl customReviewRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomTeaImageRepositoryImpl customTeaImageRepository;
	public static void main(String[] args) {
		SpringApplication.run(MystoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User("email","password");
		Tea tea = new Tea();tea.setName("NAME");
		TeaImage teaImage = new TeaImage(UUID.randomUUID().toString());
		customTeaImageRepository.saveNewTeaImage(teaImage);
		System.out.println(teaImage.getId());
		customTeaImageRepository.updateTeaImageAndTea(teaImage,tea);
		customTeaRepository.saveNewTea(tea);
		customUserRepository.saveNewUser(user);
		customTeaRepository.updateTeaAndUser(tea,user);
		customTeaRepository.deleteRelationTeaAndUser(tea.getId(), user.getId());

	}
}
