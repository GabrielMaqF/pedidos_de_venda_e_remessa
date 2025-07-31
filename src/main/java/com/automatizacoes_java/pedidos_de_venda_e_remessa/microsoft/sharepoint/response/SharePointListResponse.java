package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public abstract class SharePointListResponse<T> {

	private List<T> items;

	@JsonProperty("@odata.nextLink")
	private String nextLink;

	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * Este método abstrato é a chave da solução. Forçamos qualquer classe filha a
	 * implementá-lo, nos dizendo qual é o tipo concreto de 'T'.
	 *
	 * @return A classe do DTO a ser desserializado.
	 */
	protected abstract Class<T> getItemType();

	@JsonProperty("value")
	private void unpackNestedFields(JsonNode valueNode) {
		if (valueNode.isArray()) {
			this.items = StreamSupport.stream(valueNode.spliterator(), false).map(itemNode -> itemNode.path("fields"))
					.map(fieldsNode -> {
						try {
							// Agora usamos o método abstrato para obter a classe real!
							return mapper.treeToValue(fieldsNode, getItemType());
						} catch (Exception e) {
							// Use um logger de verdade aqui (SLF4J)
							System.err.println("Erro ao desserializar DTO do tipo " + getItemType().getSimpleName()
									+ ": " + e.getMessage());
							return null;
						}
					}).filter(item -> item != null).collect(Collectors.toList());
		}
	}
}