package hl.quizonline.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

@Entity
@Table(name="question_package")
public class QuestionPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "question_packageid")
	private int questionPackageID;
	
	@Column
	@Nationalized
	private String name;
	
	@OneToMany(mappedBy = "questionPackage")
	private List<Question> questions;
	
	@OneToMany(mappedBy = "questionPackage")
	private List<ExamQuestion> examQuestions;
	
	@ManyToOne
	@JoinColumn(name = "accountid")
	private Account account;

	public int getQuestionPackageID() {
		return questionPackageID;
	}

	public void setQuestionPackageID(int questionPackageID) {
		this.questionPackageID = questionPackageID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<ExamQuestion> getExamQuestions() {
		return examQuestions;
	}

	public void setExamQuestions(List<ExamQuestion> examQuestions) {
		this.examQuestions = examQuestions;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
	
}
