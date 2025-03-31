package com.mppkvvcl.ngbmiscellaneousbatch.service.mis;

import com.mppkvvcl.ngbmiscellaneousbatch.dao.PaymentPANFYMappingDAO;
import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.PaymentPANFYMappingInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.GlobalResources;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PaymentPANFYMappingService {
    private static final Logger LOGGER = GlobalResources.getLogger(PaymentPANFYMappingService.class);

    @Autowired
    private PaymentPANFYMappingDAO paymentPANFYMappingDAO;

    @Autowired
    private GlobalResources globalResources;

    public PaymentPANFYMappingInterface insert(final PaymentPANFYMappingInterface paymentPANFYMapping) {
        final String methodName = "insert() : ";
        LOGGER.info(methodName + "called");
        if (paymentPANFYMapping == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        setAuditDetails(paymentPANFYMapping);
        return paymentPANFYMappingDAO.add(paymentPANFYMapping);
    }

    private void setAuditDetails(final PaymentPANFYMappingInterface paymentPANFYMapping) {
        final String methodName = "setAuditDetails() : ";
        LOGGER.info(methodName + "called");
        if (paymentPANFYMapping != null) {
            paymentPANFYMapping.setCreatedBy(globalResources.getProjectDetail());
            paymentPANFYMapping.setCreatedOn(GlobalResources.getCurrentDate());
        }
    }

    public PaymentPANFYMappingInterface update(final PaymentPANFYMappingInterface paymentPANFYMapping) {
        final String methodName = "update() : ";
        LOGGER.info(methodName + "called");
        if (paymentPANFYMapping == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        setUpdateAuditDetails(paymentPANFYMapping);
        return paymentPANFYMappingDAO.update(paymentPANFYMapping);
    }

    private void setUpdateAuditDetails(final PaymentPANFYMappingInterface paymentPANFYMapping) {
        final String methodName = "setUpdateAuditDetails() : ";
        LOGGER.info(methodName + "called");
        if (paymentPANFYMapping != null) {
            paymentPANFYMapping.setUpdatedBy(globalResources.getProjectDetail());
            paymentPANFYMapping.setUpdatedOn(GlobalResources.getCurrentDate());
        }
    }

    public PaymentPANFYMappingInterface getByPANAndFinancialYear(String pan, String financialYear) {
        final String methodName = "getByPANAndFinancialYear() : ";
        LOGGER.info(methodName + "called ");
        if (StringUtils.isEmpty(pan) || StringUtils.isEmpty(financialYear)) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        return paymentPANFYMappingDAO.getByPanAndFinancialYear(pan, financialYear);
    }
}
