package com.mppkvvcl.ngbmiscellaneousbatch.job.processor.jobone;

import com.mppkvvcl.ngbinterface.interfaces.PaymentInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.service.process.PaymentImplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class JobOneStepThreeItemProcessor implements ItemProcessor<PaymentInterface, Map<String, Object>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobOneStepThreeItemProcessor.class);

    @Autowired
    private PaymentImplService paymentImplService;

    @Override
    public Map<String, Object> process(PaymentInterface payment) {
        final String methodName = "process() : ";
        if (payment == null) return null;
        LOGGER.info(methodName + "called for NGB Payment ID : " + payment.getId());

        final Map<String, Object> outMap = paymentImplService.process(payment);

        LOGGER.info(methodName + "Processing completed for " + payment.getId());
        return outMap;
    }
}
