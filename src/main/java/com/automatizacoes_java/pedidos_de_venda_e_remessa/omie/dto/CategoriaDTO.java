package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.StringToBooleanDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaDTO {

	@JsonProperty("codigo")
	private String codigo;

	@JsonProperty("descricao")
	private String descricao;

	@JsonProperty("descricao_padrao")
	private String descricaoPadrao;

	@JsonProperty("tipo_categoria")
	private String tipoCategoria;

	@JsonProperty("conta_inativa")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean inativo;

	@JsonProperty("conta_despesa")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean contaDespesa;

	@JsonProperty("conta_receita")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean contaReceita;

	@JsonProperty("totalizadora")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean totalizadora;

	@JsonProperty("transferencia")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean transferencia;

	@JsonProperty("nao_exibir")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean naoExibir;

	@JsonProperty("definida_pelo_usuario")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean definidaPeloUsuario;

	@JsonProperty("id_conta_contabil")
	private Long idContaContabil;

	@JsonProperty("tag_conta_contabil")
	private String tagContaContabil;

	@JsonProperty("natureza")
	private String natureza;

	@JsonProperty("codigo_dre")
	private String codigoDre;

	@JsonProperty("categoria_superior")
	private String categoriaSuperior;

	@JsonProperty("dadosDRE")
	private DadosDRECategoriaDTO dadosDre;

	@Data
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class DadosDRECategoriaDTO {

		@JsonProperty("codigoDRE")
		private String codigoDRE;

		@JsonProperty("descricaoDRE")
		private String descricaoDRE;

		@JsonProperty("nivelDRE")
		private Integer nivelDRE;

		@JsonProperty("naoExibirDRE")
		@JsonDeserialize(using = StringToBooleanDeserializer.class)
		private Boolean naoExibirDRE;

		@JsonProperty("sinalDRE")
		@JsonDeserialize(using = StringToBooleanDeserializer.class)
		private Boolean sinalDRE;

		@JsonProperty("totalizaDRE")
		@JsonDeserialize(using = StringToBooleanDeserializer.class)
		private Boolean totalizaDRE;
	}

}
