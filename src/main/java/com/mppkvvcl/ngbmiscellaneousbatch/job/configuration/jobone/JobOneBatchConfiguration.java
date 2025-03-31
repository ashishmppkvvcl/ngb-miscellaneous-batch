package com.mppkvvcl.ngbmiscellaneousbatch.job.configuration.jobone;

import com.mppkvvcl.ngbinterface.interfaces.BatchCounterInterface;
import com.mppkvvcl.ngbinterface.interfaces.PaymentInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.job.holder.jobone.JobOneHolder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
public class JobOneBatchConfiguration {

    public static final String JOB_NAME = "PAYMENT-JOB";

    public static final int STEP_ONE_CHUNK_SIZE = 1;

    public static final int STEP_TWO_CHUNK_SIZE = 1;

    public static final int STEP_THREE_CHUNK_SIZE = 1;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobOneStepOneConfiguration jobOneStepOneConfiguration;

    @Autowired
    private JobOneStepTwoConfiguration jobOneStepTwoConfiguration;

    @Autowired
    private JobOneStepThreeConfiguration jobOneStepThreeConfiguration;

    @Bean
    public Step jobOneStep1() {
        return stepBuilderFactory.get("step1-1").<BatchCounterInterface, BatchCounterInterface>chunk(STEP_ONE_CHUNK_SIZE)
                .reader(jobOneStepOneConfiguration.jobOneStepOneReader())
                .writer(jobOneStepOneConfiguration.jobOneStepOneWriter())
                .build();
    }

    @Bean
    public Step jobOneStep2() {
        return stepBuilderFactory.get("step1-2").<PaymentInterface, PaymentInterface>chunk(STEP_TWO_CHUNK_SIZE)
                //.faultTolerant()
                .reader(jobOneStepTwoConfiguration.jobOneStepTwoReader())
                .writer(jobOneStepTwoConfiguration.jobOneStepTwoWriter())
                .build();
    }

    @Bean
    public Step jobOneStep3(@Qualifier("ngbTransactionManager") PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("step1-3").<PaymentInterface, Map<String, Object>>chunk(STEP_THREE_CHUNK_SIZE)
                //.faultTolerant()
                .reader(jobOneStepThreeConfiguration.jobOneStepThreeReader())
                .processor(jobOneStepThreeConfiguration.jobOneStepThreeProcessor())
                .writer(jobOneStepThreeConfiguration.jobOneStepThreeCompositeWriter())
                .transactionManager(transactionManager)
                .listener(jobOneStepThreeConfiguration.jobOneStepThreeListener())
                .build();
    }

    @Bean
    public Job paymentJob() {

        JobOneHolder.LAST_PAYMENT_COUNTER = null;
        JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER = null;

        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())

                .flow(jobOneStep1())
                .next(jobOneStep2())
                .next(jobOneStep3(null))

                .end()
                .build();
    }
}
