package com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.excel.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.RelatorioService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.config.GraphExcelProperties;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.microsoft.token.TokenMicrosoftGraph;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelOrdemServicoService {
	private static final Logger logger = LoggerFactory.getLogger(ExcelOrdemServicoService.class);

	private final GraphExcelProperties prop;
	private final TokenMicrosoftGraph token;
	private final RelatorioService relatorioService;

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final WebClient.Builder webClientBuilder;

	private String getBaseUrlOS() { // Monta a URL base para o workbook/tabela.
		return "https://graph.microsoft.com/v1.0/sites/" + prop.getSiteShP() + "/drive/items/"
				+ prop.getArquivoRelatorio() + "/workbook/tables('" + prop.getTabelaRelatorioOS() + "')";
	}

	private String getUrlHeaders(String url) { // URL do headerRowRange da tabela
		return url + "/headerRowRange";
	}

	private String getUrlDeleteAllItems(String url) { // URL para deletar todos os registros da tabela
		return url + "/dataBodyRange/delete";
	}

	private String getUrlAddAllItems(String url) { // URL adicionar todos os registros da tabela
		return url + "/rows/add";
	}

	/**
	 * Consulta o BDA e devolve o relatório como List<Map<coluna, valor>>.
	 */
	private List<Map<String, String>> getRelatorioOrdemServico() {
		logger.info("----- Buscando Relatorio OS");
		return relatorioService.listarTudoRelatorioOrdemServico();
	}

	public void garantirTabelaComCabecalho() {
		try {
			String url = getBaseUrlOS(); // .../workbook/tables('TabelaRelatorioOS')
			String accessToken = token.getToken().get();

			WebClient webClient = webClientBuilder.build();

			// PATCH { "showHeaders": true }
			Map<String, Object> body = Map.of("showHeaders", true);

			String response = webClient.patch().uri(url).headers(h -> {
				h.setBearerAuth(accessToken);
				h.setContentType(MediaType.APPLICATION_JSON);
				h.setAccept(List.of(MediaType.APPLICATION_JSON));
			}).bodyValue(body).retrieve()
					.onStatus(status -> !status.is2xxSuccessful(),
							clientResponse -> clientResponse.bodyToMono(String.class)
									.map(resp -> new RuntimeException("Erro ao atualizar showHeaders da tabela: "
											+ clientResponse.statusCode().value() + " - " + resp)))
					.bodyToMono(String.class).block();

			logger.info("Tabela configurada com showHeaders=true. Resposta: {}", response);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao garantir cabeçalho na tabela do Excel", e);
		}
	}

	/**
	 * Busca os headers do Excel (linha de cabeçalho da tabela) via Microsoft Graph.
	 * Retorna uma lista de nomes de colunas na ORDEM exata em que estão no Excel.
	 */
	public List<String> buscarHeadersDoExcelOS() {
		logger.info("----- Buscando Headers OS");

		try {
			String url = getUrlHeaders(getBaseUrlOS());
			String accessToken = token.getToken().get(); // mantive como você usou

			WebClient webClient = webClientBuilder.build();

			// Chama o Graph usando WebClient e pega o body como String
			String responseBody = webClient.get().uri(url).headers(h -> {
				h.setBearerAuth(accessToken);
				h.setAccept(List.of(MediaType.APPLICATION_JSON));
			}).retrieve().onStatus(status -> !status.is2xxSuccessful(), clientResponse -> clientResponse
					.bodyToMono(String.class)
					.map(body -> new RuntimeException(
							"Erro ao buscar headers do Excel: " + clientResponse.statusCode().value() + " - " + body)))
					.bodyToMono(String.class).block(); // aqui você mantém o fluxo síncrono

			logger.info(responseBody);

			JsonNode root = objectMapper.readTree(responseBody);
			JsonNode valuesNode = root.path("values");

			if (!valuesNode.isArray() || valuesNode.isEmpty()) {
				throw new RuntimeException("Não foi possível encontrar a linha de header no Excel.");
			}

			// primeira (e única) linha de header
			JsonNode headerRow = valuesNode.get(0);

			List<String> headersList = new ArrayList<>();
			for (JsonNode cell : headerRow) {
				headersList.add(cell.asText());
			}

			return headersList;

		} catch (Exception e) {
			throw new RuntimeException("Falha ao consultar headers do Excel", e);
		}
	}

	/**
	 * Usa os headers do Excel para ordenar os dados do BDA na mesma sequência.
	 */
	public List<List<String>> montarRelatorioNaOrdemDoExcel() {
		// 1. Headers do Excel
		List<String> excelHeaders = buscarHeadersDoExcelOS();

		logger.info("----- Montando Relatorio OS");

		// 2. Relatório vindo do BDA
		List<Map<String, String>> relatorio = getRelatorioOrdemServico();

		List<List<String>> linhasOrdenadas = new LinkedList<>();

		for (Map<String, String> linhaBDA : relatorio) {
			List<String> linhaOrdenada = new ArrayList<>(excelHeaders.size());

			for (String header : excelHeaders) {
				// pega o valor da coluna com o mesmo nome do header
				String valor = linhaBDA.get(header);
				linhaOrdenada.add(valor); // pode ser null, o Graph aceita null/vazio
			}

			linhasOrdenadas.add(linhaOrdenada);
		}

		return linhasOrdenadas;
	}

	/**
	 * 1) Deleta todas as linhas de dados da tabela do Excel (mantém o cabeçalho).
	 */
	private void deletarTodosRegistrosDaTabelaOS() {
		try {
			String url = getUrlDeleteAllItems(getBaseUrlOS());
			String accessToken = token.getToken().get();

			WebClient webClient = webClientBuilder.build();

			// body esperado pelo Graph: { "shift": "Up" }
			Map<String, String> body = Map.of("shift", "Up");

			String responseBody = webClient.post().uri(url).headers(h -> {
				h.setBearerAuth(accessToken);
				h.setContentType(MediaType.APPLICATION_JSON);
				h.setAccept(List.of(MediaType.APPLICATION_JSON));
			}).bodyValue(body).retrieve()
					.onStatus(status -> !status.is2xxSuccessful(),
							clientResponse -> clientResponse.bodyToMono(String.class)
									.map(resp -> new RuntimeException("Erro ao deletar registros da tabela do Excel: "
											+ clientResponse.statusCode().value() + " - " + resp)))
					.bodyToMono(String.class).block();

			logger.info("Registros da tabela do Excel deletados com sucesso. Resposta: {}", responseBody);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao deletar registros da tabela do Excel", e);
		}
	}

	/**
	 * 2) Insere todos os registros (já ordenados) na tabela do Excel.
	 * `linhasOrdenadas` deve ser o mesmo formato da tabela (mesmo número de
	 * colunas).
	 */
	private void inserirRegistrosNaTabelaOS(List<List<String>> linhasOrdenadas) {
		if (linhasOrdenadas == null || linhasOrdenadas.isEmpty()) {
			logger.warn("Não há linhas para inserir na tabela do Excel.");
			return;
		}

		logger.info("----- Inserindo registros OS Excel");

		try {
			String url = getUrlAddAllItems(getBaseUrlOS());
			String accessToken = token.getToken().get();

			WebClient webClient = webClientBuilder.build();

			// Graph espera: { "values": [ [linha1col1, linha1col2, ...], [linha2col1, ...],
			// ... ] }
			Map<String, Object> body = Map.of("values", linhasOrdenadas);

			String responseBody = webClient.post().uri(url).headers(h -> {
				h.setBearerAuth(accessToken);
				h.setContentType(MediaType.APPLICATION_JSON);
				h.setAccept(List.of(MediaType.APPLICATION_JSON));
			}).bodyValue(body).retrieve()
					.onStatus(status -> !status.is2xxSuccessful(),
							clientResponse -> clientResponse.bodyToMono(String.class)
									.map(resp -> new RuntimeException("Erro ao inserir registros na tabela do Excel: "
											+ clientResponse.statusCode().value() + " - " + resp)))
					.bodyToMono(String.class).block();

			logger.info("Registros inseridos com sucesso na tabela do Excel. Resposta: {}", responseBody);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao inserir registros na tabela do Excel", e);
		}
	}

	/**
	 * MÉTODO PRINCIPAL:
	 *
	 * - lê o cabeçalho do Excel - monta o relatório na mesma ordem - apaga os
	 * registros atuais da tabela - insere os novos registros
	 */
	public void atualizarTabelaOSComRelatorio() {
		logger.info("Iniciando atualização da tabela do Excel com relatório de Ordem de Serviço...");

		// 0) Garante que a tabela tem cabeçalho
		garantirTabelaComCabecalho();

		// Monta as linhas já na ordem do Excel
		List<List<String>> linhasOrdenadas = montarRelatorioNaOrdemDoExcel();

		// Limpa tabela
		deletarTodosRegistrosDaTabelaOS();

		// Insere registros novos
		inserirRegistrosNaTabelaOS(linhasOrdenadas);

		logger.info("Tabela do Excel atualizada com sucesso!");

	}
}
