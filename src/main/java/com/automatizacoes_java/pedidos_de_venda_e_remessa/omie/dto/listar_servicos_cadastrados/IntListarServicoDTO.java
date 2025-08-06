package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_servicos_cadastrados;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IntListarServicoDTO {
	@JsonProperty("nCodServ")
	private Long codigoServico;

	@JsonProperty("cCodIntServ")
	private String codigoIntegracaoServico;
}