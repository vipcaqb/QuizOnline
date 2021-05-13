package hl.quizonline.service;

import java.util.List;

import org.springframework.data.domain.Page;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.MailBox;
import hl.quizonline.entity.MailTo;
import hl.quizonline.entity.QuestionPackage;

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
	 * Xóa mail.
	 *
	 * @param mailBoxID the mail box ID
	 */
	void deleteMailToAndAllMailBox(int mailBoxID);

	/**
	 * Gửi thông báo đến mail của người dùng khi admin xóa examPackage.
	 *
	 * @param to the to
	 * @param examDeleted the exam deleted
	 * @param reason the reason
	 */
	void noticeUserWhenAdminDeleteExamPackage(Account to, ExamPackage examDeleted, String reason);
	
	/**
	 * Gửi thông báo đến mail của người dùng khi admin xóa questionPackage.
	 *
	 * @param to the to
	 * @param questionDeleted the question deleted
	 * @param reason the reason
	 */
	void noticeUserWhenAdminDeleteQuestionPackage(Account to, QuestionPackage questionDeleted, String reason);
	
	/**
	 * Xóa mail đã nhận.
	 *
	 * @param mailBox the mail box
	 * @param username username của người nhận
	 */
	void deleteReceive(int mailBoxID, String username);
	
	/**
	 * Xóa thư đã gửi
	 *
	 * @param mailBox the mail box
	 */
	void deleteSent(int mailBoxID);
}
