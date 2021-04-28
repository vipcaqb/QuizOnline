package hl.quizonline.model;

import java.util.List;

public class DoExamResultModel {
	int totalOfQuestion;
	int correctQuestion;
	int score;
	List<QuestionModel> questionModelList;
	public int getTotalOfQuestion() {
		return totalOfQuestion;
	}
	public void setTotalOfQuestion(int totalOfQuestion) {
		this.totalOfQuestion = totalOfQuestion;
	}
	public int getCorrectQuestion() {
		return correctQuestion;
	}
	public void setCorrectQuestion(int correctQuestion) {
		this.correctQuestion = correctQuestion;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public List<QuestionModel> getQuestionModelList() {
		return questionModelList;
	}
	public void setQuestionModelList(List<QuestionModel> questionModelList) {
		this.questionModelList = questionModelList;
	}
}
