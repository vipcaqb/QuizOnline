package hl.quizonline.model;

import hl.quizonline.entity.Account;

public class ExamDonation {
	private String username;
	private String fullname;
	private String urlAvatar;
	private long numberOfExam;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public long getNumberOfExam() {
		return numberOfExam;
	}

	public void setNumberOfExam(long numberOfExam) {
		this.numberOfExam = numberOfExam;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}

	public ExamDonation(String username, String fullname, long numberOfExam) {
		super();
		this.username = username;
		this.fullname = fullname;
		this.numberOfExam = numberOfExam;
	}
	

	public ExamDonation(String username, String fullname, String urlAvatar, long numberOfExam) {
		super();
		this.username = username;
		this.fullname = fullname;
		this.urlAvatar = urlAvatar;
		this.numberOfExam = numberOfExam;
	}

	public ExamDonation() {
		super();
		// TODO Auto-generated constructor stub
	}

}
