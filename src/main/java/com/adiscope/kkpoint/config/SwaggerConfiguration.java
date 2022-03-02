package com.adiscope.kkpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket swagger_Main() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("001. main")
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.adiscope.kkpoint"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .useDefaultResponseMessages(false); // 기본으로 세팅되는 200,401,403,404 메시지를 표시 하지 않음
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("킥킥포인트 API Documentation")
                .description("킥킥포인트 앱 개발시 사용되는 서버 API에 대한 연동 문서입니다\nhttps://nwiki.neowiz.com/pages/viewpage.action?pageId=103308246").version("1").build();
    }
}
