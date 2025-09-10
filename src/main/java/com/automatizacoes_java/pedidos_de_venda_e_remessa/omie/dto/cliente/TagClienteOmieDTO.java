package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagClienteOmieDTO {
	private String tag;
}