package hl.quizonline.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the examPackage database table.
 * 
 */
@Entity
@Table(name="examPackage")
@NamedQuery(name="ExamPackage.findAll", query="SELECT e FROM ExamPackage e")
public class ExamPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int examPackageID;

	@Column
	@Nationalized
	private String examPackageTitle;
	
	@Column
	private boolean mixQuestion;
	
	@Column 
	private int joinAmount;
	
	@Column
	private boolean usePassword;
	
	@Column
	private String password;
	
	@Column
	private boolean isPublic;
	
	@Column
	private boolean isExerciseExam;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startDatetime;
	
	@Column
	private int doExamTime;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="accountID")
	private Account account;

	//bi-directional many-to-many association to Category
	@ManyToMany
	@JoinTable(
		name="examPackage_category"
		, joinColumns={
			@JoinColumn(name="examPackageID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="categoryID")
			}
		)
	private List<Category> categories;

	//bi-directional many-to-one association to Examination
	@OneToMany(mappedBy="examPackage")
	private List<Examination> examinations;

	public ExamPackage() {
	}

	public int getExamPackageID() {
		return this.examPackageID;
	}

	public void setExamPackageID(int examPackageID) {
		this.examPackageID = examPackageID;
	}

	public String getExamPackageTitle() {
		return this.examPackageTitle;
	}

	public void setExamPackageTitle(String examPackageTitle) {
		this.examPackageTitle = examPackageTitle;
	}
	
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Examination> getExaminations() {
		return this.examinations;
	}

	public boolean isExerciseExam() {
		return isExerciseExam;
	}

	public void setExerciseExam(boolean isExerciseExam) {
		this.isExerciseExam = isExerciseExam;
	}

	public void setExaminations(List<Examination> examinations) {
		this.examinations = examinations;
	}
	
	public boolean isMixQuestion() {
		return mixQuestion;
	}

	public void setMixQuestion(boolean mixQuestion) {
		this.mixQuestion = mixQuestion;
	}

	public int getJoinAmount() {
		return joinAmount;
	}

	public void setJoinAmount(int joinAmount) {
		this.joinAmount = joinAmount;
	}

	public boolean isUsePassword() {
		return usePassword;
	}

	public void setUsePassword(boolean usePassword) {
		this.usePassword = usePassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Date getStartDatetime() {
		return startDatetime;
	}

	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}

	public int getDoExamTime() {
		return doExamTime;
	}

	public void setDoExamTime(int doExamTime) {
		this.doExamTime = doExamTime;
	}
	
	

	@Override
	public String toString() {
		return "ExamPackage [examPackageTitle=" + examPackageTitle + ", mixQuestion=" + mixQuestion + ", usePassword="
				+ usePassword + ", password=" + password + ", isExerciseExam=" + isExerciseExam + ", startDatetime="
				+ startDatetime + ", doExamTime=" + doExamTime + ", account=" + account + "]";
	}

	public Examination addExamination(Examination examination) {
		getExaminations().add(examination);
		examination.setExamPackage(this);

		return examination;
	}

	public Examination removeExamination(Examination examination) {
		getExaminations().remove(examination);
		examination.setExamPackage(null);

		return examination;
	}

	public ExamPackage(String examPackageTitle) {
		super();
		this.examPackageTitle = examPackageTitle;
	}

	public ExamPackage(String examPackageTitle, Account account) {
		super();
		this.examPackageTitle = examPackageTitle;
		this.account = account;
	}
	
	
	
}