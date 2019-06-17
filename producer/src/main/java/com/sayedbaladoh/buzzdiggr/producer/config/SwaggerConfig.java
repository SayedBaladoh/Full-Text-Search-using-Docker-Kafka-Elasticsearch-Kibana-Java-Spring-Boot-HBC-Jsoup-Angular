package com.sayedbaladoh.buzzdiggr.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket Api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.basePackage("com.sayedbaladoh.buzzdiggr.producer.controller"))
				// .paths(regex("/api.*"))
				.build()
                .apiInfo(metaData());
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("Producer Service API")
				.description("Producer service to collect, clean data collected from different sources, and send it to Kafka producer")
				.version("1.0.0")
//				.license("Apache License Version 2.0")
//				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.contact(
						new Contact("Sayed Baladoh", "https://www.linkedin.com/in/sayed-baladoh-227aa66b/", "sayedbaladoh@yahoo.com"))
				.build();
	}
	
}
