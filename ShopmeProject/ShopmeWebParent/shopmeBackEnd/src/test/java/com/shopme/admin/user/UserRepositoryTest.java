package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreatUser() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User  userNamHM = new User("nam@codejava.net","nam2020","Nam","Ha Minh");
		userNamHM.addRole(roleAdmin);
		User saveUser = repo.save(userNamHM);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	@Test
	public void testCreatUserTwoRole() {
	
		User  UserRavi = new User("ravi@gmail.com","ravi2020","Ravi","Kumar");
	Role roleEditor = new Role(3);
	Role roleAssistant = new Role(5);
	UserRavi.addRole(roleEditor);
	UserRavi.addRole(roleAssistant);
	repo.save(UserRavi);
		assertThat(UserRavi.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void listAlluser() {
		Iterable<User> findAll = repo.findAll();
		findAll.forEach(user->System.out.println(user));
	}
 	
	@Test
	public void getUserById() {
		Optional<User> findById = repo.findById(1);
		System.out.println(findById.get());
	assertThat(findById).isNotNull();
		
	}
	
	@Test
	public void testUpdateUserDetail() {
		User user = repo.findById(1).get();
		user.setEnabled(true);
		user.setEmail("Namjavaprogrammer@gmail.com");
		
		repo.save(user);
	}
	@Test
	public void testUpdateUserRole() {
		User user = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		user.getRoles().remove(roleEditor);
		user.addRole(roleSalesperson);
		
		repo.save(user);
	}
	
	@Test
	public void deletUser() {
		Integer userId=2;
		repo.deleteById(userId);
		
	}
	
	@Test 
	public void  getUserByEmail() {
		String email = "ravi@gmail.com";
		User userByEmail = repo.getUserByEmail(email);
		assertThat(userByEmail).isNotNull();
	}
	@Test
	public void testcountUserById() {
		Integer id=5;
		Long countById = repo.countById(id);
		assertThat(countById).isEqualTo(1);
	}
	@Test
	public void disableUser() {
		Integer id =1;
		repo.updateUserEnabled(id, false);
	}
	
}