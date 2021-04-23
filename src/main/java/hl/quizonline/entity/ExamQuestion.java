package hl.quizonline.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="exam_question")
public class ExamQuestion implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int examQuestionID;
	
	@ManyToOne
	@JoinColumn(name = "examinationid",updatable = false,insertable = false)
	private Examination examination;
	
	@ManyToOne
	@JoinColumn(name = "question_packageid",updatable = false,insertable = false)
	private QuestionPackage questionPackage;

	public int getExamQuestionID() {
		return examQuestionID;
	}

	public void setExamQuestionID(int examQuestionID) {
		this.examQuestionID = examQuestionID;
	}

	public Examination getExamination() {
		return examination;
	}

	public void setExamination(Examination examination) {
		this.examination = examination;
	}

	public QuestionPackage getQuestionPackage() {
		return questionPackage;
	}

	public void setQuestionPackage(QuestionPackage questionPackage) {
		this.questionPackage = questionPackage;
	}
	
	
	
}
