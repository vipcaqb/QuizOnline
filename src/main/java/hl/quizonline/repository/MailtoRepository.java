package hl.quizonline.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.MailBox;
import hl.quizonline.entity.MailTo;

@Repository
public interface MailtoRepository extends JpaRepository<MailTo, Integer> {
	
	Page<MailTo> findByAccountAndDeleted(Account account, boolean deleted, Pageable pageable);
	
	@Query("SELECT mt FROM MailTo mt INNER JOIN mt.mailBox mb "
			+ "WHERE mt.account = ?1 AND mt.deleted = ?2 ORDER BY mb.sendDate DESC")
	Page<MailTo> findByAccountAndDeletedAndSort(Account account, boolean deleted, Pageable pageable);
	
	List<MailTo> findByAccountAndMailBox(Account account , MailBox mailbox);
}
