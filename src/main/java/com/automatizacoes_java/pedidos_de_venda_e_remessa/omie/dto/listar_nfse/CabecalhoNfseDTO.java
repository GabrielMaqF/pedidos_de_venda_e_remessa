package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.listar_nfse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CabecalhoNfseDTO {

    @JsonProperty("nCodNF")
    private Long codigoNf;

    @JsonProperty("nNumeroNFSe")
    private String numeroNfse;
}