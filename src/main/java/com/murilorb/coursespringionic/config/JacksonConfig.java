package com.murilorb.coursespringionic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.murilorb.coursespringionic.domains.BarcodePayment;
import com.murilorb.coursespringionic.domains.CardPayment;

/*
 * Classe de configuracao (@Configuration) para inicio da execução de
 * informacoes (@Bean) para registrar as subclasses
 */
@Configuration
public class JacksonConfig {

	// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-without-hinting-the-pare
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(CardPayment.class);
				objectMapper.registerSubtypes(BarcodePayment.class);
				super.configure(objectMapper);
			}
		};
		return builder;
	}

}