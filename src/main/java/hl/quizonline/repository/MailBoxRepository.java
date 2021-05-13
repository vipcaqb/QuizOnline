package hl.quizonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.MailBox;

@Repository
public interface MailBoxRepository extends JpaRepository<MailBox, Integer> {
	Page<MailBox> findByAccountAndDeleted(Account account, boolean deleted, Pageable pageable);
	
}
