package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.OmieWebhookHandler;
import com.fasterxml.jackson.databind.JsonNode;
@Component
public class ClienteHandler implements OmieWebhookHandler{
    private static final Logger logger = LoggerFactory.getLogger(ClienteHandler.class);

	@Override
	public void processar(JsonNode event, EmpresaEntity empresa, String acao) {
        logger.info("Webhook da OMIE recebido para Cliente");
        logger.info("Empresa: {}", empresa);
        logger.info("Acao: {}", acao);
        logger.info("Valor: {}", event);
        
        
        
	}

	@Override
	public String getTopicName() {
		return "clientefornecedor";
	}

}
