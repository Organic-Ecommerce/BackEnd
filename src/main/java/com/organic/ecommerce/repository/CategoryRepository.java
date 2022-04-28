package com.organic.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.organic.ecommerce.model.Category;

public interface CategoryRepository  extends JpaRepository<Category, Long>{
	
public List <Category> findAllByCategoryContainingIgnoreCase (String category);
	
	@Query(value="select * from tb_category where tb_category.category = % ", 
			nativeQuery = true)
	List<Category> getCategoryProducts(String category);

}
