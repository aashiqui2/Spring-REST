package com.springrest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {
	
	@Bean
	RestTemplate restTemplate() {
//		By default, RestTemplate uses SimpleClientHttpRequestFactory, which has limited HTTP features.
//		You are switching to HttpComponentsClientHttpRequestFactory (based on Apache HttpClient).
		return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	}

}
