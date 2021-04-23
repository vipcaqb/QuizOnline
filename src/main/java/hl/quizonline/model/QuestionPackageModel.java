package hl.quizonline.model;

public class QuestionPackageModel {
	
	private int questionPackageID;
	private String questionPackageName;
	private boolean checked;
	private int questionSize;
	public int getQuestionPackageID() {
		return questionPackageID;
	}
	public void setQuestionPackageID(int questionPackageID) {
		this.questionPackageID = questionPackageID;
	}
	public String getQuestionPackageName() {
		return questionPackageName;
	}
	public void setQuestionPackageName(String questionPackageName) {
		this.questionPackageName = questionPackageName;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public int getQuestionSize() {
		return questionSize;
	}
	public void setQuestionSize(int questionSize) {
		this.questionSize = questionSize;
	}
	public QuestionPackageModel(int questionPackageID, String questionPackageName, boolean checked) {
		super();
		this.questionPackageID = questionPackageID;
		this.questionPackageName = questionPackageName;
		this.checked = checked;
	}
	public QuestionPackageModel(int questionPackageID, String questionPackageName, boolean checked, int questionSize) {
		super();
		this.questionPackageID = questionPackageID;
		this.questionPackageName = questionPackageName;
		this.checked = checked;
		this.questionSize = questionSize;
	}
	
	
}
