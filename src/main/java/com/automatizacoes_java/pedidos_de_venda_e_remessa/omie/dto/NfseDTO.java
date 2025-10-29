package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NfseDTO {

    @JsonProperty("Cabecalho")
    private CabecalhoNfseDTO cabecalho;

    @JsonProperty("OrdemServico")
    private OrdemServicoNfseDTO ordemServico;
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class CabecalhoNfseDTO {

        @JsonProperty("nCodNF")
        private Long codigoNf;

        @JsonProperty("nNumeroNFSe")
        private String numeroNfse;
    }
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class OrdemServicoNfseDTO {

        @JsonProperty("nCodigoOS")
        private Long codigoOs;
    }
}