package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_servicos_cadastrados;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServicoCadastroDTO {
	@JsonProperty("cabecalho")
	private CabecalhoServicoDTO cabecalho;

	@JsonProperty("intListar")
	private IntListarServicoDTO intListar;
}