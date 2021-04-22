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
	
	
}
