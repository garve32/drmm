package com.hanwha.drmm.core.datasource;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@MapperScan(value = "com.hanwha.drmm.api.mapper", sqlSessionFactoryRef = "batchSf")
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

    @Bean(name = "batchSf")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("batchDs") DataSource dataSource)
        throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = patternResolver.getResources("/mapper/drmm/**/**.xml");
        factoryBean.setMapperLocations(resources);
        factoryBean.setTypeAliasesPackage("com.hanwha.drmm.api.dto");
        SqlSessionFactory sqlSessionFactory = factoryBean.getObject();
        assert sqlSessionFactory != null;
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactory.getConfiguration().setCallSettersOnNulls(true);

        return factoryBean.getObject();
    }

}
