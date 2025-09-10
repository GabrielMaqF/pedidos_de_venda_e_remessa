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
public class VendedorSharepointDTO extends SharePointItemBaseDTO {

	@JsonProperty("CodigoVDN")
	private Long codigo;

	@JsonProperty("NomeVDN")
	private String nome;

	@JsonProperty("EmailVDN")
	private String email;

	@JsonProperty("NomeFantasiaEmpresa")
	private String nomeFantasiaEmpresa;

	private boolean inativo;

	@JsonProperty("InativoVDN")
	public void setInativo(String inativoStr) {
		this.inativo = "S".equalsIgnoreCase(inativoStr);
	}

}