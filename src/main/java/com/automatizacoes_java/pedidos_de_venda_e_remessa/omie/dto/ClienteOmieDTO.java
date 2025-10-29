package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto;

import java.util.List;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.util.StringToBooleanDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteOmieDTO {

	private String bairro;
	private String cep;
	private String cidade;
	private String email;
	private String cnae;
	private String contato;

	@JsonProperty("bloquear_faturamento")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean bloquearFaturamento;

	@JsonProperty("cidade_ibge")
	private String cidadeIbge;

	@JsonProperty("cnpj_cpf")
	private String cnpjCpf;

	@JsonProperty("codigo_cliente_integracao")
	private String codigoClienteIntegracao;

	@JsonProperty("codigo_cliente_omie")
	private Long codigoClienteOmie;

	@JsonProperty("codigo_pais")
	private String codigoPais;

	private String complemento;

	@JsonProperty("dadosBancarios")
	private DadosBancariosClienteOmieDTO dadosBancarios;

	private String endereco;

	@JsonProperty("enderecoEntrega")
	private EnderecoEntregaClienteOmieDTO enderecoEntrega;

	@JsonProperty("endereco_numero")
	private String enderecoNumero;

	@JsonProperty("enviar_anexos")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean enviarAnexos;

	private String estado;

	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean exterior;

	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean inativo;

	private InfoClienteOmieDTO info;

	@JsonProperty("inscricao_estadual")
	private String inscricaoEstadual;

	@JsonProperty("inscricao_municipal")
	private String inscricaoMunicipal;

	@JsonProperty("nome_fantasia")
	private String nomeFantasia;

	@JsonProperty("pessoa_fisica")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean pessoaFisica;

	@JsonProperty("razao_social")
	private String razaoSocial;

	private RecomendacoesClienteOmieDTO recomendacoes;

	private List<TagClienteOmieDTO> tags;

	@JsonProperty("telefone1_ddd")
	private String telefone1Ddd;

	@JsonProperty("telefone1_numero")
	private String telefone1Numero;

	@JsonProperty("optante_simples_nacional")
	@JsonDeserialize(using = StringToBooleanDeserializer.class)
	private Boolean optanteSimplesNacional;
	
	@Data
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class DadosBancariosClienteOmieDTO {

		private String agencia;

		@JsonProperty("cChavePix")
		private String chavePix;

		@JsonProperty("codigo_banco")
		private String codigoBanco;

		@JsonProperty("conta_corrente")
		private String contaCorrente;

		@JsonProperty("doc_titular")
		private String docTitular;

		@JsonProperty("nome_titular")
		private String nomeTitular;

		@JsonProperty("transf_padrao")
		@JsonDeserialize(using = StringToBooleanDeserializer.class)
		private Boolean transfPadrao;
	}
	
	@Data
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class EnderecoEntregaClienteOmieDTO {

		@JsonProperty("entBairro")
		private String entBairro;

		@JsonProperty("entCEP")
		private String entCEP;

		@JsonProperty("entCidade")
		private String entCidade;

		@JsonProperty("entCnpjCpf")
		private String entCnpjCpf;

		@JsonProperty("entComplemento")
		private String entComplemento;

		@JsonProperty("entEndereco")
		private String entEndereco;

		@JsonProperty("entEstado")
		private String entEstado;

		@JsonProperty("entNumero")
		private String entNumero;

		@JsonProperty("entRazaoSocial")
		private String entRazaoSocial;
	}
	
	@Data
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class InfoClienteOmieDTO {

		@JsonProperty("cImpAPI")
		@JsonDeserialize(using = StringToBooleanDeserializer.class)
		private Boolean cImpAPI;

		@JsonProperty("dAlt")
		private String dAlt;

		@JsonProperty("dInc")
		private String dInc;

		@JsonProperty("hAlt")
		private String hAlt;

		@JsonProperty("hInc")
		private String hInc;

		@JsonProperty("uAlt")
		private String uAlt;

		@JsonProperty("uInc")
		private String uInc;
	}
	
	@Data
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class RecomendacoesClienteOmieDTO {

		@JsonProperty("gerar_boletos")
		@JsonDeserialize(using = StringToBooleanDeserializer.class)
		private Boolean gerarBoletos;

		@JsonProperty("tipo_assinante")
		private String tipoAssinante;

		@JsonProperty("email_fatura")
		private String emailFatura;

		@JsonProperty("numero_parcelas")
		private String numeroParcelas;
	}
	
	@Data
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class TagClienteOmieDTO {
		private String tag;
	}
}
