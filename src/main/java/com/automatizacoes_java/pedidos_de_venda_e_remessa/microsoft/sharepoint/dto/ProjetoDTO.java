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
public class ProjetoDTO extends SharePointItemBaseDTO {

	@JsonProperty("CodigoPJT")
	private Long codigo;

	@JsonProperty("NomePJT")
	private String nome;

	@JsonProperty("NomeFantasiaEmpresa")
	private String nomeFantasiaEmpresa;

	private boolean inativo;

	@JsonProperty("InativoPJT")
	public void setInativo(String inativoStr) {
		this.inativo = "S".equalsIgnoreCase(inativoStr);
	}
}
