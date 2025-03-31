package com.mppkvvcl.ngbmiscellaneousbatch.job.configuration.jobzero;

import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.ConfiguratorInterface;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobZeroBatchConfiguration {

    public static final String JOB_NAME = "CONFIG-JOB";

    public static final int STEP_Zero_CHUNK_SIZE = 3;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobZeroStepOneConfiguration jobZeroStepOneConfiguration;

    @Bean
    public Step jobZeroStep1() {
        return stepBuilderFactory.get("step0-1").<ConfiguratorInterface, ConfiguratorInterface>chunk(STEP_Zero_CHUNK_SIZE)
                .reader(jobZeroStepOneConfiguration.jobZeroStepOneReader())
                .writer(jobZeroStepOneConfiguration.jobZeroStepOneWriter())
                .build();
    }

    @Bean
    public Job configJob() {

        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())

                .flow(jobZeroStep1())

                .end()
                .build();
    }
}
