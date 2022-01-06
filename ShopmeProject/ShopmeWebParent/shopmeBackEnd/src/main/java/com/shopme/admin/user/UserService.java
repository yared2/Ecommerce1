package com.shopme.admin.user;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
@Transactional
public class UserService {

	public static final int USERS_PER_PAGE = 4;

	@Autowired
	private UserRepository UserRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> ListAll() {
		return (List<User>) UserRepo.findAll();

	}

	public Page<User> listByPage(Integer pageNum, String sortField, String sortDir, String keyword) {

	
		Sort sort = Sort.by(sortField);
		sort =sortDir.equals("asc") ?sort.ascending() :sort.descending();
		
		
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);

		if (keyword != null) {
		
		return	UserRepo.findAll(keyword, pageable);
		
			
		}
		return UserRepo.findAll(pageable);
	}

	public List<Role> listRoles() {
		return (List<Role>) roleRepo.findAll();

	}

	public User save(User user) {

		boolean isUpdating = (user.getId() != null);
		if (isUpdating) {
			User user2 = UserRepo.findById(user.getId()).get();
			if (user.getPassword().isEmpty()) {
				user.setPassword(user2.getPassword());

			} else {
				encodePassword(user);
			}
		} else {
			encodePassword(user);

		}
		return UserRepo.save(user);
	}

	private void encodePassword(User user) {
		String encode = passwordEncoder.encode(user.getPassword());
		user.setPassword(encode);

	}

	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = UserRepo.getUserByEmail(email);
		if (userByEmail == null)
			return true; // no matching email ok
		boolean isCreatingNew = (id == null);

		if (isCreatingNew) {
			if (userByEmail != null)
				return false; // id is null ,but there is email avilable
		} else {
			if (userByEmail.getId() != id) // id we have d/f from id coming meaning not the same person
				return false;
		}

		return true;
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return UserRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("could not find any user with ID " + id);
		}
	}

	public void delete(Integer id) throws UserNotFoundException {
		Long countById = UserRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		UserRepo.deleteById(id);
	}

	// update
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		UserRepo.updateUserEnabled(id, enabled);
	}

}
