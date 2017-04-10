package com.kongfu.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

	public User findByUname(String uname);
	//@Query("from User u where u.name=:name")//继承JpaRepository<User, Long>
	public User findByUnameAndPwd(String uname,String pwd);
	@Query("select count(id) from User u where u.uname=?1")
	public int findUserCount(String uname);
	public User findById(String userId);
	public User findByEmail(String email);
}
