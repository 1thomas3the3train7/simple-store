package com.example.MYSTORE;

import com.example.MYSTORE.PRODUCTS.Model.Category;
import com.example.MYSTORE.PRODUCTS.Model.Reviews;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomCategoryRepositoryImpl;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomReviewRepositoryImpl;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomTeaRepositoryImpl;
import com.example.MYSTORE.SECURITY.JWT.JWTRefreshToken;
import com.example.MYSTORE.SECURITY.Model.Role;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Model.VerificationToken;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomJWTRTokenRepositoryImpl;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomUserRepositoryImpl;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomVTokenRepositoryImpl;
import com.example.MYSTORE.SECURITY.Service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class MystoreApplicationTests {
	@Autowired
	private CustomTeaRepositoryImpl customTeaRepository;
	@Autowired
	private CustomCategoryRepositoryImpl customCategoryRepository;
	@Autowired
	private CustomReviewRepositoryImpl customReviewRepository;
	@Autowired
	private CustomVTokenRepositoryImpl customVTokenRepository;
	@Autowired
	private CustomUserRepositoryImpl customUserRepository;
	@Autowired
	private CustomJWTRTokenRepositoryImpl customJWTRTokenRepository;
	@Autowired
	private UserService userService;
	@PersistenceContext
	private EntityManager em;
	@Test
	void contextLoads() {
		User user = new User("email","password");
		user.setMyenabled(true);
		user.setRoles(List.of(new Role("ROLE_ADMIN")));
		userService.SaveUser(user);
		Reviews reviews = new Reviews();
		reviews.setUsername("username");
		customReviewRepository.saveNewReview(reviews);
		Category category = new Category("name1");
		Category category1 = new Category("name2");
		Category category2 = new Category("name3");
		customCategoryRepository.saveNewCategory(category);
		customCategoryRepository.saveNewCategory(category1);
		customCategoryRepository.saveNewCategory(category2);
		Tea tea = new Tea();
		tea.setName("name");
		tea.setAbout("about");
		customTeaRepository.saveNewTea(tea);
		customReviewRepository.updateReviewAndTea(reviews,tea);
		customCategoryRepository.updateCategoryAndTea(category,tea);
		customCategoryRepository.updateCategoryAndTea(category1,tea);
		Tea tea1 = customTeaRepository.getTeaAndCategoryByTeaId(Long.parseLong("1"));
		System.out.println(tea1.getCategories());
		Tea tea2 = new Tea();
		tea2.setId(Long.parseLong("1"));
		tea2.setName("nameedit");
		tea2.setAbout("abouted");
		tea2.setPrice(1000);
		/*tea2.setOldPrice(2000);*/
		tea2.setSubname("subnameedit");
		Tea tea3 = customTeaRepository.getEagerTeaCategoryReviewImage(Long.parseLong("1"));
		System.out.println(tea3);
		System.out.println(tea3.getCategories());

	}
	@Test
	void test2(){
		User user = new User("email","password");
		userService.SaveUser(user);
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(UUID.randomUUID().toString());
		customVTokenRepository.saveNewVToken(verificationToken);
		customVTokenRepository.updateVTokenAndUser(verificationToken,user);
		User user1 = customUserRepository.getUserAndVTokenByEmail("email");
		VerificationToken verificationToken1 = customVTokenRepository.getVTokenByUser(user);
		System.out.println(verificationToken1);
		customVTokenRepository.deleteVToken(verificationToken1);
	}
	@Test
	void test3(){
		User user = new User("email","password");
		user.setMyenabled(true);
		customUserRepository.saveNewUser(user);
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(UUID.randomUUID().toString());
		customVTokenRepository.saveNewVToken(verificationToken);
		customVTokenRepository.updateVTokenAndUser(verificationToken,user);
		JWTRefreshToken jwtRefreshToken = new JWTRefreshToken(UUID.randomUUID().toString());
		customJWTRTokenRepository.saveNewJWTRToken(jwtRefreshToken);
		customJWTRTokenRepository.updateJWTRTokenAndUser(jwtRefreshToken,user);
		User user1 = customUserRepository.getUserByJWTRefreshToken(jwtRefreshToken);
		System.out.println(user1.getEmail());
	}
	@Test
	void test4(){
		Tea tea = new Tea();
		tea.setName("NAME");
		customTeaRepository.saveNewTea(tea);
		User user = new User("email","password");
		userService.SaveUser(user);
		customTeaRepository.updateTeaAndUser(tea,user);
		User user1 = customUserRepository.getUserByTeaIdAndEmail(tea.getId(),"email");
		System.out.println(user1.getPassword());
	}

}
