package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente;

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
}
