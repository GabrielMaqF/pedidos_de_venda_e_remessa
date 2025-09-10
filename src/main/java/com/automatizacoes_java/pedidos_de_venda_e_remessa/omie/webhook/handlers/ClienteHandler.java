package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.handlers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente.ClienteOmieDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.OmieWebhookActionStrategy;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.OmieWebhookHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ClienteHandler implements OmieWebhookHandler {
	private static final Logger logger = LoggerFactory.getLogger(ClienteHandler.class);

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final Map<String, OmieWebhookActionStrategy<ClienteOmieDTO>> actionStrategies;

	public ClienteHandler(List<OmieWebhookActionStrategy<ClienteOmieDTO>> strategies) {
		this.actionStrategies = strategies.stream()
				.collect(Collectors.toMap(OmieWebhookActionStrategy::getAcao, Function.identity()));
	}

	@Override
	public void processar(JsonNode event, EmpresaEntity empresa, String acao) {
		logger.info("Webhook da OMIE recebido para Cliente");
		logger.info("Empresa: {}", empresa);
		logger.info("Acao: {}", acao);
		logger.info("Valor: {}", event);

		try {
			ClienteOmieDTO dto = objectMapper.treeToValue(event, ClienteOmieDTO.class);

			Optional.ofNullable(actionStrategies.get(acao.toLowerCase())).ifPresentOrElse(
					strategy -> strategy.processar(dto, empresa),
					() -> logger.warn("Nenhuma estratégia de ação encontrada para '{}' em Ordem de Serviço.", acao));

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public String getTopicName() {
		return "clientefornecedor";
	}

}
