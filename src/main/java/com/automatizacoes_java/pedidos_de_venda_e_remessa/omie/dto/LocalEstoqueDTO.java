// src/main/java/com/automatizacoes_java/pedidos_de_venda_e_remessa/omie/dto/LocalEstoqueDTO.java
package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.StringToBooleanDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Espelha o payload da OMIE para Local de Estoque.
 * Mantém datas/horas como String (formato dd/MM/yyyy e HH:mm:ss) exatamente como vem.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalEstoqueDTO {

	@JsonProperty("codigo")
    private String codigo;

    @JsonProperty("codigo_cliente")
    private Long codigoCliente;

    @JsonProperty("codigo_local_estoque")
    private Long codigoLocalEstoque;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("dispConsumoOP")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean dispConsumoOP;

    @JsonProperty("dispOrdemProducao")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean dispOrdemProducao;

    @JsonProperty("dispRemessa")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean dispRemessa;

    @JsonProperty("dispVenda")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean dispVenda;

    @JsonProperty("inativo")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean inativo;

    @JsonProperty("padrao")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean padrao;

    @JsonProperty("tipo")
    private String tipo;

    // Mantidos como String conforme recebido pela OMIE
    @JsonProperty("dInc")
    private String dInc;

    @JsonProperty("hInc")
    private String hInc;

    @JsonProperty("dAlt")
    private String dAlt;

    @JsonProperty("hAlt")
    private String hAlt;

    @JsonProperty("uInc")
    private String uInc;

    @JsonProperty("uAlt")
    private String uAlt;
}
