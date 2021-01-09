package com.fu.epayment.service;

import com.fu.epayment.domain.GeographicalData;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link GeographicalData}.
 */
public interface GeographicalDataService {

    /**
     * Save a geographicalData.
     *
     * @param geographicalData the entity to save.
     * @return the persisted entity.
     */
    GeographicalData save(GeographicalData geographicalData);

    /**
     * Get all the geographicalData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GeographicalData> findAll(Pageable pageable);


    /**
     * Get the "id" geographicalData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GeographicalData> findOne(Long id);

    /**
     * Delete the "id" geographicalData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
