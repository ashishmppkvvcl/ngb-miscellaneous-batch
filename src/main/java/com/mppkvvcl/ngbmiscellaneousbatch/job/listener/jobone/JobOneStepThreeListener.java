package com.mppkvvcl.ngbmiscellaneousbatch.job.listener.jobone;

import com.mppkvvcl.ngbmiscellaneousbatch.job.holder.jobone.ConfigHolder;
import com.mppkvvcl.ngbmiscellaneousbatch.job.holder.jobone.JobOneHolder;
import com.mppkvvcl.ngbmiscellaneousbatch.service.ngb.AdjustmentService;
import com.mppkvvcl.ngbmiscellaneousbatch.service.ngb.BatchCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

public class JobOneStepThreeListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobOneStepThreeListener.class);

    @Autowired
    private BatchCounterService batchCounterService;

    @Autowired
    private AdjustmentService adjustmentService;

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        final String methodName = "afterStep() : ";
        LOGGER.info(methodName + "called");

        if (stepExecution == null) {
            LOGGER.info(methodName + "Invalid Input Data received.Failing batch");
            stepExecution.setExitStatus(ExitStatus.FAILED);
            return ExitStatus.FAILED;
        }

        LOGGER.info(methodName + "Before Cleaning up the counter values in JobOneHolder are");
        LOGGER.info(methodName + "Last Payment Id is " + JobOneHolder.LAST_PAYMENT_COUNTER);
        LOGGER.info(methodName + "Current Max Payment Id is " + JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER);
        //LOGGER.info(methodName + "Execution Status SUCCESS is " + JobOneHolder.EXECUTION_SUCCESSFULLY_COMPLETED);

        if (JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER != null && JobOneHolder.LAST_PAYMENT_COUNTER != null
                && JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER > JobOneHolder.LAST_PAYMENT_COUNTER) {

            batchCounterService.updateReferenceIdByHeader(JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER, "PAYMENT_FOR_194Q");

            LOGGER.info(methodName + "PAYMENT Counter difference is " + (JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER - JobOneHolder.LAST_PAYMENT_COUNTER));
        }

        JobOneHolder.LAST_PAYMENT_COUNTER = null;
        JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER = null;

        ConfigHolder.TDS_194Q_FY_THRESHOLD_AMOUNT = null;
        ConfigHolder.TDS_194Q_PAN_AVAILABLE_TDS_PERCENTAGE = null;
        ConfigHolder.TDS_194Q_PAN_NOT_AVAILABLE_TDS_PERCENTAGE = null;

        LOGGER.info(methodName + "Cleaned up the counter values successfully in JobOneHolder");
        return ExitStatus.COMPLETED;
    }
}
