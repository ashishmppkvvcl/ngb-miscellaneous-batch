package com.mppkvvcl.ngbmiscellaneousbatch.job.writer.jobone;

import com.mppkvvcl.ngbinterface.interfaces.BatchCounterInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.job.holder.jobone.JobOneHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class JobOneStepOneItemWriter implements ItemWriter<BatchCounterInterface> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobOneStepOneItemWriter.class);

    public void write(List<? extends BatchCounterInterface> list) {
        String methodName = "write(): ";
        LOGGER.info(methodName + "called");
        if (list.size() != 1 || list.get(0) == null) {
            LOGGER.error(methodName + "Expected only one item in list, but got " + list.size());
            return;
        }

        long referenceId = list.get(0).getReferenceId();
        JobOneHolder.LAST_PAYMENT_COUNTER = referenceId;
        LOGGER.info(methodName + "Setting Job One LAST_PAYMENT_COUNTER holder to " + referenceId);
    }
}
