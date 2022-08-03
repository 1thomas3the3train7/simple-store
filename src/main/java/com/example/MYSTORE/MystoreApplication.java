package com.example.MYSTORE;


import com.example.MYSTORE.PRODUCTS.Model.*;
import com.example.MYSTORE.PRODUCTS.Repository.*;
import com.example.MYSTORE.PRODUCTS.Service.FileService;
import com.example.MYSTORE.PRODUCTS.Service.ProductService;
import com.example.MYSTORE.SECURITY.Model.Role;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Repository.JWTRefreshTokenRepository;
import com.example.MYSTORE.SECURITY.Repository.UserRepository;
import com.example.MYSTORE.SECURITY.Service.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@SpringBootApplication
public class MystoreApplication implements CommandLineRunner {
	private String ab = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

	public static void main(String[] args) {
		SpringApplication.run(MystoreApplication.class, args);
	}

	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private TeaRepository teaRepository;
	@Autowired
	private JWTRefreshTokenRepository jwtRefreshTokenRepository;
	@Autowired
	private FileService fileService;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private TeaListsRepository teaListsRepository;
	@Autowired
	private ReviewsRepository reviewsRepository;
	@Autowired
	private TeaImageRepository teaImageRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomTeaRepository customTeaRepository;
	@Autowired
	private LazyTeaRepository lazyTeaRepository;
	@Autowired
	private SlaiderRepository slaiderRepository;
	@Override
	public void run(String... args) throws Exception {
		User user = new User("email","password");user.setUsername("1thomas3the3train7");
		user.setMyenabled(true);
		user.setRoles(List.of(new Role("ROLE_ADMIN")));
		userService.SaveUser(user);
		User user1 = new User("email1","password");
		user1.setMyenabled(true);
		user1.setUsername("figipain");
		userService.SaveUser(user1);
		TeaLists teaLists = new TeaLists("list1");
		TeaLists teaLists1 = new TeaLists("list2");
		Category category = new Category("category");
		Category category1 = new Category("category1");
		SlaiderImages slaiderImages = new SlaiderImages();
		slaiderImages.setName("leftslaider");
		slaiderRepository.save(slaiderImages);
		SlaiderImages slaiderImages1 = new SlaiderImages();
		slaiderImages1.setName("rightslaider");
		slaiderRepository.save(slaiderImages1);
		teaListsRepository.save(teaLists);
		teaListsRepository.save(teaLists1);
		categoryRepository.save(category);categoryRepository.save(category1);

		Tea tea = new Tea();
		tea.setName("Ягодный чай");tea.setSubname("Красный чай");tea.setAbout(ab);tea.setPrice(1000);tea.setOldPrice(1500);
		teaRepository.save(tea);tea.setMainLinkImage("tea1.webp");
		TeaImage teaImage = new TeaImage("db7bcb6f8d2e1f5e056b8ed6848c6ee4.webp");
		TeaImage teaImage1 = new TeaImage("lmpzmez89be5afr2u1agmpcmnzcz5m6g.webp");
		teaImageRepository.save(teaImage);teaImageRepository.save(teaImage1);
		Tea tea1 = lazyTeaRepository.findById(Long.parseLong("1"));
		tea1.addTeaImage(teaImage);
		tea1.addTeaImage(teaImage1);
		TeaLists teaLists2 = teaListsRepository.findByName("list1");
		teaLists2.addTea(tea1);
		teaRepository.saveAndFlush(tea1);
	}

}
