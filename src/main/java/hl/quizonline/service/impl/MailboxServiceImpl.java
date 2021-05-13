package hl.quizonline.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.MailBox;
import hl.quizonline.entity.MailTo;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.repository.AccountRepository;
import hl.quizonline.repository.MailBoxRepository;
import hl.quizonline.repository.MailtoRepository;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.MailToService;
import hl.quizonline.service.MailboxService;

@Service
public class MailboxServiceImpl implements MailboxService {

	@Autowired
	MailBoxRepository mailboxRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	MailtoRepository mailtoRepository;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	MailboxService mailboxService;
	
	@Autowired
	MailToService mailToService;
	
	@Override
	public Page<MailTo> getReceiveList(Account account, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return mailtoRepository.findByAccountAndDeletedAndSort(account, false, pageable);
	}

	@Override
	public Page<MailBox> getSendList(Account account, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return mailboxRepository.findByAccountAndDeleted(account, false, pageable);
	}

	@Override
	@Transactional
	public boolean sendMail(String to, String subject, String message, Account account) {
		//kiem tra 'to'
		to = to.trim();
		
		//tao mailbox 
		MailBox mailBox = new MailBox();
		mailBox.setAccount(account);
		mailBox.setContent(message);
		mailBox.setDeleted(false);
		mailBox.setTitle(subject);
		
		Date now = new Date(System.currentTimeMillis());
		mailBox.setSendDate(now);
		MailBox mailCreated = mailboxRepository.save(mailBox);
		
		//split 'to'
		String []toSplited = to.split("[,]");
		//tao mail to
		for (String user : toSplited) {
			Optional<Account> oAccountReceive = accountRepository.findByUsername(user);
			if(oAccountReceive.isPresent()) {
				Account accountReceive = oAccountReceive.get();
				//tao mailTo
				MailTo mailTo = new MailTo();
				mailTo.setAccount(accountReceive);
				mailTo.setDeleted(false);
				mailTo.setSeen(false);
				mailTo.setMailBox(mailCreated);
				
				mailtoRepository.save(mailTo);
			}
		}
		return true;
	}

	@Override
	public void delete(MailTo mailTo) {
		mailTo.setDeleted(true);
		mailtoRepository.save(mailTo);
	}

	@Override
	public MailBox readMail(int mailBoxID) {
		MailBox mailBox = mailboxRepository.findById(mailBoxID).get();
		return mailBox;
	}

	@Override
	@Transactional
	public void deleteMailToAndAllMailBox(int mailBoxID) {
		//lấy mail box
		Optional<MailBox> opMailBox = mailboxRepository.findById(mailBoxID);
		if(opMailBox.isPresent()) {
			MailBox mailBox = opMailBox.get();
			mailBox.setDeleted(true);
			mailboxRepository.save(mailBox);
		}
	}

	@Override
	@Transactional
	public void noticeUserWhenAdminDeleteExamPackage(Account to, ExamPackage examDeleted, String reason) {
		String title = "[Thông báo] Đề thi của bạn đã bị xóa!";
		String content = "Chúng tôi rất tiếc khi phải thông báo với bạn rằng"
				+ " đề thi ["+examDeleted.getExamPackageTitle()+"] đã bị xóa với lý do : "+ reason;
		//Lấy thông tin tài khoản admin
		Account adminAccount = accountService.getAccountByUsername("admin").get();
		
		//Điền thông tin vào mail box (Người gửi)
		MailBox mailBox = new MailBox();
		mailBox.setAccount(adminAccount);
		mailBox.setTitle(title);
		mailBox.setContent(content);
		mailBox.setDeleted(false);
		mailBox.setSendDate(new Date(System.currentTimeMillis()));
		mailBox = mailboxRepository.save(mailBox);
		//Điền thông tin người nhận vào mailto (Người nhận)
		MailTo mailTo = new MailTo();
		mailTo.setAccount(to);
		mailTo.setDeleted(false);
		mailTo.setSeen(false);
		mailTo.setMailBox(mailBox);
		mailtoRepository.save(mailTo);
		
	}

	@Override
	public void noticeUserWhenAdminDeleteQuestionPackage(Account to, QuestionPackage questionDeleted, String reason) {
		String title = "[Thông báo] Gói câu hỏi của bạn đã bị xóa!";
		String content = "Chúng tôi rất tiếc khi phải thông báo với bạn rằng"
				+ " gói câu hỏi ["+questionDeleted.getName()+"] đã bị xóa với lý do : "+ reason;
		//Lấy thông tin tài khoản admin
		Account adminAccount = accountService.getAccountByUsername("admin").get();
		
		//Điền thông tin vào mail box (Người gửi)
		MailBox mailBox = new MailBox();
		mailBox.setAccount(adminAccount);
		mailBox.setTitle(title);
		mailBox.setContent(content);
		mailBox.setDeleted(false);
		mailBox.setSendDate(new Date(System.currentTimeMillis()));
		mailBox = mailboxRepository.save(mailBox);
		//Điền thông tin người nhận vào mailto (Người nhận)
		MailTo mailTo = new MailTo();
		mailTo.setAccount(to);
		mailTo.setDeleted(false);
		mailTo.setSeen(false);
		mailTo.setMailBox(mailBox);
		mailtoRepository.save(mailTo);
		
	}

	@Override
	public void deleteReceive(int  mailBoxID, String username) {
		Optional<Account> opAccount = accountRepository.findByUsername(username);
		if(opAccount.isEmpty()) return;
		Optional<MailBox> opMailBox = mailboxRepository.findById(mailBoxID);
		if(opMailBox.isEmpty()) return;
		List<MailTo> mailToList = mailtoRepository.findByAccountAndMailBox(opAccount.get(), opMailBox.get());
		for(int i = 0 ; i<mailToList.size();i++) {
			mailtoRepository.delete(mailToList.get(i));
		}
	}

	@Override
	public void deleteSent(int mailBoxID) {
		Optional<MailBox> opMailBox = mailboxRepository.findById(mailBoxID);
		if(opMailBox.isEmpty()) return;
		MailBox mailBox = opMailBox.get();
		if(mailBox.getMailTos().size()==0) {
			mailboxRepository.delete(mailBox);
		}else {
			mailBox.setDeleted(true);
			mailboxRepository.save(mailBox);
		}
		
	}

	
	
}
