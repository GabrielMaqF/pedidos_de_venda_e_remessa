package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para o sub-bloco "impostos" dentro de "ServicosPrestados". Converte os
 * campos "S"/"N" da API em booleanos.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImpostosDTO {

	private boolean fixarCofins, fixarCsll, fixarInss, fixarIrrf, fixarIss, fixarPis, retemCofins, retemCsll, retemInss,
			retemIrrf, retemPis;

	@JsonProperty("lDeduzISS")
	private boolean deduzIss;

	// Alíquotas
	@JsonProperty("nAliqCOFINS")
	private BigDecimal aliquotaCofins;
	@JsonProperty("nAliqCSLL")
	private BigDecimal aliquotaCsll;
	@JsonProperty("nAliqINSS")
	private BigDecimal aliquotaInss;
	@JsonProperty("nAliqIRRF")
	private BigDecimal aliquotaIrrf;
	@JsonProperty("nAliqISS")
	private BigDecimal aliquotaIss;
	@JsonProperty("nAliqPIS")
	private BigDecimal aliquotaPis;

	// Redução de Base
	@JsonProperty("nAliqRedBaseCOFINS")
	private BigDecimal aliquotaReducaoBaseCofins;
	@JsonProperty("nAliqRedBaseINSS")
	private BigDecimal aliquotaReducaoBaseInss;
	@JsonProperty("nAliqRedBasePIS")
	private BigDecimal aliquotaReducaoBasePis;

	// Bases e Valores
	@JsonProperty("nBaseISS")
	private BigDecimal baseIss;
	@JsonProperty("nTotDeducao")
	private BigDecimal totalDeducao;
	@JsonProperty("nValorCOFINS")
	private BigDecimal valorCofins;
	@JsonProperty("nValorCSLL")
	private BigDecimal valorCsll;
	@JsonProperty("nValorDeducao")
	private BigDecimal valorDeducao;
	@JsonProperty("nValorDeducaoIRRF")
	private BigDecimal valorDeducaoIrrf;
	@JsonProperty("nValorINSS")
	private BigDecimal valorInss;
	@JsonProperty("nValorIRRF")
	private BigDecimal valorIrrf;
	@JsonProperty("nValorISS")
	private BigDecimal valorIss;
	@JsonProperty("nValorPIS")
	private BigDecimal valorPis;
	// --- SETTERS CUSTOMIZADOS PARA CONVERSÃO ---

	@JsonProperty("cFixarCOFINS")
	public void setFixarCofins(String valor) {
		this.fixarCofins = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cFixarCSLL")
	public void setFixarCsll(String valor) {
		this.fixarCsll = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cFixarINSS")
	public void setFixarInss(String valor) {
		this.fixarInss = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cFixarIRRF")
	public void setFixarIrrf(String valor) {
		this.fixarIrrf = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cFixarISS")
	public void setFixarIss(String valor) {
		this.fixarIss = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cFixarPIS")
	public void setFixarPis(String valor) {
		this.fixarPis = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cRetemCOFINS")
	public void setRetemCofins(String valor) {
		this.retemCofins = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cRetemCSLL")
	public void setRetemCsll(String valor) {
		this.retemCsll = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cRetemINSS")
	public void setRetemInss(String valor) {
		this.retemInss = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cRetemIRRF")
	public void setRetemIrrf(String valor) {
		this.retemIrrf = "S".equalsIgnoreCase(valor);
	}

	@JsonProperty("cRetemPIS")
	public void setRetemPis(String valor) {
		this.retemPis = "S".equalsIgnoreCase(valor);
	}
}
