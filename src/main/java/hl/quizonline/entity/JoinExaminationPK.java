package hl.quizonline.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the joinExamination database table.
 * 
 */
@Embeddable
public class JoinExaminationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column()
	private int examinationID;

	@Column()
	private int accountID;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date timeStart;

	public JoinExaminationPK() {
	}
	public int getExaminationID() {
		return this.examinationID;
	}
	public void setExaminationID(int examinationID) {
		this.examinationID = examinationID;
	}
	public int getAccountID() {
		return this.accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public java.util.Date getTimeStart() {
		return this.timeStart;
	}
	public void setTimeStart(java.util.Date timeStart) {
		this.timeStart = timeStart;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JoinExaminationPK)) {
			return false;
		}
		JoinExaminationPK castOther = (JoinExaminationPK)other;
		return 
			(this.examinationID == castOther.examinationID)
			&& (this.accountID == castOther.accountID)
			&& this.timeStart.equals(castOther.timeStart);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.examinationID;
		hash = hash * prime + this.accountID;
		hash = hash * prime + this.timeStart.hashCode();
		
		return hash;
	}
}