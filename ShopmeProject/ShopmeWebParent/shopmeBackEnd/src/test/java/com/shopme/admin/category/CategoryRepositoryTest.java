package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
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
		Category laptops = new Category("iPhone",parent);
	//	Category component = new Category("Smartphones",parent);

	    Category save = repo.save(laptops);
		assertThat(save.getId()).isGreaterThan(0);

	}
	@Test
	public void testGetCatefory() {
		  Category category = repo.findById(2).get();
	System.out.println(category.getName());

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

 @Test
 public void testRootCategory() {
	 List<Category> listRootCategories = repo.findRootCategories(Sort.by("name").ascending());
	 listRootCategories.forEach(cat->{
		 System.out.println(cat.getName());
	 });
	 assertThat(listRootCategories.size()).isGreaterThan(0);
 }
 @Test
 public void findByName() {
	 String name="Computers1";
	 Category findByName = repo.findByName(name);
	 assertThat(findByName).isNotNull();
	 assertThat(findByName.getName()).isEqualTo(name);

 }

 @Test
 public void findByAlias() {
	 String name="Electronics";
	 Category findByName = repo.findByAlias(name);
	 assertThat(findByName).isNotNull();
	 assertThat(findByName.getAlias()).isEqualTo(name);

 }
}
