package com.fu.epayment.repository;

import com.fu.epayment.domain.Transaction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
}
