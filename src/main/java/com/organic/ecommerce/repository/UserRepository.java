package com.organic.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.organic.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByUser(String name);
	
	public List<User> findByUserContainingIgnoreCase(String name);	

}
