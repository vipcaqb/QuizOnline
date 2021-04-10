package hl.quizonline.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import hl.quizonline.entity.ExamPackage;
import hl.quizonline.service.ExamPackageService;

@Service
public class ExamPackageImpl implements ExamPackageService {

	@Override
	public List<ExamPackage> getList() {
		ExamPackage eP1 = new ExamPackage();
		ExamPackage eP2 = new ExamPackage();
		ExamPackage eP3 = new ExamPackage();
		ExamPackage eP4 = new ExamPackage();
		eP1.setExamPackageTitle("exam 1");
		eP2.setExamPackageTitle("exam 2");
		eP3.setExamPackageTitle("exam 3");
		eP4.setExamPackageTitle("exam 4");
		List<ExamPackage> list = Arrays.asList(eP1,eP2,eP3,eP4);
		return list;
	}
}
