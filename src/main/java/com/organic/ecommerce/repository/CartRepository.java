package com.organic.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organic.ecommerce.model.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	
	//Cart getCartByCartId(String CartId);
	//void update(Cart cart);

}
