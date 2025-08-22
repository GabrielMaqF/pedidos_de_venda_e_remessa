package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class OmieWebhookHandlerFactory {
	private final Map<String, OmieWebhookHandler> handlers;

	public OmieWebhookHandlerFactory(List<OmieWebhookHandler> handlerList) {
		this.handlers = handlerList.stream()
				.collect(Collectors.toMap(OmieWebhookHandler::getTopicName, Function.identity()));
	}

	public Optional<OmieWebhookHandler> getHandler(String topicName) {
		return Optional.ofNullable(handlers.get(topicName.toLowerCase()));
	}
}
