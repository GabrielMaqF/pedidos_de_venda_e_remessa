package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response;

import java.util.List;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ProdutoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmieListarProdutoResponse {

	@JsonProperty("pagina")
	private int pagina;

	@JsonProperty("total_de_paginas")
	private int totalDePaginas;

	@JsonProperty("registros")
	private int registrosPorPagina;

	@JsonProperty("total_de_registros")
	private int totalDeRegistros;

	@JsonProperty("produto_servico_cadastro")
	private List<ProdutoDTO> produtoServicoCadastro;
}
