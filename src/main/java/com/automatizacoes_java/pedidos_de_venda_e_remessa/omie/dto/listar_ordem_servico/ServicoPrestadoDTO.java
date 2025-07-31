package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para o bloco "ServicosPrestados" de uma Ordem de Serviço do OMIE.
 * Converte os campos "S"/"N" da API em booleanos.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServicoPrestadoDTO {

	@JsonProperty("nIdItem")
	private Long idItem;

	@JsonProperty("nCodServico")
	private Long codigoServico;

	@JsonProperty("cDescServ")
	private String descricao;

	@JsonProperty("nQtde")
	private BigDecimal quantidade;

	@JsonProperty("nValUnit")
	private BigDecimal valorUnitario;

	@JsonProperty("nSeqItem")
	private Integer sequenciaItem;

	@JsonProperty("cCodCategItem")
	private String codigoCategoriaItem;

	@JsonProperty("cCodServLC116")
	private String codigoServicoLC116;

	@JsonProperty("cCodServMun")
	private String codigoServicoMunicipal;

	@JsonProperty("cTpDesconto")
	private String tipoDesconto;

	@JsonProperty("cTribServ")
	private String tributacaoServico;

	@JsonProperty("nAliqDesconto")
	private BigDecimal aliquotaDesconto;

	@JsonProperty("nValorAcrescimos")
	private BigDecimal valorAcrescimos;

	@JsonProperty("nValorDesconto")
	private BigDecimal valorDesconto;

	@JsonProperty("nValorOutrasRetencoes")
	private BigDecimal valorOutrasRetencoes;

	private boolean naoGerarFinanceiro, reembolso, retemIss;

	@JsonProperty("impostos")
	private ImpostosDTO impostos;

	// --- SETTERS CUSTOMIZADOS PARA CONVERSÃO ---

	@JsonProperty("cNaoGerarFinanceiro")
	public void setNaoGerarFinanceiro(String valor) {
		this.naoGerarFinanceiro = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cReembolso")
	public void setReembolso(String valor) {
		this.reembolso = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cRetemISS")
	public void setRetemIss(String valor) {
		this.retemIss = "S".equalsIgnoreCase(valor);
	}
}
