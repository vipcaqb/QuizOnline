package hl.quizonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hl.quizonline.config.MyConstances;
import hl.quizonline.entity.Account;
import hl.quizonline.entity.MailBox;
import hl.quizonline.entity.MailTo;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.MailToService;
import hl.quizonline.service.MailboxService;

@Controller
@RequestMapping("/manage/mailbox")
public class MailBoxController {
	@Autowired
	MailboxService mailboxService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	MailToService mailToService;
	
	@GetMapping(value = {"","/{pageNo}"})
	public String showMailbox(@PathVariable(name = "pageNo",required = false) Integer pageNo,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			if(pageNo==null) pageNo = 1;
			String currentUserName = authentication.getName();
			Account account = accountService.getAccountByUsername(currentUserName).get();
			
			Page<MailTo> page = mailboxService.getReceiveList(account, pageNo, MyConstances.PAGE_SIZE);
			List<MailTo> mailToList = page.getContent();
			
			model.addAttribute("mailToList", mailToList);
			model.addAttribute("page", page);
			return "manage/manage-mailbox";
		}
		
		return "redirect:/login";
	}
	
	@GetMapping(value = {"sent","sent/{pageNo}"})
	public String showSentMail(@PathVariable(name = "pageNo",required = false) Integer pageNo,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			if(pageNo==null) pageNo = 1;
			String currentUserName = authentication.getName();
			Account account = accountService.getAccountByUsername(currentUserName).get();
			
			Page<MailBox> page = mailboxService.getSendList(account, pageNo, MyConstances.PAGE_SIZE);
			List<MailBox> mailBoxList = page.getContent();
			model.addAttribute("mailBoxList", mailBoxList);
			model.addAttribute("page", page);
			return "manage/manage-mailbox-sent";
		}
		return "redirect:/login";
	}
	
	@GetMapping("read/{mailBoxID}")
	public String readMailForm(@PathVariable("mailBoxID") Integer mailBoxID, Model model) {
		MailBox mailBox = mailboxService.readMail(mailBoxID);
		String receiver = "";
		for(int i =0;i<mailBox.getMailTos().size();i++) {
			if(i>0)
				receiver+=","+ mailBox.getMailTos().get(i).getAccount().getUsername();
			else receiver+=mailBox.getMailTos().get(i).getAccount().getUsername();
		}
		
		model.addAttribute("mailBox", mailBox);
		model.addAttribute("receiver", receiver);
		return "manage/manage-mailbox-read";
	}
	
	@PostMapping("/sendmail")
	public String sendMail(@RequestParam(name = "to") String to,
				@RequestParam(name = "subject") String subject,
				@RequestParam(name = "message") String message
			) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			Account account = accountService.getAccountByUsername(currentUserName).get();
			mailboxService.sendMail(to, subject, message, account);
			return "redirect:/manage/mailbox";
		}
		return "redirect:/login";
	}
}
	