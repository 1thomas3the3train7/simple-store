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
	private int count = 1;
	public void saveTea(String name,String subname,String about,Integer price,Integer oldPrice,String mainlink,String mainlink2,String mainlink3,String list){
		Tea tea = new Tea();
		tea.setName(name);tea.setSubname(subname);tea.setAbout(ab);tea.setPrice(price);tea.setOldPrice(oldPrice);tea.setMainLinkImage(mainlink);
		teaRepository.save(tea);
		TeaImage teaImage = new TeaImage(mainlink2);
		TeaImage teaImage1 = new TeaImage(mainlink2);
		teaImageRepository.save(teaImage);teaImageRepository.save(teaImage1);
		Tea tea1 = lazyTeaRepository.findByName(name);
		tea1.addTeaImage(teaImage);
		tea1.addTeaImage(teaImage1);
		TeaLists teaLists2 = teaListsRepository.findByName(list);
		teaLists2.addTea(tea1);
		teaRepository.saveAndFlush(tea1);
	}
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
		tea.setName("Ягодный чай");tea.setSubname("Красный чай");tea.setAbout(ab);tea.setPrice(1000);tea.setOldPrice(1500);tea.setMainLinkImage("tea1.webp");
		teaRepository.save(tea);
		TeaImage teaImage = new TeaImage("db7bcb6f8d2e1f5e056b8ed6848c6ee4.webp");
		TeaImage teaImage1 = new TeaImage("lmpzmez89be5afr2u1agmpcmnzcz5m6g.webp");
		teaImageRepository.save(teaImage);teaImageRepository.save(teaImage1);
		Tea tea1 = lazyTeaRepository.findById(Long.parseLong("1"));
		tea1.addTeaImage(teaImage);
		tea1.addTeaImage(teaImage1);
		TeaLists teaLists2 = teaListsRepository.findByName("list1");
		teaLists2.addTea(tea1);
		teaRepository.saveAndFlush(tea1);

		saveTea("Черный чай","Черный чай",ab,2000,3000,"stkm5pyy72h77xtxgnz8i0hkmfqyyzqj.webp",
				"y780akcg5719hrcq2k1v6tc9cx7ms9bu.webp","1b80702e3fe9b5637ae81f71693610c3.webp","list1");

		saveTea("Зеленый чай","Зеленый чай",ab,4000,5000,"ec9d32f8618994c0e32e1d5f28b813b9.webp",
				"27161556e8cd9baee7195a407716a245.webp","978634967cea4aeda4a992ac2671dbef.webp","list2");
		saveTea("Мятный чай","Зеленый чай",ab,6000,7000,"wi0cqvtf3z5101bshbptuw0ds78xy98q.webp",
				"t41dww17jgbtc1lqh9fj9mnsqr0wp324.webp","gnxd8qo4fik9iw61seiz5eor9fw8nfe9.webp","list2");
	}

}
