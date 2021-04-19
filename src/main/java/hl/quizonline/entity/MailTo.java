package hl.quizonline.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MailTo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "mail_toid")
	private int mailToID;
	
	@ManyToOne
	@JoinColumn(name = "accountid",updatable = false,insertable = false)
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "mail_boxid",updatable = false,insertable = false)
	private MailBox mailBox;
	
	@Column
	private boolean seen;
	
	@Column 
	private boolean deleted;

	public int getMailToID() {
		return mailToID;
	}

	public void setMailToID(int mailToID) {
		this.mailToID = mailToID;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public MailBox getMailBox() {
		return mailBox;
	}

	public void setMailBox(MailBox mailBox) {
		this.mailBox = mailBox;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public MailTo(int mailToID, Account account, MailBox mailBox, boolean seen, boolean deleted) {
		super();
		this.mailToID = mailToID;
		this.account = account;
		this.mailBox = mailBox;
		this.seen = seen;
		this.deleted = deleted;
	}

	public MailTo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
