package hl.quizonline.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Account;
import hl.quizonline.model.CustomUserDetails;
import hl.quizonline.repository.AccountRepository;
import hl.quizonline.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired
	AccountRepository accRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void registerAccount(Account acc) {
		acc.setPassword(passwordEncoder.encode(acc.getPassword()));
		Optional<Account> rs = accRepository.findByUsername(acc.getUsername());
		if(rs.isEmpty()) {
			accRepository.save(acc);
		}
	}


	@Override
	public void editAccount() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Account> getList() {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public boolean checkLogin(String username, String password) {
		Optional<Account> account = accRepository.findByUsernameAndPassword(username, password);
		if(account.isPresent()) {
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> rs = accRepository.findByUsername(username);
		if(rs.isPresent()) {
			return new CustomUserDetails(rs.get());
		}
		else {
			System.out.println("Khong tim thay tai khoan");
			throw new UsernameNotFoundException(username);
		}
	}

}
