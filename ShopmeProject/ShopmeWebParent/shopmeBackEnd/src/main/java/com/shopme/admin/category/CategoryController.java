package com.shopme.admin.category;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.query.Param;
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
import com.shopme.admin.user.UserService;
import com.shopme.common.entity.Category;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService service;

	@GetMapping("/categories")
	public String listFirstPage(@Param("sortDir") String sortDir ,Model model) {

	return listByPage(1,sortDir,null,model);
	}

	@GetMapping("/categories/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum") int PageNum,
			@Param("sortDir")	String sortDir ,
			@Param("keyword") String keyword,
			Model model) {
		
		if(sortDir == null || sortDir.isEmpty()) {
			sortDir="asc";
		}
CategoryPageInfo pageInfo = new CategoryPageInfo();
			List<Category> listAllCategory = service.listByPage(pageInfo,PageNum,sortDir,keyword);
			String reverseSortDir=sortDir.equals("asc")?"desc":"asc";
			
			
			long startCount =(PageNum-1)*CategoryService.ROOt_CATEGOIES_PER_PAGE+1;
		    long endCount = startCount+ CategoryService.ROOt_CATEGOIES_PER_PAGE-1;
		    if(endCount > pageInfo.getTotalElements()) {
			endCount=pageInfo.getTotalElements();
		   }
			
			model.addAttribute("totalPage", pageInfo.getTotalPages());
			model.addAttribute("totalItems", pageInfo.getTotalElements());
			model.addAttribute("currentPage", PageNum);
			model.addAttribute("pageNum", PageNum);
			model.addAttribute("sortField","name");
			model.addAttribute("sortDir",sortDir);
			model.addAttribute("keyword",keyword);
			
			model.addAttribute("startCount",startCount);
			model.addAttribute("endCount",endCount);
			
			model.addAttribute("listCategories", listAllCategory);
			model.addAttribute("reverseSortDir",reverseSortDir);
			return "categories/categories";
		
	}
	

	@GetMapping("/Categories/new")
	public String newCategory(Model model) {
		List<Category> listCategoriesUsedInform = service.listCategoriesUsedInform();
		Category cat = new Category();

		model.addAttribute("category", cat);

		model.addAttribute("listCategories", listCategoriesUsedInform);
		model.addAttribute("pageTitle", "Create New Category");
		return "categories/category_form";

	}
	@PostMapping("/Categories/save")
	public String saveCategory(Category category,@RequestParam("fileImage") MultipartFile
			multipartFile,RedirectAttributes ra) throws IOException {

		if(!multipartFile.isEmpty()) {
			String filename=StringUtils.cleanPath(multipartFile.getOriginalFilename());
			category.setImage(filename);

			Category savedCategory =service.save(category);
			String uploadDir = "../category-images/"+savedCategory.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, filename, multipartFile);


		}else {
			service.save(category);

		}


		ra.addFlashAttribute("message", "Category  has been saved  successfully");
		return "redirect:/categories";
	}


	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable(name="id") Integer id,Model model,
			RedirectAttributes ra) {
		try {
			Category category =service.get(id);
			List<Category>listCategories = service.listCategoriesUsedInform();

			model.addAttribute("category", category);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("pageTitle","Edit Category (ID:"+id +")");


			return "Categories/category_form";
		}catch (CategoryNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());

			return "redirect:/categories";

		}
	}
	@GetMapping("/categories/{id}/enabled/{status}")
	public String updateEnabledStatus(@PathVariable("id") Integer id,@PathVariable("status") boolean enabled,
			RedirectAttributes ra) {
		service.updateEnabledStatus(id, enabled);
		String status=enabled?"enabled":"disabled";
		String message="The category ID "+ id +"has been "+status;
		ra.addFlashAttribute("message", message);
		
		return "redirect:/categories";
		
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable(name = "id") Integer id,
			Model model, RedirectAttributes ra) {
		try {
			service.delete(id);
			String categoryDir ="../category-images/"+id;
			FileUploadUtil.removeDir(categoryDir);
			ra.addFlashAttribute("message", "The category Id "+id +"has been deleted successfully");
		}catch (CategoryNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/categories";
	}
		@GetMapping("/categories/export/csv")
	public void exportToCsv(HttpServletResponse res) throws IOException {
		List<Category> listCateogories=service.listCategoriesUsedInform();
		CategoryCsvExporter exporter= new CategoryCsvExporter();
		exporter.export(listCateogories, res);
	}
	
	}








































