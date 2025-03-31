package com.mppkvvcl.ngbmiscellaneousbatch.job.configuration.jobzero;

import com.mppkvvcl.ngbmiscellaneousbatch.bean.Configurator;
import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.ConfiguratorInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.job.writer.jobzero.JobZeroStepOneItemWriter;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobZeroStepOneConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobZeroStepOneConfiguration.class);

    public static final int STEP_ONE_CHUNK_SIZE = 3;

    @Autowired
    @Qualifier("misDataSource")
    private DataSource dataSource;

    @Bean
    public JdbcPagingItemReader<Configurator> jobZeroStepOneReader() {
        String methodName = "jobZeroStepOneReader() : ";
        LOGGER.info(methodName + "called to get Configurations");

        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("properties", Arrays.asList("TDS-194Q-FY-THRESHOLD-AMOUNT", "TDS-194Q-PAN-AVAILABLE-TDS-PERCENTAGE", "TDS-194Q-PAN-NOT-AVAILABLE-TDS-PERCENTAGE"));

        return new JdbcPagingItemReaderBuilder<Configurator>()
                .name("configurationReader")
                .dataSource(dataSource)
                .queryProvider(jobZeroStepOneQueryProvider())
                .parameterValues(parameterValues)
                .rowMapper(new JobZeroStepOneConfiguration.ConfiguratorRowMapper())
                .fetchSize(STEP_ONE_CHUNK_SIZE)
                .pageSize(STEP_ONE_CHUNK_SIZE)
                .maxItemCount(STEP_ONE_CHUNK_SIZE)
                .build();
    }

    @Bean
    public ItemWriter<ConfiguratorInterface> jobZeroStepOneWriter() {
        return new JobZeroStepOneItemWriter();
    }

    private PostgresPagingQueryProvider jobZeroStepOneQueryProvider() {
        final String methodName = "jobZeroStepOneQueryProvider() : ";
        LOGGER.info(methodName + "called");

        PostgresPagingQueryProvider provider = new PostgresPagingQueryProvider();
        provider.setSelectClause("select * ");
        provider.setFromClause("from configurator where property_name in (:properties)");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.DESCENDING);
        provider.setSortKeys(sortKeys);

        return provider;
    }

    private class ConfiguratorRowMapper implements RowMapper<Configurator> {
        @Override
        public Configurator mapRow(ResultSet rs, int i) throws SQLException {
            Configurator configurator = new Configurator();

            configurator.setId(rs.getLong(1));
            configurator.setPropertyName(rs.getString(2));
            configurator.setPropertyValue(rs.getString(3));

            return configurator;
        }
    }
}
