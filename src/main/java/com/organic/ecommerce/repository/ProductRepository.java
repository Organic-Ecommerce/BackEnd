package com.organic.ecommerce.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.organic.ecommerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
	List <Product> findAllByTitleContainingIgnoreCase(String title);
	
	@Query(value = "select * from tb_product where user_id = :id", nativeQuery = true)
	public List<Product> getProductPorIdUser(@Param("id") Long id);
	
	@Query(value = "select * from tb_product where category_id = :id", nativeQuery = true)
	public List<Product> getProductPorIdCategory(@Param("id") Long id);
	
public List <Product> findAllByPriceGreaterThanOrderByPrice (BigDecimal price);
	
	public List <Product> findAllByPriceLessThanOrderByPrice (BigDecimal price);
	
	public List <Product> findAllByPriceBetweenOrderByPrice(BigDecimal price1, BigDecimal price2);
	
}
