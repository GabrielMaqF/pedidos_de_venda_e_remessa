package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO para o bloco "InfoCadastro" de uma Ordem de Serviço do OMIE.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoCadastroDTO {

	@JsonProperty("dDtInc")
	private String dataInclusao;

	@JsonProperty("cHrInc")
	private String horaInclusao;

	@JsonProperty("dDtAlt")
	private String dataAlteracao;

	@JsonProperty("cHrAlt")
	private String horaAlteracao;

	@JsonProperty("dDtFat")
	private String dataFaturamento;

	@JsonProperty("cHrFat")
	private String horaFaturamento;

	@JsonProperty("dDtCanc")
	private String dataCancelamento;

	@JsonProperty("cHrCanc")
	private String horaCancelamento;

	@JsonProperty("cAmbiente")
	private String ambiente;
	
	@JsonProperty("cOrigem")
	private String origem;

	private boolean faturada, cancelada;

	@JsonProperty("cFaturada")
	public void setFaturada(String faturadaStr) {
		this.faturada = "S".equalsIgnoreCase(faturadaStr);
	}

	@JsonProperty("cCancelada")
	public void setCancelada(String canceladaStr) {
		this.cancelada = "S".equalsIgnoreCase(canceladaStr);
	}
}
