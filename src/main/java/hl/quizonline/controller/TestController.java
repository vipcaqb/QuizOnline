package hl.quizonline.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hl.quizonline.entity.Account;

@Controller
@PropertySource("classpath:message.properties")
@RequestMapping("/test")
public class TestController {
	@Autowired
	Environment env;
	@GetMapping("")
	public String test() {
		return "admin/admin-base";
	}
}
