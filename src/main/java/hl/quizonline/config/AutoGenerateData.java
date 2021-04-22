package hl.quizonline.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import hl.quizonline.entity.Category;
import hl.quizonline.service.CategoryService;

@Component
public class AutoGenerateData implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	CategoryService categoryService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//generate categories
		System.out.println("creating categories");
		
		if(!categoryService.exist("Khác")) {
			Category c = new Category("Khác");
			categoryService.create(c);
		}
		if(!categoryService.exist("Toán")) {
			Category c = new Category("Toán");
			categoryService.create(c);
		}
		if(!categoryService.exist("Văn")) {
			Category c = new Category("Văn");
			categoryService.create(c);
		}
		if(!categoryService.exist("Lịch sử")) {
			Category c = new Category("Lịch sử");
			categoryService.create(c);
		}
		
		
		System.out.println("create category successful!");
	}

}