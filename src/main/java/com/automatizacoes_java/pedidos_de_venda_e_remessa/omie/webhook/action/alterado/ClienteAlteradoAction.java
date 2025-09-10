package com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.action.alterado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.entidade.EmpresaEntity;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.domain.service.ClienteFornecedorService;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.dto.cliente.ClienteOmieDTO;
import com.automatizacoes_java.pedidos_de_venda_e_remessa.omie.webhook.OmieWebhookActionStrategy;

@Component
public class ClienteAlteradoAction implements OmieWebhookActionStrategy<ClienteOmieDTO> {

	@Autowired
	private ClienteFornecedorService service;

	@Override
	public void processar(ClienteOmieDTO dto, EmpresaEntity empresa) {
		service.criarOuAtualizarPorOmie(dto, empresa);
	}

	@Override
	public String getAcao() {
		return "alterado";
	}

}
