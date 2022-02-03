package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


import com.shopme.common.entity.Category;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {
	

	@Autowired
	private CategoryRepository repo;
	
	@Test
	public void testCreateRootCategory() {
		Category category = new Category("Electronics");
		Category save = repo.save(category);
		assertThat(save.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(7);
		Category subcategory = new Category("iPhone",parent);
		
	     Category save = repo.save(subcategory);
	 	assertThat(save.getId()).isGreaterThan(0);
	
	}
	@Test
	public void testGetCatefory() {
		  Category category = repo.findById(2).get();
	System.out.println(category);
	
	for(Category cat : category.getChildren()) {
		System.out.println(cat.getName());
	}
	}
	@Test
	public void testPrintCategoryes() {
		Iterable<Category> categories = repo.findAll();
		
		for(Category category :categories) {
			if(category.getParent() == null) { 
				System.out.println(category.getName());
			
			Set<Category> children = category.getChildren();
			
			for(Category subcat :children) {
				System.out.println("--"+subcat.getName());
				printChildern(subcat,1);
			}
			}
		}
	}
 private void printChildern(Category parent ,int sublevel) {
	 int newSubLevel= sublevel + 1;
	Set<Category> children = parent.getChildren();
	
	 for(Category subcat :children) {
		 for(int i = 0;i < newSubLevel; i++) {  // we can just put syout(----) insted of forloop
			 System.out.print("--");
		}
			System.out.println(subcat.getName());
			 printChildern(subcat,newSubLevel);
		}
	 

 }
}
