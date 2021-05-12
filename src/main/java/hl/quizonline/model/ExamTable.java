package hl.quizonline.model;

import hl.quizonline.entity.ExamPackage;

public class ExamTable {
	private int id;
	private String title;
	private int size;
	private int times;
	private boolean status;
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public ExamTable(int id, String title, int size, int times, boolean status) {
		super();
		this.id = id;
		this.title = title;
		this.size = size;
		this.times = times;
		this.status = status;
	}
	
	public ExamTable(ExamPackage examPackage) {
		this.id = examPackage.getExamPackageID();
		this.title=examPackage.getExamPackageTitle();
		this.size = examPackage.getExaminations().size();
		this.times = examPackage.getDoExamTime();
		this.status = examPackage.isPublic();
	}
}
