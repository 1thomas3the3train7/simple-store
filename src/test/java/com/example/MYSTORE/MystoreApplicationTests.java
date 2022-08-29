package com.example.MYSTORE;

import com.example.MYSTORE.PRODUCTS.Model.*;
import com.example.MYSTORE.PRODUCTS.Repository.CustomTeaRepository;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.*;
import com.example.MYSTORE.PRODUCTS.Service.FileService;
import com.example.MYSTORE.PRODUCTS.Service.ProductUtils;
import com.example.MYSTORE.SECURITY.JWT.JWTRefreshToken;
import com.example.MYSTORE.SECURITY.Model.Role;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Model.VerificationToken;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomJWTRTokenRepositoryImpl;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomUserRepositoryImpl;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomVTokenRepositoryImpl;
import com.example.MYSTORE.SECURITY.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
class MystoreApplicationTests {
	private final CustomTeaRepository customTeaRepository;
	private final CustomTeaImageRepositoryImpl customTeaImageRepository;
	private final CustomCategoryRepositoryImpl customCategoryRepository;
	private final CustomReviewRepositoryImpl customReviewRepository;
	private final CustomUserRepositoryImpl customUserRepository;
	private final CustomSlaiderRepositoryImpl customSlaiderRepository;
	private final CustomTeaListRepositoryImpl customTeaListRepository;
	private final CustomVTokenRepositoryImpl customVTokenRepository;
	private final CustomJWTRTokenRepositoryImpl customJWTRTokenRepository;
	private final UserService userService;
	private final ProductUtils productUtils;
	@Autowired
	MystoreApplicationTests(CustomTeaRepository customTeaRepository,CustomTeaImageRepositoryImpl customTeaImageRepository,
				   CustomCategoryRepositoryImpl customCategoryRepository,CustomReviewRepositoryImpl customReviewRepository,
				   CustomUserRepositoryImpl customUserRepository,CustomSlaiderRepositoryImpl customSlaiderRepository,
				   CustomTeaListRepositoryImpl customTeaListRepository,CustomJWTRTokenRepositoryImpl customJWTRTokenRepository,
							CustomVTokenRepositoryImpl customVTokenRepository,ProductUtils productUtils,UserService userService){
		this.customTeaRepository = customTeaRepository;
		this.customTeaListRepository = customTeaListRepository;
		this.customSlaiderRepository = customSlaiderRepository;
		this.customReviewRepository = customReviewRepository;
		this.customCategoryRepository = customCategoryRepository;
		this.customTeaImageRepository = customTeaImageRepository;
		this.customVTokenRepository = customVTokenRepository;
		this.productUtils = productUtils;
		this.customJWTRTokenRepository = customJWTRTokenRepository;
		this.customUserRepository = customUserRepository;
		this.userService = userService;
	}

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
		customTeaRepository.updateTeaAndUser(tea,user);//user have id == null because user alreaady saved in test3
		User user1 = customUserRepository.getUserByTeaIdAndEmail(tea.getId(),"email");
		System.out.println(user1.getPassword());
	}
	@Test
	void test5(){
		SlaiderImages slaiderImages = new SlaiderImages();
		slaiderImages.setName("name");
		customSlaiderRepository.saveNewSlaider(slaiderImages);
		TeaImage teaImage = new TeaImage("linkimage");
		TeaImage teaImage1 = new TeaImage("linkimaage1");
		customTeaImageRepository.saveNewTeaImage(teaImage);
		customTeaImageRepository.saveNewTeaImage(teaImage1);
		customSlaiderRepository.updateSlaiderAndTeaImage(slaiderImages,teaImage);
		customSlaiderRepository.updateSlaiderAndTeaImage(slaiderImages,teaImage1);
		customTeaImageRepository.deleteTeaImageBySlaider(slaiderImages);
	}
	@Test
	void test6(){
		Tea tea =  new Tea();
		tea.setName("name");
		Reviews reviews = new Reviews();
		reviews.setUsername("username");
		customReviewRepository.saveNewReview(reviews);
		customTeaRepository.saveNewTea(tea);
		customReviewRepository.updateReviewAndTea(reviews,tea);
		System.out.println(customReviewRepository.countReviewsByTea(tea));
	}
	@Test
	void test7(){
		for(int i = 0;i <= 10;i++){
			Tea tea = new Tea();
			tea.setName("name");
			tea.setPrice(1000 + i);
			Category category = new Category("category");
			customTeaRepository.saveNewTea(tea);
			customCategoryRepository.saveNewCategory(category);
			customCategoryRepository.updateCategoryAndTea(category,tea);
			if(i % 2 == 0){
				Category category1 = new Category("categoryif");
				customCategoryRepository.saveNewCategory(category1);
				customCategoryRepository.updateCategoryAndTea(category1,tea);
			}
		}
		Set<String> cat = new HashSet<>();
		System.out.println(cat);
	}
	@Test
	void test8(){
		SlaiderImages slaiderImages = new SlaiderImages();
		slaiderImages.setName("name1");
		customSlaiderRepository.saveNewSlaider(slaiderImages);
		TeaImage teaImage = new TeaImage("link1");
		TeaImage teaImage1 = new TeaImage("link2");
		customTeaImageRepository.saveNewTeaImage(teaImage);
		customTeaImageRepository.saveNewTeaImage(teaImage1);
		customSlaiderRepository.updateSlaiderAndTeaImage(slaiderImages,teaImage);
		customSlaiderRepository.updateSlaiderAndTeaImage(slaiderImages,teaImage1);
		customSlaiderRepository.updateSlaiderAndTeaImage(slaiderImages,teaImage1);
		Tea tea = new Tea();
		tea.setName("name1");
		Tea tea1 = new Tea();
		tea1.setName("name2");
		customTeaRepository.saveNewTea(tea1);
		customTeaRepository.saveNewTea(tea);
		TeaLists teaLists = new TeaLists();
		teaLists.setName("name1");
		customTeaListRepository.saveNewTeaList(teaLists);
		customTeaListRepository.uploadTeaListAndTeaById(tea1.getId(), teaLists.getId());
		customTeaListRepository.uploadTeaListAndTeaById(tea.getId(), teaLists.getId());
		customTeaListRepository.deleteRelationTeaListAndTeaByName(teaLists.getId(), tea.getId());
		customTeaListRepository.deleteRelationTeaListAndTeaByName(teaLists.getId(), tea1.getId());
	}
	@Test
	void test9(){
		Tea tea = new Tea();tea.setName("NAMET1");
		Tea tea1 = new Tea();tea1.setName("NAMET2");
		Tea tea2 = new Tea();tea2.setName("NAMET3");
		tea.setPrice(1000);tea1.setPrice(2000);tea2.setPrice(3000);
		Category category = new Category("categoryt1");
		Category category1 = new Category("categoryt2");
		customCategoryRepository.saveNewCategory(category);
		customCategoryRepository.saveNewCategory(category1);
		customTeaRepository.saveNewTea(tea2);
		customTeaRepository.saveNewTea(tea1);
		customTeaRepository.saveNewTea(tea);
		customCategoryRepository.updateCategoryAndTea(category,tea);
		customCategoryRepository.updateCategoryAndTea(category1,tea1);
		customCategoryRepository.updateCategoryAndTea(category1,tea);
		Set<String> c = new HashSet<>();
		c.add("categoryt2");
		List<Tea> teas = customTeaRepository.findTeaByNameAndPriceAndCategoryName(
				"nam",c,1,9999,1
		);
		List<Tea> teaList = customTeaRepository.findTeaByNameAndPrice("nam",1,9999,1);
 		teas.forEach(System.out::println);
		teaList.forEach(System.out::println);
	}
	@Transactional(rollbackFor = Exception.class)
	public void ae(){
		Tea tea = new Tea();
		tea.setName("name11");
		customTeaRepository.saveNewTea(tea);
		System.out.println(1/0);

	}
	@Test
	void test10(){
		ae();
	}
	@Test
	void test11(){
	 	List<Tea> teas = em.createQuery("select t from Tea t",Tea.class).getResultList();
		 teas.forEach(System.out::println);
	}
}
