package hl.quizonline.model;

import hl.quizonline.entity.QuestionPackage;

public class QuestionTable {
	private int id;
	private String title;
	private int size;
	private int times;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public QuestionTable(int id, String title, int size, int times) {
		super();
		this.id = id;
		this.title = title;
		this.size = size;
		this.times = times;
	}
	
	public QuestionTable(QuestionPackage questionPackage) {
		this.id = questionPackage.getQuestionPackageID();
		this.title = questionPackage.getName();
		this.size = questionPackage.getQuestions().size();
		this.times = questionPackage.getExamQuestions().size();
	}
	
}
