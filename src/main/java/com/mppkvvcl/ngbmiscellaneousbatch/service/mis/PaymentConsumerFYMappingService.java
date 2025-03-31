package com.mppkvvcl.ngbmiscellaneousbatch.service.mis;

import com.mppkvvcl.ngbmiscellaneousbatch.dao.PaymentConsumerFYMappingDAO;
import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.PaymentConsumerFYMappingInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.GlobalResources;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PaymentConsumerFYMappingService {
    private static final Logger LOGGER = GlobalResources.getLogger(PaymentConsumerFYMappingService.class);

    @Autowired
    private PaymentConsumerFYMappingDAO paymentConsumerFYMappingDAO;

    @Autowired
    private GlobalResources globalResources;

    public PaymentConsumerFYMappingInterface insert(final PaymentConsumerFYMappingInterface paymentConsumerFYMapping) {
        final String methodName = "insert() : ";
        LOGGER.info(methodName + "called");
        if (paymentConsumerFYMapping == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        setAuditDetails(paymentConsumerFYMapping);
        return paymentConsumerFYMappingDAO.add(paymentConsumerFYMapping);
    }

    private void setAuditDetails(final PaymentConsumerFYMappingInterface paymentConsumerFYMapping) {
        final String methodName = "setAuditDetails() : ";
        LOGGER.info(methodName + "called");
        if (paymentConsumerFYMapping != null) {
            paymentConsumerFYMapping.setCreatedBy(globalResources.getProjectDetail());
            paymentConsumerFYMapping.setCreatedOn(GlobalResources.getCurrentDate());
        }
    }

    public PaymentConsumerFYMappingInterface update(final PaymentConsumerFYMappingInterface paymentConsumerFYMapping) {
        final String methodName = "update() : ";
        LOGGER.info(methodName + "called");
        if (paymentConsumerFYMapping == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        setUpdateAuditDetails(paymentConsumerFYMapping);
        return paymentConsumerFYMappingDAO.update(paymentConsumerFYMapping);
    }

    private void setUpdateAuditDetails(final PaymentConsumerFYMappingInterface paymentConsumerFYMapping) {
        final String methodName = "setUpdateAuditDetails() : ";
        LOGGER.info(methodName + "called");
        if (paymentConsumerFYMapping != null) {
            paymentConsumerFYMapping.setUpdatedBy(globalResources.getProjectDetail());
            paymentConsumerFYMapping.setUpdatedOn(GlobalResources.getCurrentDate());
        }
    }

    public PaymentConsumerFYMappingInterface getByConsumerAndFinancialYear(String consumerNo, String financialYear) {
        final String methodName = "getByConsumerAndFinancialYear() : ";
        LOGGER.info(methodName + "called ");
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(financialYear)) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        return paymentConsumerFYMappingDAO.getByConsumerNoAndFinancialYear(consumerNo, financialYear);
    }
}
