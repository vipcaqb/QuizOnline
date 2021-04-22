package hl.quizonline.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Nationalized;


/**
 * The persistent class for the answer database table.
 * 
 */
@Entity
@Table(name="answer")
@NamedQuery(name="Answer.findAll", query="SELECT a FROM Answer a")
public class Answer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int answerID;

	@Column(length = 1000)
	@Nationalized
	private String answerContent;

	@Column
	private boolean idCorrect;

	//bi-directional many-to-one association to Question
	@ManyToOne
	@JoinColumn(name="questionID")
	private Question question;

	public Answer() {
	}

	public int getAnswerID() {
		return this.answerID;
	}

	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}

	public String getAnswerContent() {
		return this.answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public boolean getIdCorrect() {
		return this.idCorrect;
	}

	public void setIdCorrect(boolean idCorrect) {
		this.idCorrect = idCorrect;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer(String answerContent, boolean idCorrect, Question question) {
		super();
		this.answerContent = answerContent;
		this.idCorrect = idCorrect;
		this.question = question;
	}
	
	

}