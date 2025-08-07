package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_contrato_servico;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContratoServicoCadastroDTO {

	@JsonProperty("cabecalho")
	private CabecalhoContratoServicoDTO cabecalho;
}