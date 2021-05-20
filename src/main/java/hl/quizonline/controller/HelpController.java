package hl.quizonline.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.model.ExamDonation;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.ExamPackageService;

@Controller
@RequestMapping("/help")
public class HelpController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	ExamPackageService examPackageService;
	
	@GetMapping("")
	public String loadHelpInterface(Model model) {
		//danh sách xếp hạng tạo đề
		Page<Account> pageAccount = accountService.getTop10();
		List<Account> aList = pageAccount.getContent();
			//Chuyển vào model
		List<ExamDonation> accountList = new ArrayList<ExamDonation>();
		for(int i =0;i<aList.size();i++) {
			accountList.add(new ExamDonation(aList.get(i).getUsername(),
					aList.get(i).getFullname(), aList.get(i).getUrlAvatar(), aList.get(i).getExamPackages().size()));
		}
			//Sắp xếp lại theo thứ tạo giảm dần của số lượng đề thi
		for(int i =0;i<accountList.size()-1;i++) {
			for(int j=i+1;j<accountList.size();j++) {
				if(accountList.get(i).getNumberOfExam()<accountList.get(j).getNumberOfExam()) {
					ExamDonation temp = accountList.get(i);
					accountList.set(i, accountList.get(j));
					accountList.set(j, temp);
				}
			}
		}
		
		//de thi sap dien ra
		List<ExamPackage> examPackageIsCommingList = examPackageService.getListIsComing();
		
		model.addAttribute("accountList", accountList);	
		model.addAttribute("examPackageIsCommingList", examPackageIsCommingList);
		return "help";
	}
}
