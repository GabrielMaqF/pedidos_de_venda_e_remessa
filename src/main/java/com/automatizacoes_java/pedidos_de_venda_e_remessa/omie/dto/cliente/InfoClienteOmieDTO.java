package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.StringToBooleanDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoClienteOmieDTO {

	@JsonProperty("cImpAPI")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean cImpAPI;

	@JsonProperty("dAlt")
	private String dAlt;

	@JsonProperty("dInc")
	private String dInc;

	@JsonProperty("hAlt")
	private String hAlt;

	@JsonProperty("hInc")
	private String hInc;

	@JsonProperty("uAlt")
	private String uAlt;

	@JsonProperty("uInc")
	private String uInc;
}