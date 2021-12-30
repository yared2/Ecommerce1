package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class userController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String ListAll(Model model) {
		List<User> listAll = service.ListAll();
		model.addAttribute("listusers", listAll);
	
		return "users";
	}
	
	@GetMapping("/users/new")
		public String newUser(Model model) {
		List<Role> listRoles = service.listRoles();
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New  User");
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		System.out.println(user);
		service.save(user);
		redirectAttributes.addFlashAttribute("message", "The user has been added succesfully");
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Integer id,Model model,RedirectAttributes redirectAttributes) {
		try {
			User user =service.get(id);
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit User"+"(ID: " + user.getId()+")");
			List<Role> listRoles = service.listRoles();
			model.addAttribute("listRoles", listRoles);
		
			return "user_form";
		} catch (UserNotFoundException ex) {
			
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/users";
	}
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable("id")Integer id,RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "The user " + id +" has been deleted successfuly" );
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/users";
	}
	

}
