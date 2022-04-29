package com.organic.ecommerce.controller;

import java.math.BigDecimal;
import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.organic.ecommerce.model.Category;
import com.organic.ecommerce.model.Product;
import com.organic.ecommerce.repository.CategoryRepository;
import com.organic.ecommerce.repository.ProductRepository;



@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
	
	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;
	
	@Autowired 
	public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository){
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}
	
	// Crud oficial
	
	@GetMapping
	public ResponseEntity <List<Product>> getAll(){
		return ResponseEntity.ok(this.productRepository.findAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getById(@PathVariable Long id){
		return productRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<Product>> findByTitle (@PathVariable String title){
		return ResponseEntity.ok(productRepository.findAllByTitleContainingIgnoreCase(title));
	}
	
	
	@GetMapping("user/{id}")
	public ResponseEntity<List<Product>> productForId(@PathVariable Long id){
		return ResponseEntity.ok(productRepository.getProductPorIdUser(id));
	}
	
	@GetMapping("category/{id}")
	public ResponseEntity<List<Product>> productByCategory(@PathVariable Long id){
		return ResponseEntity.ok(productRepository.getProductPorIdCategory(id));
	}
	
	@GetMapping("/big/{price}")
	public ResponseEntity<List<Product>> findByBigPrice (@PathVariable BigDecimal price){
		return ResponseEntity.ok(productRepository.findAllByPriceGreaterThanOrderByPrice(price));
	}
	
	@GetMapping("/small/{price}")
	public ResponseEntity<List<Product>> findBySmallPrice (@PathVariable BigDecimal price){
		return ResponseEntity.ok(productRepository.findAllByPriceLessThanOrderByPrice(price));
	}
	
	@GetMapping("/between/{price1}~{price2}")
	public ResponseEntity<List<Product>> findByBetween (@PathVariable BigDecimal price1, @PathVariable BigDecimal price2){
		return ResponseEntity.ok(productRepository.findAllByPriceBetweenOrderByPrice(price1, price2));
	}
	
	
	// CRUD OFICIAL
	
	 @PostMapping()
	 public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
		 		Optional<Category> optionalCategory = categoryRepository.findById(product.getCategory().getId());//Star Wars - id = 1 ;"tema" :{id: 1}// null // True Or False
	
	        if (!optionalCategory.isPresent()) {
	            return ResponseEntity.badRequest().build();
	        }
	        product.setCategory(optionalCategory.get());
	        Product savedProduct = productRepository.save(product);
	        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	            .buildAndExpand(savedProduct.getId()).toUri();
	        return ResponseEntity.created(location).body(savedProduct);
	    }
	 
	 @PutMapping
		public ResponseEntity<Product> putProduct (@Valid @RequestBody Product product){
			return productRepository.findById(product.getId())
				.map(resposta -> ResponseEntity.ok().body(productRepository.save(product)))
				.orElse(ResponseEntity.notFound().build());
		}
	 
	@DeleteMapping(path = "/{id}")
	public  ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		return productRepository.findById(id).map(record -> {
			productRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		}).orElse(ResponseEntity.notFound().build());
		
	}

}
