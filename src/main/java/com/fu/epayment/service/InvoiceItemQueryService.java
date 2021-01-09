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

import com.fu.epayment.domain.InvoiceItem;
import com.fu.epayment.domain.*; // for static metamodels
import com.fu.epayment.repository.InvoiceItemRepository;
import com.fu.epayment.service.dto.InvoiceItemCriteria;

/**
 * Service for executing complex queries for {@link InvoiceItem} entities in the database.
 * The main input is a {@link InvoiceItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvoiceItem} or a {@link Page} of {@link InvoiceItem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceItemQueryService extends QueryService<InvoiceItem> {

    private final Logger log = LoggerFactory.getLogger(InvoiceItemQueryService.class);

    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemQueryService(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    /**
     * Return a {@link List} of {@link InvoiceItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvoiceItem> findByCriteria(InvoiceItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InvoiceItem> specification = createSpecification(criteria);
        return invoiceItemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InvoiceItem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceItem> findByCriteria(InvoiceItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InvoiceItem> specification = createSpecification(criteria);
        return invoiceItemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InvoiceItem> specification = createSpecification(criteria);
        return invoiceItemRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoiceItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InvoiceItem> createSpecification(InvoiceItemCriteria criteria) {
        Specification<InvoiceItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InvoiceItem_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), InvoiceItem_.name));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), InvoiceItem_.amount));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceId(),
                    root -> root.join(InvoiceItem_.invoice, JoinType.LEFT).get(Invoice_.id)));
            }
        }
        return specification;
    }
}
