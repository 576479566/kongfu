package com.kongfu.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.kongfu.entity.Role;
import com.kongfu.entity.RoleRepository;
import com.kongfu.entity.User;
import com.kongfu.entity.UserRepository;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private RoleRepository rolesRepo;
    
    /**
     * 用户角色的对应关系.
     * 查询有问题
     */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
		log.info("Authenticating {}", login);
        User user = userRepo.findByEmail(login);
        if (user == null) {
            throw new UsernameNotFoundException("User " + login + " was not found in the database");
        } 
        //else if (!user.getEnabled()) {
       //     throw new UserNotEnabledException("User " + login + " was not enabled");
        //}
        
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (Role authority : rolesRepo.findById(user.getId())) {//查询有问题
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getRolename());
            grantedAuthorities.add(grantedAuthority);
        }

        return new org.springframework.security.core.userdetails.User(login, user.getPwd(),
                grantedAuthorities);
	}

}
