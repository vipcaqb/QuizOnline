package hl.quizonline.model;

import java.util.List;

public class QuestionModel {
	String questionContent;
	List<String> userAnswerList;
	boolean isCorrect;
	List<String> correctAnswerList;
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	public List<String> getUserAnswerList() {
		return userAnswerList;
	}
	public void setUserAnswerList(List<String> userAnswerList) {
		this.userAnswerList = userAnswerList;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	public List<String> getCorrectAnswerList() {
		return correctAnswerList;
	}
	public void setCorrectAnswerList(List<String> correctAnswerList) {
		this.correctAnswerList = correctAnswerList;
	}
	
	
}
