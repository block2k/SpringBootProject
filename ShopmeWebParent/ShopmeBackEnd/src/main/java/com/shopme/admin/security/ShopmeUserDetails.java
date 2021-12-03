package com.shopme.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

public class ShopmeUserDetails implements UserDetails {

    private final User user;

    public ShopmeUserDetails(User user) {
	this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	Set<Role> roles = user.getRoles();
	List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	for (Role role : roles) {
	    authorities.add(new SimpleGrantedAuthority(role.getName()));
	}
	return authorities;
    }

    @Override
    public String getPassword() {
	return user.getPassword();
    }

    public String getFullName() {
	return this.user.getFirstName() + " " + this.user.getLastName();
    }

    @Override
    public String getUsername() {
	return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
	return true;
    }

    @Override
    public boolean isAccountNonLocked() {
	return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return true;
    }

    @Override
    public boolean isEnabled() {
	return user.isEnable();
    }

    public void setFirstName(String firstName) {
	this.user.setFirstName(firstName);
    }

    public void setLastName(String lastName) {
	this.user.setLastName(lastName);
    }
}
