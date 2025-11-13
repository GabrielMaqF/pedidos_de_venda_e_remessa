
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
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarCategoriaResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarClienteFornecedorResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarCnaeResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarContaCorrenteResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarContratosServicoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarDepartamentoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarLocalEstoqueResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarNfseResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarOsResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarProdutoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarProjetoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarServicoCadastradoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarTiposFaturamentoResponse;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.response.OmieListarVendedorResponse;

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
	
	public CompletableFuture<OmieListarLocalEstoqueResponse> listarLocalEstoquePorPagina(EmpresaEntity empresa, int pagina) {
		var params = Map.of("nPagina", pagina, "nRegPorPagina", 100);
		var payload = new OmieRequestPayload<>("ListarLocaisEstoque", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));
		
		return webClient.post().uri("/estoque/local/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarLocalEstoqueResponse.class).onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Locais Estoque", empresa.getNomeFantasia(), pagina);
					return Mono.just(createEmptyLocalEstoqueResponse(pagina));
				}).toFuture();
	}

	public CompletableFuture<OmieListarProdutoResponse> listarProdutoPorPagina(EmpresaEntity empresa, int pagina) {
		var params = Map.of("pagina", pagina, "registros_por_pagina", REGISTROS_POR_PAGINA, "apenas_importado_api", "N",
				"filtrar_apenas_omiepdv", "N");
		var payload = new OmieRequestPayload<>("ListarProdutos", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/geral/produtos/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarProdutoResponse.class).onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Produto", empresa.getNomeFantasia(), pagina);
					return Mono.just(createEmptyProdutoResponse(pagina));
				}).toFuture();
	}

	public CompletableFuture<OmieListarDepartamentoResponse> listarDepartamentoPorPagina(EmpresaEntity empresa,
			int pagina) {
		var params = Map.of("pagina", pagina, "registros_por_pagina", REGISTROS_POR_PAGINA, "apenas_importado_api",
				"N");
		var payload = new OmieRequestPayload<>("ListarDepartamentos", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/geral/departamentos/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarDepartamentoResponse.class)
				.onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Departamento", empresa.getNomeFantasia(), pagina);
					return Mono.just(createEmptyDepartamentoResponse(pagina));
				}).toFuture();
	}

	public CompletableFuture<OmieListarCategoriaResponse> listarCategoriaPorPagina(EmpresaEntity empresa, int pagina) {
		var params = Map.of("pagina", pagina, "registros_por_pagina", REGISTROS_POR_PAGINA, "apenas_importado_api",
				"N");
		var payload = new OmieRequestPayload<>("ListarCategorias", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/geral/categorias/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarCategoriaResponse.class).onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Categoria", empresa.getNomeFantasia(), pagina);
					return Mono.just(createEmptyCategoriaResponse(pagina));
				}).toFuture();
	}

	public CompletableFuture<OmieListarContaCorrenteResponse> listarContaCorrentePorPagina(EmpresaEntity empresa,
			int pagina) {
		var params = Map.of("pagina", pagina, "registros_por_pagina", REGISTROS_POR_PAGINA, "apenas_importado_api",
				"N");
		var payload = new OmieRequestPayload<>("ListarResumoContasCorrentes", empresa.getAppKey(),
				empresa.getAppSecret(), List.of(params));

		return webClient.post().uri("/geral/contacorrente/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarContaCorrenteResponse.class)
				.onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Conta Corrente", empresa.getNomeFantasia(), pagina);
					return Mono.just(createEmptyContaCorrenteResponse(pagina));
				}).toFuture();
	}

	public CompletableFuture<OmieListarVendedorResponse> listarVendedorPorPagina(EmpresaEntity empresa, int pagina) {
		var params = Map.of("pagina", pagina, "registros_por_pagina", REGISTROS_POR_PAGINA, "apenas_importado_api",
				"N");
		var payload = new OmieRequestPayload<>("ListarVendedores", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/geral/vendedores/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarVendedorResponse.class).onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Vendedor", empresa.getNomeFantasia(), pagina);
					return Mono.just(createEmptyVendedorResponse(pagina));
				}).toFuture();
	}

	public CompletableFuture<OmieListarProjetoResponse> listarProjetoPorPagina(EmpresaEntity empresa, int pagina) {
		var params = Map.of("pagina", pagina, "registros_por_pagina", REGISTROS_POR_PAGINA, "apenas_importado_api",
				"N");
		var payload = new OmieRequestPayload<>("ListarProjetos", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/geral/projetos/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarProjetoResponse.class).onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Projeto", empresa.getNomeFantasia(), pagina);
					return Mono.just(createEmptyProjetoResponse(pagina));
				}).toFuture();
	}

	public CompletableFuture<OmieListarClienteFornecedorResponse> listarClienteFornecedorPorPagina(
			EmpresaEntity empresa, int pagina) {
		var params = Map.of("pagina", pagina, "registros_por_pagina", REGISTROS_POR_PAGINA, "apenas_importado_api",
				"N");
		var payload = new OmieRequestPayload<>("ListarClientes", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));

		return webClient.post().uri("/geral/clientes/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarClienteFornecedorResponse.class)
				.onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Cliente Fornecedor", empresa.getNomeFantasia(), pagina);
					return Mono.just(createEmptyClienteFornecedorResponse(pagina));
				}).toFuture();
	}

	public CompletableFuture<OmieListarOsResponse> listarOsPorPagina(EmpresaEntity empresa, int pagina) {
		var params = new ListarOsParamsDTO(pagina, REGISTROS_POR_PAGINA, "N");
		var payload = new OmieRequestPayload<>("ListarOS", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));
		return webClient.post().uri("/servicos/os/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarOsResponse.class).doOnNext(res -> {
					if (res.getOrdensDeServico() != null && !res.getOrdensDeServico().isEmpty()) {
						var primeira = res.getOrdensDeServico().get(0);
						int qtdServicos = primeira.getServicosPrestados() == null ? 0
								: primeira.getServicosPrestados().size();
						logger.info("Empresa {} | página {} | OS retornadas: {} | ServicosPrestados da 1ª OS: {}",
								empresa.getNomeFantasia(), pagina, res.getOrdensDeServico().size(), qtdServicos);
					}
				}).onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Ordens de Serviço", empresa.getNomeFantasia(), pagina);
					return Mono.just(createEmptyOsResponse(pagina));
				}).toFuture();
	}

	public CompletableFuture<OmieListarServicoCadastradoResponse> listarServicoCadastradoPorPagina(
			EmpresaEntity empresa, int pagina) {
		var params = Map.of("nPagina", pagina, "nRegPorPagina", REGISTROS_POR_PAGINA);
		var payload = new OmieRequestPayload<>("ListarCadastroServico", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));
		return webClient.post().uri("/servicos/servico/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarServicoCadastradoResponse.class)
				.onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Serviços Cadastrados", empresa.getNomeFantasia(), pagina);
					return Mono.just(createEmptyServicoCadastradoResponse(pagina));
				}).toFuture();
	}

	public CompletableFuture<OmieListarContratosServicoResponse> listarContratoServicoPorPagina(EmpresaEntity empresa,
			int pagina) {
		var params = Map.of("pagina", pagina, "registros_por_pagina", REGISTROS_POR_PAGINA, "apenas_importado_api",
				"N");
		var payload = new OmieRequestPayload<>("ListarContratos", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));
		return webClient.post().uri("/servicos/contrato/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarContratosServicoResponse.class)
				.onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Contratos", empresa.getNomeFantasia(), pagina);
					return Mono.just(new OmieListarContratosServicoResponse());
				}).toFuture();
	}

	public CompletableFuture<OmieListarTiposFaturamentoResponse> listarTiposFaturamento(EmpresaEntity empresa) {
		var params = Map.of("pagina", 1, "registros_por_pagina", 100);
		var payload = new OmieRequestPayload<>("ListarTipoFatContrato", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));
		return webClient.post().uri("/servicos/contratotpfat/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarTiposFaturamentoResponse.class)
				.onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "Tipos de Faturamento", empresa.getNomeFantasia(), 1);
					return Mono.just(new OmieListarTiposFaturamentoResponse());
				}).toFuture();
	}

	public CompletableFuture<OmieListarCnaeResponse> listarCnaePorPagina(EmpresaEntity empresa, int pagina) {
		var params = Map.of("pagina", pagina, "registros_por_pagina", REGISTROS_POR_PAGINA);
		var payload = new OmieRequestPayload<>("ListarCNAE", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));
		return webClient.post().uri("/produtos/cnae/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarCnaeResponse.class).onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "CNAE", empresa.getNomeFantasia(), pagina);
					return Mono.just(createEmptyCnaeResponse(pagina));
				}).toFuture();
	}

	public CompletableFuture<OmieListarNfseResponse> listarNfsePorPagina(EmpresaEntity empresa, int pagina) {
		var params = Map.of("nPagina", pagina, "nRegPorPagina", 100);
		var payload = new OmieRequestPayload<>("ListarNFSEs", empresa.getAppKey(), empresa.getAppSecret(),
				List.of(params));
		return webClient.post().uri("/servicos/nfse/").bodyValue(payload).retrieve()
				.bodyToMono(OmieListarNfseResponse.class).onErrorResume(WebClientResponseException.class, ex -> {
					handleApiError(ex, "NFS-e", empresa.getNomeFantasia(), pagina);
					return Mono.just(new OmieListarNfseResponse());
				}).toFuture();
	}

	private void handleApiError(WebClientResponseException ex, String endpointName, String companyName, int page) {
		OmieFaultResponse fault = ex.getResponseBodyAs(OmieFaultResponse.class);
		if (fault != null && NO_RECORDS_FAULT_CODE.equals(fault.getFaultcode())) {
			logger.info("API OMIE: Não há registros de {} na página {} para a empresa '{}'.", endpointName, page,
					companyName);
		} else {
			logger.error("API OMIE: Erro {} ao consultar {} para a empresa '{}', página {}. Resposta: {}",
					ex.getStatusCode(), endpointName, companyName, page, ex.getResponseBodyAsString());
		}
	}
	
	private OmieListarLocalEstoqueResponse createEmptyLocalEstoqueResponse(int pagina) {
		OmieListarLocalEstoqueResponse emptyResponse = new OmieListarLocalEstoqueResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1);
		emptyResponse.setLocaisEncontrados(Collections.emptyList());
		return emptyResponse;
	}

	private OmieListarProdutoResponse createEmptyProdutoResponse(int pagina) {
		OmieListarProdutoResponse emptyResponse = new OmieListarProdutoResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1);
		emptyResponse.setProdutoServicoCadastro(Collections.emptyList());
		return emptyResponse;
	}

	private OmieListarDepartamentoResponse createEmptyDepartamentoResponse(int pagina) {
		OmieListarDepartamentoResponse emptyResponse = new OmieListarDepartamentoResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1);
		emptyResponse.setDepartamentos(Collections.emptyList());
		return emptyResponse;
	}

	private OmieListarCategoriaResponse createEmptyCategoriaResponse(int pagina) {
		OmieListarCategoriaResponse emptyResponse = new OmieListarCategoriaResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1);
		emptyResponse.setCategoriaCadastro(Collections.emptyList());
		return emptyResponse;
	}

	private OmieListarContaCorrenteResponse createEmptyContaCorrenteResponse(int pagina) {
		OmieListarContaCorrenteResponse emptyResponse = new OmieListarContaCorrenteResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1);
		emptyResponse.setContaCorrenteLista(Collections.emptyList());
		return emptyResponse;
	}

	private OmieListarVendedorResponse createEmptyVendedorResponse(int pagina) {
		OmieListarVendedorResponse emptyResponse = new OmieListarVendedorResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1);
		emptyResponse.setCadastro(Collections.emptyList());
		return emptyResponse;
	}

	private OmieListarProjetoResponse createEmptyProjetoResponse(int pagina) {
		OmieListarProjetoResponse emptyResponse = new OmieListarProjetoResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1);
		emptyResponse.setCadastro(Collections.emptyList());
		return emptyResponse;
	}

	private OmieListarClienteFornecedorResponse createEmptyClienteFornecedorResponse(int pagina) {
		OmieListarClienteFornecedorResponse emptyResponse = new OmieListarClienteFornecedorResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1);
		emptyResponse.setClientesCadastrado(Collections.emptyList());
		return emptyResponse;
	}

	private OmieListarOsResponse createEmptyOsResponse(int pagina) {
		OmieListarOsResponse emptyResponse = new OmieListarOsResponse();
		emptyResponse.setPagina(pagina);
		emptyResponse.setTotalDePaginas(pagina - 1);
		emptyResponse.setOrdensDeServico(Collections.emptyList());
		return emptyResponse;
	}

	private OmieListarServicoCadastradoResponse createEmptyServicoCadastradoResponse(int pagina) {
		OmieListarServicoCadastradoResponse emptyResponse = new OmieListarServicoCadastradoResponse();
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