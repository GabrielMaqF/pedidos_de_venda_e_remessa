package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_contrato_servico;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CabecalhoContratoServicoDTO {

	@JsonProperty("nCodCtr")
	private Long codigoContrato;

	@JsonProperty("cNumCtr")
	private String numeroContrato;

	@JsonProperty("nCodCli")
	private Long codigoCliente;

	@JsonProperty("dVigInicial")
	private String dataVigenciaInicial;

	@JsonProperty("dVigFinal")
	private String dataVigenciaFinal;

	@JsonProperty("cTipoFat")
	private String tipoFaturamento;

	@JsonProperty("nDiaFat")
	private Integer diaFaturamento;
}