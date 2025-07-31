package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "api.microsoft.graph")
@Validated
@Getter @Setter
public class GraphTokenProperties {

	private String urlToken;
	private String clientId;
	private String clientSecret;
	private String scope;
	private String grantType;

//	@PostConstruct
//	public void init() {
//		System.out.println("urlToken: " + urlToken);
//		System.out.println("clientId: " + clientId);
//		System.out.println("clientSecret: " + clientSecret);
//		System.out.println("scope: " + scope);
//		System.out.println("grantType: " + grantType);
//	}

}
