package com.planetrush.planetrush.core.config.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition(
	info = @Info(
		title = "플래닛러쉬 API",
		description = "<h3>플래닛러쉬 API</h3>",
		version = "v1",
		contact = @Contact(
			name = "김도은(팀장), 김경민, 박정의, 심종한, 안홍찬, 이강현")
	)
)
@Configuration
public class SwaggerConfig {

	private final String AUTHORIZATION = "Authorization";
	private final String BEARER_TOKEN = "Bearer Token";

	@Bean
	public OpenAPI api() {
		SecurityScheme apiKey = new SecurityScheme()
			.type(SecurityScheme.Type.APIKEY)
			.in(SecurityScheme.In.HEADER)
			.name(AUTHORIZATION);

		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList(BEARER_TOKEN);

		return new OpenAPI()
			.components(new Components().addSecuritySchemes(BEARER_TOKEN, apiKey))
			.addSecurityItem(securityRequirement);
	}

	@Bean
	public GroupedOpenApi allApi() {
		return GroupedOpenApi.builder().group("all").pathsToMatch("/**").build();
	}

	@Bean
	public GroupedOpenApi memberApi() {
		return GroupedOpenApi.builder().group("member").pathsToMatch("/api/**/members/**").build();
	}

	@Bean
	public GroupedOpenApi planetApi() {
		return GroupedOpenApi.builder().group("planet").pathsToMatch("/api/**/planets/**").build();
	}

}
