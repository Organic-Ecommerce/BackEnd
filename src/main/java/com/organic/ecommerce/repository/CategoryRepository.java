package com.organic.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.organic.ecommerce.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	public List<Category> findAllByCategoryContainingIgnoreCase(String category);

	@Query(value = "select * from tb_category where tb_category.category = % ", nativeQuery = true)
	public List<Category> getCategoryProducts(String category);

}
