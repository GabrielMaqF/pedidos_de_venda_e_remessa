package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.config.OmieProperties;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.request.ListarOsParamsDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.request.OmieRequestPayload;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieFaultResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@Service
public class OmieApiClientService {

	private static final Logger logger = LoggerFactory.getLogger(OmieApiClientService.class);
	private static final String NO_RECORDS_FAULT_CODE = "SOAP-ENV:Client-5113";

	private final WebClient.Builder webClientBuilder;
	private final OmieProperties omieProperties;
	private WebClient webClient;

	// Injeção de dependência via construtor (Best Practice)
	public OmieApiClientService(WebClient.Builder webClientBuilder, OmieProperties omieProperties) {
		this.webClientBuilder = webClientBuilder;
		this.omieProperties = omieProperties;
	}

	@PostConstruct
	private void init() {
		this.webClient = webClientBuilder.baseUrl(omieProperties.getUrl()).build();
	}

	public CompletableFuture<OmieListarOsResponse> listarPrimeiraPaginaOs(EmpresaEntity empresa) {
		logger.info("Buscando primeira página de OS para a empresa: {}", empresa.getNomeFantasia());

		var params = new ListarOsParamsDTO(1, 50, "N");
		var payload = new OmieRequestPayload<>("ListarOS", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/servicos/os/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarOsResponse.class).onErrorResume(WebClientResponseException.class, ex -> {
					// Tenta desserializar o corpo do erro para o nosso DTO de falha
					OmieFaultResponse fault = ex.getResponseBodyAs(OmieFaultResponse.class);

					// Verifica se o erro é o específico de "nenhum registro encontrado"
					if (fault != null && NO_RECORDS_FAULT_CODE.equals(fault.getFaultcode())) {
						logger.info(
								"API OMIE informou que não há registros para a empresa '{}'. Retornando resposta vazia.",
								empresa.getNomeFantasia());
						// Transforma o erro em uma resposta de sucesso com uma lista vazia
						return Mono.just(createEmptyResponse());
					}
					// Para todos os outros erros, propaga a exceção original
					return Mono.error(ex);
				}).toFuture().exceptionally(ex -> {
					logger.error("Erro final ao processar a chamada para a API OMIE para a empresa '{}'",
							empresa.getNomeFantasia(), ex);
					throw new RuntimeException(
							"Erro ao consultar Ordens de Serviço no OMIE para a empresa: " + empresa.getNomeFantasia(),
							ex);
				});
	}

	/**
	 * Cria uma instância padrão de OmieListarOsResponse para representar uma
	 * resposta vazia.
	 */
	private OmieListarOsResponse createEmptyResponse() {
		OmieListarOsResponse emptyResponse = new OmieListarOsResponse();
		emptyResponse.setPagina(1);
		emptyResponse.setTotalDePaginas(0);
		emptyResponse.setTotalDeRegistros(0);
		emptyResponse.setRegistrosPorPagina(0);
		emptyResponse.setOrdensDeServico(Collections.emptyList());
		return emptyResponse;
	}
}

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.config.OmieProperties;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.request.ListarOsParamsDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.request.OmieRequestPayload;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;
//
//import jakarta.annotation.PostConstruct;
//
//@Service
//public class OmieApiClientService {
//
//	private static final Logger logger = LoggerFactory.getLogger(OmieApiClientService.class);
//
//	@Autowired
//	private WebClient.Builder webClientBuilder;
//
//	@Autowired
//	private OmieProperties omieProperties;
//
//	private WebClient webClient;
//
//	@PostConstruct
//	private void init() {
//		this.webClient = webClientBuilder.baseUrl(omieProperties.getUrl()).build();
//	}
//
//	/**
//	 * Busca a primeira página de Ordens de Serviço de uma empresa específica.
//	 * 
//	 * @param empresa A entidade Empresa contendo as credenciais (AppKey e
//	 *                AppSecret).
//	 * @return Um CompletableFuture contendo a resposta da API.
//	 */
//	public CompletableFuture<OmieListarOsResponse> listarPrimeiraPaginaOs(EmpresaEntity empresa) {
//		logger.info("Buscando primeira página de OS para a empresa: {}", empresa.getNomeFantasia());
//
//		// 1. Monta os parâmetros da chamada
//		var params = new ListarOsParamsDTO(1, 50, "N");
//
//		// 2. Monta o corpo completo da requisição
//		var payload = new OmieRequestPayload<>("ListarOS", empresa.getAppKey(), empresa.getAppSecret(),
//				List.of(params));
//
//		// 3. Executa a chamada POST
//		return webClient.post().uri("/servicos/os/") // Endpoint para Ordens de Serviço
//				.bodyValue(payload).retrieve().bodyToMono(OmieListarOsResponse.class).toFuture();
//	}
//}
