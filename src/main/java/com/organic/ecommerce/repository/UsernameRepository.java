package com.organic.ecommerce.repository;

//import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.organic.ecommerce.model.Username;

@Repository
public interface UsernameRepository extends JpaRepository<Username, Long> {

	public Optional<Username> findByUsername(String username);

	//public List<Username> findByUsernameContainingIgnoreCase(String username);

}