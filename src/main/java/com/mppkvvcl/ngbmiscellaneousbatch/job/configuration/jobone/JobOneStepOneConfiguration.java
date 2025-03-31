package com.mppkvvcl.ngbmiscellaneousbatch.job.configuration.jobone;

import com.mppkvvcl.ngbinterface.interfaces.BatchCounterInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.job.writer.jobone.JobOneStepOneItemWriter;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.MiscellaneousBatchConstants;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.HibernateCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class JobOneStepOneConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobOneStepOneConfiguration.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public HibernateCursorItemReader<BatchCounterInterface> jobOneStepOneReader() {
        String methodName = "jobOneStepOneReader() : ";
        LOGGER.info(methodName + "called to get counter for Payment by PAN table");
        HibernateCursorItemReader<BatchCounterInterface> counterItemReader = new HibernateCursorItemReader<BatchCounterInterface>();
        counterItemReader.setSessionFactory(getSessionFactory());
        counterItemReader.setQueryString("from BatchCounter where header='PAYMENT_FOR_194Q'");
        counterItemReader.setUseStatelessSession(MiscellaneousBatchConstants.FALSE);
        return counterItemReader;
    }

    @Bean
    public ItemWriter<BatchCounterInterface> jobOneStepOneWriter() {
        return new JobOneStepOneItemWriter();
    }

    public SessionFactory getSessionFactory() {
        String methodName = "getSessionFactory() : ";
        LOGGER.info(methodName + "called");

        return entityManagerFactory.unwrap(SessionFactory.class);
    }
}
