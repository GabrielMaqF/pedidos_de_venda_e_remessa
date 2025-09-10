package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Data
@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpresaSharepointDTO extends SharePointItemBaseDTO {

	@JsonProperty("AppKey")
	private String appKey;

	@JsonProperty("AppSecret")
	private String appSecret;

	@JsonProperty("Raz_x00e3_oSocial")
	private String razaoSocial;

	@JsonProperty("NomeFantasia")
	private String nomeFantasia;

	@JsonProperty("cnpj")
	private String cnpj;

	@JsonProperty("codigo_empresa")
	private Long codigoEmpresa;

	/*
	 * Campos não fundamentais:
	 *
	 *
	 * private String idConsulta;
	 *
	 * @JsonProperty("@odata.etag") public void setIdConsulta(String etag) { if
	 * (etag != null && etag.contains(",")) { this.idConsulta = etag.split(",")[0];
	 * } else { this.idConsulta = etag; } }
	 *
	 * public String getIdConsulta() { return idConsulta; }
	 *
	 * @JsonProperty("id") private String id;
	 *
	 * @JsonProperty("codigo_empresa") private Long codigoEmpresa;
	 *
	 * @JsonProperty("endereco") private String endereco;
	 *
	 * @JsonProperty("endereco_numero") private String enderecoNumero;
	 *
	 * @JsonProperty("complemento") private String complemento;
	 *
	 * @JsonProperty("bairro") private String bairro;
	 *
	 * @JsonProperty("cidade") private String cidade;
	 *
	 * @JsonProperty("estado") private String estado;
	 *
	 * @JsonProperty("cep") private String cep;
	 *
	 * @JsonProperty("codigo_pais") private String codigoPais;
	 *
	 * @JsonProperty("telefone1_ddd") private String telefoneDdd;
	 *
	 * @JsonProperty("telefone1_numero") private String telefoneNumero;
	 *
	 * @JsonProperty("email") private String email;
	 */
}
