package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmieWebhookDTO {

	@JsonProperty("messageId")
	private String messageId;

	@JsonProperty("topic")
	private String topic;

	@JsonProperty("event")
	private JsonNode event;

	@JsonProperty("author")
	private JsonNode author;

	@JsonProperty("appKey")
	private String appKey;

	@JsonProperty("appHash")
	private String appHash;

	@JsonProperty("origin")
	private String origin;

}
