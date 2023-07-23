package com.hanwha.drmm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
//@RequiredArgsConstructor
public class CustomBatchConfigurer extends DefaultBatchConfiguration {

//    @Bean(name = "batchH2Config")
//    @ConfigurationProperties(prefix = "spring.datasource.hikari.batch")
//    public HikariConfig h2Config() {
//        return new HikariConfig();
//    }
//
//    @Bean(name = "batchDs")
//    public DataSource dataSource() {
//        return new HikariDataSource(h2Config());
//    }

//    private final DataSource batchDs;
//
//    @Override
//    protected DataSource getDataSource() {
//        return batchDs;
//    }
//
//    @Override
//    public JobRepository jobRepository() {
//        JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
//        try {
//            factoryBean.afterPropertiesSet();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            return factoryBean.getObject();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
