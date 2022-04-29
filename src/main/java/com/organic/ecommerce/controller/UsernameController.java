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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.organic.ecommerce.model.Username;
import com.organic.ecommerce.model.UsernameLogin;
import com.organic.ecommerce.repository.UsernameRepository;
import com.organic.ecommerce.service.UsernameService;

@RestController
@RequestMapping("/username")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsernameController {

	@Autowired
	private UsernameService usernameService;
	
	@Autowired
	private UsernameRepository usernameRepository;
	
	
	@GetMapping("/all")
	public ResponseEntity <List<Username>> getAll(){
		
		return ResponseEntity.ok(usernameRepository.findAll());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Username> getById(@PathVariable Long id){
		return usernameRepository.findById(id)
			.map(resposta -> ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/login")
	public ResponseEntity<UsernameLogin> loginUser(@RequestBody Optional <UsernameLogin> userLogin){
		
		return usernameService.autenticarUsuario(userLogin)
			.map(respostaLogin -> ResponseEntity.status(HttpStatus.OK).body(respostaLogin))
			.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/register")
	public ResponseEntity<Username> postUsuario(@Valid @RequestBody Username user){
		
		return usernameService.cadastrarUsuario(user)
			.map(register -> ResponseEntity.status(HttpStatus.CREATED).body(register))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PutMapping("/updated")
	public ResponseEntity<Username> putUser(@Valid @RequestBody Username user) {
		return usernameService.atualizarUsuario(user)
			.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		
		return usernameRepository.findById(id)
			.map(resposta -> {
				usernameRepository.deleteById(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			})
			.orElse(ResponseEntity.notFound().build());
	}
}