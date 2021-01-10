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

import com.fu.epayment.domain.PaymentInfo;
import com.fu.epayment.domain.*; // for static metamodels
import com.fu.epayment.repository.PaymentInfoRepository;
import com.fu.epayment.service.dto.PaymentInfoCriteria;

/**
 * Service for executing complex queries for {@link PaymentInfo} entities in the database.
 * The main input is a {@link PaymentInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentInfo} or a {@link Page} of {@link PaymentInfo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentInfoQueryService extends QueryService<PaymentInfo> {

    private final Logger log = LoggerFactory.getLogger(PaymentInfoQueryService.class);

    private final PaymentInfoRepository paymentInfoRepository;

    public PaymentInfoQueryService(PaymentInfoRepository paymentInfoRepository) {
        this.paymentInfoRepository = paymentInfoRepository;
    }

    /**
     * Return a {@link List} of {@link PaymentInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentInfo> findByCriteria(PaymentInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentInfo> specification = createSpecification(criteria);
        return paymentInfoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PaymentInfo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentInfo> findByCriteria(PaymentInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentInfo> specification = createSpecification(criteria);
        return paymentInfoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentInfo> specification = createSpecification(criteria);
        return paymentInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentInfo> createSpecification(PaymentInfoCriteria criteria) {
        Specification<PaymentInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentInfo_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PaymentInfo_.name));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), PaymentInfo_.accountNumber));
            }
            if (criteria.getCardNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCardNumber(), PaymentInfo_.cardNumber));
            }
            if (criteria.getBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBalance(), PaymentInfo_.balance));
            }
            if (criteria.getTransactionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionsId(),
                    root -> root.join(PaymentInfo_.transactions, JoinType.LEFT).get(Transaction_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(PaymentInfo_.customer, JoinType.LEFT).get(Customer_.id)));
            }
        }
        return specification;
    }
}
