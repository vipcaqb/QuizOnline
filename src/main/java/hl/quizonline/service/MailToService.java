package hl.quizonline.service;

import hl.quizonline.entity.MailTo;

// TODO: Auto-generated Javadoc
/**
 * The Interface MailToService.
 */
public interface MailToService {
	
	/**
	 * Delete.
	 *
	 * @param mailTo the mail to
	 */
	void delete(MailTo mailTo);
	
	/**
	 * Seen.
	 *
	 * @param mailTo the mail to
	 */
	void seen(MailTo mailTo);

}
