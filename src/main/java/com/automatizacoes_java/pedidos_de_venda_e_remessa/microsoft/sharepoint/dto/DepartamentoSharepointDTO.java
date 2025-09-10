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
public class DepartamentoSharepointDTO extends SharePointItemBaseDTO {

	@JsonProperty("CodigoDEP")
	private String codigo;

	@JsonProperty("DescricaoDEP")
	private String descricao;

	@JsonProperty("EstruturaDEP")
	private String estrutura;

	@JsonProperty("NomeFantasiaEmpresa")
	private String nomeFantasiaEmpresa;

	private boolean inativo;

	@JsonProperty("InativoDEP")
	public void setInativo(String inativoStr) {
		this.inativo = "S".equalsIgnoreCase(inativoStr);
	}

}