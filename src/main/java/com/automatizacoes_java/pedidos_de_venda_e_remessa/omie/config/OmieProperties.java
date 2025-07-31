package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "api.omie")
@Data
public class OmieProperties {
	private String url;
}
