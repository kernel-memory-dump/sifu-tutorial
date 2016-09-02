/**
* Copyright 2016 dryTools doo
* Email: contact@drytools.co
* 
* This file is part of todo.
* 
* todo is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* todo is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with todo. If not, see <http://www.gnu.org/licenses/>.*
**/
package com.todo.backend.config;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@EnableJpaRepositories("com.todo.backend.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Bean(destroyMethod = "close")
    public DataSource dataSource(DataSourceProperties dataSourceProperties, CustomProperties customProperties) {

        log.debug("Initializing datasource...");

        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(dataSourceProperties.getDriverClassName());
        config.addDataSourceProperty("url", dataSourceProperties.getUrl());
        config.addDataSourceProperty("user", dataSourceProperties.getUsername());
        config.addDataSourceProperty("password", dataSourceProperties.getPassword() == null ? "" : dataSourceProperties.getPassword());

        // mysql specific
        config.addDataSourceProperty("cachePrepStmts", customProperties.getDatasource().isCachePrepStmts());
        config.addDataSourceProperty("prepStmtCacheSize", customProperties.getDatasource().getPrepStmtCacheSize());
        config.addDataSourceProperty("prepStmtCacheSqlLimit", customProperties.getDatasource().getPrepStmtCacheSqlLimit());
        config.addDataSourceProperty("useServerPrepStmts", customProperties.getDatasource().isUseServerPrepStmts());

        return new HikariDataSource(config);
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, DataSourceProperties dataSourceProperties, LiquibaseProperties liquibaseProperties) {

        log.debug("Initializing liquibase...");

        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:liquibase/db-changelog.xml");
        return liquibase;
    }

    @Bean
    public JPQLQueryFactory factory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

}
