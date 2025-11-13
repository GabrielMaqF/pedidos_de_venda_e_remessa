package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto;

import java.math.BigDecimal;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.StringToBooleanDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoDTO {

    @JsonProperty("codigo_produto")
    private Long codigoProduto; // -> chave OMIE (usaremos como 'codigo' da entidade)

    private String codigo; // código textual exibido no OMIE (ex.: "A-0025")

    @JsonProperty("codigo_produto_integracao")
    private String codigoProdutoIntegracao;

    private String descricao;

    @JsonProperty("descr_detalhada")
    private String descricaoDetalhada;

    private String unidade;
    private String ncm;
    private String ean;
    private String marca;
    private String modelo;

    private String cfop;
    private String cst_icms;
    private String csosn_icms;
    private String cst_pis;
    private String cst_cofins;
    private String codigo_beneficio;
    private String motivo_deson_icms;
    private String tipoItem;

    @JsonProperty("aliquota_icms") private BigDecimal aliquotaIcms;
    @JsonProperty("aliquota_pis")  private BigDecimal aliquotaPis;
    @JsonProperty("aliquota_cofins") private BigDecimal aliquotaCofins;
    @JsonProperty("aliquota_ibpt") private BigDecimal aliquotaIbpt;
    @JsonProperty("per_icms_fcp") private BigDecimal perIcmsFcp;

    @JsonProperty("red_base_icms") private BigDecimal redBaseIcms;
    @JsonProperty("red_base_pis")  private BigDecimal redBasePis;
    @JsonProperty("red_base_cofins") private BigDecimal redBaseCofins;

    private BigDecimal valor_unitario;

    private BigDecimal altura;
    private BigDecimal largura;
    private BigDecimal profundidade;
    @JsonProperty("peso_bruto") private BigDecimal pesoBruto;
    @JsonProperty("peso_liq")   private BigDecimal pesoLiq;

    @JsonProperty("estoque_minimo") private BigDecimal estoqueMinimo;
    @JsonProperty("quantidade_estoque") private BigDecimal quantidadeEstoque;

    @JsonProperty("dias_crossdocking") private Integer diasCrossdocking;
    @JsonProperty("dias_garantia")     private Integer diasGarantia;
    @JsonProperty("lead_time")         private Integer leadTime;

    private String cest;
    @JsonProperty("descricao_familia") private String descricaoFamilia;
    @JsonProperty("codigo_familia")    private Long codigoFamilia;
    @JsonProperty("codInt_familia")    private String codIntFamilia;

    @JsonProperty("obs_internas") private String obsInternas;

    @JsonProperty("produto_lote")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean produtoLote;

    @JsonProperty("produto_variacao")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean produtoVariacao;

    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean bloqueado;

    @JsonProperty("bloquear_exclusao")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean bloquearExclusao;

    @JsonProperty("exibir_descricao_nfe")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean exibirDescricaoNfe;

    @JsonProperty("exibir_descricao_pedido")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean exibirDescricaoPedido;

    @JsonProperty("importado_api")
    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean importadoApi;

    @JsonDeserialize(using = StringToBooleanDeserializer.class)
    private Boolean inativo;

    private InfoProdutoOmieDTO info;

    @JsonProperty("recomendacoes_fiscais")
    private RecomendacoesFiscaisProdutoOmieDTO recomendacoesFiscais;

    // -------- blocos aninhados --------

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InfoProdutoOmieDTO {
        @JsonProperty("dAlt") private String dAlt;
        @JsonProperty("dInc") private String dInc;
        @JsonProperty("hAlt") private String hAlt;
        @JsonProperty("hInc") private String hInc;
        @JsonProperty("uAlt") private String uAlt;
        @JsonProperty("uInc") private String uInc;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RecomendacoesFiscaisProdutoOmieDTO {

        @JsonProperty("cnpj_fabricante")
        private String cnpjFabricante;

        @JsonProperty("cupom_fiscal")
        @JsonDeserialize(using = StringToBooleanDeserializer.class)
        private Boolean cupomFiscal;

        @JsonProperty("id_cest")
        private String idCest;

        @JsonProperty("id_preco_tabelado")
        private Long idPrecoTabelado;

        @JsonProperty("indicador_escala")
        private String indicadorEscala;

        @JsonProperty("market_place")
        @JsonDeserialize(using = StringToBooleanDeserializer.class)
        private Boolean marketPlace;

        @JsonProperty("origem_mercadoria")
        private String origemMercadoria;
    }
}
