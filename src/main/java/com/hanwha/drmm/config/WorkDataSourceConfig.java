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
public class WorkDataSourceConfig {

    @Bean(name = "workH2Config")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.work")
    public HikariConfig h2Config() {
        return new HikariConfig();
    }

    @Bean(name = "workDs")
    public DataSource dataSource() {
        return new HikariDataSource(h2Config());
    }

    @Bean(name = "workTm")
    public PlatformTransactionManager transactionManager(@Qualifier("workDs") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
