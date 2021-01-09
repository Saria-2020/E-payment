package com.fu.epayment.service.impl;

import com.fu.epayment.service.ActivityInformationService;
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
public class ActivityInformationServiceImpl implements ActivityInformationService {

    private final Logger log = LoggerFactory.getLogger(ActivityInformationServiceImpl.class);

    private final ActivityInformationRepository activityInformationRepository;

    public ActivityInformationServiceImpl(ActivityInformationRepository activityInformationRepository) {
        this.activityInformationRepository = activityInformationRepository;
    }

    @Override
    public ActivityInformation save(ActivityInformation activityInformation) {
        log.debug("Request to save ActivityInformation : {}", activityInformation);
        return activityInformationRepository.save(activityInformation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityInformation> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityInformations");
        return activityInformationRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityInformation> findOne(Long id) {
        log.debug("Request to get ActivityInformation : {}", id);
        return activityInformationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivityInformation : {}", id);
        activityInformationRepository.deleteById(id);
    }
}
