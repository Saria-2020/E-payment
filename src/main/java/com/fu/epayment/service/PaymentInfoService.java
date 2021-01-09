package com.fu.epayment.service;

import com.fu.epayment.domain.PaymentInfo;
import com.fu.epayment.repository.PaymentInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PaymentInfo}.
 */
@Service
@Transactional
public class PaymentInfoService {

    private final Logger log = LoggerFactory.getLogger(PaymentInfoService.class);

    private final PaymentInfoRepository paymentInfoRepository;

    public PaymentInfoService(PaymentInfoRepository paymentInfoRepository) {
        this.paymentInfoRepository = paymentInfoRepository;
    }

    /**
     * Save a paymentInfo.
     *
     * @param paymentInfo the entity to save.
     * @return the persisted entity.
     */
    public PaymentInfo save(PaymentInfo paymentInfo) {
        log.debug("Request to save PaymentInfo : {}", paymentInfo);
        return paymentInfoRepository.save(paymentInfo);
    }

    /**
     * Get all the paymentInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentInfo> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentInfos");
        return paymentInfoRepository.findAll(pageable);
    }


    /**
     * Get one paymentInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentInfo> findOne(Long id) {
        log.debug("Request to get PaymentInfo : {}", id);
        return paymentInfoRepository.findById(id);
    }

    /**
     * Delete the paymentInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentInfo : {}", id);
        paymentInfoRepository.deleteById(id);
    }
}
