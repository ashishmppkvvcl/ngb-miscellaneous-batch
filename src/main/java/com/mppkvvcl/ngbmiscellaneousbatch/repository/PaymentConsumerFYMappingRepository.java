package com.mppkvvcl.ngbmiscellaneousbatch.repository;

import com.mppkvvcl.ngbmiscellaneousbatch.bean.PaymentConsumerFYMapping;
import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.PaymentConsumerFYMappingInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentConsumerFYMappingRepository extends JpaRepository<PaymentConsumerFYMapping, Long> {

    public PaymentConsumerFYMappingInterface save(PaymentConsumerFYMappingInterface paymentConsumerFYMapping);

    public PaymentConsumerFYMappingInterface findById(long id);

    public PaymentConsumerFYMappingInterface findByConsumerNoAndFinancialYear(String consumerNo, String financialYear);
}
