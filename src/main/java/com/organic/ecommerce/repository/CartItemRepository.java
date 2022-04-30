package com.organic.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organic.ecommerce.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	

	
	//void addCartItem(CartItem cartItem);
	
	//void removeCartItem(String CartItemId);
	
	//void removeAllCartItems(Cart cart);

}
