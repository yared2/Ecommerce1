package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.shopme.common.entity.Category;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@MockBean
	private CategoryRepository repo;

	@InjectMocks
	private CategoryService service;

	@Test
	public void testCheckUniqInNewModeReturnDuplicateName() {
		Integer id=null;
		String name="Computer";
		String alias="abc";
		Category category = new Category(id,name,alias);

		Mockito.when(repo.findByName(name)).thenReturn(category);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkunique(id, name, alias);

	assertThat(result).isEqualTo("DuplicateName");


	}

	@Test
	public void testCheckUniqInNewModeReturnDuplicateAlias() {
		Integer id=null;
		String name="NameAbc";
		String alias="Computers";
		Category category = new Category(id,name,alias);

		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(category);

		String result = service.checkunique(id, name, alias);

	assertThat(result).isEqualTo("DuplicateAlias");


	}

	@Test
	public void testCheckUniqInNewModeReturnDuplicateOk() {
		Integer id=null;
		String name="NameAbc";
		String alias="Computers";
	//	Category category = new Category(id,name,alias);

		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkunique(id, name, alias);

	assertThat(result).isEqualTo("OK");


	}

	@Test
	public void testCheckUniqInNewEditModeReturnDuplicateOk() {
		Integer id=null;
		String name="NameAbc";
		String alias="Computers";
		Category category = new Category(2,name,alias);

		Mockito.when(repo.findByName(name)).thenReturn(category);
		Mockito.when(repo.findByAlias(alias)).thenReturn(null);

		String result = service.checkunique(id, name, alias);

	assertThat(result).isEqualTo("DuplicateName");


	}
	@Test
	public void testCheckUniqInEditModeReturnDuplicateAlias() {
		Integer id=1;
		String name="NameAbc";
		String alias="Computers";
		Category category = new Category(2,name,alias);

		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(category);

		String result = service.checkunique(id, name, alias);

	assertThat(result).isEqualTo("DuplicateAlias");


	}
	@Test
	public void testCheckUniqInEditModeReturnOk() {
		Integer id=1;
		String name="NameAbc";
		String alias="Computers";
		Category category = new Category(id,name,alias);

		Mockito.when(repo.findByName(name)).thenReturn(null);
		Mockito.when(repo.findByAlias(alias)).thenReturn(category);

		String result = service.checkunique(id, name, alias);

	assertThat(result).isEqualTo("OK");


	}

}
