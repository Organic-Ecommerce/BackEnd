package com.organic.ecommerce.controller;

import java.net.URI;
import java.util.List;

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
import com.organic.ecommerce.repository.CategoryRepository;
import com.organic.ecommerce.repository.ProductRepository;



@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
	
private CategoryRepository categoryRepository;
	
	@Autowired 
	public CategoryController(ProductRepository productRepository, CategoryRepository categoryRepository){
		this.categoryRepository = categoryRepository;
	}
	@GetMapping
	public ResponseEntity<List<Category>> getAll(){
		return ResponseEntity.ok(categoryRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> getById(@PathVariable Long id){
		return categoryRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
				}
	@GetMapping("/category/{category}")
	public ResponseEntity<List<Category>> getByDescricao(@PathVariable String category){
		return ResponseEntity.ok(categoryRepository.findAllByCategoryContainingIgnoreCase(category ));
		
	}
	
    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedCategory.getId()).toUri();

        return ResponseEntity.created(location).body(savedCategory);
    }
	
    @PutMapping
	public ResponseEntity<Category> putCategory(@Valid @RequestBody Category category) {
					
		return categoryRepository.findById(category.getId())
				.map(resposta -> {
					return ResponseEntity.ok().body(categoryRepository.save(category));
				})
				.orElse(ResponseEntity.notFound().build());

	}
    
	@DeleteMapping(path = "/{id}")
	public  ResponseEntity<?> deleteCategory(@PathVariable Long id) {
		return categoryRepository.findById(id).map(record -> {
			categoryRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		}).orElse(ResponseEntity.notFound().build());
		
	}

}
