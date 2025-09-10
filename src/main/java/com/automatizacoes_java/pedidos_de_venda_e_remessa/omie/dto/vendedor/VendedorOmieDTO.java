package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.vendedor;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.StringToBooleanDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendedorOmieDTO {
	
	private Long codigo;
	private String codInt, nome, email;
	private Double comissao;
	
	public void setComissao(String comissao) {
		this.comissao = Double.parseDouble(comissao);
	}
	
	@JsonProperty("fatura_pedido")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean faturaPedido;
	
	@JsonProperty("visualiza_pedido")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean visualizaPedido;

	@JsonProperty("inativo")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean inativo;
}
