package com.mppkvvcl.ngbmiscellaneousbatch.repository;

import com.mppkvvcl.ngbmiscellaneousbatch.bean.PaymentPANFYMapping;
import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.PaymentPANFYMappingInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentPANFYMappingRepository extends JpaRepository<PaymentPANFYMapping, Long> {

    public PaymentPANFYMappingInterface save(PaymentPANFYMappingInterface paymentPANFYMapping);

    public PaymentPANFYMappingInterface findById(long id);

    public PaymentPANFYMappingInterface findByPanAndFinancialYear(String pan, String financialYear);
}
