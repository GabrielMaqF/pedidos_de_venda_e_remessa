package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO específico para os parâmetros da chamada "ListarOS" da API do OMIE.
 */
@Data
@AllArgsConstructor
public class ListarOsParamsDTO {

	@JsonProperty("pagina")
	private int pagina;

	@JsonProperty("registros_por_pagina")
	private int registrosPorPagina;

	@JsonProperty("apenas_importado_api")
	private String apenasImportadoApi;
}
