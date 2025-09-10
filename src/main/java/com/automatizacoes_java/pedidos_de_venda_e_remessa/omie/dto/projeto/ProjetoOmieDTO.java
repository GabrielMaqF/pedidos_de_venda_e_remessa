package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.projeto;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.StringToBooleanDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjetoOmieDTO {

	private String codInt, nome;
	private Long codigo;

	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean inativo;

}
