
package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarCnaeResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarContratosServicoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarNfseResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarServicosResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarTiposFaturamentoResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor // Utilizando injeção via construtor com Lombok
public class OmieApiClientService {

	private static final Logger logger = LoggerFactory.getLogger(OmieApiClientService.class);
	private static final String NO_RECORDS_FAULT_CODE = "SOAP-ENV:Client-5113";
	private static final int REGISTROS_POR_PAGINA = 1000; // Padrão da API para performance

	private final WebClient.Builder webClientBuilder;
	private final OmieProperties omieProperties;
	private WebClient webClient;

	@PostConstruct
	private void init() {
		this.webClient = webClientBuilder.baseUrl(omieProperties.getUrl()).build();
	}

    public CompletableFuture<OmieListarOsResponse> listarOsPorPagina(EmpresaEntity empresa, int pagina) {
        var params = new ListarOsParamsDTO(pagina, REGISTROS_POR_PAGINA, "N");
        var payload = new OmieRequestPayload<>("ListarOS", empresa.getAppKey(), empresa.getAppSecret(), List.of(params));
        return webClient.post().uri("/servicos/os/").bodyValue(payload).retrieve()
                .bodyToMono(OmieListarOsResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    handleApiError(ex, "Ordens de Serviço", empresa.getNomeFantasia(), pagina);
                    return Mono.just(createEmptyOsResponse(pagina));
                }).toFuture();
    }

    public CompletableFuture<OmieListarServicosResponse> listarServicosPorPagina(EmpresaEntity empresa, int pagina) {
        var params = Map.of("nPagina", pagina, "nRegPorPagina", REGISTROS_POR_PAGINA);
        var payload = new OmieRequestPayload<>("ListarCadastroServico", empresa.getAppKey(), empresa.getAppSecret(), List.of(params));
        return webClient.post().uri("/servicos/servico/").bodyValue(payload).retrieve()
                .bodyToMono(OmieListarServicosResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    handleApiError(ex, "Serviços Cadastrados", empresa.getNomeFantasia(), pagina);
                    return Mono.just(createEmptyServicosResponse(pagina));
                }).toFuture();
    }

    public CompletableFuture<OmieListarContratosServicoResponse> listarContratosPorPagina(EmpresaEntity empresa, int pagina) {
        var params = Map.of("pagina", pagina, "registros_por_pagina", REGISTROS_POR_PAGINA, "apenas_importado_api", "N");
        var payload = new OmieRequestPayload<>("ListarContratos", empresa.getAppKey(), empresa.getAppSecret(), List.of(params));
        return webClient.post().uri("/servicos/contrato/").bodyValue(payload).retrieve()
                .bodyToMono(OmieListarContratosServicoResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    handleApiError(ex, "Contratos", empresa.getNomeFantasia(), pagina);
                    return Mono.just(new OmieListarContratosServicoResponse());
                }).toFuture();
    }

    public CompletableFuture<OmieListarTiposFaturamentoResponse> listarTiposFaturamento(EmpresaEntity empresa) {
        var params = Map.of("pagina", 1, "registros_por_pagina", 100);
        var payload = new OmieRequestPayload<>("ListarTipoFatContrato", empresa.getAppKey(), empresa.getAppSecret(), List.of(params));
        return webClient.post().uri("/servicos/contratotpfat/").bodyValue(payload).retrieve()
                .bodyToMono(OmieListarTiposFaturamentoResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    handleApiError(ex, "Tipos de Faturamento", empresa.getNomeFantasia(), 1);
                    return Mono.just(new OmieListarTiposFaturamentoResponse());
                }).toFuture();
    }

    public CompletableFuture<OmieListarCnaeResponse> listarCnaePorPagina(EmpresaEntity empresa, int pagina) {
        var params = Map.of("pagina", pagina, "registros_por_pagina", REGISTROS_POR_PAGINA);
        var payload = new OmieRequestPayload<>("ListarCNAE", empresa.getAppKey(), empresa.getAppSecret(), List.of(params));
        return webClient.post().uri("/produtos/cnae/").bodyValue(payload).retrieve()
                .bodyToMono(OmieListarCnaeResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    handleApiError(ex, "CNAE", empresa.getNomeFantasia(), pagina);
                    return Mono.just(createEmptyCnaeResponse(pagina));
                }).toFuture();
    }
    
    public CompletableFuture<OmieListarNfseResponse> listarNfsePorPagina(EmpresaEntity empresa, int pagina) {
		var params = Map.of("nPagina", pagina, "nRegPorPagina", 100);
		var payload = new OmieRequestPayload<>("ListarNFSEs", empresa.getAppKey(), empresa.getAppSecret(), List.of(params));
		return webClient.post().uri("/servicos/nfse/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarNfseResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "NFS-e", empresa.getNomeFantasia(), pagina);
					return Mono.just(new OmieListarNfseResponse());
				}).toFuture();
	}
    
    private void handleApiError(WebClientResponseException ex, String endpointName, String companyName, int page) {
        OmieFaultResponse fault = ex.getResponseBodyAs(OmieFaultResponse.class);
        if (fault != null && NO_RECORDS_FAULT_CODE.equals(fault.getFaultcode())) {
            logger.info("API OMIE: Não há registros de {} na página {} para a empresa '{}'.", endpointName, page, companyName);
        } else {
            logger.error("API OMIE: Erro {} ao consultar {} para a empresa '{}', página {}. Resposta: {}",
                    ex.getStatusCode(), endpointName, companyName, page, ex.getResponseBodyAsString());
        }
    }

    private OmieListarOsResponse createEmptyOsResponse(int pagina) {
        OmieListarOsResponse emptyResponse = new OmieListarOsResponse();
        emptyResponse.setPagina(pagina);
        emptyResponse.setTotalDePaginas(pagina - 1);
        emptyResponse.setOrdensDeServico(Collections.emptyList());
        return emptyResponse;
    }
    
    private OmieListarServicosResponse createEmptyServicosResponse(int pagina) {
		OmieListarServicosResponse emptyResponse = new OmieListarServicosResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1);
		emptyResponse.setServicos(Collections.emptyList());
		return emptyResponse;
	}

    private OmieListarCnaeResponse createEmptyCnaeResponse(int pagina) {
        OmieListarCnaeResponse emptyResponse = new OmieListarCnaeResponse();
        emptyResponse.setPagina(pagina);
        emptyResponse.setTotalDePaginas(pagina - 1);
        emptyResponse.setCadastros(Collections.emptyList());
        return emptyResponse;
    }
}
/*
package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarCnaeResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarContratosServicoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarNfseResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarServicosResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarTiposFaturamentoResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor // Utilizando injeção via construtor com Lombok
public class OmieApiClientService {

	private static final Logger logger = LoggerFactory.getLogger(OmieApiClientService.class);
	private static final String NO_RECORDS_FAULT_CODE = "SOAP-ENV:Client-5113";
	private static final int REGISTROS_POR_PAGINA = 1000; // Padrão da API para performance

	private final WebClient.Builder webClientBuilder;
	private final OmieProperties omieProperties;
	private WebClient webClient;

	@PostConstruct
	private void init() {
		this.webClient = webClientBuilder.baseUrl(omieProperties.getUrl()).build();
	}

	public CompletableFuture<OmieListarOsResponse> listarOsPorPagina(EmpresaEntity empresa, int pagina) {
		logger.debug("Buscando página {} de OS para a empresa: {}", pagina, empresa.getNomeFantasia());

		var params = new ListarOsParamsDTO(pagina, REGISTROS_POR_PAGINA, "N");
		var payload = new OmieRequestPayload<>("ListarOS", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/servicos/os/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarOsResponse.class).onErrorResume(WebClientResponseException.class, ex -> {
					OmieFaultResponse fault = ex.getResponseBodyAs(OmieFaultResponse.class);
					if (fault != null && NO_RECORDS_FAULT_CODE.equals(fault.getFaultcode())) {
						logger.info("API OMIE informou que não há registros na página {} para a empresa '{}'.", pagina,
								empresa.getNomeFantasia());
						return Mono.just(createEmptyResponse(pagina));
					}
					return Mono.error(ex);
				}).toFuture().exceptionally(ex -> {
					logger.error("Erro final ao processar a chamada para a API OMIE para a empresa '{}', página {}",
							empresa.getNomeFantasia(), pagina, ex);
					throw new RuntimeException("Erro ao consultar Ordens de Serviço no OMIE.", ex);
				});
	}

	public CompletableFuture<OmieListarServicosResponse> listarServicosPorPagina(EmpresaEntity empresa, int pagina) {
		logger.debug("Buscando página {} de Serviços Cadastrados para a empresa: {}", pagina,
				empresa.getNomeFantasia());

		var params = Map.of("nPagina", pagina, "nRegPorPagina", 1000); // Usando um Map simples para os parâmetros
		var payload = new OmieRequestPayload<>("ListarCadastroServico", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/servicos/servico/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarServicosResponse.class)
				// --- BLOCO DE TRATAMENTO DE ERRO ADICIONADO ---
				.onErrorResume(WebClientResponseException.class, ex -> {
					OmieFaultResponse fault = ex.getResponseBodyAs(OmieFaultResponse.class);
					if (fault != null && "SOAP-ENV:Client-5113".equals(fault.getFaultcode())) {
						logger.info(
								"API OMIE informou que não há Serviços Cadastrados na página {} para a empresa '{}'.",
								pagina, empresa.getNomeFantasia());
						// Retorna uma resposta vazia, que é um cenário esperado e não um erro.
						return Mono.just(createEmptyServicosResponse(pagina));
					}
					// Para todos os outros erros, propaga a exceção.
					return Mono.error(ex);
				})
				// --- FIM DO BLOCO ---
				.toFuture().exceptionally(ex -> {
					logger.error("Erro final ao consultar Serviços Cadastrados no OMIE para a empresa '{}', página {}",
							empresa.getNomeFantasia(), pagina, ex);
					throw new RuntimeException("Erro ao consultar Serviços Cadastrados no OMIE.", ex);
				});
	}

	// Adicione este novo método à classe
	public CompletableFuture<OmieListarContratosServicoResponse> listarContratosPorPagina(EmpresaEntity empresa,
			int pagina) {
		logger.debug("Buscando página {} de Contratos para a empresa: {}", pagina, empresa.getNomeFantasia());
		var params = Map.of("pagina", pagina, "registros_por_pagina", 1000, "apenas_importado_api", "N");
		var payload = new OmieRequestPayload<>("ListarContratos", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/servicos/contrato/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarContratosServicoResponse.class)
				.onErrorResume(WebClientResponseException.class, ex -> {
					OmieFaultResponse fault = ex.getResponseBodyAs(OmieFaultResponse.class);
					if (fault != null && "SOAP-ENV:Client-5113".equals(fault.getFaultcode())) {
						logger.info("API OMIE informou que não há Contratos na página {} para a empresa '{}'.", pagina,
								empresa.getNomeFantasia());
						return Mono.just(new OmieListarContratosServicoResponse()); // Retorna objeto vazio
					}
					return Mono.error(ex);
				}).toFuture().exceptionally(ex -> {
					logger.error("Erro final ao consultar Contratos no OMIE para a empresa '{}', página {}",
							empresa.getNomeFantasia(), pagina, ex);
					throw new RuntimeException("Erro ao consultar Contratos no OMIE.", ex);
				});
	}

	public CompletableFuture<OmieListarTiposFaturamentoResponse> listarTiposFaturamento(EmpresaEntity empresa) {
		logger.debug("Buscando Tipos de Faturamento de Contrato para a empresa: {}", empresa.getNomeFantasia());
		var params = Map.of("pagina", 1, "registros_por_pagina", 100); // Geralmente são poucos registros
		var payload = new OmieRequestPayload<>("ListarTipoFatContrato", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/servicos/contratotpfat/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarTiposFaturamentoResponse.class).toFuture().exceptionally(ex -> {
					logger.error("Erro ao consultar Tipos de Faturamento no OMIE para a empresa '{}'",
							empresa.getNomeFantasia(), ex);
					throw new RuntimeException("Erro ao consultar Tipos de Faturamento no OMIE.", ex);
				});
	}

	public CompletableFuture<OmieListarCnaeResponse> listarCnaePorPagina(EmpresaEntity empresa, int pagina) {
		logger.debug("Buscando página {} de CNAE para a empresa: {}", pagina, empresa.getNomeFantasia());
		var params = Map.of("pagina", pagina, "registros_por_pagina", 1000);
		var payload = new OmieRequestPayload<>("ListarCNAE", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));
		return webClient.post().uri("/produtos/cnae/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarCnaeResponse.class).toFuture().exceptionally(ex -> {
					logger.error("Erro ao consultar CNAE no OMIE para a empresa '{}', página {}",
							empresa.getNomeFantasia(), pagina, ex);
					throw new RuntimeException("Erro ao consultar CNAE no OMIE.", ex);
				});
	}

	private OmieListarOsResponse createEmptyResponse(int pagina) {
		OmieListarOsResponse emptyResponse = new OmieListarOsResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1); // Garante que o loop de paginação pare
		emptyResponse.setTotalDeRegistros(0);
		emptyResponse.setRegistrosPorPagina(0);
		emptyResponse.setOrdensDeServico(Collections.emptyList());
		return emptyResponse;
	}

	private OmieListarServicosResponse createEmptyServicosResponse(int pagina) {
		OmieListarServicosResponse emptyResponse = new OmieListarServicosResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1); // Garante que o loop de paginação pare
		emptyResponse.setServicos(Collections.emptyList());
		return emptyResponse;
	}

	public CompletableFuture<OmieListarNfseResponse> listarNfsePorPagina(EmpresaEntity empresa, int pagina) {
		logger.debug("Buscando página {} de NFS-e para a empresa: {}", pagina, empresa.getNomeFantasia());
		var params = Map.of("nPagina", pagina, "nRegPorPagina", 100); // Usando 100 para evitar timeouts
		var payload = new OmieRequestPayload<>("ListarNFSEs", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/servicos/nfse/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarNfseResponse.class).onErrorResume(WebClientResponseException.class, ex -> {
					OmieFaultResponse fault = ex.getResponseBodyAs(OmieFaultResponse.class);
					if (fault != null && "SOAP-ENV:Client-5113".equals(fault.getFaultcode())) {
						logger.info("API OMIE informou que não há NFS-e na página {} para a empresa '{}'.", pagina,
								empresa.getNomeFantasia());
						return Mono.just(new OmieListarNfseResponse()); // Retorna objeto vazio
					}
					return Mono.error(ex);
				}).toFuture().exceptionally(ex -> {
					logger.error("Erro final ao consultar NFS-e no OMIE para a empresa '{}', página {}",
							empresa.getNomeFantasia(), pagina, ex);
					throw new RuntimeException("Erro ao consultar NFS-e no OMIE.", ex);
				});
	}
}
*/
//==========================================================================

