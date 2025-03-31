package com.mppkvvcl.ngbmiscellaneousbatch.job.writer.jobone;

import com.mppkvvcl.ngbmiscellaneousbatch.bean.PaymentConsumerFYMapping;
import com.mppkvvcl.ngbmiscellaneousbatch.bean.PaymentPANFYMapping;
import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.PaymentConsumerFYMappingInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.PaymentPANFYMappingInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.service.mis.PaymentConsumerFYMappingService;
import com.mppkvvcl.ngbmiscellaneousbatch.service.mis.PaymentPANFYMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class JobOneStepThreeMISItemWriter implements ItemWriter<Map<String, Object>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobOneStepThreeMISItemWriter.class);

    @Autowired
    private PaymentPANFYMappingService paymentPANFYMappingService;

    @Autowired
    private PaymentConsumerFYMappingService paymentConsumerFYMappingService;

    public void write(List<? extends Map<String, Object>> list) {
        String methodName = "write(): ";
        LOGGER.info(methodName + "called");
        if (list == null || list.size() == 0) {
            LOGGER.info(methodName + "No items to write");
            return;
        }

        int panInsertedCount = 0;
        int panUpdatedCount = 0;
        int consumerInsertedCount = 0;
        int consumerUpdatedCount = 0;

        for (Map<String, Object> entityMap : list) {
            if (entityMap == null) continue;

            PaymentPANFYMappingInterface paymentPANFYMapping = (PaymentPANFYMapping) entityMap.get("PAYMENT_BY_PAN");
            PaymentConsumerFYMappingInterface paymentConsumerFYMapping = (PaymentConsumerFYMapping) entityMap.get("PAYMENT_BY_CONSUMER");

            if (paymentPANFYMapping != null) {
                if (paymentPANFYMapping.getCreatedOn() != null && paymentPANFYMapping.getId() > 0) {
                    paymentPANFYMappingService.update(paymentPANFYMapping);
                    panUpdatedCount++;
                } else {
                    paymentPANFYMappingService.insert(paymentPANFYMapping);
                    panInsertedCount++;
                }
            }

            if (paymentConsumerFYMapping != null) {
                if (paymentConsumerFYMapping.getCreatedOn() != null && paymentConsumerFYMapping.getId() > 0) {
                    paymentConsumerFYMappingService.update(paymentConsumerFYMapping);
                    consumerUpdatedCount++;
                } else {
                    paymentConsumerFYMappingService.insert(paymentConsumerFYMapping);
                    consumerInsertedCount++;
                }
            }
        }

        LOGGER.info(methodName + "Size of List is : " + list.size());
        LOGGER.info(methodName + "Payment PAN Insert Count " + panInsertedCount);
        LOGGER.info(methodName + "Payment PAN Update Count " + panUpdatedCount);
        LOGGER.info(methodName + "Payment Consumer Insert Count " + consumerInsertedCount);
        LOGGER.info(methodName + "Payment Consumer Update Count " + consumerUpdatedCount);
    }
}
