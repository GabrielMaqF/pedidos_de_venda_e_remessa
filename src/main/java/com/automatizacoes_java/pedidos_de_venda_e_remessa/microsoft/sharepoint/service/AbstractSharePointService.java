package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.reactive.function.client.WebClient;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config.GraphProperties;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.dto.SharePointItemBaseDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.sharepoint.response.SharePointListResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.token.TokenMicrosoftGraph;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

public abstract class AbstractSharePointService<T_DTO extends SharePointItemBaseDTO, T_RESPONSE extends SharePointListResponse<T_DTO>> {

	@Autowired
	private TokenMicrosoftGraph tokenService;

	@Autowired
	protected GraphProperties graphProperties; // protected para acesso na filha se necessário

	@Autowired
	private WebClient.Builder webClientBuilder;

	private WebClient webClient;

	@PostConstruct
	private void init() {
		this.webClient = this.webClientBuilder.build();
	}

	private static final ObjectMapper mapper = new ObjectMapper();

	// 1. Método abstrato para a classe filha nos dizer qual ID de lista usar
	protected abstract String getListId();

	// 2. Método abstrato para a classe filha nos dizer qual classe de Response usar
	// na desserialização
	protected abstract Class<T_RESPONSE> getResponseClass();

	// 3. Método abstrato para a classe filha nos dizer qual classe de DTO usar na
	// desserialização individual
	protected abstract Class<T_DTO> getDtoClass();

	private String montarBaseUrl() {
		return "https://graph.microsoft.com/v1.0/sites/" + graphProperties.getSiteOmie() + "/lists/" + getListId()
				+ "/items?expand=fields&$top=9999";//
	}

	private String montarUrlParaItem(String listItemId) {
		return "https://graph.microsoft.com/v1.0/sites/" + graphProperties.getSiteOmie() + "/lists/" + getListId()
				+ "/items/" + listItemId + "?expand=fields";
	}

	/**
	 * Método público que inicia o processo de listagem completa com paginação.
	 */

	@Async
	public CompletableFuture<List<T_DTO>> listarTodos() {
		// Primeiro, obtemos o token.
		return tokenService.getToken().thenCompose(token -> {
			// Em seguida, iniciamos a busca recursiva pela primeira página.
			// Começamos com uma lista vazia de itens acumulados.
			return fetchPageAndContinue(montarBaseUrl(), token, new java.util.ArrayList<>());
		});
	}

	/**
	 * Método auxiliar recursivo que busca uma página e continua para a próxima se
	 * ela existir.
	 *
	 * @param url              URL da página a ser buscada (pode ser a base ou um
	 *                         nextLink)
	 * @param token            O token de autenticação
	 * @param accumulatedItems A lista de itens coletados até agora
	 * @return Um CompletableFuture que será concluído com a lista final de todos os
	 *         itens
	 */
	private CompletableFuture<List<T_DTO>> fetchPageAndContinue(String url, String token,
			List<T_DTO> accumulatedItems) {

		return this.webClient.get().uri(url).header("Authorization", "Bearer " + token).retrieve()
				.bodyToMono(getResponseClass()).toFuture().thenCompose(response -> {
					// Adiciona os itens da página atual à lista acumulada
					accumulatedItems.addAll(response.getItems());

					// Verifica se existe uma próxima página
					if (response.getNextLink() != null) {
						// CASO RECURSIVO: Chama a si mesmo com a URL da próxima página
						System.out.println("Buscando próxima página para " + getDtoClass().getSimpleName() + "...");
						return fetchPageAndContinue(response.getNextLink(), token, accumulatedItems);
					} else {
						// CASO BASE: Não há mais páginas, retorna a lista completa
						System.out.println("Paginação concluída para " + getDtoClass().getSimpleName() + ". Total de "
								+ accumulatedItems.size() + " itens.");
						return CompletableFuture.completedFuture(accumulatedItems);
					}
				});
	}

	@Async
	public CompletableFuture<T_DTO> buscarPorId(String listItemId) {
		return tokenService.getToken()
				.thenCompose(token -> this.webClient.get().uri(montarUrlParaItem(listItemId))
						.header("Authorization", "Bearer " + token).retrieve().bodyToMono(JsonNode.class).toFuture()
						.thenApply(jsonNode -> {
							try {
								return mapper.treeToValue(jsonNode.path("fields"), getDtoClass());
							} catch (Exception e) {
								System.err.println("Erro ao desserializar DTO individual: " + e.getMessage());
								return null;
							}
						}));
	}
}