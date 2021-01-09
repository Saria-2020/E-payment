package com.fu.epayment.repository;

import com.fu.epayment.domain.PaymentInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long>, JpaSpecificationExecutor<PaymentInfo> {
}
