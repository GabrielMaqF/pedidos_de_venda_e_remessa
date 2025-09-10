package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.repository.EmpresaRepository;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.OmieWebhookDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.OmieWebhookHandlerFactory;

@Service
public class OmieWebhookService {
	private static final Logger logger = LoggerFactory.getLogger(OmieWebhookService.class);

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private OmieWebhookHandlerFactory handlerFactory;

	@Async
	public void processar(OmieWebhookDTO payload) {
		empresaRepository.findByAppKey(payload.getAppKey()).ifPresentOrElse(empresa -> {
			String[] topicParts = payload.getTopic().split("\\.");

			if (topicParts.length < 2) {
				logger.error("Tópico do webhook ('{}') está em um formato inválido.", payload.getTopic());
				return;
			}

			String tipoEntidade = topicParts[0];
			String acao = topicParts[1];
			
			logger.warn("TIPO: {}", tipoEntidade);

			handlerFactory.getHandler(tipoEntidade).ifPresentOrElse(
					handler -> handler.processar(payload.getEvent(), empresa, acao),
					() -> logger.warn("Nenhum handler encontrado para o tópico: {}", payload.getTopic()));

		}, () -> logger.error("Webhook recebido com appKey '{}' não corresponde a nenhuma empresa.",
				payload.getAppKey()));
	}

}
