package com.mppkvvcl.ngbmiscellaneousbatch.job.configuration.jobone;

import com.mppkvvcl.ngbentity.beans.Payment;
import com.mppkvvcl.ngbinterface.interfaces.PaymentInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.job.writer.jobone.JobOneStepTwoItemWriter;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.MiscellaneousBatchConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobOneStepTwoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobOneStepTwoConfiguration.class);

    public static final int STEP_TWO_CHUNK_SIZE = 1;

    @Autowired
    @Qualifier("ngbDataSource")
    private DataSource dataSource;

    @Bean
    public JdbcPagingItemReader<Payment> jobOneStepTwoReader() {
        String methodName = "jobOneStepTwoReader() : ";
        LOGGER.info(methodName + "called to get max record from ngb payment table");

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        final Date referenceDate = calendar.getTime();

        Map<String, Object> parameterValues = new HashMap<>();
        //parameterValues.put("minId", JobOneHolder.LAST_PAYMENT_COUNTER);
        parameterValues.put("referenceDate", referenceDate);
        parameterValues.put("deleted", MiscellaneousBatchConstants.FALSE);

        return new JdbcPagingItemReaderBuilder<Payment>()
                .name("paymentCounterReader")
                .dataSource(dataSource)
                .queryProvider(jobOneStepTwoQueryProvider())
                .parameterValues(parameterValues)
                .rowMapper(new JobOneStepTwoConfiguration.PaymentRowMapper())
                .fetchSize(STEP_TWO_CHUNK_SIZE)
                .pageSize(STEP_TWO_CHUNK_SIZE)
                .maxItemCount(STEP_TWO_CHUNK_SIZE)
                .build();
    }

    @Bean
    public ItemWriter<PaymentInterface> jobOneStepTwoWriter() {
        return new JobOneStepTwoItemWriter();
    }

    private PostgresPagingQueryProvider jobOneStepTwoQueryProvider() {
        final String methodName = "jobOneStepTwoQueryProvider() : ";
        LOGGER.info(methodName + "called");

        PostgresPagingQueryProvider provider = new PostgresPagingQueryProvider();
        provider.setSelectClause("select * ");
        provider.setFromClause("from payment where deleted= :deleted and created_on::date= :referenceDate");// id> :minId and

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.DESCENDING);
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
