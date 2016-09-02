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

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfiguration {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    @Bean
    public Docket swaggerSpringfoxDocket() {

        log.debug("Initializing swagger...");

        final StopWatch watch = new StopWatch();
        watch.start();

        final Docket docket = new Docket(DocumentationType.SWAGGER_2).forCodeGeneration(true).genericModelSubstitutes(ResponseEntity.class).ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(java.sql.Date.class).directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class).directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalTime.class, Date.class).select().paths(PathSelectors.regex("/api/.*")).build();

        watch.stop();
        log.debug("Swagger started in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

}
