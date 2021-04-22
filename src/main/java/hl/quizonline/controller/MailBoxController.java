package hl.quizonline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage/mailbox")
public class MailBoxController {
	@GetMapping("")
	public String showMailbox(Model model) {
		return "manage/manage-mailbox";
	}
}
	