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

import com.organic.ecommerce.model.CartItem;
import com.organic.ecommerce.model.Product;
import com.organic.ecommerce.repository.CartItemRepository;
import com.organic.ecommerce.repository.CartRepository;
import com.organic.ecommerce.repository.ProductRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/cartitem")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartItemController {

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@GetMapping
	public ResponseEntity <List<CartItem>> getAll(){
		return ResponseEntity.ok(this.cartItemRepository.findAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<CartItem> getById(@PathVariable Long id){
		return cartItemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());	
	}
	
	@PostMapping
	public ResponseEntity<CartItem> postCartItem (@Valid @RequestBody CartItem cartItem){
		
		/** Checa antes de Persistir o Objeto Cart se o Produto existe 
		 *  Se o Produto não existir,(=0) o status devolvido será Bad Request (400).???
		*/

		/*if (productRepository.existsById(cartItem.getProduct().getId()))*/
			return ResponseEntity.status(HttpStatus.CREATED).body(cartItemRepository.save(cartItem));
	
		/*Optional<Product> optionalProduct = productRepository.findById(cartItem.getProduct().getId());

        if (!optionalProduct.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();*/
	
	}
	
	@PutMapping
	public ResponseEntity<CartItem> putCartItem (@Valid @RequestBody CartItem cartItem){
		return cartItemRepository.findById(cartItem.getId())
			.map(resposta -> ResponseEntity.ok().body(cartItemRepository.save(cartItem)))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path = "/{id}")
	public  ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
		return cartItemRepository.findById(id).map(record -> {
			cartItemRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		}).orElse(ResponseEntity.notFound().build());
		
	}
	
	
	
}


