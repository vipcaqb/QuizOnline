package hl.quizonline.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hl.quizonline.entity.Account;

public class CustomUserDetails implements UserDetails {
	
	Account account;
	
	public Account getAccount() {
		return account;
	}

	public CustomUserDetails(Account account) {
		super();
		this.account = account;
	}
	
	public CustomUserDetails() {
		super();
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(account.getRole()));
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getUsername();
	}
	
	public String getFullname() {
		return account.getFullname();
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
		return account.getEnable();
	}

	public String getUrlAvatar() {
		return account.getUrlAvatar();
	}
}
