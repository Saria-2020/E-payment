package com.fu.epayment.service;

import com.fu.epayment.domain.GeographicalData;
import com.fu.epayment.repository.GeographicalDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GeographicalData}.
 */
@Service
@Transactional
public class GeographicalDataService {

    private final Logger log = LoggerFactory.getLogger(GeographicalDataService.class);

    private final GeographicalDataRepository geographicalDataRepository;

    public GeographicalDataService(GeographicalDataRepository geographicalDataRepository) {
        this.geographicalDataRepository = geographicalDataRepository;
    }

    /**
     * Save a geographicalData.
     *
     * @param geographicalData the entity to save.
     * @return the persisted entity.
     */
    public GeographicalData save(GeographicalData geographicalData) {
        log.debug("Request to save GeographicalData : {}", geographicalData);
        return geographicalDataRepository.save(geographicalData);
    }

    /**
     * Get all the geographicalData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GeographicalData> findAll(Pageable pageable) {
        log.debug("Request to get all GeographicalData");
        return geographicalDataRepository.findAll(pageable);
    }


    /**
     * Get one geographicalData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GeographicalData> findOne(Long id) {
        log.debug("Request to get GeographicalData : {}", id);
        return geographicalDataRepository.findById(id);
    }

    /**
     * Delete the geographicalData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GeographicalData : {}", id);
        geographicalDataRepository.deleteById(id);
    }
}
