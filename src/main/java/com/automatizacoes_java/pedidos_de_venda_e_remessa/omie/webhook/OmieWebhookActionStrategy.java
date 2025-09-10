package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;

public interface OmieWebhookActionStrategy<T> {

	void processar(T dto, EmpresaEntity empresa);

	String getAcao();
}
