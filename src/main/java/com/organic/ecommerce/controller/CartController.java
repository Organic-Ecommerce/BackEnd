package com.organic.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organic.ecommerce.model.Cart;
import com.organic.ecommerce.model.CartItem;
import com.organic.ecommerce.model.Product;
import com.organic.ecommerce.repository.CartItemRepository;
import com.organic.ecommerce.repository.CartRepository;
import com.organic.ecommerce.repository.ProductRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {
	
	@Autowired
	private CartRepository cartRepository;
	
	@GetMapping
	public ResponseEntity <List<Cart>> getAll(){
		return ResponseEntity.ok(this.cartRepository.findAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Cart> getById(@PathVariable Long id){
		return cartRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());	
	}
	
	@PostMapping
	public ResponseEntity<Cart> postProd (@Valid @RequestBody Cart cart){
		/*Optional<CartItem> optionalCardItens = cartItemRepository.findById(cart.getCartItem().getId());

        if (!optionalCardItens.isPresent()) {
            return ResponseEntity.badRequest().build();
        }*/
		return ResponseEntity.status(HttpStatus.CREATED).body(cartRepository.save(cart));
	}
	
	@PutMapping
	public ResponseEntity<Cart> putCart (@Valid @RequestBody Cart cart){
		return cartRepository.findById(cart.getId())
			.map(resposta -> ResponseEntity.ok().body(cartRepository.save(cart)))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path = "/{id}")
	public  ResponseEntity<?> deleteCart(@PathVariable Long id) {
		return cartRepository.findById(id).map(record -> {
			cartRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		}).orElse(ResponseEntity.notFound().build());
		
	}

}
