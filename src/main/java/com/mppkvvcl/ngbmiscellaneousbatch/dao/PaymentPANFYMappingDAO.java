package com.mppkvvcl.ngbmiscellaneousbatch.dao;

import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.PaymentPANFYMappingInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.dao.PaymentPANFYMappingDAOInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.repository.PaymentPANFYMappingRepository;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.GlobalResources;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PaymentPANFYMappingDAO implements PaymentPANFYMappingDAOInterface<PaymentPANFYMappingInterface> {

    private static final Logger LOGGER = GlobalResources.getLogger(PaymentPANFYMappingDAO.class);

    @Autowired
    private PaymentPANFYMappingRepository paymentPANFYMappingRepository;

    @Override
    public PaymentPANFYMappingInterface get(PaymentPANFYMappingInterface paymentPANFYMapping) {
        final String methodName = "get() : ";
        LOGGER.info(methodName + "called");
        if (paymentPANFYMapping == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        return paymentPANFYMappingRepository.findById(paymentPANFYMapping.getId());
    }

    @Override
    public PaymentPANFYMappingInterface getById(long id) {
        final String methodName = "getById() : ";
        LOGGER.info(methodName + "called");
        return paymentPANFYMappingRepository.findById(id);
    }

    @Override
    public PaymentPANFYMappingInterface add(PaymentPANFYMappingInterface paymentPANFYMapping) {
        final String methodName = "add() : ";
        LOGGER.info(methodName + "called");
        if (paymentPANFYMapping == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        return paymentPANFYMappingRepository.save(paymentPANFYMapping);
    }

    @Override
    public PaymentPANFYMappingInterface update(PaymentPANFYMappingInterface paymentPANFYMapping) {
        final String methodName = "update() : ";
        LOGGER.info(methodName + "called ");
        if (paymentPANFYMapping == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        return paymentPANFYMappingRepository.save(paymentPANFYMapping);
    }

    @Override
    public PaymentPANFYMappingInterface getByPanAndFinancialYear(String pan, String financialYear) {
        final String methodName = "getByPanAndFinancialYear() : ";
        LOGGER.info(methodName + "called ");
        if (StringUtils.isEmpty(pan) || StringUtils.isEmpty(financialYear)) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        return paymentPANFYMappingRepository.findByPanAndFinancialYear(pan, financialYear);
    }
}
