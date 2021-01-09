package com.fu.epayment.repository;

import com.fu.epayment.domain.GeographicalData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GeographicalData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeographicalDataRepository extends JpaRepository<GeographicalData, Long>, JpaSpecificationExecutor<GeographicalData> {
}
