package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoEntregaClienteOmieDTO {

	@JsonProperty("entBairro")
	private String entBairro;

	@JsonProperty("entCEP")
	private String entCEP;

	@JsonProperty("entCidade")
	private String entCidade;

	@JsonProperty("entCnpjCpf")
	private String entCnpjCpf;

	@JsonProperty("entComplemento")
	private String entComplemento;

	@JsonProperty("entEndereco")
	private String entEndereco;

	@JsonProperty("entEstado")
	private String entEstado;

	@JsonProperty("entNumero")
	private String entNumero;

	@JsonProperty("entRazaoSocial")
	private String entRazaoSocial;
}