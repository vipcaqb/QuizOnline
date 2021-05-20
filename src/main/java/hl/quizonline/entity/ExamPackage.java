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
	
	@Column(length = 1000)
	@Nationalized
	private String description;
	
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
	
	@Column
	private int numberOfQuestion;
	
	@Column
	private boolean showResults;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date createDatetime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date startDatetime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date endDatetime;
	
	@Column
	private int doExamTime;
	
	@Column(columnDefinition = "integer default 0")
	private int views;
	
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
	
	//bi-directional many-to-one association to joinExaminations
	@OneToMany(mappedBy="examPackage")
	private List<JoinExamination> joinExaminations;

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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndDatetime() {
		return endDatetime;
	}

	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = endDatetime;
	}

	public Account getAccount() {
		return this.account;
	}

	public int getNumberOfQuestion() {
		return numberOfQuestion;
	}

	public void setNumberOfQuestion(int numberOfQuestion) {
		this.numberOfQuestion = numberOfQuestion;
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

	
	
	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
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

	public boolean isShowResults() {
		return showResults;
	}

	public void setShowResults(boolean showResults) {
		this.showResults = showResults;
	}
	
	

	public List<JoinExamination> getJoinExaminations() {
		return joinExaminations;
	}

	public void setJoinExaminations(List<JoinExamination> joinExaminations) {
		this.joinExaminations = joinExaminations;
	}

	public ExamPackage(int examPackageID) {
		super();
		this.examPackageID = examPackageID;
	}

	@Override
	public String toString() {
		return "ExamPackage [examPackageID=" + examPackageID + ", examPackageTitle=" + examPackageTitle
				+ ", mixQuestion=" + mixQuestion + ", joinAmount=" + joinAmount + ", usePassword=" + usePassword
				+ ", password=" + password + ", isPublic=" + isPublic + ", isExerciseExam=" + isExerciseExam
				+ ", showResults=" + showResults + ", startDatetime=" + startDatetime + ", doExamTime=" + doExamTime
				+ ", account=" + account + ", categories=" + categories + ", examinations=" + examinations + "]";
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
	
	

	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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