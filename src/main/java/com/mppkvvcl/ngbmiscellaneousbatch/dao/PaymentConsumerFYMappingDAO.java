package com.mppkvvcl.ngbmiscellaneousbatch.dao;

import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.PaymentConsumerFYMappingInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.dao.PaymentConsumerFYMappingDAOInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.repository.PaymentConsumerFYMappingRepository;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.GlobalResources;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PaymentConsumerFYMappingDAO implements PaymentConsumerFYMappingDAOInterface<PaymentConsumerFYMappingInterface> {

    private static final Logger LOGGER = GlobalResources.getLogger(PaymentConsumerFYMappingDAO.class);

    @Autowired
    private PaymentConsumerFYMappingRepository paymentConsumerFYMappingRepository;

    @Override
    public PaymentConsumerFYMappingInterface get(PaymentConsumerFYMappingInterface paymentConsumerFYMapping) {
        final String methodName = "get() : ";
        LOGGER.info(methodName + "called");
        if (paymentConsumerFYMapping == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        return paymentConsumerFYMappingRepository.findById(paymentConsumerFYMapping.getId());
    }

    @Override
    public PaymentConsumerFYMappingInterface getById(long id) {
        final String methodName = "getById() : ";
        LOGGER.info(methodName + "called");
        return paymentConsumerFYMappingRepository.findById(id);
    }

    @Override
    public PaymentConsumerFYMappingInterface add(PaymentConsumerFYMappingInterface paymentConsumerFYMapping) {
        final String methodName = "add() : ";
        LOGGER.info(methodName + "called");
        if (paymentConsumerFYMapping == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        return paymentConsumerFYMappingRepository.save(paymentConsumerFYMapping);
    }

    @Override
    public PaymentConsumerFYMappingInterface update(PaymentConsumerFYMappingInterface paymentConsumerFYMapping) {
        final String methodName = "update() : ";
        LOGGER.info(methodName + "called ");
        if (paymentConsumerFYMapping == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        return paymentConsumerFYMappingRepository.save(paymentConsumerFYMapping);
    }

    @Override
    public PaymentConsumerFYMappingInterface getByConsumerNoAndFinancialYear(String consumerNo, String financialYear) {
        final String methodName = "getByConsumerNoAndFinancialYear() : ";
        LOGGER.info(methodName + "called ");
        if (StringUtils.isEmpty(consumerNo) || StringUtils.isEmpty(financialYear)) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        return paymentConsumerFYMappingRepository.findByConsumerNoAndFinancialYear(consumerNo, financialYear);
    }
}
