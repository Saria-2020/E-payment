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

import com.fu.epayment.domain.GeographicalData;
import com.fu.epayment.domain.*; // for static metamodels
import com.fu.epayment.repository.GeographicalDataRepository;
import com.fu.epayment.service.dto.GeographicalDataCriteria;

/**
 * Service for executing complex queries for {@link GeographicalData} entities in the database.
 * The main input is a {@link GeographicalDataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GeographicalData} or a {@link Page} of {@link GeographicalData} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GeographicalDataQueryService extends QueryService<GeographicalData> {

    private final Logger log = LoggerFactory.getLogger(GeographicalDataQueryService.class);

    private final GeographicalDataRepository geographicalDataRepository;

    public GeographicalDataQueryService(GeographicalDataRepository geographicalDataRepository) {
        this.geographicalDataRepository = geographicalDataRepository;
    }

    /**
     * Return a {@link List} of {@link GeographicalData} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GeographicalData> findByCriteria(GeographicalDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GeographicalData> specification = createSpecification(criteria);
        return geographicalDataRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GeographicalData} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GeographicalData> findByCriteria(GeographicalDataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GeographicalData> specification = createSpecification(criteria);
        return geographicalDataRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GeographicalDataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GeographicalData> specification = createSpecification(criteria);
        return geographicalDataRepository.count(specification);
    }

    /**
     * Function to convert {@link GeographicalDataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GeographicalData> createSpecification(GeographicalDataCriteria criteria) {
        Specification<GeographicalData> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GeographicalData_.id));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), GeographicalData_.state));
            }
            if (criteria.getUnits() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnits(), GeographicalData_.units));
            }
            if (criteria.getDistrict() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistrict(), GeographicalData_.district));
            }
            if (criteria.getSquare() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSquare(), GeographicalData_.square));
            }
            if (criteria.getRealEstateNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRealEstateNumber(), GeographicalData_.realEstateNumber));
            }
            if (criteria.getActivityNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivityNumber(), GeographicalData_.activityNumber));
            }
            if (criteria.getAreaOfTheRealEstate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaOfTheRealEstate(), GeographicalData_.areaOfTheRealEstate));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(GeographicalData_.customer, JoinType.LEFT).get(Customer_.id)));
            }
        }
        return specification;
    }
}
