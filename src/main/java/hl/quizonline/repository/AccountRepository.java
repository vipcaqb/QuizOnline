package hl.quizonline.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Account;
import hl.quizonline.model.ExamDonation;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	Optional<Account> findByUsername(String username);
	Optional<Account> findByUsernameAndPassword(String username, String password);
	
}