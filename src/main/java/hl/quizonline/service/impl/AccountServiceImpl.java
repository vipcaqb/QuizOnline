package hl.quizonline.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Account;
import hl.quizonline.model.CustomUserDetails;
import hl.quizonline.model.ExamDonation;
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
	public void editAccount(Account account) {
		Optional<Account> oa = accRepository.findByUsername(account.getUsername());
		if(oa.isPresent()) {
			accRepository.save(account);
		}
	}

	@Override
	public List<Account> getList() {
		return accRepository.findAll();
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


	@Override
	public Optional<Account> getAccountByUsername(String username) {
		return accRepository.findByUsername(username);
	}


	@Override
	public Page<Account> getAllAccount(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return accRepository.findAll(pageable);
	}


	@Override
	public Page<Account> getTop10() {
		Pageable pageable = PageRequest.of(0, 9);
		return accRepository.findAll(pageable);
	}

}
