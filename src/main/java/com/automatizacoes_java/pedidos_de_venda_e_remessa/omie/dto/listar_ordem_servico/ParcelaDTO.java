package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO para o bloco "Parcelas" de uma Ordem de Serviço do OMIE.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParcelaDTO {

	@JsonProperty("nParcela")
	private Integer numeroParcela;

	@JsonProperty("dDtVenc")
	private String dataVencimento;

	@JsonProperty("nDias")
	private String numeroDias;

	@JsonProperty("nValor")
	private BigDecimal valor;

	@JsonProperty("nPercentual")
	private BigDecimal numeroPercentual;

	private boolean naoGerarBoleto;

	@JsonProperty("nao_gerar_boleto")
	public void setNaoGerarBoleto(String naoGerarBoletoStr) {
		this.naoGerarBoleto = "S".equalsIgnoreCase(naoGerarBoletoStr);
	}
}
