package com.mppkvvcl.ngbmiscellaneousbatch.job.writer.jobone;

import com.mppkvvcl.ngbinterface.interfaces.PaymentInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.job.holder.jobone.JobOneHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class JobOneStepTwoItemWriter implements ItemWriter<PaymentInterface> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobOneStepTwoItemWriter.class);

    public void write(List<? extends PaymentInterface> list) {
        String methodName = "write(): ";
        LOGGER.info(methodName + "called");
        if (list.size() != 1 || list.get(0) == null) {
            LOGGER.error(methodName + "Expected only one item in list, but got " + list.size());
            return;
        }

        long referenceId = list.get(0).getId();
        JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER = referenceId;
        LOGGER.info(methodName + "Setting Job One NGB_MAX_PAYMENT_COUNTER holder to " + referenceId);
    }
}
