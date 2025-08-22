package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.fasterxml.jackson.databind.JsonNode;

public interface OmieWebhookHandler {
	void processar(JsonNode event, EmpresaEntity empresa, String acao);

	String getTopicName();
}
