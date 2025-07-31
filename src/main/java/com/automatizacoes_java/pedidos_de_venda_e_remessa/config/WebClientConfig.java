package com.automatizacoes_java.pedidos_de_venda_e_remessa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	WebClient.Builder webClientBuilder() {
		// Define o novo tamanho máximo do buffer. Vamos usar 16MB como um exemplo
		// robusto.
		final int size = 16 * 1024 * 1024;

		final ExchangeStrategies strategies = ExchangeStrategies.builder()
				.codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size)).build();

		return WebClient.builder().exchangeStrategies(strategies);
	}
}