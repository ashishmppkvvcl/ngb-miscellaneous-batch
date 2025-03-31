package com.mppkvvcl.ngbmiscellaneousbatch.schedule;

import com.mppkvvcl.ngbmiscellaneousbatch.job.configuration.jobmaster.JobMasterBatchConfiguration;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.GlobalResources;
import org.slf4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {
    private static final Logger LOGGER = GlobalResources.getLogger(JobScheduler.class);

    private static ApplicationContext APPLICATION_CONTEXT;

    @Autowired
    public JobScheduler(final ApplicationContext applicationContext) {
        APPLICATION_CONTEXT = applicationContext;
    }

    //@Scheduled(cron = "0 22 16 * * *")
    @Scheduled(cron = "${ngb.misc.batch.schedule}")
    public static void launchMasterBatch() {
        String methodName = "launchMasterBatch() : ";
        LOGGER.info(methodName + "called");
        LOGGER.info(methodName + "LAUNCHING MASTER MISCELLANEOUS BATCH");

        final JobLauncher jobLauncher = (JobLauncher) APPLICATION_CONTEXT.getBean("jobLauncher");
        if (jobLauncher != null) {
            LOGGER.info("JobLauncher is not NULL. Proceeding..");
            Job masterJob = (Job) APPLICATION_CONTEXT.getBean("masterJob");
            if (masterJob != null) {
                LOGGER.info(JobMasterBatchConfiguration.JOB_NAME + " is not NULL");
                try {
                    JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
                    JobExecution jobExecution = jobLauncher.run(masterJob, jobParameters);
                    if (jobExecution != null && jobExecution.getExitStatus().compareTo(ExitStatus.COMPLETED) == 0) {
                        LOGGER.info(JobMasterBatchConfiguration.JOB_NAME + " EXITED With Completed status");
                    }
                } catch (Exception exception) {
                    LOGGER.error(methodName + "Exception while running Schedules Job " + exception);
                }
            }
        }
    }
}
