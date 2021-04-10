package hl.quizonline.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Nationalized;

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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int examPackageID;

	@Column
	@Nationalized
	private String examPackageTitle;

	@Column
	private String urlThumnail;

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

	public String getUrlThumnail() {
		return this.urlThumnail;
	}

	public void setUrlThumnail(String urlThumnail) {
		this.urlThumnail = urlThumnail;
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

	public void setExaminations(List<Examination> examinations) {
		this.examinations = examinations;
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

}