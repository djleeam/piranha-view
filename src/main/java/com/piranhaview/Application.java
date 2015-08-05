package com.piranhaview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.fasterxml.classmate.TypeResolver;

@SpringBootApplication
@EnableSwagger2
public class Application {

	@Autowired
	private TypeResolver typeResolver;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket swaggerSpringMvcPlugin() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("piranha-view-api")
				.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.regex("/api.*"))
					.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Piranha View Api Documentation", "Piranha View Api Documentation", "1.0", "urn:tos",
		          "ltruong0968@gmail.com", "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
	}
}
