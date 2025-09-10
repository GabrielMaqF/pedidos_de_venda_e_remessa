package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaSharepointDTO extends SharePointItemBaseDTO {

	@JsonProperty("DescricaoCTG")
	private String descricao;

	@JsonProperty("CodigoCTG")
	private String codigo;

	@JsonProperty("NomeFantasiaEmpresa")
	private String nomeFantasiaEmpresa;

	private boolean inativo, receita, despesa, transferencia, totalizadora;

	@JsonProperty("ContaInativaCTG")
	public void setInativo(String inativoStr) {
		this.inativo = "S".equalsIgnoreCase(inativoStr);
	}

	@JsonProperty("ContaReceitaCTG")
	public void setReceita(String receitaStr) {
		this.receita = "S".equalsIgnoreCase(receitaStr);
	}

	@JsonProperty("ContaDespesaCTG")
	public void setDespesa(String despesaStr) {
		this.despesa = "S".equalsIgnoreCase(despesaStr);
	}

	@JsonProperty("TransferenciaCTG")
	public void setTransferencia(String transferenciaStr) {
		this.transferencia = "S".equalsIgnoreCase(transferenciaStr);
	}

	@JsonProperty("TotalizadoraCTG")
	public void setTotalizadora(String totalizadoraStr) {
		this.totalizadora = "S".equalsIgnoreCase(totalizadoraStr);
	}

}