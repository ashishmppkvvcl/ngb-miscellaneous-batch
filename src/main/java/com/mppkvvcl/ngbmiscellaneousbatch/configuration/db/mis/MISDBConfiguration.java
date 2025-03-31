package com.mppkvvcl.ngbmiscellaneousbatch.configuration.db.mis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "misEntityManagerFactory", basePackages = {"com.mppkvvcl.ngbmiscellaneousbatch.repository"}, transactionManagerRef = "misTransactionManager")
@ComponentScan(basePackages = {"com.mppkvvcl.ngbmiscellaneousbatch"})
public class MISDBConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MISDBConfiguration.class);

    @Bean(name = "misDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mis")
    public DataSource dataSource() {
        String methodName = "dataSource() : ";
        LOGGER.info(methodName + "called");

        return DataSourceBuilder.create().build();
    }

    @Bean(name = "misEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean misEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("misDataSource") DataSource dataSource) {
        String methodName = "misEntityManagerFactory() : ";
        LOGGER.info(methodName + "called");

        return builder.dataSource(dataSource).packages("com.mppkvvcl.ngbmiscellaneousbatch.bean").persistenceUnit("mis").build();
    }

    @Bean(name = "misTransactionManager")
    public PlatformTransactionManager misTransactionManager(@Qualifier("misEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        String methodName = "misTransactionManager() : ";
        LOGGER.info(methodName + "called");
        return new JpaTransactionManager(entityManagerFactory);
    }
}
