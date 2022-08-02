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
		Tea tea = new Tea("c","about","asd",1000,"Asd");
		tea.setName("vlad");
		Tea tea1 = new Tea("wd","about1","asd",2000,"asd");
		tea1.setName("vitek");
		Tea tea2 = new Tea("wd","about2","asd",3000,"asd");
		tea2.setName("vani4");
		Tea tea3 = new Tea("wd","about2","asd",4000,"asd");
		tea3.setName("maksa");
		tea.setMainLinkImage("vladd.jpg");
		tea1.setMainLinkImage("vladd.jpg");
		tea2.setMainLinkImage("vladd.jpg");
		tea3.setMainLinkImage("vladd.jpg");
		teaRepository.save(tea);
		teaRepository.save(tea1);
		teaRepository.save(tea2);
		teaRepository.save(tea3);
		SlaiderImages slaiderImages = new SlaiderImages();
		slaiderImages.setName("leftslaider");
		slaiderRepository.save(slaiderImages);
		SlaiderImages slaiderImages1 = new SlaiderImages();
		slaiderImages1.setName("rightslaider");
		slaiderRepository.save(slaiderImages1);
		teaListsRepository.save(teaLists);
		teaListsRepository.save(teaLists1);
		categoryRepository.save(category);categoryRepository.save(category1);
		Tea tea4 = lazyTeaRepository.findById(Long.parseLong("2"));
		tea4.addCategory(category);tea4.addCategory(category1);
		teaRepository.save(tea4);
		Tea tea5 = lazyTeaRepository.findById(Long.parseLong("1"));
		Set<Reviews> reviews2 = tea5.getReviews();
		System.out.println(customTeaRepository.findMaxPrice());
	}

}
