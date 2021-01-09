package com.fu.epayment.service.impl;

import com.fu.epayment.service.GeographicalDataService;
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
public class GeographicalDataServiceImpl implements GeographicalDataService {

    private final Logger log = LoggerFactory.getLogger(GeographicalDataServiceImpl.class);

    private final GeographicalDataRepository geographicalDataRepository;

    public GeographicalDataServiceImpl(GeographicalDataRepository geographicalDataRepository) {
        this.geographicalDataRepository = geographicalDataRepository;
    }

    @Override
    public GeographicalData save(GeographicalData geographicalData) {
        log.debug("Request to save GeographicalData : {}", geographicalData);
        return geographicalDataRepository.save(geographicalData);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GeographicalData> findAll(Pageable pageable) {
        log.debug("Request to get all GeographicalData");
        return geographicalDataRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GeographicalData> findOne(Long id) {
        log.debug("Request to get GeographicalData : {}", id);
        return geographicalDataRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GeographicalData : {}", id);
        geographicalDataRepository.deleteById(id);
    }
}
