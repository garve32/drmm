package com.hanwha.drmm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchDataSourceConfig {

    @Bean(name = "batchH2Config")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.batch")
    public HikariConfig h2Config() {
        return new HikariConfig();
    }

    @Bean(name = "batchDs")
    public DataSource dataSource() {
        return new HikariDataSource(h2Config());
    }

    @Bean(name = "batchTm")
    public PlatformTransactionManager transactionManager(@Qualifier("batchDs") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
