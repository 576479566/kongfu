package com.kongfu.entity;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<User, String> {

	public List<Role> findById(int id);
}
