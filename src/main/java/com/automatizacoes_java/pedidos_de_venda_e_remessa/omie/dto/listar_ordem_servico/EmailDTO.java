package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para o bloco "Email" de uma Ordem de Serviço do OMIE. Converte os campos
 * "S"/"N" da API em booleanos.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDTO {

	private boolean enviaBoleto;
	private boolean enviaLink;
	private boolean enviaPix;
	private boolean enviaRecibo;
	private boolean enviaViaUnica;

	@JsonProperty("cEnviarPara")
	private String enviarPara;

	// --- SETTERS CUSTOMIZADOS PARA CONVERSÃO ---

	@JsonProperty("cEnvBoleto")
	public void setEnviaBoleto(String enviaBoleto) {
		this.enviaBoleto = "S".equalsIgnoreCase(enviaBoleto);
	}

	@JsonProperty("cEnvLink")
	public void setEnviaLink(String enviaLink) {
		this.enviaLink = "S".equalsIgnoreCase(enviaLink);
	}

	@JsonProperty("cEnvPix")
	public void setEnviaPix(String enviaPix) {
		this.enviaPix = "S".equalsIgnoreCase(enviaPix);
	}

	@JsonProperty("cEnvRecibo")
	public void setEnviaRecibo(String enviaRecibo) {
		this.enviaRecibo = "S".equalsIgnoreCase(enviaRecibo);
	}

	@JsonProperty("cEnvViaUnica")
	public void setEnviaViaUnica(String enviaViaUnica) {
		this.enviaViaUnica = "S".equalsIgnoreCase(enviaViaUnica);
	}
}
