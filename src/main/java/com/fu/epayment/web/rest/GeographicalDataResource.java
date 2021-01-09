package com.fu.epayment.web.rest;

import com.fu.epayment.domain.GeographicalData;
import com.fu.epayment.service.GeographicalDataService;
import com.fu.epayment.web.rest.errors.BadRequestAlertException;
import com.fu.epayment.service.dto.GeographicalDataCriteria;
import com.fu.epayment.service.GeographicalDataQueryService;

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
 * REST controller for managing {@link com.fu.epayment.domain.GeographicalData}.
 */
@RestController
@RequestMapping("/api")
public class GeographicalDataResource {

    private final Logger log = LoggerFactory.getLogger(GeographicalDataResource.class);

    private static final String ENTITY_NAME = "geographicalData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeographicalDataService geographicalDataService;

    private final GeographicalDataQueryService geographicalDataQueryService;

    public GeographicalDataResource(GeographicalDataService geographicalDataService, GeographicalDataQueryService geographicalDataQueryService) {
        this.geographicalDataService = geographicalDataService;
        this.geographicalDataQueryService = geographicalDataQueryService;
    }

    /**
     * {@code POST  /geographical-data} : Create a new geographicalData.
     *
     * @param geographicalData the geographicalData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geographicalData, or with status {@code 400 (Bad Request)} if the geographicalData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/geographical-data")
    public ResponseEntity<GeographicalData> createGeographicalData(@RequestBody GeographicalData geographicalData) throws URISyntaxException {
        log.debug("REST request to save GeographicalData : {}", geographicalData);
        if (geographicalData.getId() != null) {
            throw new BadRequestAlertException("A new geographicalData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeographicalData result = geographicalDataService.save(geographicalData);
        return ResponseEntity.created(new URI("/api/geographical-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /geographical-data} : Updates an existing geographicalData.
     *
     * @param geographicalData the geographicalData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geographicalData,
     * or with status {@code 400 (Bad Request)} if the geographicalData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geographicalData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/geographical-data")
    public ResponseEntity<GeographicalData> updateGeographicalData(@RequestBody GeographicalData geographicalData) throws URISyntaxException {
        log.debug("REST request to update GeographicalData : {}", geographicalData);
        if (geographicalData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeographicalData result = geographicalDataService.save(geographicalData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, geographicalData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /geographical-data} : get all the geographicalData.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geographicalData in body.
     */
    @GetMapping("/geographical-data")
    public ResponseEntity<List<GeographicalData>> getAllGeographicalData(GeographicalDataCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GeographicalData by criteria: {}", criteria);
        Page<GeographicalData> page = geographicalDataQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /geographical-data/count} : count all the geographicalData.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/geographical-data/count")
    public ResponseEntity<Long> countGeographicalData(GeographicalDataCriteria criteria) {
        log.debug("REST request to count GeographicalData by criteria: {}", criteria);
        return ResponseEntity.ok().body(geographicalDataQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /geographical-data/:id} : get the "id" geographicalData.
     *
     * @param id the id of the geographicalData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geographicalData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/geographical-data/{id}")
    public ResponseEntity<GeographicalData> getGeographicalData(@PathVariable Long id) {
        log.debug("REST request to get GeographicalData : {}", id);
        Optional<GeographicalData> geographicalData = geographicalDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geographicalData);
    }

    /**
     * {@code DELETE  /geographical-data/:id} : delete the "id" geographicalData.
     *
     * @param id the id of the geographicalData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/geographical-data/{id}")
    public ResponseEntity<Void> deleteGeographicalData(@PathVariable Long id) {
        log.debug("REST request to delete GeographicalData : {}", id);
        geographicalDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
