package hl.quizonline.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="mailbox")
public class MailBox implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int mailBoxID;
	
	@Column
	@Nationalized
	private String title;
	
	@Column(length = 1000)
	@Nationalized
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sendDate;
	
	@Column
	private boolean deleted;
	
	@ManyToOne
	@JoinColumn(name = "accountid")
	private Account account;
	
	@OneToMany(mappedBy = "mailBox")
	private List<MailTo> mailTos;

	public int getMailBoxID() {
		return mailBoxID;
	}

	public void setMailBoxID(int mailBoxID) {
		this.mailBoxID = mailBoxID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<MailTo> getMailTos() {
		return mailTos;
	}

	public void setMailTos(List<MailTo> mailTos) {
		this.mailTos = mailTos;
	}

	public MailBox(int mailBoxID, String title, String content, Date sendDate, Account account) {
		super();
		this.mailBoxID = mailBoxID;
		this.title = title;
		this.content = content;
		this.sendDate = sendDate;
		this.account = account;
	}

	public MailBox() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MailBox [mailBoxID=" + mailBoxID + ", title=" + title + ", content=" + content + ", sendDate="
				+ sendDate + ", account=" + account + "]";
	}
}
