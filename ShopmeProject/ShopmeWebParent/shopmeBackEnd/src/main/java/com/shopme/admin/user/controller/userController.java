package com.shopme.admin.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.admin.user.UserService;
import com.shopme.admin.user.export.UserCsvExporter;
import com.shopme.admin.user.export.UserExcelExporter;
import com.shopme.admin.user.export.UserPdfExporter;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class userController {

	@Autowired
	private UserService service;

	@GetMapping("/users")
	public String listFirstPage(Model model) {
		return listByPage(1,"firstName","asc",model,null);
	}
	@GetMapping("/users/page/{pageNum}")
	public String  listByPage(@PathVariable("pageNum") int pageNum,@Param("sortField") String sortField,
			@Param("sortDir") String  sortDir ,Model model,
			 @Param("keyword")String keyword) {

		
		Page<User> page = service.listByPage(pageNum,sortField,sortDir,keyword);

		List<User> listusers = page.getContent();

	    long startCount =(pageNum-1)*UserService.USERS_PER_PAGE+1;
	    long endCount = startCount+ UserService.USERS_PER_PAGE-1;
	    if(endCount > page.getTotalElements()) {
		endCount=page.getTotalElements();
	   }

	    String reverseSortDir = sortDir.equals("asc") ? "desc":"asc";
	    model.addAttribute("currentPage", pageNum);
	    model.addAttribute("totalPage", page.getTotalPages());
	    model.addAttribute("startCount", startCount);
	    model.addAttribute("endCount", endCount);
	    model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listusers", listusers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		return "users/users";
	}

	@GetMapping("/users/new")
		public String newUser(Model model) {
		List<Role> listRoles = service.listRoles();
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New  User");
		return "users/user_form";
	}

	@PostMapping("/users/save")
	public String saveUser(User user,@AuthenticationPrincipal ShopmeUserDetails loggedUser, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
	if(!multipartFile.isEmpty()) {
		String fileName = StringUtils.cleanPath( multipartFile.getOriginalFilename());
			user.setPhoto(fileName);


		User saveUser = service.save(user);
		String uploadDir="user-photos/"+saveUser.getId();
		FileUploadUtil.cleanDir(uploadDir);
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
	}else {
		if(user.getPhoto().isEmpty()) user.setPhoto(null);
		service.save(user);
	}

		redirectAttributes.addFlashAttribute("message", "The user has been added succesfully");

	    return getRedirectUrltoAffectedUser(user);
	}
	private String getRedirectUrltoAffectedUser(User user) {
		String 	firstPartofEmail = user.getEmail().split("@")[0];
		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword="+firstPartofEmail;
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Integer id,Model model,RedirectAttributes redirectAttributes) {
		try {
			User user =service.get(id);
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Edit User"+"(ID: " + user.getId()+")");
			List<Role> listRoles = service.listRoles();
			model.addAttribute("listRoles", listRoles);

			return "users/user_form";
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

	@GetMapping("/users/{id}/enabled/{status}")
	public  String updateUserEnableStatus(@PathVariable("id") Integer id , @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		service.updateUserEnabledStatus(id, enabled);
		String status= enabled? "enabled":"disabled";
		String message ="The user ID "+id +" has been "+status;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/users";
	}
@GetMapping("/users/export/csv")
public void ExportToCsv(HttpServletResponse response) throws IOException {
	List<User> listAll = service.ListAll();
	UserCsvExporter export = new UserCsvExporter();
	export.export(listAll, response);
}

@GetMapping("/users/export/excel")
public void ExportToExcel(HttpServletResponse response) throws IOException {
	List<User> listAll = service.ListAll();
	UserExcelExporter export = new UserExcelExporter();
	export.export(listAll, response);
}

@GetMapping("/users/export/pdf")
public void ExportToPdf(HttpServletResponse response) throws IOException {
	List<User> listAll = service.ListAll();
	UserPdfExporter export = new UserPdfExporter();
	export.export(listAll, response);
}


}
