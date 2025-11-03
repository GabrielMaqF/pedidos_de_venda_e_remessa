package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response;

import java.util.List;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.ServicoCadastroDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmieListarServicoCadastradoResponse {
	@JsonProperty("nPagina")
	private int pagina;

	@JsonProperty("nTotPaginas")
	private int totalDePaginas;

	@JsonProperty("cadastros")
	private List<ServicoCadastroDTO> servicos;
}