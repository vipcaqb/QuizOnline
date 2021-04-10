package hl.quizonline.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import hl.quizonline.entity.Account;

// TODO: Auto-generated Javadoc
/**
 * The Interface AccountService.
 */
public interface AccountService extends UserDetailsService  {
	
	/**
	 * Register account.
	 */
	void registerAccount(Account acc);
	
	/**
	 * Check login.
	 *
	 * @return true, if successful
	 */
	boolean checkLogin(String username, String password);
	
	/**
	 * Edits the account.
	 */
	void editAccount();
	
	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	List<Account> getList();
}
