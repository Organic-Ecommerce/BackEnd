package com.organic.ecommerce.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.apache.commons.codec.binary.Base64;

import com.organic.ecommerce.model.User;
import com.organic.ecommerce.model.UserLogin;
import com.organic.ecommerce.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public Optional<User> registerUser(User user) {

		if (userRepository.findByUser(user.getUser()).isPresent())
			return Optional.empty();

		user.setPassword(criptografarSenha(user.getPassword()));

		return Optional.of(userRepository.save(user));

	}

	public Optional<User> updatedUser(User user) {
		
		
		
		if (userRepository.findById(user.getId()).isPresent()) {
			
			Optional<User> usuarioBanco = userRepository.findByUser(user.getUser());	
			
			if((usuarioBanco.isPresent()) && (usuarioBanco.get().getId() != user.getId())) {
				
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuario já existe");
			
			}
			user.setPassword(criptografarSenha(user.getPassword()));
			return Optional.ofNullable(userRepository.save(user));

		}

		return Optional.empty();

	}

	public Optional<UserLogin> autenticarUser(Optional<UserLogin> userLogin) {

		Optional<User> user = userRepository.findByUser(userLogin.get().getUser());

		if (user.isPresent()) {

			if (compararSenhas(userLogin.get().getPassword(), user.get().getPassword())) {

				userLogin.get().setId(user.get().getId());
				userLogin.get().setName(user.get().getName());
				userLogin.get().setPhoto(user.get().getPhoto());
				userLogin.get().setAbout(user.get().getAbout());
				userLogin.get()
						.setToken(gerarBasicToken(userLogin.get().getUser(), userLogin.get().getPassword()));
				userLogin.get().setPassword(user.get().getPassword());

				return userLogin;

			}
		}

		return Optional.empty();

	}

	private String criptografarSenha(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(password);

	}

	private boolean compararSenhas(String passwordType, String passwordDB) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(passwordType, passwordDB);

	}

	private String gerarBasicToken(String user, String password) {

		String token = user + ":" + password;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);

	}

}
