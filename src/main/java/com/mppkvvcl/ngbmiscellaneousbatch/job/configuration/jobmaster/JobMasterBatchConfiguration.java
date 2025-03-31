package com.mppkvvcl.ngbmiscellaneousbatch.job.configuration.jobmaster;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobMasterBatchConfiguration {

    public static final String JOB_NAME = "MASTER-JOB";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public Job paymentJob;

    @Autowired
    public Job configJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    public Job masterJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        final Step jobZeroStep = new JobStepBuilder(new StepBuilder("CONFIG-JOB-AS-CHILD"))
                .job(configJob)
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();

        final Step jobOneStep = new JobStepBuilder(new StepBuilder("PAYMENT-JOB-AS-CHILD"))
                .job(paymentJob)
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();

        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())

                // Job 0
                .flow(jobZeroStep)

                //Job 1
                .next(jobOneStep)

                .end()
                .build();
    }
}
