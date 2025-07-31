package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_ordem_servico;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO para o bloco "Departamentos" de uma Ordem de Serviço do OMIE. Renomeado
 * para DepartamentoOsDTO para evitar conflito com o DTO do SharePoint.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartamentoOsDTO {

	@JsonProperty("cCodDepto")
	private String codigoDepartamento;

	@JsonProperty("nPerc")
	private BigDecimal percentual;

	@JsonProperty("nValor")
	private BigDecimal valor;

	private boolean valorFixo;

	@JsonProperty("nValorFixo")
	public void setValorFixo(String valorFixoStr) {
		this.valorFixo = "S".equalsIgnoreCase(valorFixoStr);
	}
}
