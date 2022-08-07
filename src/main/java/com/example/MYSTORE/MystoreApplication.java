package com.example.MYSTORE;
import com.example.MYSTORE.SECURITY.Model.Role;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Repository.UserRepository;
import com.example.MYSTORE.SECURITY.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MystoreApplication implements CommandLineRunner {
	@Autowired
	private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(MystoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User("emai","password");
		user.setRoles(List.of(new Role("ROLE_ADMIN")));
		user.setMyenabled(true);
		userService.SaveUser(user);
	}
}
