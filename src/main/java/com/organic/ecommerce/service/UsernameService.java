package com.organic.ecommerce.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.organic.ecommerce.model.Username;
import com.organic.ecommerce.model.UsernameLogin;
import com.organic.ecommerce.repository.UsernameRepository;

@Service
public class UsernameService {

	@Autowired
	private UsernameRepository usernameRepository;

	public Optional<Username> cadastrarUsuario(Username username) {

		if (usernameRepository.findByUsername(username.getUsername()).isPresent())
			return Optional.empty();

		username.setPassword(criptografarSenha(username.getPassword()));

		return Optional.of(usernameRepository.save(username));

	}

	public Optional<Username> atualizarUsuario(Username username) {
		
		
		
		if (usernameRepository.findById(username.getId()).isPresent()) {
			
			Optional<Username> usuarioBanco = usernameRepository.findByUsername(username.getUsername());	
			
			if((usuarioBanco.isPresent()) && (usuarioBanco.get().getId() != username.getId())) {
				
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuario já existe");
			
			}
			username.setPassword(criptografarSenha(username.getPassword()));
			return Optional.ofNullable(usernameRepository.save(username));

		}

		return Optional.empty();

	}

	public Optional<UsernameLogin> autenticarUsuario(Optional<UsernameLogin> usernameLogin) {

		Optional<Username> usuario = usernameRepository.findByUsername(usernameLogin.get().getUsername());

		if (usuario.isPresent()) {

			if (compararSenhas(usernameLogin.get().getPassword(), usuario.get().getPassword())) {

				usernameLogin.get().setId(usuario.get().getId());
				usernameLogin.get().setName(usuario.get().getName());
				usernameLogin.get().setPhoto(usuario.get().getPhoto());
				usernameLogin.get().setAbout(usuario.get().getAbout());
				usernameLogin.get()
						.setToken(gerarBasicToken(usernameLogin.get().getUsername(), usernameLogin.get().getPassword()));
				usernameLogin.get().setPassword(usuario.get().getPassword());

				return usernameLogin;

			}
		}

		return Optional.empty();

	}

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);

	}

	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(senhaDigitada, senhaBanco);

	}

	private String gerarBasicToken(String usuario, String senha) {

		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);

	}
	
	

}