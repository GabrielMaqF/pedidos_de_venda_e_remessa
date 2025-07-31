package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SharePointItemBaseDTO {
	/*
	 * private String idConsulta;
	 *
	 * @JsonProperty("@odata.etag") public void setIdConsulta(String etag) { if
	 * (etag != null && etag.contains(",")) { this.idConsulta = etag.split(",")[0];
	 * } else { this.idConsulta = etag; } }
	 *
	 * public String getIdConsulta() { return idConsulta; }
	 */
	@JsonProperty("id")
	private String id;

}
