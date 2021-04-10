package hl.quizonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hl.quizonline.entity.ExamPackage;
import hl.quizonline.service.ExamPackageService;

@Controller
@RequestMapping("/exam")
public class ExamPackageController {
	@Autowired
	ExamPackageService examPackageService;
	
	@GetMapping()
	@ResponseBody
	public String listExam() {
		return "List of exam package";
	}
}
