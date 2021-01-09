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

import com.fu.epayment.domain.ActivityInformation;
import com.fu.epayment.domain.*; // for static metamodels
import com.fu.epayment.repository.ActivityInformationRepository;
import com.fu.epayment.service.dto.ActivityInformationCriteria;

/**
 * Service for executing complex queries for {@link ActivityInformation} entities in the database.
 * The main input is a {@link ActivityInformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ActivityInformation} or a {@link Page} of {@link ActivityInformation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActivityInformationQueryService extends QueryService<ActivityInformation> {

    private final Logger log = LoggerFactory.getLogger(ActivityInformationQueryService.class);

    private final ActivityInformationRepository activityInformationRepository;

    public ActivityInformationQueryService(ActivityInformationRepository activityInformationRepository) {
        this.activityInformationRepository = activityInformationRepository;
    }

    /**
     * Return a {@link List} of {@link ActivityInformation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ActivityInformation> findByCriteria(ActivityInformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ActivityInformation> specification = createSpecification(criteria);
        return activityInformationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ActivityInformation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivityInformation> findByCriteria(ActivityInformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ActivityInformation> specification = createSpecification(criteria);
        return activityInformationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ActivityInformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ActivityInformation> specification = createSpecification(criteria);
        return activityInformationRepository.count(specification);
    }

    /**
     * Function to convert {@link ActivityInformationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ActivityInformation> createSpecification(ActivityInformationCriteria criteria) {
        Specification<ActivityInformation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ActivityInformation_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ActivityInformation_.name));
            }
            if (criteria.getSector() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSector(), ActivityInformation_.sector));
            }
            if (criteria.getTypeOfActivity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTypeOfActivity(), ActivityInformation_.typeOfActivity));
            }
            if (criteria.getPropertyType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPropertyType(), ActivityInformation_.propertyType));
            }
            if (criteria.getAreaClass() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaClass(), ActivityInformation_.areaClass));
            }
            if (criteria.getArchitectureType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArchitectureType(), ActivityInformation_.architectureType));
            }
            if (criteria.getNumberOfFloors() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumberOfFloors(), ActivityInformation_.numberOfFloors));
            }
            if (criteria.getFeatures() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeatures(), ActivityInformation_.features));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(),
                    root -> root.join(ActivityInformation_.category, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(ActivityInformation_.customer, JoinType.LEFT).get(Customer_.id)));
            }
        }
        return specification;
    }
}
