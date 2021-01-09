package com.fu.epayment.web.rest;

import com.fu.epayment.domain.PaymentInfo;
import com.fu.epayment.service.PaymentInfoService;
import com.fu.epayment.web.rest.errors.BadRequestAlertException;
import com.fu.epayment.service.dto.PaymentInfoCriteria;
import com.fu.epayment.service.PaymentInfoQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fu.epayment.domain.PaymentInfo}.
 */
@RestController
@RequestMapping("/api")
public class PaymentInfoResource {

    private final Logger log = LoggerFactory.getLogger(PaymentInfoResource.class);

    private static final String ENTITY_NAME = "paymentInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentInfoService paymentInfoService;

    private final PaymentInfoQueryService paymentInfoQueryService;

    public PaymentInfoResource(PaymentInfoService paymentInfoService, PaymentInfoQueryService paymentInfoQueryService) {
        this.paymentInfoService = paymentInfoService;
        this.paymentInfoQueryService = paymentInfoQueryService;
    }

    /**
     * {@code POST  /payment-infos} : Create a new paymentInfo.
     *
     * @param paymentInfo the paymentInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentInfo, or with status {@code 400 (Bad Request)} if the paymentInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-infos")
    public ResponseEntity<PaymentInfo> createPaymentInfo(@RequestBody PaymentInfo paymentInfo) throws URISyntaxException {
        log.debug("REST request to save PaymentInfo : {}", paymentInfo);
        if (paymentInfo.getId() != null) {
            throw new BadRequestAlertException("A new paymentInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentInfo result = paymentInfoService.save(paymentInfo);
        return ResponseEntity.created(new URI("/api/payment-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-infos} : Updates an existing paymentInfo.
     *
     * @param paymentInfo the paymentInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentInfo,
     * or with status {@code 400 (Bad Request)} if the paymentInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-infos")
    public ResponseEntity<PaymentInfo> updatePaymentInfo(@RequestBody PaymentInfo paymentInfo) throws URISyntaxException {
        log.debug("REST request to update PaymentInfo : {}", paymentInfo);
        if (paymentInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentInfo result = paymentInfoService.save(paymentInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-infos} : get all the paymentInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentInfos in body.
     */
    @GetMapping("/payment-infos")
    public ResponseEntity<List<PaymentInfo>> getAllPaymentInfos(PaymentInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PaymentInfos by criteria: {}", criteria);
        Page<PaymentInfo> page = paymentInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-infos/count} : count all the paymentInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payment-infos/count")
    public ResponseEntity<Long> countPaymentInfos(PaymentInfoCriteria criteria) {
        log.debug("REST request to count PaymentInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payment-infos/:id} : get the "id" paymentInfo.
     *
     * @param id the id of the paymentInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-infos/{id}")
    public ResponseEntity<PaymentInfo> getPaymentInfo(@PathVariable Long id) {
        log.debug("REST request to get PaymentInfo : {}", id);
        Optional<PaymentInfo> paymentInfo = paymentInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentInfo);
    }

    /**
     * {@code DELETE  /payment-infos/:id} : delete the "id" paymentInfo.
     *
     * @param id the id of the paymentInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-infos/{id}")
    public ResponseEntity<Void> deletePaymentInfo(@PathVariable Long id) {
        log.debug("REST request to delete PaymentInfo : {}", id);
        paymentInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
