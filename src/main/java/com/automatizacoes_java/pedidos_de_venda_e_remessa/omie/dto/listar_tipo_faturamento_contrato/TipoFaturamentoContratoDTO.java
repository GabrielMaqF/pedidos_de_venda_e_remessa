package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_tipo_faturamento_contrato;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoFaturamentoContratoDTO {

    @JsonProperty("cCodigo")
    private String codigo;

    @JsonProperty("cDescricao")
    private String descricao;
}