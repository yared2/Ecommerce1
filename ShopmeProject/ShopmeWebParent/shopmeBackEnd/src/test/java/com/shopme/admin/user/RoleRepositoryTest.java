package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository repo;

	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin","manage everything");
		Role saveRole = repo.save(roleAdmin);
		assertThat(saveRole.getId()).isGreaterThan(0);
	}
	@Test
	public void testCreateRestRole() {
		Role roleSelaseperson = new Role("SalesPerosn","manage product price,customer,shipping"
				+ ",order and sales report");

		Role roleEditor = new Role("Editor","manage categories, brands,articles and menu");
		Role roleShipper = new Role("Shipper","view products,view orders and update order status");
		Role roleAssistant = new Role("Assistant","manag questions and reviews");

repo.saveAll(List.of(roleSelaseperson, roleEditor, roleShipper, roleAssistant));

	}

}
