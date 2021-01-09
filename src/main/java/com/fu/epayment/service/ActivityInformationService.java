package com.fu.epayment.service;

import com.fu.epayment.domain.ActivityInformation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ActivityInformation}.
 */
public interface ActivityInformationService {

    /**
     * Save a activityInformation.
     *
     * @param activityInformation the entity to save.
     * @return the persisted entity.
     */
    ActivityInformation save(ActivityInformation activityInformation);

    /**
     * Get all the activityInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivityInformation> findAll(Pageable pageable);


    /**
     * Get the "id" activityInformation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActivityInformation> findOne(Long id);

    /**
     * Delete the "id" activityInformation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
