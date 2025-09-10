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
public class ClienteSharepointDTO extends SharePointItemBaseDTO {

	@JsonProperty("CodigoClienteCLT")
	private Long codigo;

	@JsonProperty("CodigoClienteIntegracaoCLT")
	private String codigoIntegracao;

	@JsonProperty("CNPJ_x002d_CLIENTE")
	private String cnpj;

	@JsonProperty("NomeCLT")
	private String nomeFantasia;

	@JsonProperty("RazaoSocialCLT")
	private String razaoSocial;

	@JsonProperty("NomeFantasiaEmpresa")
	private String nomeFantasiaEmpresa;

	@JsonProperty("cidade")
	private String cidade;

	@JsonProperty("estado")
	private String estado;

	@JsonProperty("cep")
	private String cep;

	@JsonProperty("bairro")
	private String bairro;
	
	@JsonProperty("telefone1_numero")
	private String telefone;

	private boolean inativo;

	@JsonProperty("inativo")
	public void setInativo(String inativoStr) {
		this.inativo = "S".equalsIgnoreCase(inativoStr);
	}

}
