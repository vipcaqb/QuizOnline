package hl.quizonline.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import hl.quizonline.config.MyConstances;
import hl.quizonline.entity.Account;
import hl.quizonline.entity.JoinExamination;
import hl.quizonline.enumrable.Gender;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.JoinExamService;

@Controller
public class AccountController {
	@Autowired
	AccountService accountService;
	
	@Autowired
	JoinExamService joinExamService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/register")
	public String registerForm(Model model) {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute Account acc,Model model) {
		System.out.println("post register");
		acc.setRole("ROLE_STUDENT");
		acc.setEnable(true);
		accountService.registerAccount(acc);
		
		return "redirect:/profile";
	}
	
	@GetMapping("/profile")
	public String showProfile(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
		    String currentUserName = authentication.getName();
		    Account account = accountService.getAccountByUsername(currentUserName).get();
		    
		    model.addAttribute("account", account);
		    return "manage/profile";
		}
		
		
		
		return "redirect:/login";
	}
	
	@GetMapping(value= {"/history","/history/{pageNo}"})
	public String showHistory(@PathVariable(name="pageNo",required = false) Integer pageNo,Model model) {
		if(pageNo == null) pageNo = 1;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
				
		    String currentUserName = authentication.getName();
		    Account account = accountService.getAccountByUsername(currentUserName).get();
			    
			Page<JoinExamination> joinExaminationPage = joinExamService.getListByAccount(account, pageNo, MyConstances.PAGE_SIZE);
			List<JoinExamination> joinExaminationList = joinExaminationPage.getContent();
			
			model.addAttribute("joinExaminationList", joinExaminationList);
			model.addAttribute("page", joinExaminationPage);
			return "manage/history";
		}
		return "redirect:/login";
	}
	
	@PostMapping(value = "/profile/update")
	public String updateAccount(@RequestParam(name = "fullname") String fullname,
							@RequestParam(	name = "phone") String phone,
							@RequestParam(name = "email") String email,
							@RequestParam(name = "gender") String gender,
							@RequestParam(name = "dateOfBirth") String dateOfBirth){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    Account account = accountService.getAccountByUsername(currentUserName).get();
		    account.setFullname(fullname);
		    account.setPhone(phone);
		    account.setEmail(email);
		    if(gender.equals("MALE")) {
		    	account.setGender(Gender.MALE);
		    }
		    else if (gender.equals("FEMALE")) {
		    	account.setGender(Gender.FEMALE);
		    }
		    else {
		    	account.setGender(Gender.ORTHER);
		    }
		    try {
				Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
				account.setDateOfBirth(date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println(account);
		    accountService.editAccount(account);
		    return "redirect:/profile";
		}
		
		return "redirect:/login";
	}
	
	@PostMapping("/manage/changepassword")
	public String changePassword(@RequestParam(name = "oldPass") String oldPass,
			@RequestParam(name = "newPass") String newPass)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    Account account = accountService.getAccountByUsername(currentUserName).get();
		    
		    //Kiểm tra xem mật khẩu hiện tại có trùng vs mk cũ k
		    if(passwordEncoder.matches(oldPass, account.getPassword())) {
		    	account.setPassword(passwordEncoder.encode(newPass));
		    	accountService.editAccount(account);
		    	 return "redirect:/profile";
		    }
		    else {
			    return "manage/profile";
			    	
		    }
		   
		}
		return "redirect:/login";
	}
	
	@PostMapping("/manage/uploadAvatar")
	public String uploadAvatar(@RequestParam("image") MultipartFile photo) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			Account account = accountService.getAccountByUsername(currentUserName).get();
			String photoName;
			//thay đổi avatar
			if(photo!=null) {
				Path path = Paths.get("avatar-upload/");
				
				try {
					InputStream iS = photo.getInputStream();
					// Lưu lên server, Tên ảnh = tên username
					Files.copy(iS, path.resolve(account.getUsername()+".jpg"),StandardCopyOption.REPLACE_EXISTING);
					//Lưu vào db
					photoName=account.getUsername();
					account.setUrlAvatar(photoName+".jpg");
					accountService.editAccount(account);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
			return "redirect:/profile";
		}
		return "redirect:/login";
	}
	
}
