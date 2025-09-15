package com.DtoEntityIdProblem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DtoEntityIdProblem.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByEmail(String email);

}
