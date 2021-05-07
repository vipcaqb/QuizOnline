package hl.quizonline.service;

import java.util.List;

import org.springframework.data.domain.Page;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.MailBox;
import hl.quizonline.entity.MailTo;

// TODO: Auto-generated Javadoc
/**
 * The Interface MailboxService.
 */
public interface MailboxService {
	
	/**
	 * Gets the receive list.
	 *
	 * @param account the account
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the receive list
	 */
	Page<MailTo> getReceiveList(Account account,int pageNo, int pageSize);
	
	/**
	 * Gets the send list.
	 *
	 * @param account the account
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the send list
	 */
	Page<MailBox> getSendList(Account account, int pageNo, int pageSize);
	
	/**
	 * Send mail.
	 *
	 * @param to the to
	 * @param subject the subject
	 * @param message the message
	 * @param account the account
	 * @return true, if successful
	 */
	boolean sendMail(String to, String subject, String message,Account account);

	/**
	 * Delete.
	 *
	 * @param mailTo the mail to
	 */
	void delete(MailTo mailTo);
	
	/**
	 * Read mail.
	 *
	 * @param mailBoxID the mail box ID
	 * @return the mail box
	 */
	MailBox readMail(int mailBoxID);
	
	/**
	 * Xóa mail
	 *
	 * @param mailBoxID the mail box ID
	 */
	void deleteMailToAndAllMailBox(int mailBoxID);
}
