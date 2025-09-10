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
public class DadosBancariosClienteOmieDTO {

	private String agencia;

	@JsonProperty("cChavePix")
	private String chavePix;

	@JsonProperty("codigo_banco")
	private String codigoBanco;

	@JsonProperty("conta_corrente")
	private String contaCorrente;

	@JsonProperty("doc_titular")
	private String docTitular;

	@JsonProperty("nome_titular")
	private String nomeTitular;

	@JsonProperty("transf_padrao")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean transfPadrao;
}