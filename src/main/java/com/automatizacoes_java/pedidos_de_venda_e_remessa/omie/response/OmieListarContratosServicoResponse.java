package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response;

import java.util.List;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_contrato_servico.ContratoServicoCadastroDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data

@JsonIgnoreProperties(ignoreUnknown = true)

public class OmieListarContratosServicoResponse {

	@JsonProperty("pagina")

	private int pagina;

	@JsonProperty("total_de_paginas")

	private int totalDePaginas;

	@JsonProperty("contratoCadastro")

	private List<ContratoServicoCadastroDTO> contratos;

}