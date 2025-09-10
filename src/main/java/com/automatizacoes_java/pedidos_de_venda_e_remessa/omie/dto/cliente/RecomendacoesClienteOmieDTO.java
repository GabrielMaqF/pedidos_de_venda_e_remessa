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
public class RecomendacoesClienteOmieDTO {

	@JsonProperty("gerar_boletos")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean gerarBoletos;

	@JsonProperty("tipo_assinante")
	private String tipoAssinante;

	@JsonProperty("email_fatura")
	private String emailFatura;

	@JsonProperty("numero_parcelas")
	private String numeroParcelas;
}