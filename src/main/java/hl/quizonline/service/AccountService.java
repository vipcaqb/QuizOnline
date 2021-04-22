package hl.quizonline.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import hl.quizonline.entity.Account;

// TODO: Auto-generated Javadoc
/**
 * The Interface AccountService.
 */
public interface AccountService extends UserDetailsService  {
	
	/**
	 * Register account.
	 *
	 * @param acc the acc
	 */
	void registerAccount(Account acc);
	
	/**
	 * Check login.
	 *
	 * @param username the username
	 * @param password the password
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
	
	/**
	 * Gets the account by username.
	 *
	 * @param username the username
	 * @return the account by username
	 */
	Optional<Account> getAccountByUsername(String username);
}
