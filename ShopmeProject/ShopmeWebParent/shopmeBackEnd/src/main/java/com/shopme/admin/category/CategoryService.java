package com.shopme.admin.category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopme.common.entity.Category;

@Service
@Transactional
public class CategoryService {
	
	public static final int ROOt_CATEGOIES_PER_PAGE=4;

	@Autowired
	private CategoryRepository repo;


	public List<Category> listByPage(CategoryPageInfo pageInfo, int pageNum, String sortDir,String keyword){
		Sort sort= Sort.by("name");
		 if (sortDir.equals("asc")) {
			sort=sort.ascending();
		}else if(sortDir.equals("desc")) {
			sort=sort.descending();
		}
Pageable pageable = PageRequest.of(pageNum-1, ROOt_CATEGOIES_PER_PAGE, sort);

Page<Category>pageCateogries = null;
if(keyword != null && !keyword.isEmpty()) {
	pageCateogries = repo.search(keyword, pageable);
}else {
	pageCateogries  = repo.findRootCategories(pageable);
}
		List<Category>rootCateogries = pageCateogries.getContent();
		pageInfo.setTotalElements(pageCateogries.getTotalElements());
		pageInfo.setTotalPages(pageCateogries.getTotalPages());
		if(keyword != null && !keyword.isEmpty()) {
			List<Category>searchResult = pageCateogries.getContent();
			for(Category category :searchResult) {
				category.setHasChildren(category.getChildren().size()>0);
			}
			return searchResult;
		}else {
			return listHierarchicalCategory(rootCateogries,sortDir);
			
		}
		
		
	}

private List<Category>  listHierarchicalCategory(List<Category> rootCateogries ,String sortDir){
	List<Category> hierarichalCategories=  new ArrayList<>();

	for(Category rootCateogry : rootCateogries) {
		hierarichalCategories.add(Category.copyFull(rootCateogry));

		Set<Category>children=sortSubCategories(rootCateogry.getChildren(),sortDir);

		for(Category subCateogry : children) {
			String name = "--"+subCateogry.getName();
			hierarichalCategories.add(Category.copyFull(subCateogry, name));
			listSubHierarchicalCateogies(hierarichalCategories,subCateogry,1 ,sortDir);
		}
	}

	return hierarichalCategories;

}

private void listSubHierarchicalCateogies(List<Category> hierarichalCategories,Category parent,int sublevel,String sortDir) {

	Set<Category>children=sortSubCategories(parent.getChildren(),sortDir);

	int newsubLevel= sublevel+1;
	for(Category subCategory : children ) {
		String name="";
		for(int i=0; i<newsubLevel;i++) {
			name+="--";

		}
		name+=subCategory.getName();
		hierarichalCategories.add(Category.copyFull(subCategory, name));
		listSubHierarchicalCateogies(hierarichalCategories, subCategory, newsubLevel,sortDir);

	}
}

	public List<Category> listCategoriesUsedInform(){
		List<Category>categoriesUsedInForm = new ArrayList<>();

		Iterable<Category> categoriesInDB = repo.findRootCategories(Sort.by("name").ascending());


		for(Category category :categoriesInDB) {
			if(category.getParent() == null) {
				categoriesUsedInForm.add(Category.copyIdandName(category));

			Set<Category> children = sortSubCategories(category.getChildren());

			for(Category  subcat:children) {

				String name = "--"+subcat.getName();
				categoriesUsedInForm.add(Category.copyIdandName(subcat.getId(), name));

				listSubCategoriesUsedInform(categoriesUsedInForm,subcat,1);
			}
			}
		}
		return categoriesUsedInForm;
	}
	 private void listSubCategoriesUsedInform(List<Category>categoriesUsedInForm , Category parent ,int sublevel) {
		 int newSubLevel= sublevel + 1;
		Set<Category> children = sortSubCategories(parent.getChildren());

		 for(Category subcat :children) {
			 String name="";
			 for(int i = 0;i < newSubLevel; i++) {  // we can just put syout(----) insted of forloop
				 name+="--";
			}
			 name+=subcat.getName();
			 categoriesUsedInForm.add(Category.copyIdandName(subcat.getId(), name));
			 listSubCategoriesUsedInform(categoriesUsedInForm ,subcat,newSubLevel);
			}


	 }



	public Category save(Category category) {

		return repo.save(category);
	}

	public Category get(Integer id)  throws CategoryNotFoundException{
		try {
		return repo.findById(id).get();
	}catch (NoSuchElementException e) {
		throw new CategoryNotFoundException("Could not find any category");
	}

	}


	public String checkunique(Integer id,String name,String alias){
		boolean isCreatingNew =(id==null ||id==0);
		Category findByName = repo.findByName(name);
		if(isCreatingNew) {
			if(findByName!=null) {
				return "DuplicateName";
			}else {
				Category categoryByAlias = repo.findByAlias(alias);
				if(categoryByAlias!=null) {
					return "DuplicateAlias";
				}
			}
		}else {
			if(findByName!=null && findByName.getId()!=id) {
				return "DuplicateName";
			}
			Category categoryByAlias = repo.findByAlias(alias);
			if(categoryByAlias!=null && categoryByAlias.getId()!=id) {
				return "DuplicateAlias";
			}
		}

		return "OK";
	}

	
	private SortedSet<Category>sortSubCategories(Set<Category> children){
		return sortSubCategories(children,"asc");
		
	} 

	private SortedSet<Category>sortSubCategories(Set<Category> children,String sortDir){
		
	
		SortedSet<Category>sortedChilderen= new TreeSet<>(new Comparator<Category>() {

			@Override
			public int compare(Category o1, Category o2) {
				if("asc".equals(sortDir)){

				return o1.getName().compareTo(o2.getName());
			}else {
				return o2.getName().compareTo(o1.getName());
			}
			}

		});
		sortedChilderen.addAll(children);

		return sortedChilderen;
	
}
	
	public void updateEnabledStatus(Integer Id,boolean enabled) {
		repo.updateEnabledStatus(Id, enabled);
	}
	
	public void delete(Integer id)throws CategoryNotFoundException{
		Long countById = repo.countById(id);
		if(countById==null || countById ==0) {
			throw new CategoryNotFoundException("could not find any category with ID " +id);
		}
		repo.deleteById(id);
	}
	
}

























