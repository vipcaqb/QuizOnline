package hl.quizonline.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import hl.quizonline.entity.Account;
import hl.quizonline.model.ExamDonation;

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
	 *
	 * @param account the account
	 */
	void editAccount(Account account);
	
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
	
	/**
	 * Gets the all account.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the all account
	 */
	Page<Account> getAllAccount(int pageNo, int pageSize);
	
	/**
	 * Tìm kiếm các tài khoản theo username và fullname
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param key the key
	 * @return the page
	 */
	Page<Account> searchAllAccount(int pageNo, int pageSize, String key);
	
	/**
	 * Tìm kiếm các account theo username và fullname mà có chứa key.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param key the key
	 * @return the page
	 */
	Page<Account> findAccount(int pageNo, int pageSize, String key);
	
	/**
	 * Lấy danh sách những người tạo đề nhiều nhất.
	 *
	 * @return the top
	 */
	Page<Account> getTop10();
	
	/**
	 * Khóa/ mở tài khoản, nếu tài khoản chưa bị khóa thì khóa, đã bị khóa thì mở khóa.
	 *
	 * @param account the account
	 */
	void lockAccount(Account account);
}
