package hl.quizonline.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the joinExamination database table.
 * 
 */
@Entity
@Table(name="joinExamination")
@NamedQuery(name="JoinExamination.findAll", query="SELECT j FROM JoinExamination j")
public class JoinExamination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long joinExamID;
	
	@ManyToOne
	@JoinColumn(name="exam_packageid")
	private ExamPackage examPackage;

	@ManyToOne
	@JoinColumn(name="accountid")
	private Account account;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date timeFinish;


	@Column
	private int correctQuestionNumber;
	
	@Column
	private int score;
	
	@Column
	private int examTimes;

	public Long getJoinExamID() {
		return joinExamID;
	}

	public void setJoinExamID(Long joinExamID) {
		this.joinExamID = joinExamID;
	}

	public ExamPackage getExamPackage() {
		return examPackage;
	}

	public void setExamPackage(ExamPackage examPackage) {
		this.examPackage = examPackage;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public java.util.Date getTimeFinish() {
		return timeFinish;
	}

	public void setTimeFinish(java.util.Date timeFinish) {
		this.timeFinish = timeFinish;
	}

	public int getCorrectQuestionNumber() {
		return correctQuestionNumber;
	}

	public void setCorrectQuestionNumber(int correctQuestionNumber) {
		this.correctQuestionNumber = correctQuestionNumber;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getExamTimes() {
		return examTimes;
	}

	public void setExamTimes(int examTimes) {
		this.examTimes = examTimes;
	}

	@Override
	public String toString() {
		return "JoinExamination [joinExamID=" + joinExamID + ", examPackage=" + examPackage + ", account=" + account
				+ ", timeFinish=" + timeFinish + ", correctQuestionNumber=" + correctQuestionNumber + ", score=" + score
				+ ", examTimes=" + examTimes + "]";
	}
	
	

}