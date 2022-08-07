package com.example.MYSTORE;

import com.example.MYSTORE.PRODUCTS.Model.Category;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomCategoryRepositoryImpl;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomTeaRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
class MystoreApplicationTests {
	@Autowired
	private CustomTeaRepositoryImpl customTeaRepository;
	@Autowired
	private CustomCategoryRepositoryImpl customCategoryRepository;
	@PersistenceContext
	private EntityManager em;
	@Test
	void contextLoads() {
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
		customTeaRepository.uploadTea(tea2);
	}

}
