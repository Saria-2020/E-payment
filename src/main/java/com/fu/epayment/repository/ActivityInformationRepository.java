package com.fu.epayment.repository;

import com.fu.epayment.domain.ActivityInformation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ActivityInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityInformationRepository extends JpaRepository<ActivityInformation, Long>, JpaSpecificationExecutor<ActivityInformation> {
}
