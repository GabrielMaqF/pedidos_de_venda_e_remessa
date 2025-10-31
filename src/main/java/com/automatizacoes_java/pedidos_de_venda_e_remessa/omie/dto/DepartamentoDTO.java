package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.StringToBooleanDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartamentoDTO {

	@JsonIgnoreProperties("codigo")
	private String codigo;

	@JsonIgnoreProperties("descricao")
	private String descricao;

	@JsonIgnoreProperties("estrutura")
	private String estrutura;

	@JsonIgnoreProperties("inativo")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean inativo;

	@JsonIgnoreProperties("nivel_totalizador")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean nivelTotalizador;
}
