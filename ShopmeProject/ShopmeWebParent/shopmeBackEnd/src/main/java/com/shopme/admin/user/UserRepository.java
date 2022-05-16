package com.shopme.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.User;

public interface UserRepository  extends PagingAndSortingRepository<User,Integer>{
	@Query("SELECT u FROM User u where u.email =:email")
public User getUserByEmail(@Param("email") String email);

	public Long countById(Integer id);

	@Query("Select u from User u where concat(u.id,' ',u.email,' ',u.firstName,' ',u.lastName) LIKE %?1%")
	public  Page<User> findAll(String keyword,Pageable pageable);

	//updated
	@Query("update User u set  u.enabled=?2 Where u.id= ?1")
	@Modifying
	public void updateUserEnabled(Integer id , boolean enabled);


}
