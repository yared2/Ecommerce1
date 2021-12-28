package com.shopme.admin.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
@Service
public class UserService {
	
	
	@Autowired
	private UserRepository UserRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	public List<User> ListAll(){
		
//		Iterable<User> findAll = repo.findAll();
//		List<User> users= new ArrayList<>();
//		findAll.forEach(user->users.add(user));
		
		return (List<User>) UserRepo.findAll();
		
	}
	
	public List<Role> listRoles(){
		return (List<Role>) roleRepo.findAll();
		
	}

	public void save(User user) {
		UserRepo.save(user);
		
	}

}
