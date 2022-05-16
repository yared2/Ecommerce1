package com.shopme.admin.category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
	@Query("select c from Category c where c.parent.id is NULL" )
	public List<Category>findRootCategories(Sort sort);
	
	@Query("select c from Category c where c.parent.id is NULL" )
	public Page<Category>findRootCategories( Pageable pageable);
	
	@Query("Select c from Category c where c.name like %?1% ")
	public Page<Category> search(String keyword,Pageable pageable);
	

public Category findByName(String name);

public Category  findByAlias(String alias);

@Query("update Category c set c.enabled=?2 where c.id=?1")
@Modifying
public void updateEnabledStatus(Integer Id,boolean enabled);


public Long countById(Integer id);

}
