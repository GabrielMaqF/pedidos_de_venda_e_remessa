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
public class ContaCorrenteDTO extends SharePointItemBaseDTO {

	@JsonProperty("nCodCC_CC")
	private Long codigo;

	@JsonProperty("DescricaoCC")
	private String descricao;

	@JsonProperty("NomeFantasiaEmpresa")
	private String nomeFantasiaEmpresa;

}