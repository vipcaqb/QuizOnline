package hl.quizonline.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import hl.quizonline.enumrable.Gender;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Account database table.
 * 
 */
@Entity
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int accountID;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	
	@Column
	@Nationalized
	@NotBlank()
	private String fullname;

	@Column
	private String password;

	@Column
	private Gender gender;
	
	@Column
	private String urlAvatar;

	@Column(unique = true)
	private String username;
	
	@Column
	private String phone;
	
	@Column
	private String email;
	
	@Column
	private Boolean enable;


	@JoinColumn(name="role")
	private String role;

	//bi-directional many-to-one association to ExamPackage
	@OneToMany(mappedBy="account")
	private List<ExamPackage> examPackages;

	//bi-directional many-to-one association to JoinExamination
	@OneToMany(mappedBy="account")
	private List<JoinExamination> joinExaminations;

	public Account() {
	}

	public Account(int accountID, Date dateOfBirth, @NotBlank String fullname, String password, Gender gender,
			String urlAvatar, String username, Boolean enable, String role, List<ExamPackage> examPackages,
			List<JoinExamination> joinExaminations) {
		super();
		this.accountID = accountID;
		this.dateOfBirth = dateOfBirth;
		this.fullname = fullname;
		this.password = password;
		this.gender = gender;
		this.urlAvatar = urlAvatar;
		this.username = username;
		this.enable = enable;
		this.role = role;
		this.examPackages = examPackages;
		this.joinExaminations = joinExaminations;
	}

	public Gender getGender() {
		return gender;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getAccountID() {
		return this.accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrlAvatar() {
		return this.urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ExamPackage> getExamPackages() {
		return this.examPackages;
	}

	public void setExamPackages(List<ExamPackage> examPackages) {
		this.examPackages = examPackages;
	}

	public ExamPackage addExamPackage(ExamPackage examPackage) {
		getExamPackages().add(examPackage);
		examPackage.setAccount(this);

		return examPackage;
	}

	public ExamPackage removeExamPackage(ExamPackage examPackage) {
		getExamPackages().remove(examPackage);
		examPackage.setAccount(null);

		return examPackage;
	}

	public List<JoinExamination> getJoinExaminations() {
		return this.joinExaminations;
	}

	public void setJoinExaminations(List<JoinExamination> joinExaminations) {
		this.joinExaminations = joinExaminations;
	}

	public JoinExamination addJoinExamination(JoinExamination joinExamination) {
		getJoinExaminations().add(joinExamination);
		joinExamination.setAccount(this);

		return joinExamination;
	}

	public JoinExamination removeJoinExamination(JoinExamination joinExamination) {
		getJoinExaminations().remove(joinExamination);
		joinExamination.setAccount(null);

		return joinExamination;
	}

	@Override
	public String toString() {
		return "Account [dateOfBirth=" + dateOfBirth + ", fullname=" + fullname + ", password=" + password + ", gender="
				+ gender + ", urlAvatar=" + urlAvatar + ", username=" + username + ", enable=" + enable + ", role="
				+ role + "]";
	}

	

}