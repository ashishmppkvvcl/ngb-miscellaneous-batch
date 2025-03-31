package com.mppkvvcl.ngbmiscellaneousbatch.configuration.db.ngb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "ngbEntityManagerFactory", basePackages = {"com.mppkvvcl.ngbdao.repositories"}, transactionManagerRef = "ngbTransactionManager")
@ComponentScan(basePackages = {"com.mppkvvcl.ngbdao.daos"})
public class NGBDBConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(NGBDBConfiguration.class);

    @Primary
    @Bean(name = "ngbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ngb")
    public DataSource dataSource() {
        String methodName = "dataSource() : ";
        LOGGER.info(methodName + "called");

        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "ngbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean ngbEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("ngbDataSource") DataSource dataSource) {
        final String methodName = "ngbEntityManagerFactory() : ";
        LOGGER.info(methodName + "called");

        return builder.dataSource(dataSource).packages("com.mppkvvcl.ngbentity.beans").persistenceUnit("ngb").build();
    }

    @Primary
    @Bean(name = "ngbTransactionManager")
    public PlatformTransactionManager ngbTransactionManager(@Qualifier("ngbEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        final String methodName = "ngbTransactionManager() : ";
        LOGGER.info(methodName + "called");
        return new JpaTransactionManager(entityManagerFactory);
    }
}
