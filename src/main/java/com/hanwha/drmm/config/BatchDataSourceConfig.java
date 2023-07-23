package com.hanwha.drmm.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchDataSourceConfig {

    @Bean(name = "batchDs")
    @ConfigurationProperties("spring.datasource.hikari.batch")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "batchTm")
    public PlatformTransactionManager transactionManager(@Qualifier("batchDs") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
