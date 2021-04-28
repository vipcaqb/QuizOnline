package hl.quizonline.model;

import hl.quizonline.entity.Account;

public class LeaderboardModel {
	Account account;
	int examTimes;
	int score;
	boolean isSuccess;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public int getExamTimes() {
		return examTimes;
	}
	public void setExamTimes(int examTimes) {
		this.examTimes = examTimes;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public LeaderboardModel(Account account, int examTimes, int score, boolean isSuccess) {
		super();
		this.account = account;
		this.examTimes = examTimes;
		this.score = score;
		this.isSuccess = isSuccess;
	}
	public LeaderboardModel() {
		super();
	}
}
