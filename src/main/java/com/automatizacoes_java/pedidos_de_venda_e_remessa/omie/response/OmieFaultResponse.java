package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Representa a estrutura de erro retornada pela API OMIE quando uma falha
 * ocorre.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmieFaultResponse {

	@JsonProperty("faultstring")
	private String faultstring;

	@JsonProperty("faultcode")
	private String faultcode;
}