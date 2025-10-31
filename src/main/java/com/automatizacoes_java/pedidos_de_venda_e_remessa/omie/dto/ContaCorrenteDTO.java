package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContaCorrenteDTO {
	
	@JsonProperty("codigo_banco")
	private String codigoBanco;

	@JsonProperty("descricao")
	private String descricao;
	
	@JsonProperty("nCodCC")
	private Long codigo;
	
	@JsonProperty("valor_limite")
	private BigDecimal valorLimite;
	
}
