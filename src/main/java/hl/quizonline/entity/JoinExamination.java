package hl.quizonline.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the joinExamination database table.
 * 
 */
@Entity
@Table(name="joinExamination")
@NamedQuery(name="JoinExamination.findAll", query="SELECT j FROM JoinExamination j")
public class JoinExamination implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private JoinExaminationPK id;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeFinish;

	//bi-directional many-to-one association to Account
	@ManyToOne
	@JoinColumn(name="accountID",insertable=false, updatable=false)
	private Account account;

	//bi-directional many-to-one association to Examination
	@ManyToOne
	@JoinColumn(name="examinationID",insertable=false, updatable=false)
	private Examination examination;

	public JoinExamination() {
	}

	public JoinExaminationPK getId() {
		return this.id;
	}

	public void setId(JoinExaminationPK id) {
		this.id = id;
	}

	public Date getTimeFinish() {
		return this.timeFinish;
	}

	public void setTimeFinish(Date timeFinish) {
		this.timeFinish = timeFinish;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Examination getExamination() {
		return this.examination;
	}

	public void setExamination(Examination examination) {
		this.examination = examination;
	}

}