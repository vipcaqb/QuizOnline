package hl.quizonline.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Nationalized;

import java.util.ArrayList;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The persistent class for the question database table.
 * 
 */
@Entity
@Table(name="question")
@NamedQuery(name="Question.findAll", query="SELECT q FROM Question q")
public class Question implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The question ID. */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int questionID;

	/** The question content. */
	@Column(length = 1000)
	@Nationalized
	private String questionContent;

	/** The answers. */
	//bi-directional many-to-one association to Answer
	@OneToMany(mappedBy="question")
	private List<Answer> answers;

	/** The images. */
	//bi-directional many-to-one association to Image
	@OneToMany(mappedBy="question")
	private List<Image> images;

	/** The question package. */
	//bi-directional many-to-one association to Examination
	@ManyToOne
	@JoinColumn(name="question_packageid")
	private QuestionPackage questionPackage;

	/**
	 * Instantiates a new question.
	 */
	public Question() {
	}

	/**
	 * Gets the question ID.
	 *
	 * @return the question ID
	 */
	public int getQuestionID() {
		return this.questionID;
	}

	/**
	 * Sets the question ID.
	 *
	 * @param questionID the new question ID
	 */
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	/**
	 * Gets the question content.
	 *
	 * @return the question content
	 */
	public String getQuestionContent() {
		return this.questionContent;
	}

	/**
	 * Sets the question content.
	 *
	 * @param questionContent the new question content
	 */
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	/**
	 * Gets the answers.
	 *
	 * @return the answers
	 */
	public List<Answer> getAnswers() {
		return this.answers;
	}

	/**
	 * Sets the answers.
	 *
	 * @param answers the new answers
	 */
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	/**
	 * Adds the answer.
	 *
	 * @param answer the answer
	 * @return the answer
	 */
	public Answer addAnswer(Answer answer) {
		getAnswers().add(answer);
		answer.setQuestion(this);

		return answer;
	}
	
	

	/**
	 * Gets the question package.
	 *
	 * @return the question package
	 */
	public QuestionPackage getQuestionPackage() {
		return questionPackage;
	}

	/**
	 * Sets the question package.
	 *
	 * @param questionPackage the new question package
	 */
	public void setQuestionPackage(QuestionPackage questionPackage) {
		this.questionPackage = questionPackage;
	}

	/**
	 * Removes the answer.
	 *
	 * @param answer the answer
	 * @return the answer
	 */
	public Answer removeAnswer(Answer answer) {
		getAnswers().remove(answer);
		answer.setQuestion(null);

		return answer;
	}

	/**
	 * Gets the images.
	 *
	 * @return the images
	 */
	public List<Image> getImages() {
		return this.images;
	}

	/**
	 * Sets the images.
	 *
	 * @param images the new images
	 */
	public void setImages(List<Image> images) {
		this.images = images;
	}

	/**
	 * Adds the image.
	 *
	 * @param image the image
	 * @return the image
	 */
	public Image addImage(Image image) {
		getImages().add(image);
		image.setQuestion(this);

		return image;
	}

	/**
	 * Removes the image.
	 *
	 * @param image the image
	 * @return the image
	 */
	public Image removeImage(Image image) {
		getImages().remove(image);
		image.setQuestion(null);

		return image;
	}
	
	/**
	 * Count the number of correct answer.
	 *
	 * @return the int
	 */
	public int numberOfCorrect() {
		List<Answer> answerList = this.getAnswers();
		int count = 0;
		for(int i =0;i<answerList.size();i++) {
			if(answerList.get(i).getIdCorrect()) {
				count++;
			}
		}
		return count;
	}

	public List<Answer> getCorrectAnswers(){
		List<Answer> correctAnswerList = new ArrayList<Answer>();
		for(int i =0;i<this.getAnswers().size();i++) {
			if(this.getAnswers().get(i).getIdCorrect()) {
				correctAnswerList.add(this.getAnswers().get(i));
			}
		}
		return correctAnswerList;
	}

	@Override
	public String toString() {
		return "Question [questionID=" + questionID + ", questionContent=" + questionContent + ", answers=" + answers
				+ "]";
	}

}