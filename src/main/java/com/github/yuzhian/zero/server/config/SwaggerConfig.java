package com.github.yuzhian.zero.server.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * @author yuzhian
 * @since 2020-10-31
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .globalRequestParameters(Collections.singletonList(tokenRequestParameter()))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot项目基础服务文档")
                .description("SpringBoot项目基础服务后端接口API")
                .contact(new Contact("yuzhian", "http://github.com/yuzhian", "yu97@live.com"))
                .version("0.0.1-SNAPSHOT")
                .build();
    }

    private RequestParameter tokenRequestParameter() {
        return new RequestParameterBuilder()
                .name("token")
                .description("令牌")
                .in("header")
                .required(false)
                .build();
    }
}
