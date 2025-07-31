package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO para o bloco "InformacoesAdicionais" de uma Ordem de Serviço do OMIE.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InformacoesAdicionaisDTO {

	@JsonProperty("cCodCateg")
	private String codigoCategoria;

	@JsonProperty("nCodCC")
	private Long codigoContaCorrente;

	@JsonProperty("nCodProj")
	private Long codigoProjeto;

	@JsonProperty("cNumContrato")
	private String numeroContrato;

	@JsonProperty("cCidPrestServ")
	private String cidadePrestacaoServico;

	@JsonProperty("cDadosAdicNF")
	private String dadosAdicionaisNF;

	@JsonProperty("cContato")
	private String contato;
}
