package com.mppkvvcl.ngbmiscellaneousbatch.job.configuration.jobone;

import com.mppkvvcl.ngbentity.beans.Payment;
import com.mppkvvcl.ngbmiscellaneousbatch.job.holder.jobone.JobOneHolder;
import com.mppkvvcl.ngbmiscellaneousbatch.job.listener.jobone.JobOneStepThreeListener;
import com.mppkvvcl.ngbmiscellaneousbatch.job.processor.jobone.JobOneStepThreeItemProcessor;
import com.mppkvvcl.ngbmiscellaneousbatch.job.writer.jobone.JobOneStepThreeMISItemWriter;
import com.mppkvvcl.ngbmiscellaneousbatch.job.writer.jobone.JobOneStepThreeNGBItemWriter;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.MiscellaneousBatchConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobOneStepThreeConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobOneStepThreeConfiguration.class);

    public static final int STEP_THREE_CHUNK_SIZE = 1;

    @Autowired
    @Qualifier("ngbDataSource")
    private DataSource dataSource;

    @Bean
    @StepScope
    public JdbcPagingItemReader<Payment> jobOneStepThreeReader() {
        String methodName = "jobOneStepThreeReader() : ";
        LOGGER.info(methodName + "called");

        //if (JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER == null)
        //    JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER = JobOneHolder.LAST_PAYMENT_COUNTER;
        LOGGER.info(methodName + "Getting ngb payment after id {} till id {}", JobOneHolder.LAST_PAYMENT_COUNTER, JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER);

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("minId", JobOneHolder.LAST_PAYMENT_COUNTER);
        parameterValues.put("maxId", JobOneHolder.MAX_CURRENT_PAYMENT_COUNTER);
        parameterValues.put("deleted", MiscellaneousBatchConstants.FALSE);

        return new JdbcPagingItemReaderBuilder<Payment>()
                .name("paymentReader")
                .dataSource(dataSource)
                .queryProvider(jobOneStepThreeQueryProvider())
                .parameterValues(parameterValues)
                .rowMapper(new PaymentRowMapper())
                .fetchSize(STEP_THREE_CHUNK_SIZE)
                .pageSize(STEP_THREE_CHUNK_SIZE)
                .build();
    }

    @Bean
    public JobOneStepThreeItemProcessor jobOneStepThreeProcessor() {
        return new JobOneStepThreeItemProcessor();
    }

    @Bean
    public ItemWriter<Map<String, Object>> jobOneStepThreeMISWriter() {
        return new JobOneStepThreeMISItemWriter();
    }

    @Bean
    public ItemWriter<Map<String, Object>> jobOneStepThreeNGBWriter() {
        return new JobOneStepThreeNGBItemWriter();
    }

    @Bean
    public CompositeItemWriter<Map<String, Object>> jobOneStepThreeCompositeWriter() {
        CompositeItemWriter compositeItemWriter = new CompositeItemWriter<Map<String, Object>>();
        compositeItemWriter.setDelegates(Arrays.asList(jobOneStepThreeMISWriter(), jobOneStepThreeNGBWriter()));
        return compositeItemWriter;
    }

    @Bean
    @StepScope
    public JobOneStepThreeListener jobOneStepThreeListener() {
        String methodName = "jobOneStepThreeListener() : ";
        LOGGER.info(methodName + "called");
        return new JobOneStepThreeListener();
    }

    private PostgresPagingQueryProvider jobOneStepThreeQueryProvider() {
        String methodName = "jobOneStepThreeQueryProvider() : ";
        LOGGER.info(methodName + "called");

        PostgresPagingQueryProvider provider = new PostgresPagingQueryProvider();
        provider.setSelectClause("select * ");
        provider.setFromClause("from payment");
        provider.setWhereClause("where id> :minId and id <= :maxId and deleted= :deleted");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);
        provider.setSortKeys(sortKeys);

        return provider;
    }

    private class PaymentRowMapper implements RowMapper<Payment> {
        @Override
        public Payment mapRow(ResultSet rs, int i) throws SQLException {
            Payment payment = new Payment();

            payment.setId(rs.getLong(1));
            payment.setLocationCode(rs.getString(4));
            payment.setConsumerNo(rs.getString(5));
            payment.setPunchingDate(rs.getDate(6));
            payment.setPayDate(rs.getDate(7));
            payment.setAmount(rs.getLong(8));
            payment.setDeleted(rs.getBoolean(12));
            payment.setPosted(rs.getBoolean(13));
            payment.setPostingBillMonth(rs.getString(14));
            payment.setPostingDate(rs.getDate(15));

            return payment;
        }
    }
}
