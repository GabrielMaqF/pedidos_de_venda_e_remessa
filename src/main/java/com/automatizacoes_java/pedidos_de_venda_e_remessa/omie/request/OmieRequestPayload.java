package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Representa a estrutura genérica do corpo de uma requisição para a API do
 * OMIE.
 */
@Data
@AllArgsConstructor
public class OmieRequestPayload<T> {

	@JsonProperty("call")
	private String call;

	@JsonProperty("app_key")
	private String appKey;

	@JsonProperty("app_secret")
	private String appSecret;

	@JsonProperty("param")
	private List<T> param;
}
