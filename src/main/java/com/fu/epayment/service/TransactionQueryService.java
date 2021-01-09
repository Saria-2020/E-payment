package com.fu.epayment.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.fu.epayment.domain.Transaction;
import com.fu.epayment.domain.*; // for static metamodels
import com.fu.epayment.repository.TransactionRepository;
import com.fu.epayment.service.dto.TransactionCriteria;

/**
 * Service for executing complex queries for {@link Transaction} entities in the database.
 * The main input is a {@link TransactionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Transaction} or a {@link Page} of {@link Transaction} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionQueryService extends QueryService<Transaction> {

    private final Logger log = LoggerFactory.getLogger(TransactionQueryService.class);

    private final TransactionRepository transactionRepository;

    public TransactionQueryService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Return a {@link List} of {@link Transaction} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Transaction> findByCriteria(TransactionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Transaction> specification = createSpecification(criteria);
        return transactionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Transaction} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Transaction> findByCriteria(TransactionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Transaction> specification = createSpecification(criteria);
        return transactionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Transaction> specification = createSpecification(criteria);
        return transactionRepository.count(specification);
    }

    /**
     * Function to convert {@link TransactionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Transaction> createSpecification(TransactionCriteria criteria) {
        Specification<Transaction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Transaction_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Transaction_.name));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUuid(), Transaction_.uuid));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Transaction_.amount));
            }
            if (criteria.getDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTime(), Transaction_.dateTime));
            }
            if (criteria.getPaymentInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentInfo(), Transaction_.paymentInfo));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceId(),
                    root -> root.join(Transaction_.invoice, JoinType.LEFT).get(Invoice_.id)));
            }
        }
        return specification;
    }
}
