package hl.quizonline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.MailTo;
import hl.quizonline.repository.MailtoRepository;
import hl.quizonline.service.MailToService;

@Service
public class MailToServiceImpl implements MailToService {

	@Autowired
	MailtoRepository mailToRepository;
	
	@Override
	public void delete(MailTo mailTo) {
		mailTo.setDeleted(true);
		mailToRepository.save(mailTo);
	}

	@Override
	public void seen(MailTo mailTo) {
		mailTo.setSeen(true);
		mailToRepository.save(mailTo);
	}

}
