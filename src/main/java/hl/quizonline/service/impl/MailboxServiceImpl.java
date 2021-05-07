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
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.MailBox;
import hl.quizonline.entity.MailTo;
import hl.quizonline.repository.AccountRepository;
import hl.quizonline.repository.MailBoxRepository;
import hl.quizonline.repository.MailtoRepository;
import hl.quizonline.service.MailboxService;

@Service
public class MailboxServiceImpl implements MailboxService {

	@Autowired
	MailBoxRepository mailboxRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	MailtoRepository mailtoRepository;
	
	@Override
	public Page<MailTo> getReceiveList(Account account, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return mailtoRepository.findByAccountAndDeleted(account, false, pageable);
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
		return mailboxRepository.findById(mailBoxID).get();
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

}
