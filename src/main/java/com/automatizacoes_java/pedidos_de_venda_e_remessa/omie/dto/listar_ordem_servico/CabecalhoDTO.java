package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO para o bloco "Cabecalho" de uma Ordem de Serviço do OMIE. Contém os dados
 * principais da OS.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CabecalhoDTO {

	@JsonProperty("nCodOS")
	private Long codigoOs;

	@JsonProperty("cNumOS")
	private String numeroOs;

	@JsonProperty("nCodCli")
	private Long codigoCliente;

	@JsonProperty("dDtPrevisao")
	private String dataPrevisao;

	@JsonProperty("nValorTotal")
	private BigDecimal valorTotal;

	@JsonProperty("cEtapa")
	private String etapa;

	// --- CAMPOS ADICIONADOS ---

	@JsonProperty("cCodIntOS")
	private String codigoIntegracaoOs;

	@JsonProperty("cCodParc")
	private String codigoParcela;

	@JsonProperty("nQtdeParc")
	private Integer quantidadeParcelas;

	@JsonProperty("nValorTotalImpRet")
	private BigDecimal valorTotalImpostosRetidos;
}