//package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.client.WebClientResponseException;
//
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.config.OmieProperties;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.request.ListarOsParamsDTO;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.request.OmieRequestPayload;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieFaultResponse;
//import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;
//
//import jakarta.annotation.PostConstruct;
//import reactor.core.publisher.Mono;
//
//@Service
//public class OmieApiClientService {
//
//	private static final Logger logger = LoggerFactory.getLogger(OmieApiClientService.class);
//	private static final String NO_RECORDS_FAULT_CODE = "SOAP-ENV:Client-5113";
//
//	private final WebClient.Builder webClientBuilder;
//	private final OmieProperties omieProperties;
//	private WebClient webClient;
//
//	// Injeção de dependência via construtor (Best Practice)
//	public OmieApiClientService(WebClient.Builder webClientBuilder, OmieProperties omieProperties) {
//		this.webClientBuilder = webClientBuilder;
//		this.omieProperties = omieProperties;
//	}
//
//	@PostConstruct
//	private void init() {
//		this.webClient = webClientBuilder.baseUrl(omieProperties.getUrl()).build();
//	}
//
//	public CompletableFuture<OmieListarOsResponse> listarPrimeiraPaginaOs(EmpresaEntity empresa) {
//		logger.info("Buscando primeira página de OS para a empresa: {}", empresa.getNomeFantasia());
//
//		var params = new ListarOsParamsDTO(1, 50, "N");
//		var payload = new OmieRequestPayload<>("ListarOS", empresa.getAppKey(), empresa.getAppSecret(),
//				List.of(params));
//
//		return webClient.post().uri("/servicos/os/").bodyValue(payload).retrieve()
//				.bodyToMono(OmieListarOsResponse.class).onErrorResume(WebClientResponseException.class, ex -> {
//					// Tenta desserializar o corpo do erro para o nosso DTO de falha
//					OmieFaultResponse fault = ex.getResponseBodyAs(OmieFaultResponse.class);
//
//					// Verifica se o erro é o específico de "nenhum registro encontrado"
//					if (fault != null && NO_RECORDS_FAULT_CODE.equals(fault.getFaultcode())) {
//						logger.info(
//								"API OMIE informou que não há registros para a empresa '{}'. Retornando resposta vazia.",
//								empresa.getNomeFantasia());
//						// Transforma o erro em uma resposta de sucesso com uma lista vazia
//						return Mono.just(createEmptyResponse());
//					}
//					// Para todos os outros erros, propaga a exceção original
//					return Mono.error(ex);
//				}).toFuture().exceptionally(ex -> {
//					logger.error("Erro final ao processar a chamada para a API OMIE para a empresa '{}'",
//							empresa.getNomeFantasia(), ex);
//					throw new RuntimeException(
//							"Erro ao consultar Ordens de Serviço no OMIE para a empresa: " + empresa.getNomeFantasia(),
//							ex);
//				});
//	}
//
//	/**
//	 * Cria uma instância padrão de OmieListarOsResponse para representar uma
//	 * resposta vazia.
//	 */
//	private OmieListarOsResponse createEmptyResponse() {
//		OmieListarOsResponse emptyResponse = new OmieListarOsResponse();
//		emptyResponse.setPagina(1);
//		emptyResponse.setTotalDePaginas(0);
//		emptyResponse.setTotalDeRegistros(0);
//		emptyResponse.setRegistrosPorPagina(0);
//		emptyResponse.setOrdensDeServico(Collections.emptyList());
//		return emptyResponse;
//	}
//}
//
////package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.service;
////
////import java.util.List;
////import java.util.concurrent.CompletableFuture;
////
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////import org.springframework.web.reactive.function.client.WebClient;
////
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.config.OmieProperties;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.request.ListarOsParamsDTO;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.request.OmieRequestPayload;
////import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;
////
////import jakarta.annotation.PostConstruct;
////
////@Service
////public class OmieApiClientService {
////
////	private static final Logger logger = LoggerFactory.getLogger(OmieApiClientService.class);
////
////	@Autowired
////	private WebClient.Builder webClientBuilder;
////
////	@Autowired
////	private OmieProperties omieProperties;
////
////	private WebClient webClient;
////
////	@PostConstruct
////	private void init() {
////		this.webClient = webClientBuilder.baseUrl(omieProperties.getUrl()).build();
////	}
////
////	/**
////	 * Busca a primeira página de Ordens de Serviço de uma empresa específica.
////	 * 
////	 * @param empresa A entidade Empresa contendo as credenciais (AppKey e
////	 *                AppSecret).
////	 * @return Um CompletableFuture contendo a resposta da API.
////	 */
////	public CompletableFuture<OmieListarOsResponse> listarPrimeiraPaginaOs(EmpresaEntity empresa) {
////		logger.info("Buscando primeira página de OS para a empresa: {}", empresa.getNomeFantasia());
////
////		// 1. Monta os parâmetros da chamada
////		var params = new ListarOsParamsDTO(1, 50, "N");
////
////		// 2. Monta o corpo completo da requisição
////		var payload = new OmieRequestPayload<>("ListarOS", empresa.getAppKey(), empresa.getAppSecret(),
////				List.of(params));
////
////		// 3. Executa a chamada POST
////		return webClient.post().uri("/servicos/os/") // Endpoint para Ordens de Serviço
////				.bodyValue(payload).retrieve().bodyToMono(OmieListarOsResponse.class).toFuture();
////	}
////}
