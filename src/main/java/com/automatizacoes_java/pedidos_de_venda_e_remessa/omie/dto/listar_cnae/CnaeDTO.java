package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_cnae;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CnaeDTO {

	@JsonProperty("nCodigo")
	private String codigo;

	@JsonProperty("cDescricao")
	private String descricao;

	@JsonProperty("cEstrutura")
	private String estrutura;
}