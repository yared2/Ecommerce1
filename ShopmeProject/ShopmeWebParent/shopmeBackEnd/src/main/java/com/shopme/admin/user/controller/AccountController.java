package com.shopme.admin.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.user.UserService;
import com.shopme.common.entity.User;

@Controller
public class AccountController {
	@Autowired
	private UserService userService;

	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggedUser, Model model) {
		String email = loggedUser.getUsername();
		User user = userService.getUserByEmail(email);
		model.addAttribute("user", user);

		return "users/account_form";
	}

	@PostMapping("/account/update")
	public String saveDetails(User user,@AuthenticationPrincipal ShopmeUserDetails loggedUser, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
	
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhoto(fileName);

			User saveUser = userService.updateAccount(user);
			String uploadDir = "user-photos/" + saveUser.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if (user.getPhoto().isEmpty())
				user.setPhoto(null);
			userService.updateAccount(user);
		}
		
		loggedUser.setFirstname(user.getFirstName());
		loggedUser.setLastname(user.getLastName());
	
		redirectAttributes.addFlashAttribute("message", "Your account details have been saved succesfully");
		return "redirect:/account";
	}

}
