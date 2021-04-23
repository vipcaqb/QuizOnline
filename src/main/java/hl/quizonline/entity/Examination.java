package hl.quizonline.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Nationalized;

import java.util.List;


/**
 * The persistent class for the examination database table.
 * 
 */
@Entity
@Table(name="examination")
@NamedQuery(name="Examination.findAll", query="SELECT e FROM Examination e")
public class Examination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int examinationID;

	@Column
	@Nationalized
	private String examinationTitle;

	//bi-directional many-to-one association to ExamPackage
	@ManyToOne
	@JoinColumn(name="examPackageID")
	private ExamPackage examPackage;

	//bi-directional many-to-one association to JoinExamination
	@OneToMany(mappedBy="examination")
	private List<JoinExamination> joinExaminations;

	//bi-directional many-to-one association to Question
	@OneToMany(mappedBy="examination")
	private List<ExamQuestion> examQuestions;

	public Examination() {
	}

	public int getExaminationID() {
		return this.examinationID;
	}

	public void setExaminationID(int examinationID) {
		this.examinationID = examinationID;
	}

	public String getExaminationTitle() {
		return this.examinationTitle;
	}

	public void setExaminationTitle(String examinationTitle) {
		this.examinationTitle = examinationTitle;
	}

	public ExamPackage getExamPackage() {
		return this.examPackage;
	}

	public void setExamPackage(ExamPackage examPackage) {
		this.examPackage = examPackage;
	}

	public List<JoinExamination> getJoinExaminations() {
		return this.joinExaminations;
	}

	public void setJoinExaminations(List<JoinExamination> joinExaminations) {
		this.joinExaminations = joinExaminations;
	}
	
	

	public List<ExamQuestion> getExamQuestions() {
		return examQuestions;
	}

	public void setExamQuestions(List<ExamQuestion> examQuestions) {
		this.examQuestions = examQuestions;
	}

	public JoinExamination addJoinExamination(JoinExamination joinExamination) {
		getJoinExaminations().add(joinExamination);
		joinExamination.setExamination(this);

		return joinExamination;
	}

	public JoinExamination removeJoinExamination(JoinExamination joinExamination) {
		getJoinExaminations().remove(joinExamination);
		joinExamination.setExamination(null);

		return joinExamination;
	}

}