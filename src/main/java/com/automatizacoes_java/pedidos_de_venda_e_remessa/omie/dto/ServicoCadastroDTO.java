package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServicoCadastroDTO {
	@JsonProperty("cabecalho")
	private CabecalhoServicoDTO cabecalho;

	@JsonProperty("intListar")
	private IntListarServicoDTO intListar;

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class IntListarServicoDTO {
		@JsonProperty("nCodServ")
		private Long codigoServico;

		@JsonProperty("cCodIntServ")
		private String codigoIntegracaoServico;
	}

	@Getter
	@Setter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class CabecalhoServicoDTO {
		@JsonProperty("cDescricao")
		private String descricao;

		@JsonProperty("cCodigo")
		private String codigo;

		@JsonProperty("nPrecoUnit")
		private BigDecimal valorServico;
	}
}