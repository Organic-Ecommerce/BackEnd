package com.organic.ecommerce.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.organic.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	public List<Product> findAllByTitleContainingIgnoreCase(String title);

	@Query(value = "select * from tb_product where username_id = :id", nativeQuery = true)
	public List<Product> getProductPorIdUser(@Param("id") Long id);

	@Query(value = "select * from tb_product where category_id = :id", nativeQuery = true)
	public List<Product> getProductPorIdCategory(@Param("id") Long id);

	public List<Product> findAllByPriceGreaterThanOrderByPrice(BigDecimal price);

	public List<Product> findAllByPriceLessThanOrderByPrice(BigDecimal price);

	public List<Product> findAllByPriceBetweenOrderByPrice(BigDecimal price1, BigDecimal price2);
}