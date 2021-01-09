package com.fu.epayment.service;

import com.fu.epayment.domain.ActivityInformation;
import com.fu.epayment.repository.ActivityInformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ActivityInformation}.
 */
@Service
@Transactional
public class ActivityInformationService {

    private final Logger log = LoggerFactory.getLogger(ActivityInformationService.class);

    private final ActivityInformationRepository activityInformationRepository;

    public ActivityInformationService(ActivityInformationRepository activityInformationRepository) {
        this.activityInformationRepository = activityInformationRepository;
    }

    /**
     * Save a activityInformation.
     *
     * @param activityInformation the entity to save.
     * @return the persisted entity.
     */
    public ActivityInformation save(ActivityInformation activityInformation) {
        log.debug("Request to save ActivityInformation : {}", activityInformation);
        return activityInformationRepository.save(activityInformation);
    }

    /**
     * Get all the activityInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivityInformation> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityInformations");
        return activityInformationRepository.findAll(pageable);
    }


    /**
     * Get one activityInformation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActivityInformation> findOne(Long id) {
        log.debug("Request to get ActivityInformation : {}", id);
        return activityInformationRepository.findById(id);
    }

    /**
     * Delete the activityInformation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ActivityInformation : {}", id);
        activityInformationRepository.deleteById(id);
    }
}
